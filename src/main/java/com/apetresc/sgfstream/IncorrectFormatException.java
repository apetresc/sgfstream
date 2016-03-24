package com.apetresc.sgfstream;

import java.io.IOException;

public class IncorrectFormatException extends Exception {
    private final int LENGTH_OF_SNIPPET = 30;

    private String failurePoint;

    public IncorrectFormatException(String message, SGFStreamReader stream) {
        super(message);
        try {
            char[] restOfBuffer = new char[LENGTH_OF_SNIPPET];
            int bytesRead = stream.read(restOfBuffer, 0, LENGTH_OF_SNIPPET);
            if (bytesRead >= 0) {
                failurePoint = new String(restOfBuffer, 0, bytesRead);
            } else {
                failurePoint = "[Unavailable]";
            }
        } catch (IOException ioe) {
            failurePoint = "[Unavailable]";
        }
    }

    @Override
    public String getMessage() {
        return String.format("%s\n%s", super.getMessage(), failurePoint);
    }
}