package io.github.cscristianmoreno.sample.items;

import io.github.cscristianmoreno.annotations.MercadoPagoItem;
import io.github.cscristianmoreno.annotations.fields.CategoryId;
import io.github.cscristianmoreno.annotations.fields.CurrencyId;
import io.github.cscristianmoreno.annotations.fields.Description;
import io.github.cscristianmoreno.annotations.fields.Id;
import io.github.cscristianmoreno.annotations.fields.PictureUrl;
import io.github.cscristianmoreno.annotations.fields.Quantity;
import io.github.cscristianmoreno.annotations.fields.Title;
import io.github.cscristianmoreno.annotations.fields.UnitPrice;

@MercadoPagoItem
public class SimpleItem {
    @Id
    private String id;
    
    @Title
    private String title = "Remera X2 en métodos";

    @Description
    private String description = "Remera manga larga";

    @UnitPrice
    private int price = 1;

    @PictureUrl
    private String image = "https://www.shutterstock.com/image-illustration/clip-art-simple-sun-260nw-2304611571.jpg";

    @CurrencyId
    private String currency = "ARS";

    @Quantity
    private int quantity = 1;

    @CategoryId
    private String categoryId = "asd";
}
