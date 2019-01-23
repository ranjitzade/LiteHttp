package com.ranjitzade.litehttp.lib.core.response;

import com.ranjitzade.litehttp.lib.utils.Constants;
import com.ranjitzade.litehttp.lib.utils.ErrorType;

import java.net.SocketTimeoutException;

import androidx.annotation.NonNull;

/**
 * Created by ranjit
 */
public class ErrorResponse implements Constants.ErrorCodes, Constants.ErrorMessages {
    public String message;
    public Throwable exception;
    public int code;
    @ErrorType
    public int errorType;

    public static ErrorResponse getNoInternetResponse() {
        return getErrorResponse(NO_INTERNET_CONNECTION, null, null);
    }

    public static ErrorResponse getErrorResponse(int code, Throwable exception, String message) {
        ErrorResponse response = new ErrorResponse();
        response.code = code;
        if (exception instanceof SocketTimeoutException) {
            response.exception = exception;
            response.message = REQUEST_TIMEOUT_MESSAGE;
            response.errorType = ErrorType.TIMEOUT;
            return response;
        }

        if (code == NO_INTERNET_CONNECTION) {
            response.message = NO_INTERNET_MESSAGE;
            response.exception = new Exception(NO_INTERNET_MESSAGE);
            response.errorType = ErrorType.NETWORK;
            return response;
        }

        if (code >= CLIENT_ERROR && code < INTERNAL_SERVER_ERROR) {
            if (code == UN_PROCESSABLE || code == CLIENT_ERROR || code == UN_AUTHORIZED) {
                response.message = message;
                response.exception = new Exception(message);
                response.errorType = ErrorType.UNPROCESSED_ENTITY;
            } else if (code == PAGE_NOT_FOUND) {
                response.message = NO_API_EXISTS_MESSAGE;
                response.exception = new Exception(NO_API_EXISTS_MESSAGE);
                response.errorType = ErrorType.NO_API_EXISTS;
            } else if (code == FORBIDDEN) {
                response.message = BAD_REQUEST_ERROR_MESSAGE;
                response.exception = new Exception(BAD_REQUEST_ERROR_MESSAGE);
                response.errorType = ErrorType.FORBIDDEN;
            } else if (code == API_DEPRECATED) {
                response.message = message;
                response.exception = new Exception(message);
                response.errorType = ErrorType.API_DEPRICATED;
            } else {
                response.message = CLIENT_ERROR_MESSAGE;
                response.exception = new Exception(CLIENT_ERROR_MESSAGE);
                response.errorType = ErrorType.CLIENT;
            }
        } else if (code >= INTERNAL_SERVER_ERROR && code < SERVER_ISSUE) {
            if (code == SERVER_DOWN) {
                response.message = SERVER_DOWN_MESSAGE;
                response.exception = new Exception(SERVER_DOWN_MESSAGE);
                response.errorType = ErrorType.SERVER_DOWN;
            } else {
                response.message = SERVER_ERROR_MESSAGE;
                response.exception = new Exception(SERVER_ERROR_MESSAGE);
                response.errorType = ErrorType.SERVER_ERROR;
            }
        } else {
            response.errorType = ErrorType.OTHER;
            if (exception != null) {
                response.message = exception.getMessage();
                response.exception = exception;
            } else if (message != null) {
                response.message = message;
                response.exception = new Exception(message);
            } else {
                response.message = TRY_AGAIN_LATER_MESSAGE;
                response.exception = new Exception(TRY_AGAIN_LATER_MESSAGE);
            }
        }

        return response;
    }

    @NonNull
    @Override
    public String toString() {
        return "status code : " + this.code + " Message : " + this.message;
    }
}