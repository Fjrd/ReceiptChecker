package com.github.dimantchick.receiptchecker;

import com.alibaba.fastjson.JSON;
import com.github.dimantchick.receiptchecker.exceptions.*;
import com.github.dimantchick.receiptchecker.receipt.Item;
import com.github.dimantchick.receiptchecker.receipt.Receipt;
import com.github.dimantchick.receiptchecker.receipt.RootObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Универсальный API для получения информации по чекам из ФНС
 * в соответствии с неоффициальной спецификацией https://habr.com/ru/post/358966/
 *
 * @version 1.0
 * @autor Dimantchick
 */

public class ReceiptChecker {
    private static final String REGISTER_URL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/signup";
    private static final String LOGIN_URL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/login";
    private static final String RESTORE_PASS_URL = "https://proverkacheka.nalog.ru:9999/v1/mobile/users/restore";
    private static final String RECEIPT_CHECK_URL = "https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*/fss/%s/operations/%s/tickets/%s?fiscalSign=%s&date=%s&sum=%s";
    private static final String RECEIPT_RECEIVE_URL = "https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/%s/tickets/%s?fiscalSign=%s&sendToEmail=no";
    private static final int CONNECTION_TIMEOUT = 5000;
    private User user;

    /**
     * Конструктор по-умолчанию
     */
    public ReceiptChecker() {
    }

    /**
     * Конструктор - с созданым пользователем
     *
     * @param user - пользователь
     */
    public ReceiptChecker(User user) {
        this.user = user;
    }

    /**
     * Регистрация пользователя в ФНС
     * В случае успеха на телефон придет смс с паролем
     *
     * @param email - почта для регистрации
     * @param name  - любое имя пользователя
     * @param phone - телефон в формате +79*********
     * @throws IOException                   - ошибка ввода-вывода.
     * @throws UserAlreadyExistsException    - номер телефона уже зарегистрирован. Используйте восстановление пароля.
     * @throws IncorrectPhoneNumberException - Некорректный формат номера телефона.
     * @throws IncorrectEmailFormatException - Некорректный формат email.
     * @throws UnknownErrorException         - Недокументированная ошибка. Если есть исключение, то оно сохранено в объекте Answer
     */
    public void register(String email, String name, String phone) throws IOException, UserAlreadyExistsException, IncorrectPhoneNumberException, IncorrectEmailFormatException, UnknownErrorException {
        final HttpURLConnection con = createConnection(new URL(REGISTER_URL));
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        User user = new User(email, name, phone);
        String jsonUserString = JSON.toJSONString(user);
        OutputStream os = con.getOutputStream();
        os.write(jsonUserString.getBytes(StandardCharsets.UTF_8));
        os.close();
        Answer answer = makeRequest(con);
        switch (answer.getHttpCode()) {
            case 204: // Регистрация успешна. Смс с паролем должно быть выслано.
                return;
            case 409: // Пользователь уже существует. Вспользуйтесь восстановлением пароля.
                throw new UserAlreadyExistsException(answer);
            case 500: // Некорректный формат номера телефона.
                throw new IncorrectPhoneNumberException(answer);
            case 400: // Некорректный формат email.
                throw new IncorrectEmailFormatException(answer);
        }
        throw new UnknownErrorException(answer);
    }

    /**
     * Авторизация пользователя в ФНС
     * В случае успеха устанавливает поле user соответствии с данными пользователя
     *
     * @param phone    - телефон в формате +79*********
     * @param password - пароль от ФНС, пришедший в смс
     * @throws IOException                 - ошибка ввода-вывода.
     * @throws IncorrectPhonePassException - неверный телефон/пароль.
     * @throws UnknownErrorException       - Недокументированная ошибка. Если есть исключение, то оно сохранено в объекте Answer
     */
    public void login(String phone, String password) throws IOException, IncorrectPhonePassException, UnknownErrorException {
        if (phone == null || password == null || phone.equals("") || password.equals("")) {
            throw new IncorrectPhonePassException();
        }
        final HttpURLConnection con = createConnection(new URL(LOGIN_URL));
        String encoded = Base64.getEncoder().encodeToString((phone + ":" + password).getBytes(StandardCharsets.UTF_8));  //Java 8
        con.setRequestProperty("Authorization", "Basic " + encoded);
        Answer answer = makeRequest(con);
        switch (answer.getHttpCode()) {
            case 200: // Логин успешен.
                EmailNamePair enp = JSON.parseObject(answer.getAnswer(), EmailNamePair.class);
                user = new User(enp.email, enp.name, phone, password);
                return;
            case 403: // Неудачная авторизация
                throw new IncorrectPhonePassException(answer);
        }
        throw new UnknownErrorException(answer);
    }

