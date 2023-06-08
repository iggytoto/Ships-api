package org.vadimichi.dto.controllers.dungeon;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.dungeon.DungeonPath;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class DungeonPathDto extends DtoBase {
    private long id;
    private long fromRoomId;
    private long toRoomId;
    private long dungeonInstanceId;

    public static DungeonPathDto of(DungeonPath domain) {
        return builder().
                id(domain.getId()).
                fromRoomId(domain.getFromRoomId()).
                toRoomId(domain.getToRoomId()).
                dungeonInstanceId(domain.getDungeonInstanceId()).
                build();
    }
}
