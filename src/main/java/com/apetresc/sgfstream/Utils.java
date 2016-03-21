package com.apetresc.sgfstream;

import java.io.IOException;
import java.io.PushbackReader;

public class Utils {

    public static void skipWhitespace(PushbackReader stream) throws IOException {
        char c;
        do {
            c = (char) stream.read();
        } while (Character.isWhitespace(c));
        stream.unread((int) c);
    }

    public static char peek(PushbackReader stream) throws IOException {
        int c = stream.read();
        stream.unread(c);
        return (char) c;
    }
}
