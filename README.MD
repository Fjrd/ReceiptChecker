##Библиотека для проверки и получения электронных чеков из ФНС

##Зависимости
com.alibaba:fastjson 

##Использование:

#### Создание экземпляра
```java
ReceiptChecker receiptChecker = new ReceiptChecker();
```
#### Регистрация, если требуется (пароль высылает ФНС по смс)
```java
receiptChecker.register("email", "name", "+7999#######");
```
#### Восстановление пароля (пароль высылает ФНС по смс)
```java
receiptChecker.restorePass("+7999#######");
```
#### Авторизация в ФНС по номеру телефона и паролю
```java
receiptChecker.login("+7999#######", "######"); 
```
#### Создание запроса для чека по его данным
```java
ReceiptRequest receiptRequest = new ReceiptRequest("9251440300018806", "1", "58197", "0050408976", "20200509T203900", "452300");
```
#### проверка чека на существование в базе
```java
receiptChecker.isReceiptValid(receiptRequest);
```
#### Получение чека в JSON (будет доступен по receiptRequest.getJsonData())
```java
receiptChecker.receiveReceipt(receiptRequest);
```
#### Преобразование JSON чека в объект Receipt
```java
Receipt receipt = receiptRequest.getReceipt();
```