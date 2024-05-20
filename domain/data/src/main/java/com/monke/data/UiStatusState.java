package com.monke.data;

public sealed class UiStatusState permits
        UiStatusState.Default,
        UiStatusState.Error,
        UiStatusState.Success {

    public static UiStatusState fromResult(Result result) {
        if (result.isFailure()) {
            return new UiStatusState.Error(result.getException());
        }
        return new UiStatusState.Success();
    }

    public Exception getException() {
        return ((Error) this).exception;
    }

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public boolean isFailure() {
        return this instanceof Error;
    }

    public boolean isDefault() {
        return this instanceof Default;
    }

    public static final class Default extends UiStatusState { }

    public static final class Success extends UiStatusState { }

    public static final class Error extends UiStatusState {

        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
