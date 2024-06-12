package org.lcdpframework.web.model;

public record Response<T>(Integer code, String msg, T data) {

    private static final Integer SUCCESS_CODE = 200;
    private static final Integer FAIL_CODE = 9999;

    public static <T> Response<T> buildResponse(Status status) {
        return new Response<>(status.code, status.message, null);
    }

    public static <T> Response<T> buildResponse(Status status, T data) {
        return new Response<>(status.code, status.message, data);
    }

    public static <T> Response<T> ok() {
        return buildResponse(Status.SUCCESS);
    }

    public static <T> Response<T> ok(T data) {
        return buildResponse(Status.SUCCESS, data);
    }

    public static <T> Response<T> fail() {
        return buildResponse(Status.FAIL);
    }

    public static <T> Response<T> fail(T data) {
        return buildResponse(Status.FAIL, data);
    }


    public enum Status {

        SUCCESS(SUCCESS_CODE, "success"),
        FAIL(FAIL_CODE, "failed");

        private final Integer code;
        private final String message;

        Status(Integer code, String message) {
            this.code = code;
            this.message = message;
        }
    }


}
