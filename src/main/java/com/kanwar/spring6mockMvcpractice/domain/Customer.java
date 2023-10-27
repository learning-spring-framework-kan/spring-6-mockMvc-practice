package com.kanwar.spring6mockMvcpractice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Customer {
    private UUID id;
    private String name;
    private LocalDateTime establishedTimestamp;
    private LocalDateTime dob;
    private int ssnLast4;
}
