package org.vadimichi.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
public class ErrorResponseDto extends DtoBase {

    public static ErrorResponseDto Of(String s) {
        final var result = new ErrorResponseDto();
        result.setCode(1L);
        result.setMessage(s);
        return result;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
