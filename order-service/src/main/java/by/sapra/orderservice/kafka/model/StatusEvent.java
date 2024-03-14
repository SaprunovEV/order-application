package by.sapra.orderservice.kafka.model;

import lombok.Data;

import java.time.Instant;

@Data
public class StatusEvent {
    private String status;
    private Instant date;
}
