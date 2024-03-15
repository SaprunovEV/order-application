package by.sapra.kafka.model;

import lombok.Data;

@Data
public class OrderEvent {
    private String product;
    private Integer quantity;
}
