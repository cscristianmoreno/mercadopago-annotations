package io.github.cscristianmoreno.dto.mercadopago;

public class PaymentID {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("{id=%d}", id);
    }
}
