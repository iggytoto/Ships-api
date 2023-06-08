package org.vadimichi.dto.controllers.matchmaking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.vadimichi.dto.DtoBase;
import org.vadimichi.model.matchmaking.MatchType;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MatchMakingRegisterRequestDto extends DtoBase {

    private MatchType type;
}
