package io.github.cscristianmoreno.utils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import io.github.cscristianmoreno.annotations.fields.CategoryDescriptor;
import io.github.cscristianmoreno.annotations.fields.CategoryId;
import io.github.cscristianmoreno.annotations.fields.CurrencyId;
import io.github.cscristianmoreno.annotations.fields.Description;
import io.github.cscristianmoreno.annotations.fields.Id;
import io.github.cscristianmoreno.annotations.fields.PictureUrl;
import io.github.cscristianmoreno.annotations.fields.Quantity;
import io.github.cscristianmoreno.annotations.fields.Title;
import io.github.cscristianmoreno.annotations.fields.UnitPrice;
import io.github.cscristianmoreno.annotations.http.MPPaymentAuthorized;
import io.github.cscristianmoreno.annotations.http.MPPaymentCancelled;
import io.github.cscristianmoreno.annotations.http.MPPaymentChargedBack;
import io.github.cscristianmoreno.annotations.http.MPPaymentInMediation;
import io.github.cscristianmoreno.annotations.http.MPPaymentInProccess;
import io.github.cscristianmoreno.annotations.http.MPPaymentPending;
import io.github.cscristianmoreno.annotations.http.MPPaymentRefunded;
import io.github.cscristianmoreno.annotations.http.MPPaymentRejected;
import io.github.cscristianmoreno.annotations.http.MPPaymentResponse;
import io.github.cscristianmoreno.annotations.http.MPPaymentApproved;

public abstract class AnnotationUtil {

    
    /** 
     * @param status
     * @return Class<? extends Annotation>
     */
    public static Class<? extends Annotation> getAnnotationMatchStatus(final String status) {
        Map<String, Class<? extends Annotation>> map = Map.of(
            "pending", MPPaymentPending.class,
            "approved", MPPaymentApproved.class,
            "authorized", MPPaymentAuthorized.class,
            "in_proccess", MPPaymentInProccess.class,
            "in_mediation", MPPaymentInMediation.class,
            "rejected", MPPaymentRejected.class,
            "cancelled", MPPaymentCancelled.class,
            "refunded", MPPaymentRefunded.class,
            "charged_back", MPPaymentChargedBack.class
        );

        return map.get(status);
    }

    public static List<Annotation> getItemAnnotations(Annotation[] annotations) {
        List<Annotation> list = List.of(annotations);

        return list.stream()
        .filter((a) -> {
            Class<?> annotationType = a.annotationType();

            return annotationType.isAssignableFrom(CategoryDescriptor.class) ||
            annotationType.isAssignableFrom(CurrencyId.class) ||
            annotationType.isAssignableFrom(CategoryId.class) ||
            annotationType.isAssignableFrom(Description.class) ||
            annotationType.isAssignableFrom(Id.class) ||
            annotationType.isAssignableFrom(PictureUrl.class) ||
            annotationType.isAssignableFrom(Quantity.class) ||
            annotationType.isAssignableFrom(Title.class) ||
            annotationType.isAssignableFrom(UnitPrice.class);
        }).toList();
    }

    public static List<Annotation> getControllerAnnotations(Annotation[] annotations) {
        List<Annotation> list = List.of(annotations);

        return list.stream()
        .filter((a) -> {
            Class<?> annotationType = a.annotationType();

            return annotationType.isAssignableFrom(MPPaymentResponse.class) ||
            annotationType.isAssignableFrom(MPPaymentPending.class) ||
            annotationType.isAssignableFrom(MPPaymentApproved.class) ||
            annotationType.isAssignableFrom(MPPaymentAuthorized.class) ||
            annotationType.isAssignableFrom(MPPaymentInProccess.class) ||
            annotationType.isAssignableFrom(MPPaymentInMediation.class) ||
            annotationType.isAssignableFrom(MPPaymentRejected.class) ||
            annotationType.isAssignableFrom(MPPaymentCancelled.class) ||
            annotationType.isAssignableFrom(MPPaymentRefunded.class) ||
            annotationType.isAssignableFrom(MPPaymentChargedBack.class);
        }).toList();
    }
}
