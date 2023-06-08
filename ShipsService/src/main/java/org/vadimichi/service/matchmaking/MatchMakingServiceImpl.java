package org.vadimichi.service.matchmaking;

import org.springframework.stereotype.Service;
import org.vadimichi.model.matchmaking.MatchType;

@Service
public class MatchMakingServiceImpl implements MatchMakingService {
    @Override
    public long register(MatchType type, long userId) {
        return 0;
    }
}
