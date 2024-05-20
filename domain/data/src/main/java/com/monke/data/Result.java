package com.monke.data;

public sealed abstract class Result<T> {

    public boolean isSuccess() {
        return this instanceof Result.Success<T>;
    }

    public boolean isFailure() {
        return this instanceof Result.Failure<T>;
    }

    public T get() {
        return ((Success<T>)this).getData();
    }

    public Exception getException() {
        return ((Failure<T>)this).getException();
    }

    public static final class Failure<T> extends Result<T> {
        private final Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }

    public static final class Success<T> extends Result<T> {

        private T data;

        public Success(T data) {
            this.data = data;
        }

        public Success() {}

        public T getData() {
            return data;
        }
    }
}
