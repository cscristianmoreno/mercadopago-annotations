package io.github.cscristianmoreno.enums;

public enum PaymentType {
    CREDIT_CARD("credit_card"),
    DEBIT_CARD("debit_card"),
    TICKET("ticket");

    private String value;

    private PaymentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
