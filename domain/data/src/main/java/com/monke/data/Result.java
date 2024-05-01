package com.monke.data;

public abstract class Result<T> {

    public static class Failure<T> extends Result<T> {
        private final Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }

    public static class Success<T> extends Result<T> {

        private final T data;

        public Success(T data) {
            this.data = data;
        }
    }
}
