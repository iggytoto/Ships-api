package org.vadimichi.service.matchmaking;

import org.vadimichi.model.matchmaking.MatchType;
import org.vadimichi.service.exception.ServiceException;

public interface MatchMakingService {
    long register(MatchType type, long userId) throws ServiceException;
}
