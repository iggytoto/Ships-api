package org.vadimichi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = MatchResult.TABLE_NAME)
@Getter
@Setter
public class MatchResult {

    public static final String TABLE_NAME = "match_results";
    public static final String USER_ONE_ID_COLUMN_NAME = "user_one_id";
    public static final String USER_TWO_ID_COLUMN_NAME = "user_two_id";
    public static final String WINNER_USER_COLUMN_NAME = "winner_user_id";
    public static final String MATCH_TYPE_COLUMN_NAME = "match_type";
    public static final String DATE_COLUMN_NAME = "date";
    public static final String SEQUENCE_NAME = "s_match_results_id";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, allocationSize = 1)
    protected long id;

    @Column(name = USER_ONE_ID_COLUMN_NAME)
    private long userOneId;
    @Column(name = USER_TWO_ID_COLUMN_NAME)
    private long userTwoId;
    @Column(name = WINNER_USER_COLUMN_NAME)
    private long winnerUserId;
    @Column(name = MATCH_TYPE_COLUMN_NAME)
    @Enumerated(EnumType.STRING)
    private MatchType matchType;
    @Column(name = DATE_COLUMN_NAME)
    private Date date;

    protected MatchResult() {
    }

    public static MatchResult Of(long userOneId, long userTwoId, long winnerId, MatchType matchType, Date date) {
        final var result = new MatchResult();
        result.userOneId = userOneId;
        result.userTwoId = userTwoId;
        result.winnerUserId = winnerId;
        result.matchType = matchType;
        result.date = date;
        return result;
    }
}
