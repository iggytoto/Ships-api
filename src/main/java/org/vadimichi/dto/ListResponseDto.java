package org.vadimichi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;

import java.util.List;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class ListResponseDto<T extends DtoBase> extends DtoBase {
    List<T> items;
    public static ListResponseDto<?> of(List<? extends DtoBase> items){
        return new ListResponseDto<>(items);
    }
}
