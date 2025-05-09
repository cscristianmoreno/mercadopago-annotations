package io.github.cscristianmoreno.enums;

public enum PaymentMethod {
    VISA("visa"),
    MASTERCARD("master"),
    AMERICAN_EXPRESS("amex"),
    TARJETA_SHOPPING("tarshop"),
    CABAL("cabal"),
    DINERS("diners"),
    MAESTRO("maestro"),
    MASTERCARD_DEBITO("debmaster"),
    VISA_DEBITO("debvisa"),
    PAGO_FACIL("pagofacil"),
    RAPIPAGO("rapipago");

    private String value;
    private PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
