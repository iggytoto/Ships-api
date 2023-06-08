package org.vadimichi.dto.controllers.barracks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
public class EquipItemRequestDto extends DtoBase {
    long itemId;
    long unitId;
}
