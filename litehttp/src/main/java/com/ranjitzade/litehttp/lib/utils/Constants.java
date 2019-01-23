package com.ranjitzade.litehttp.lib.utils;

/**
 * Created by ranjit
 */
public interface Constants {
    interface ErrorCodes {
        short NO_INTERNET_CONNECTION = 999;
        short INTERNAL_SERVER_ERROR = 500;
        short SERVER_DOWN = 503;
        short CLIENT_ERROR = 400;
        short UN_AUTHORIZED = 401;
        short FORBIDDEN = 403;
        short PAGE_NOT_FOUND = 404;
        short UN_PROCESSABLE = 422;
        short SERVER_ISSUE = 600;
        short API_DEPRECATED = 426;
    }

    interface ErrorMessages {
        String REQUEST_TIMEOUT_MESSAGE = "Request timed out. Try again please.";
        String NO_INTERNET_MESSAGE = "Idle internet! Connect and please try again.";
        String NO_API_EXISTS_MESSAGE = "Please check for valid api endpoint.";
        String BAD_REQUEST_ERROR_MESSAGE = "Bad Request! Please try again.";
        String CLIENT_ERROR_MESSAGE = "Something bad happened. No fear. Just try again. :)";
        String SERVER_DOWN_MESSAGE = "Server is momentarily down. Please try after sometime. :)";
        String SERVER_ERROR_MESSAGE = "Could not connect to server. Please try again later. :)";
        String TRY_AGAIN_LATER_MESSAGE = "Unknown error. Please try again later :)";
    }

}
