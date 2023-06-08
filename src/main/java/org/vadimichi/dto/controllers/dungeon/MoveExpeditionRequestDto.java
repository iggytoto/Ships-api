package org.vadimichi.dto.controllers.dungeon;

import lombok.*;
import org.gassangaming.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveExpeditionRequestDto extends DtoBase {
    private long targetRoomId;
    private long expeditionId;
}
