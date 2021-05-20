package edu.hrbust.iot.amqp.web.utils.common;

import lombok.Data;

import java.util.Objects;

@Data
public class WebResponse<T> {
    public static final int SUCCESS_CODE = 20000;
    public static final int ERROR_CODE = 1;
    public static final int ERROR_RETRY_CODE = 2;

    private int code;
    private String error;
    private T data;
    private String authType;

    private Boolean needRetry;

    public static <T> WebResponse<T> success(T data){
        WebResponse<T> response = new WebResponse<>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static <T> WebResponse<T> error(String errorMessage){
        return WebResponse.error(errorMessage, false);
    }

    public static <T> WebResponse<T> errorAndRetry(String errorMessage){
        return WebResponse.error(errorMessage, true);
    }

    public static <T> WebResponse<T> error(String errorMessage, Boolean needRetry){
        WebResponse<T> response = new WebResponse<>();
        response.setCode(ERROR_CODE);
        response.setError(errorMessage);
        response.setNeedRetry(needRetry);
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebResponse<?> that = (WebResponse<?>) o;
        return code == that.code
                && Objects.equals(error, that.error)
                && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, error, data);
    }

    @Override
    public String toString() {
        return "WebApiResponse{" +
                "code=" + code +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
