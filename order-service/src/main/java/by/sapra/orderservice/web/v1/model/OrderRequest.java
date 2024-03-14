package by.sapra.orderservice.web.v1.model;

import lombok.Data;

@Data
public class OrderRequest {
    private String product;
    private Integer quantity;
}
