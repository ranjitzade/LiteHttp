package com.ranjitzade.litehttp.lib.utils;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;

import androidx.annotation.IntDef;

/**
 * Identifies the event kind which triggered a the error.
 *
 * @author ranjit
 */
@IntDef({
        ErrorType.NETWORK, ErrorType.UNEXPECTED, ErrorType.TIMEOUT, ErrorType.NO_CONTENT, ErrorType.UNAUTHENTICATED,
        ErrorType.CLIENT, ErrorType.UNPROCESSED_ENTITY, ErrorType.NO_API_EXISTS, ErrorType.FORBIDDEN,
        ErrorType.SERVER_DOWN, ErrorType.SERVER_ERROR, ErrorType.REQUEST_CANCELLED, ErrorType.REFRESH_TOKEN_FAILURE
})
@Retention(RetentionPolicy.SOURCE)
public @interface ErrorType {
    /**
     * An {@link IOException} occurred while communicating to the server.
     */
    int NETWORK = 1;
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    int UNEXPECTED = 2;
    /**
     * An {@link SocketTimeoutException} error thrown when the timeout occurs while communicating
     * with the server
     */
    int TIMEOUT = 3;
    /**
     * This will be thrown when no content available is sent form server
     */
    int NO_CONTENT = 5;
    /**
     * Called for 401 responses. when user is not authenticated to use the service
     * Access is denied. generate new refresh token
     */
    int UNAUTHENTICATED = 6;
    /**
     * Called for response codes between 400 - 500
     */
    int CLIENT = 7;
    /**
     * Called for response code 422
     * means the server understands the content type of the request entity
     * but was unable to process the contained instructions
     */
    int UNPROCESSED_ENTITY = 8;
    /**
     * called for response 404 where api endpoint does not exists
     * to indicate that the client was able to communicate with a given server,
     * but the server could not find what was requested.
     */
    int NO_API_EXISTS = 9;
    /**
     * called when api response code 403 is received for the reason that
     * the page or resource you were trying to reach is absolutely forbidden for some reason.
     */
    int FORBIDDEN = 10;
    /**
     * called when server is down or
     * server is currently unable to handle the HTTP request due to a temporary overloading or
     * maintenance of the server.
     */
    int SERVER_DOWN = 11;
    /**
     * called when something has gone wrong on the server
     */
    int SERVER_ERROR = 12;
    /**
     * called when request is cancelled
     */
    int REQUEST_CANCELLED = 13;

    // When api is not able to recreate refresh token
    int REFRESH_TOKEN_FAILURE = 14;
    /**
     * Called when Api is depricated. Should show the dialog with error message and redirection url
     */
    int API_DEPRICATED = 15;
    int OTHER = 16;
}
