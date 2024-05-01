package com.monke.data;

public class UiStatusState {

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public boolean isFailure() {
        return this instanceof Error;
    }

    public boolean isDefault() {
        return this instanceof Default;
    }

    public static class Default extends UiStatusState { }

    public static class Success extends UiStatusState { }

    public static class Error extends UiStatusState {

        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
