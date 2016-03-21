package com.apetresc.sgfstream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SGFNode {

    Map<String, SGFProperty> properties = new HashMap<String, SGFProperty>();
    
    private Set captures;
    private String previousComment;
    private int[] previousMove;

    void addProperty(SGFProperty property) {
        properties.put(property.getIdent(), property);
    }

    public Map<String, SGFProperty> getProperties() {
        return properties;
    }

    static SGFNode fromStream(SGFStreamReader stream) throws IOException, IncorrectFormatException {
        SGFNode node = new SGFNode();
        if (!(stream.readCharacter() == ';')) {
            throw new IncorrectFormatException();
        }

        while (Character.isUpperCase(stream.peek())) {
            node.addProperty(SGFProperty.fromStream(stream));
        }

        return node;
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
