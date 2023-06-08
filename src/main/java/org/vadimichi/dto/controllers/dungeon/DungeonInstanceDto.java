package org.vadimichi.dto.controllers.dungeon;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.dungeon.DungeonInstance;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class DungeonInstanceDto extends DtoBase {
    private long id;
    private List<DungeonRoomDto> rooms;
    private List<DungeonPathDto> paths;

    public static DungeonInstanceDto of(DungeonInstance domain) {
        return builder().
                id(domain.getId()).
                rooms(domain.getRooms().stream().map(DungeonRoomDto::of).toList()).
                paths(domain.getPaths().stream().map(DungeonPathDto::of).toList()).
                build();
    }
}
