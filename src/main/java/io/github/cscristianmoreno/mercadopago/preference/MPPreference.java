package io.github.cscristianmoreno.mercadopago.preference;

import java.util.List;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import io.github.cscristianmoreno.dto.mercadopago.commons.AddressDTO;
import io.github.cscristianmoreno.dto.mercadopago.commons.IdentificationRequestDTO;
import io.github.cscristianmoreno.dto.mercadopago.commons.PhoneRequestDTO;
import io.github.cscristianmoreno.exceptions.MercadoPagoException;
import io.github.cscristianmoreno.mercadopago.payer.MPPayer;
import io.github.cscristianmoreno.mercadopago.utils.PreferenceRequestUtil;
import io.github.cscristianmoreno.models.mercadopago.IMPPreference;

public class MPPreference implements IMPPreference {

    @Override
    public Preference createPreference(final List<PreferenceItemRequest> preferenceItemRequests, final MPPayer payer) {
        Preference preference = null;

        try {
            PreferencePayerRequest preferencePayerRequest = preferencePayerRequest(payer);
            preference = getPreference(preferenceItemRequests, preferencePayerRequest);
        } catch (MPException e) {
            throw new MercadoPagoException(e.getMessage());
        } catch (MPApiException e) {
            String content = e.getApiResponse().getContent();
            throw new MercadoPagoException(content);
        }

        return preference;
    }

    @Override
    public Preference createPreference(final List<PreferenceItemRequest> preferenceItemRequests) {
        Preference preference = null;

        try {
            PreferencePayerRequest preferencePayerRequest = preferencePayerRequest(new MPPayer());
            preference = getPreference(preferenceItemRequests, preferencePayerRequest);
        } catch (MPException e) {
            throw new MercadoPagoException(e.getMessage());
        } catch (MPApiException e) {
            String content = e.getApiResponse().getContent();
            throw new MercadoPagoException(content);
        }

        return preference;
    }

    
    private Preference getPreference(List<PreferenceItemRequest> preferenceItemRequests, PreferencePayerRequest preferencePayerRequest) throws MPException, MPApiException {
        PreferenceRequestUtil preferenceRequestUtil = PreferenceRequestUtil.INSTANCE;

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(preferenceItemRequests)
            .payer(preferencePayerRequest)
            .notificationUrl(preferenceRequestUtil.getNotificationUrl())
            .backUrls(preferenceRequestUtil.getBackUrls())
            .paymentMethods(preferenceRequestUtil.getPaymentMethods())
            .externalReference(preferencePayerRequest.getEmail())
            .expirationDateTo(preferenceRequestUtil.getPreferenceDuration())
            .expires(preferenceRequestUtil.isExpire())
        .build();

        PreferenceClient preferenceClient = new PreferenceClient();
        Preference preference = preferenceClient.create(preferenceRequest);
        return preference;
    }

    private PreferencePayerRequest preferencePayerRequest(MPPayer payer) {
        AddressDTO address = payer.getAddress();
        PhoneRequestDTO phone = payer.getPhone();
        IdentificationRequestDTO identificationRequest = payer.getIdentificationRequestDTO();

        PreferencePayerRequest preferencePayerRequest = PreferencePayerRequest
        .builder()
            .name(payer.getName())
            .surname(payer.getLastName())
            .email(payer.getEmail())
            .phone(
                PhoneRequest
                .builder()
                    .number(phone.getNumber())
                    .areaCode(phone.getAreaCode())
                .build()
            )
            .address(
                AddressRequest
                .builder()
                    .city(address.getCity())
                    .state(address.getState())
                    .complement(address.getCity())
                    .neighborhood(address.getNeighborhood())
                    .streetName(address.getStreetName())
                    .streetNumber(address.getStreetNumber())
                    .state(address.getState())
                    .floor(address.getFloor())
                .build()
            )
            .identification(
                IdentificationRequest
                .builder()
                    .number(identificationRequest.getNumber())
                    .type(identificationRequest.getType())
                .build()
            )
        .build();
        
        return preferencePayerRequest;
    }
}
