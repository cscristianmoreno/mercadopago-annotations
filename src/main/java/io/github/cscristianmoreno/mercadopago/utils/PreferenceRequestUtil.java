package io.github.cscristianmoreno.mercadopago.utils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferencePaymentTypeRequest;
import io.github.cscristianmoreno.annotations.MercadoPago;
import io.github.cscristianmoreno.annotations.PreferenceDuration;
import io.github.cscristianmoreno.dto.mercadopago.BackUrlsDTO;
import io.github.cscristianmoreno.enums.PaymentMethod;
import io.github.cscristianmoreno.enums.PaymentType;
import io.github.cscristianmoreno.enums.NotificationType;
import io.github.cscristianmoreno.utils.EnvironmentUtil;

public enum PreferenceRequestUtil {
    INSTANCE;
    private PreferencePaymentMethodsRequest paymentMethods;
    private PreferenceBackUrlsRequest backUrls;
    private String notificationUrl; 
    private OffsetDateTime preferenceDuration;
    private boolean expire;

    private PreferenceRequestUtil() {
        
    }

    public void configure(final MercadoPago mercadoPago) {
        String successUrl = EnvironmentUtil.getEnv(mercadoPago.redirectSuccessUrl());
        String failureUrl = EnvironmentUtil.getEnv(mercadoPago.redirectFailureUrl());
        String pendingUrl = EnvironmentUtil.getEnv(mercadoPago.redirectPendingUrl());
        String notificationUrl = EnvironmentUtil.getEnv(mercadoPago.notificationUrl());

        PaymentType[] excludedPaymentType = mercadoPago.excludedPaymentType();
        PaymentMethod[] excludedPaymentMethod = mercadoPago.excludedPaymentMethod();
        String notificationTypeValue = NotificationType.WEBHOOKS.getValue();
        
        if (!notificationTypeValue.isEmpty()) {
            notificationUrl = notificationUrl + "?source_news=" + notificationTypeValue;
        }

        int installments = mercadoPago.installments();
        PreferenceDuration preferenceDuration = mercadoPago.preferenceDuration(); 
        boolean expire = mercadoPago.expires();

        BackUrlsDTO mpBackUrlsDTO = new BackUrlsDTO();
        mpBackUrlsDTO.setSuccessUrl(successUrl);
        mpBackUrlsDTO.setFailureUrl(failureUrl);
        mpBackUrlsDTO.setPendingUrl(pendingUrl);

        setBackUrls(mpBackUrlsDTO);
        setNotificationUrl(notificationUrl);
        setPaymentMethod(installments, excludedPaymentType, excludedPaymentMethod);
        setPreferenceDuration(preferenceDuration);
        setExpire(expire);
    }


    public PreferencePaymentMethodsRequest getPaymentMethods() {
        return paymentMethods;
    }

    public PreferenceBackUrlsRequest getBackUrls() {
        return backUrls;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public OffsetDateTime getPreferenceDuration() {
        return preferenceDuration;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setBackUrls(final BackUrlsDTO mpBackUrlsDTO) {
        backUrls = PreferenceBackUrlsRequest
        .builder()
            .success(mpBackUrlsDTO.getSuccessUrl())
            .failure(mpBackUrlsDTO.getFailureUrl())
            .pending(mpBackUrlsDTO.getPendingUrl())
        .build();
    }

    public void setNotificationUrl(final String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public void setPaymentMethod(final int installments, final PaymentType[] excludePaymentTypes, final PaymentMethod[] excludePaymentMethods) {

        List<PreferencePaymentMethodRequest> excludedPaymentMethodRequests = new ArrayList<PreferencePaymentMethodRequest>();
        List<PreferencePaymentTypeRequest> excludedPaymentTypeRequests = new ArrayList<PreferencePaymentTypeRequest>();

        for (PaymentType excludePaymentType: excludePaymentTypes) {
            excludedPaymentMethodRequests.add(
                PreferencePaymentMethodRequest.builder().id(excludePaymentType.getValue()).build()
            );
        }

        for (PaymentType excludePaymentType: excludePaymentTypes) {
            excludedPaymentTypeRequests.add(
                PreferencePaymentTypeRequest
                .builder()
                    .id(excludePaymentType.getValue())
                .build()
            );
        }

        paymentMethods = PreferencePaymentMethodsRequest
        .builder()
            .installments(installments)
            .defaultInstallments(installments)
            .excludedPaymentMethods(excludedPaymentMethodRequests)
            .excludedPaymentTypes(excludedPaymentTypeRequests)
        .build();
    }

    public void setPreferenceDuration(final PreferenceDuration annotationPreferenceDuration) {
        int days = annotationPreferenceDuration.days();
        int hours = annotationPreferenceDuration.hours();
        int minutes = annotationPreferenceDuration.minutes();

        preferenceDuration = OffsetDateTime.now()
        .plusDays(days)
        .plusHours(hours)
        .plusMinutes(minutes);
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }
}
