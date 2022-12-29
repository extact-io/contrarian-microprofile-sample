package io.extact.mp.sample.jwt.resource;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {
    private CauseError causeError;
    public ProductException(CauseError causeError) {
        this.causeError= causeError;
    }
    public enum CauseError {
        NOT_FOUND,
        DUPLICATE
    }
}
