package com.bbin.api.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class DomainDTO {
    @NotEmpty
    private String domain;
}
