package org.vadimichi.dto.controllers.dungeon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.gassangaming.dto.DtoBase;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpeditionRequestDto extends DtoBase {
    long dungeonInstanceId;
    long startingRoomId;
    Collection<Long> roster;
}
