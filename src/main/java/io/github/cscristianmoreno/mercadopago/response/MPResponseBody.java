package io.github.cscristianmoreno.mercadopago.response;

import com.mercadopago.resources.payment.Payment;

import io.github.cscristianmoreno.mercadopago.result.MPResult;

public class MPResponseBody {
    private MPResult result;
    private Payment payment;
    private String status;

    public MPResult getResult() {
        return result;
    }

    public void setResult(MPResult result) {
        this.result = result;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("{MPResult=%s, payment=%s, status=%s}",
        result, payment, status);
    }
}
