package com.scalable.payment.type.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(includeFieldNames = true)
public class ProgressJSON extends BaseJSON {
    private Double price;
    private String message_flag;
}