package com.LubieKakao1212.neguns.expression;

public class EvaluationException extends RuntimeException {

    public EvaluationException() {
    }

    public EvaluationException(String message) {
        super(message);
    }

    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvaluationException(Throwable cause) {
        super(cause);
    }

    public EvaluationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
