package org.vadimichi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public abstract class DtoBase implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;
}
