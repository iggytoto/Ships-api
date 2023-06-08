package org.vadimichi.dto.controllers.events.eventinstanceresult;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.service.event.result.TrainingMatch3x3EventInstanceResult;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("TrainingMatch3x3")
public class TrainingMatch3X3EventInstanceResultDto extends EventInstanceResultDto {
    private long winnerId;
    private long userOneId;
    private long userTwoId;
    private Date date;

    @Override
    public TrainingMatch3x3EventInstanceResult toDomain() {
        var result = new TrainingMatch3x3EventInstanceResult();
        result.setWinnerId(winnerId);
        result.setUserOneId(userOneId);
        result.setUserTwoId(userTwoId);
        result.setDate(date);
        result.setEventType(eventType);
        result.setEventInstanceId(eventInstanceId);
        result.setUnitsHitPoints(unitsHitPoints);
        return result;
    }
}
