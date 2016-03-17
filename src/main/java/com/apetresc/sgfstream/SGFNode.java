package com.apetresc.sgfstream;

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

    static SGFNode fromString(StringBuffer sgf) throws IncorrectFormatException {
        SGFNode node = new SGFNode();
        if (!(sgf.charAt(0) == ';')) {
            throw new IncorrectFormatException();
        }
        sgf.deleteCharAt(0);

        /* Remove leading whitespace */
        while (Character.isWhitespace(sgf.charAt(0))) {
            sgf.deleteCharAt(0);
        }

        while (Character.isUpperCase(sgf.charAt(0))) {
            node.addProperty(SGFProperty.fromString(sgf));

            /* Remove leading whitespace */
            while (Character.isWhitespace(sgf.charAt(0))) {
                sgf.deleteCharAt(0);
            }
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
        String result = ";";
        Iterator<SGFProperty> i = properties.values().iterator();
        while (i.hasNext()) {
            result += i.next().toString();
        }
        return result;
    }

}
