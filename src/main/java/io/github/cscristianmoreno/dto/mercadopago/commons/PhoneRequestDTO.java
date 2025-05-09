package io.github.cscristianmoreno.dto.mercadopago.commons;

public class PhoneRequestDTO {
    private String areaCode;
    private String number;
    
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
