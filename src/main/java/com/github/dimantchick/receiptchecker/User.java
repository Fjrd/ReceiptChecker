package com.github.dimantchick.receiptchecker;
/**
 * Данные пользователя для запросов к серверу
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class User {
    private String email;
    private String name;
    private String phone;
    private String password;

    /**
     * Создание нового пользователя без пароля
     *
     * @param email - почта
     * @param name  - любое имя пользователя
     * @param phone - телефон в формате +79*********
     */
    public User(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    /**
     * Создание нового пользователя
     *
     * @param email    - почта
     * @param name     - любое имя пользователя
     * @param phone    - телефон в формате +79*********
     * @param password - пароль ФНС. Можно получить по СМС или использовать от приложения ФНС "Проверка чеков"
     *                 Пример получения:
     *                 BillChecker billChecker = new BillChecker();
     *                 billChecker.register("something@domen.zone", "Myname", "+7999#######");
     *                 или
     *                 billChecker.restorePass("+7999#######");
     */
    public User(String email, String name, String phone, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Создание нового пользователя по телефону и паролю;
     * поля name и email у billChecker.user заполнятся методом billChecker.login("+7999#######", "password"); автоматически.
     *
     * @param phone    - телефон в формате +79*********
     * @param password - пароль ФНС. Можно получить по СМС или использовать от приложения ФНС "Проверка чеков"
     *                 Пример получения:
     *                 BillChecker billChecker = new BillChecker();
     *                 billChecker.register("something@domen.zone", "Myname", "+7999#######");
     *                 или
     *                 billChecker.restorePass("+7999#######");
     */
    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    /**
     * Создание нового пользователя по телефону для восстановления пароля
     *
     * @param phone - телефон в формате +79*********
     */
    public User(String phone) {
        this.phone = phone;
    }

    /**
     * Геттер для имени пользователя
     *
     * @return возвращает имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Сеттер для имени пользователя
     *
     * @param name - имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Геттер для телефона
     *
     * @return возвращает телефон
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Сеттер для телефона
     *
     * @param phone - телефон в формате +79*********
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Геттер для почты
     *
     * @return возвращает почту
     */
    public String getEmail() {
        return email;
    }

    /**
     * Сеттер для почты
     *
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Геттер для пароля
     *
     * @return возвращает пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Сеттер для пароля
     *
     * @param password - пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
