package pe.todotic.bookstoreapi_s3.web.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItem {
    private String name;
    private String sku;
    private String quantity;

    @JsonProperty("unit_amount")
    private Amount unitAmount;
}
