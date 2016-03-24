package com.apetresc.sgfstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SGFStreamReader extends InputStreamReader {
    private Character peek = null;

    public SGFStreamReader(InputStream stream) {
        super(stream);
    }

    public char peek(boolean skipWhitespace) throws IOException {
        peek = readCharacter(skipWhitespace);
        return peek;
    }

    public char peek() throws IOException {
        return peek(true);
    }

    public char readCharacter(boolean skipWhitespace) throws IOException {
        if (peek != null) {
            char c = peek;
            peek = null;
            return c;
        }
        char c;
        do {
            c = (char) super.read();
        } while (Character.isWhitespace(c) && skipWhitespace);
        return c;
    }

    public char readCharacter() throws IOException {
        return readCharacter(true);
    }

}
