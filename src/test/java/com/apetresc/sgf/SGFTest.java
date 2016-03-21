package com.apetresc.sgf;

import com.apetresc.sgfstream.IncorrectFormatException;
import com.apetresc.sgfstream.SGF;
import com.apetresc.sgfstream.SGFNode;

import java.io.IOException;

import com.apetresc.sgfstream.SGFIterator;
import junit.framework.TestCase;

public class SGFTest extends TestCase {

    public void testSimpleSgf() throws IOException, IncorrectFormatException {
        SGF sgf = new SGF();
        sgf.parseSGF(this.getClass().getResourceAsStream("/sgf/simple.sgf"));

        assertNotNull(sgf.getRootTree());
        SGFIterator it = sgf.iterator();
        SGFNode metadata = it.next();
        assertEquals("Alexandre Dinerchtein", metadata.getProperties().get("PB").getValues()[0]);
        while (it.hasNext()) {
            SGFNode move = it.next();
            assertTrue(move.getProperties().containsKey("W") || move.getProperties().containsKey("B"));
            System.out.println(move.getBoardPosition());
            System.out.println("");
        }

        System.out.println(sgf.toString());
    }
}