    /**
     * Восстановление пароля от ФНС
     *
     * @param phone - телефон в формате +79*********
     * @throws IOException           - ошибка ввода-вывода.
     * @throws UserNotFoundException - телефон не зарегистрирован в ФНС. Выполните регистрацию.
     * @throws UnknownErrorException - Недокументированная ошибка. Если есть исключение, то оно сохранено в объекте Answer
     */
    public boolean restorePass(String phone) throws IOException, UserNotFoundException, UnknownErrorException {
        final HttpURLConnection con = createConnection(new URL(RESTORE_PASS_URL));
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        User user = new User(phone);
        String jsonUserString = JSON.toJSONString(user);
        OutputStream os = con.getOutputStream();
        os.write(jsonUserString.getBytes(StandardCharsets.UTF_8));
        os.close();
        Answer answer = makeRequest(con);
        switch (answer.getHttpCode()) {
            case 204:
                return true;
            case 404:
                throw new UserNotFoundException(answer);
        }
        throw new UnknownErrorException(answer);
    }

    /**
     * Проверка чека на существование
     *
     * @param receiptRequest - данные чека для запроса.
     * @throws IOException              - ошибка ввода-вывода.
     * @throws MissingDataException     - неправильные и/или отсутствуют параметры чека (дата, сумма и т.д.)
     * @throws ReceiptNotFoundException - чек не найден
     * @throws UnknownErrorException    - Недокументированная ошибка. Если есть исключение, то оно сохранено в объекте Answer
     */
    public boolean isReceiptValid(ReceiptRequest receiptRequest) throws MissingDataException, UnknownErrorException, ReceiptNotFoundException, IOException {
        String urlString = String.format(RECEIPT_CHECK_URL, receiptRequest.getFn(), receiptRequest.getType(), receiptRequest.getFd(), receiptRequest.getFpd(), receiptRequest.getDate(), receiptRequest.getSum());
        final HttpURLConnection con = createConnection(new URL(urlString));
        Answer answer = makeRequest(con);
        switch (answer.getHttpCode()) {
            case 204:
                return true;
            case 400:
                throw new MissingDataException(answer);
            case 406:
                throw new ReceiptNotFoundException(answer);
        }
        throw new UnknownErrorException(answer);
    }

    /**
     * Получение чека
     *
     * @param receiptRequest - данные чека для запроса.
     * @see ReceiptChecker#receiveReceipt(ReceiptRequest receiptRequest, User user, boolean repeat);
     */
    public void receiveReceipt(ReceiptRequest receiptRequest) throws UserNotFoundException, ReceiptNotFoundException, UnknownErrorException, IOException, NotAuthorizedException {
        receiveReceipt(receiptRequest, user, true);
    }

    /**
     * Получение чека
     *
     * @param receiptRequest - данные чека для запроса.
     * @param user           - пользователь, от имени которого делать запрос.
     * @see ReceiptChecker#receiveReceipt(ReceiptRequest receiptRequest, User user, boolean repeat);
     */
    public void receiveReceipt(ReceiptRequest receiptRequest, User user) throws UserNotFoundException, ReceiptNotFoundException, UnknownErrorException, IOException, NotAuthorizedException {
        receiveReceipt(receiptRequest, user, true);
    }

