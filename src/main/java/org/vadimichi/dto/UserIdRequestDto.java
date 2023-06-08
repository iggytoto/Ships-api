package org.vadimichi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.gassangaming.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserIdRequestDto extends DtoBase {
    long userId;
}
