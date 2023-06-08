package org.vadimichi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.gassangaming.dto.DtoBase;
import org.vadimichi.dto.unit.UnitDto;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UnitListRequestDto<TUnit extends UnitDto> extends DtoBase {
    Collection<TUnit> units;

    @JsonCreator
    public UnitListRequestDto(Collection<TUnit> units) {
        this.units = units;
    }

    public static UnitListRequestDto<?> Of(Collection<? extends UnitDto> units) {
        return new UnitListRequestDto<>(units);
    }
}
