
package com.salesreporter.exception;


public class InvalidCsvException extends Exception {

    public InvalidCsvException(String message) {
        super(message);
    }

    public InvalidCsvException(String message, Throwable cause) {
        super(message, cause);
    }
}