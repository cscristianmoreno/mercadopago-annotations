package io.github.cscristianmoreno.mercadopago.payer;

import io.github.cscristianmoreno.dto.mercadopago.commons.AddressDTO;
import io.github.cscristianmoreno.dto.mercadopago.commons.IdentificationRequestDTO;
import io.github.cscristianmoreno.dto.mercadopago.commons.PhoneRequestDTO;

public class MPPayer {
    private String name;
    private String lastName;
    private String email;
    private AddressDTO address = new AddressDTO();
    private PhoneRequestDTO phone = new PhoneRequestDTO();
    private IdentificationRequestDTO identificationRequest = new IdentificationRequestDTO();
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public AddressDTO getAddress() {
        return address;
    }
    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    public PhoneRequestDTO getPhone() {
        return phone;
    }
    public void setPhone(PhoneRequestDTO phone) {
        this.phone = phone;
    }
    public IdentificationRequestDTO getIdentificationRequestDTO() {
        return identificationRequest;
    }
    public void setIdentificationRequestDTO(IdentificationRequestDTO identificationRequest) {
        this.identificationRequest = identificationRequest;
    }
}
