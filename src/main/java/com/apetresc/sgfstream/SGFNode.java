package com.apetresc.sgfstream;

import java.io.IOException;
import java.util.*;

public class SGFNode {

    private Map<String, SGFProperty> properties = new HashMap<String, SGFProperty>();
    private SGFSequence parent;
    
    private Set captures;
    private String previousComment;
    private int[] previousMove;

    public SGFNode(SGFSequence parent) {
        this.parent = parent;
    }

    void addProperty(SGFProperty property) {
        properties.put(property.getIdent(), property);
    }

    public Map<String, SGFProperty> getProperties() {
        return properties;
    }

    static SGFNode fromStream(SGFStreamReader stream, SGFSequence parent) throws IOException, IncorrectFormatException {
        SGFNode node = new SGFNode(parent);
        if (!(stream.readCharacter() == ';')) {
            throw new IncorrectFormatException();
        }

        while (Character.isUpperCase(stream.peek())) {
            node.addProperty(SGFProperty.fromStream(stream));
        }

        return node;
    }

    public SGFSequence getParent() {
        return parent;
    }

    public BoardPosition getBoardPosition() {
        Stack<SGFGameTree> treeStack = new Stack<SGFGameTree>();
        treeStack.push(parent.getParent());
        while (treeStack.peek().getParent() != null) {
            treeStack.push(treeStack.peek().getParent());
        }

        BoardPosition boardPosition = new BoardPosition(
                Integer.parseInt(treeStack.peek().getSequence().getNodes().get(0).getProperties().get("SZ").getValues()[0]));

        outer:
        while (!treeStack.empty()) {
            SGFGameTree tree = treeStack.pop();
            SGFSequence sequence = tree.getSequence();
            inner:
            for (SGFNode node : sequence.getNodes()) {
                boardPosition.applyNode(node);
                if (this == node) {
                    break outer;
                }
            }
        }

        return boardPosition;
    }

    public void setCaptures(Set captures) {
        this.captures = captures;
    }
    
    public Set getCaptures() {
        return captures;
    }
    
    public void setPreviousComment(String previousComment) {
        this.previousComment = previousComment;
    }
    
    public String getPreviousComment() {
        return previousComment;
    }

    public void setPreviousMove(int[] point) {
        previousMove = point;
    }
    
    public int[] getPreviousMove() {
        return previousMove;
    }
    
    public String toString() {
        StringBuilder result = new StringBuilder(";");
        Iterator<SGFProperty> i = properties.values().iterator();
        while (i.hasNext()) {
            result.append(i.next().toString());
        }
        return result.toString();
    }

}
