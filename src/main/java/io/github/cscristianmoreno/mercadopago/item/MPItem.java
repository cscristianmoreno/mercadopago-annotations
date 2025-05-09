package io.github.cscristianmoreno.mercadopago.item;

import java.util.List;
import java.util.Optional;

import com.mercadopago.client.preference.PreferenceItemRequest;

import io.github.cscristianmoreno.mercadopago.utils.PreferenceItemRequestUtil;

public abstract class MPItem {

    /** Add item to list */
    public static void addItem(Class<?> clazz) throws Exception {
        PreferenceItemRequestUtil.addItem(clazz);
    }
    
    /** Get a item */
    public static Optional<PreferenceItemRequest> getItem(String id) {
        return PreferenceItemRequestUtil.getItem(id);
    }

    /** Get all items */
    public static List<PreferenceItemRequest> getItems() {
        return PreferenceItemRequestUtil.getItems();
    }
}
