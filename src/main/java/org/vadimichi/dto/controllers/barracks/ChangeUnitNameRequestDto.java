package org.vadimichi.dto.controllers.barracks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeUnitNameRequestDto extends DtoBase {
    long unitId;
    String newName;
}
