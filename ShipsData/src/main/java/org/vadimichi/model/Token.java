package org.vadimichi.model;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import static org.vadimichi.model.Token.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
public class Token implements Serializable {

    public static final String TABLE_NAME = "tokens";
    public static final String VALUE_COLUMN_NAME = "value";
    public static final String VALID_TO_COLUMN_NAME = Constants.VALID_TO_COLUMN_NAME;
    public static final String USER_ID_COLUMN_NAME = Constants.USER_ID_FOREIGN_KEY_COLUMN_NAME;
    public static final String PERMANENT_COLUMN_NAME = "permanent";
    public static final String SEQUENCE_NAME = "s_tokens_id";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, allocationSize = 1)
    protected Long id;
    @Column(name = VALUE_COLUMN_NAME)
    private String value;

    @Column(name = VALID_TO_COLUMN_NAME)
    private Date validTo;

    @Column(name = USER_ID_COLUMN_NAME)
    private long userId;

    @Column(name = PERMANENT_COLUMN_NAME)
    private boolean permanent;

    protected Token() {
    }

    protected Token(String value, long userId, Date validTo) {
        this.value = value;
        this.validTo = validTo;
        this.userId = userId;
    }

    protected Token(long id, String value, long userId, Date validTo) {
        this.value = value;
        this.validTo = validTo;
        this.userId = userId;
        this.id = id;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static Token Of(String json) throws JsonProcessingException {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(json, Token.class);
    }

    public static Token Of(String value, long userId, Date validTo) {
        return new Token(value, userId, validTo);
    }

    public static Token Of(long id, String value, long userId, Date validTo) {
        return new Token(id, value, userId, validTo);
    }
}
