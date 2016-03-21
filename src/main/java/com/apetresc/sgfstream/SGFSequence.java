package com.apetresc.sgfstream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

public class SGFSequence {

    private List nodes = new LinkedList();

    void addNode(SGFNode node) {
        nodes.add(node);
    }

    public List getNodes() {
        return nodes;
    }

    static SGFSequence fromStream(SGFStreamReader stream) throws IOException, IncorrectFormatException {
        SGFSequence sequence = new SGFSequence();
        sequence.addNode(SGFNode.fromStream(stream));

        while (stream.peek() == ';') {
            sequence.addNode(SGFNode.fromStream(stream));
        }

        return sequence;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator i = nodes.iterator();
        while (i.hasNext()) {
            result.append(i.next().toString());
        }
        return result.toString();
    }

}