    /**
     * Получение чека
     *
     * @param receiptRequest - данные чека для запроса.
     * @param user           - пользователь, от имени которого делать запрос.
     * @param repeat         - делать запрос, в случае кода 202 от сервера.
     * @throws IOException              - ошибка ввода-вывода.
     * @throws UserNotFoundException    - неудачная авторизация
     * @throws NotAuthorizedException   - user == null
     * @throws ReceiptNotFoundException - чек не найден
     * @throws UnknownErrorException    - Недокументированная ошибка. Если есть исключение, то оно сохранено в объекте Answer
     */
    private void receiveReceipt(ReceiptRequest receiptRequest, User user, boolean repeat) throws IOException, UnknownErrorException, ReceiptNotFoundException, UserNotFoundException, NotAuthorizedException {
        if (user == null) {
            throw new NotAuthorizedException();
        }
        String urlString = String.format(RECEIPT_RECEIVE_URL, receiptRequest.getFn(), receiptRequest.getFd(), receiptRequest.getFpd());
        final HttpURLConnection con = createConnection(new URL(urlString));
        String encoded = Base64.getEncoder().encodeToString((user.getPhone() + ":" + user.getPassword()).getBytes(StandardCharsets.UTF_8));  //Java 8
        con.setRequestProperty("Authorization", "Basic " + encoded);
        Answer answer = makeRequest(con);
        switch (answer.getHttpCode()) {
            case 200: // Чек получен.
                receiptRequest.setJsonData(answer.getAnswer());
                return;
            case 202: // Не произведена предварительная проверка чека. Повторно запрашиваем.
                if (repeat) {
                    // Ожидаем полсекунды (иначе сервер снова не выдаст чек)
                    try {
                        Thread.sleep(500);
                        // Повторный запрос
                        receiveReceipt(receiptRequest, user, false);
                        return;
                    } catch (InterruptedException e) {
                    }
                    answer.setErrorMessage("Не удалось отправить повторный запрос.");
                    throw new UnknownErrorException(answer);
                }
                // Если повторно не получили чек - неизвестная ошибка.
                answer.setErrorMessage("Повторный запрос получил 202 код");
                throw new UnknownErrorException(answer);
            case 403: // Неудачная авторизация
                throw new UserNotFoundException(answer);
            case 406: // Чек не найден.
                throw new ReceiptNotFoundException(answer);
        }
        answer.setErrorMessage("Неизвестная ошибка при получении чека.");
        throw new UnknownErrorException(answer);
    }

    /**
     * Создание соединения
     *
     * @param url - url для запроса
     * @throws IOException - ошибка ввода-вывода.
     */
    private HttpURLConnection createConnection(URL url) throws IOException {
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        addHeaders(con);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);
        return con;
    }

    /**
     * Выполнить запрос
     *
     * @param con - соединение с url
     * @return возвращает ответ сервера
     */
    private Answer makeRequest(HttpURLConnection con) {
        con.disconnect();
        int responseCode = -1;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            return new Answer("", responseCode, e);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return new Answer(sb.toString(), responseCode, null);
        } catch (IOException e) {
            return new Answer("", responseCode, e);
        }
    }

    /**
     * Добавляет соединению стандартные заголовки
     *
     * @param con - соединение с url
     */
    private void addHeaders(HttpURLConnection con) {
        con.setRequestProperty("Device-OS", "Android 6.0");
        con.setRequestProperty("Device-Id", "");
        con.setRequestProperty("Version", "2");
        con.setRequestProperty("Accept-Encoding", "gzip");
        con.setRequestProperty("ClientVersion", "1.4.4.4");
    }

    /**
     * Геттер для пользователя
     *
     * @return возвращает пользователя
     */
    public User getUser() {
        return user;
    }

    /**
     * Сеттер для пользователя
     *
     * @param user - пользователь
     */
    public void setUser(User user) {
        this.user = user;
    }

    public static void main(String[] args) throws IOException, UserNotFoundException, IncorrectPhonePassException, MissingDataException {
        ReceiptChecker receiptChecker = new ReceiptChecker();
        try {
            receiptChecker.login("+7999#######", "######");                                                                     //"2020-05-09T20:39:00"
            ReceiptRequest receiptRequest = new ReceiptRequest("9251440300018806", "1", "58197", "0050408976", "20200509T203900", "452300");
            System.out.println(receiptChecker.isReceiptValid(receiptRequest));
            receiptChecker.receiveReceipt(receiptRequest);
            System.out.println(receiptRequest.getJsonData());
            Receipt receipt = receiptRequest.getReceipt();
            for (Item item : receipt.getItems()) {
                System.out.println(item.getName() + "   " + item.getPrice() + " * " + item.getQuantity() + " = " + item.getSum());
            }
        } catch (UnknownErrorException e) {
            System.out.println(e.getAnswer().getHttpCode());
            e.printStackTrace();
            System.out.println(e.getAnswer().getAnswer());
            System.out.println(e.getAnswer().getErrorMessage());
        } catch (ReceiptNotFoundException e) {
            System.out.println(e.getAnswer().getHttpCode());
            e.printStackTrace();
            System.out.println(e.getAnswer().getAnswer());
        } catch (NotAuthorizedException e) {
            e.printStackTrace();
        } catch (ReceiptJSONisEmptyException e) {
            e.printStackTrace();
        }
    }
}
