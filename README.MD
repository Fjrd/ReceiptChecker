Библиотека для проверки и получения электронных чеков из ФНС
Для работы требуется com.alibaba:fastjson

Использование:
// Создание экземпляра
ReceiptChecker receiptChecker = new ReceiptChecker();
// Регистрация, если требуется (пароль высылает ФНС по смс)
receiptChecker.register("email", "name", "+7999#######");
// Восстановление пароля (пароль высылает ФНС по смс)
receiptChecker.restorePass("+7999#######");
// Авторизация в ФНС по номеру телефона и паролю
receiptChecker.login("+7999#######", "######"); 
// Создание запроса для чека по его данным
ReceiptRequest receiptRequest = new ReceiptRequest("9251440300018806", "1", "58197", "0050408976", "20200509T203900", "452300");
// проверка чека на существование в базе
receiptChecker.isReceiptValid(receiptRequest);
// Получение чека в JSON (будет доступен по receiptRequest.getJsonData())
receiptChecker.receiveReceipt(receiptRequest);
// Преобразование JSON чека в объект Receipt
Receipt receipt = receiptRequest.getReceipt();
