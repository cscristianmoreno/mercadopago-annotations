package io.github.cscristianmoreno.mercadopago.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercadopago.client.preference.PreferenceItemRequest;

import io.github.cscristianmoreno.mercadopago.item.MPItem;
import io.github.cscristianmoreno.sample.items.SimpleItem;

public class MPItemsTest {

    @BeforeEach
    void setup() throws Exception {
        MPItem.addItem(SimpleItem.class);
    }

    @Test
    void testAddItem() throws Exception {
    }

    @Test
    void testGetItem() throws Exception {
        Optional<PreferenceItemRequest> result = MPItem.getItem("1");

        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetItems() {

    }
}
