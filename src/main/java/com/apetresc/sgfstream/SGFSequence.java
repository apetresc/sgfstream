package com.apetresc.sgfstream;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

public class SGFSequence {

    private SGFGameTree parent;
    private List<SGFNode> nodes = new LinkedList<SGFNode>();

    public SGFSequence(SGFGameTree parent) {
        this.parent = parent;
    }

    public SGFGameTree getParent() {
        return parent;
    }

    void addNode(SGFNode node) {
        nodes.add(node);
    }

    public List<SGFNode> getNodes() {
        return nodes;
    }

    static SGFSequence fromStream(SGFStreamReader stream, SGFGameTree parent) throws IOException, IncorrectFormatException {
        SGFSequence sequence = new SGFSequence(parent);
        sequence.addNode(SGFNode.fromStream(stream, sequence));

        while (stream.peek() == ';') {
            sequence.addNode(SGFNode.fromStream(stream, sequence));
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
