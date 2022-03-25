package io.extact.mp.sample.openapi.resource;

import lombok.Getter;

@Getter
public class PersonException extends RuntimeException {
    private CauseError causeError;
    public PersonException(CauseError causeError) {
        this.causeError= causeError;
    }
    public enum CauseError {
        NOT_FOUND,
        CONFLICT
    }
}
