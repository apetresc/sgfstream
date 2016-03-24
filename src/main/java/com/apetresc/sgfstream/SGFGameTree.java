package com.apetresc.sgfstream;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;

public class SGFGameTree {

    private SGFSequence sequence;
    private SGFGameTree parent;
    private SGFGameTree[] subtrees;

    public SGFSequence getSequence() {
        return sequence;
    }
    
    public SGFGameTree getParent() {
        return parent;
    }

    public SGFGameTree[] getSubtrees() {
        return subtrees;
    }
    
    public SGFGameTree(SGFGameTree parent) {
        this.parent = parent;
    }

    static SGFGameTree fromStream(SGFStreamReader stream, SGFGameTree parent) throws IOException, IncorrectFormatException {
        SGFGameTree gameTree = new SGFGameTree(parent);
        ArrayList subtrees = new ArrayList();

        if (!(stream.readCharacter() == '(')) {
            throw new IncorrectFormatException("Expected character '(' to start a sequence", stream);
        }

        gameTree.sequence = SGFSequence.fromStream(stream, gameTree);

        while (!(stream.peek() == ')')) {
            subtrees.add(SGFGameTree.fromStream(stream, gameTree));
        }

        gameTree.subtrees = (SGFGameTree[]) subtrees.toArray(new SGFGameTree[0]);
        stream.readCharacter();

        return gameTree;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("(");
        result.append(sequence.toString());
        for (int i = 0; i < subtrees.length; i++) {
            result.append(subtrees[i].toString());
        }
        result.append(")");
        return result.toString();
    }
}
