package io.github.cscristianmoreno.enums;

import org.junit.jupiter.api.Test;

public class NotificationTypeTest {
    @Test
    void testValueOf() {
        NotificationType notificationType = NotificationType.WEBHOOKS;
        System.out.println(notificationType.getValue());
    }

    @Test
    void testValues() {

    }
}
