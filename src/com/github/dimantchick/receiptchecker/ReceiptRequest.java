package com.github.dimantchick.receiptchecker;

/**
 * Данные запроса чека
 *
 * @version 1.0
 * @autor Dimantchick
 */
public class ReceiptRequest {
    private final String fn;
    private final String type;
    private final String fd;
    private final String fpd;
    private final String date;
    private final String sum;
    private String stringData;

    /**
     * Конструктор - создание нового объекта
     *
     * @param fn   - Номер ФН (Фискальный Номер) — 16-значный номер. Например 8710000100518392
     * @param type - «Вид кассового чека». 1 — значит «приход», 2 — «Возврат прихода»
     * @param fd   - Номер ФД (Фискальный документ) — до 10 знаков. Например 54812
     * @param fpd  - Номер ФПД (Фискальный Признак Документа, также известный как ФП) — до 10 знаков. Например 3522207165
     * @param date - Дата чека. Гарантированно принимается формат "yyyy-MM-dd'T'HH:mm:ss" и "yyyyMMdd'T'HHmmss"
     *             Примеры форматирования даты, полученной не из qr:
     *             DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
     *             df.format(calendar.getTime());
     *             DateFormat df1 = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
     *             df.format(calendar.getTime());
     * @param sum  - Сумма чека в копейках (для 128 руб 32 коп будет "12832", для 115 руб 00 коп будет "11500")
     */

    public ReceiptRequest(String fn, String type, String fd, String fpd, String date, String sum) {
        this.fn = fn;
        this.type = type;
        this.fd = fd;
        this.fpd = fpd;
        this.date = date;
        this.sum = sum;
    }

    /**
     * Геттер для Номера ФН (Фискального Номера)
     *
     * @return возвращает Номер ФН (Фискальный Номер)
     */
    public String getFn() {
        return fn;
    }

    /**
     * Геттер для «Вида кассового чека». 1 — значит «приход», 2 — «Возврат прихода»
     *
     * @return возвращает «Вид кассового чека». 1 — значит «приход», 2 — «Возврат прихода»
     */
    public String getType() {
        return type;
    }

    /**
     * Геттер для Номера ФД (Фискального документа)
     *
     * @return возвращает Номер ФД (Фискальный документ)
     */
    public String getFd() {
        return fd;
    }

    /**
     * Геттер для Номера ФПД (Фискального Признака Документа, также известного как ФП)
     *
     * @return возвращает Номер ФПД (Фискальный Признак Документа, также известный как ФП)
     */
    public String getFpd() {
        return fpd;
    }

    /**
     * Геттер для Даты документа
     *
     * @return возвращает Дату документа
     */
    public String getDate() {
        return date;
    }

    /**
     * Геттер для Суммы документа в копейках
     *
     * @return возвращает Сумму документа в копейках
     */
    public String getSum() {
        return sum;
    }

    /**
     * Геттер для JSON - представления чека
     * для получения объекта Receipt использовать:
     * Receipt receipt = JSON.parseObject(receiptRequest.getStringData(), RootObject.class).getDocument().getReceipt();
     *
     * @return возвращает JSON - представления чека
     */
    public String getStringData() {
        return stringData;
    }

    /**
     * Сеттер для JSON - представления чека
     *
     * @param stringData - JSON - представление чека
     *                   для получения объекта Receipt использовать:
     *                   Receipt receipt = JSON.parseObject(receiptRequest.getStringData(), RootObject.class).getDocument().getReceipt();
     */
    public void setStringData(String stringData) {
        this.stringData = stringData;
    }
}
