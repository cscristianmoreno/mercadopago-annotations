package io.github.cscristianmoreno.models.mercadopago;

import java.util.List;

import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import io.github.cscristianmoreno.exceptions.MercadoPagoException;
import io.github.cscristianmoreno.mercadopago.payer.MPPayer;

public interface IMPPreference {
    Preference createPreference(List<PreferenceItemRequest> preferenceItemRequests, MPPayer payer) throws MPException, MPApiException, MercadoPagoException;
    
    Preference createPreference(List<PreferenceItemRequest> preferenceItemRequests) throws MPException, MPApiException, MercadoPagoException;

    // Preference getPreference(List<PreferenceItemRequest> preferenceItemRequests, PreferencePayerRequest preferencePayerRequest) throws MPException, MPApiException;
}