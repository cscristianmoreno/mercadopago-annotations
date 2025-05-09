package io.github.cscristianmoreno.mercadopago.result;

import com.mercadopago.resources.payment.Payment;

import io.github.cscristianmoreno.dto.mercadopago.PaymentID;

public class MPResult {
    private String id;
    private boolean liveMode;
    private String type;
    private String dateCreated;
    private String userId;
    private String apiVersion;
    private String action;
    private PaymentID data;
    private Payment payment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLiveMode() {
        return liveMode;
    }

    public void setLiveMode(boolean live_mode) {
        this.liveMode = live_mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String date_created) {
        this.dateCreated = date_created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String api_version) {
        this.apiVersion = api_version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PaymentID getData() {
        return data;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setData(PaymentID data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{id=%s, live_mode=%s, type=%s, date_created=%s, user_id=%s, api_version=%s, action=%s, data=%s}",
        id, liveMode, type, dateCreated, userId, apiVersion, action, data);
    }
}
