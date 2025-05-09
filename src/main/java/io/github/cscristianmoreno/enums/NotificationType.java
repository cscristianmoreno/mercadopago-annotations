package io.github.cscristianmoreno.enums;

public enum NotificationType {
    WEBHOOKS("webhooks"),
    IPN("ipn"),
    DEFAULT("");

    String value = null;
    NotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
