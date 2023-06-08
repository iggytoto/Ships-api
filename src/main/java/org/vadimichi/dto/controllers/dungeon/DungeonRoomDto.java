package org.vadimichi.dto.controllers.dungeon;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.dungeon.DungeonRoom;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class DungeonRoomDto extends DtoBase {
    private long id;
    private boolean isEntrance;
    private long dungeonInstanceId;

    public static DungeonRoomDto of(DungeonRoom domain) {
        return builder().
                id(domain.getId()).
                isEntrance(domain.isEntrance()).
                dungeonInstanceId(domain.getDungeonInstanceId()).
                build();
    }
}
