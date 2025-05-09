package io.github.cscristianmoreno.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import org.junit.jupiter.api.Test;

import io.github.cscristianmoreno.annotations.http.MPPaymentApproved;

public class AnnotationUtilTest {
    @Test
    void testGetAnnotationMatchStatus() {
        Class<? extends Annotation> annotation = AnnotationUtil.getAnnotationMatchStatus("approved");

        assertNotNull(annotation);
        assertTrue(annotation.isAssignableFrom(MPPaymentApproved.class));
    }
}
