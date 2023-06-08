package org.vadimichi.dto.controllers.dungeon;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.dungeon.DungeonExpedition;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class DungeonExpeditionDto extends DtoBase {
    private long id;
    private long dungeonInstanceId;
    private long userId;

    public static DtoBase of(DungeonExpedition expedition) {
        return builder().
                id(expedition.getId()).
                dungeonInstanceId(expedition.getDungeonInstanceId()).
                userId(expedition.getUserId()).
                build();
    }
}
