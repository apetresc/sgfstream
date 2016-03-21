package com.apetresc.sgfstream;

import java.io.*;

public class SGF {

    private SGFGameTree gameTree;

    public void parseSGF(String sgf) throws IncorrectFormatException, UnsupportedEncodingException {
        try {
            parseSGF(new ByteArrayInputStream(sgf.getBytes("UTF-8")));
        } catch (IOException ioe) {
            // Shouldn't ever happen
        }
    }

    public void parseSGF(InputStream in) throws IOException, IncorrectFormatException {
        this.gameTree = SGFGameTree.fromStream(new SGFStreamReader(in), null);
    }

    public SGFIterator iterator() {
        return new SGFIterator(this.gameTree);
    }

    public SGFGameTree getRootTree() {
        return gameTree;
    }

    public String toString() {
        return gameTree.toString();
    }

}
