package com.apetresc.sgfstream;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class SGFProperty {

    public static final String PLAYER_WHITE = "PW";
    public static final String PLAYER_BLACK = "PB";
    public static final String RANK_WHITE = "WR";
    public static final String RANK_BLACK = "BR";
    public static final String DATE = "DT";
    public static final String EVENT = "EV";
    public static final String PLACE = "PC";
    public static final String COMMENT = "C";
    public static final String WHITE_MOVE = "W";
    public static final String BLACK_MOVE = "B";
    
    String propIdent;
    String propValue;
    List propValues;

    void addValue(String value) {
        if (propValue == null && propValues == null) {
            propValue = value;
            return;
        }
        else {
            propValue = null;
            propValues.add(value);
        }
    }

    public String[] getValues() {
        if (propValues == null) {
            return new String[] { propValue };
        } else {
            return (String[]) propValues.toArray(new String[0]);
        }
    }

    public String getIdent() {
        return propIdent;
    }


    public SGFProperty(String propIdent) {
        this.propIdent = propIdent;
    }

    private String escapePropertyValue(String unescapedString) {
        return unescapedString.replaceAll(Pattern.quote("]"), "\\]");
    }

    static SGFProperty fromStream(SGFStreamReader stream) throws IOException, IncorrectFormatException {
        StringBuilder propIdent = new StringBuilder();
        while (Character.isUpperCase(stream.peek())) {
            propIdent.append(stream.readCharacter());
        }
        if (!(stream.peek() == '[')) {
            throw new IncorrectFormatException();
        }

        SGFProperty property = new SGFProperty(propIdent.toString());

        while (stream.peek() == '[') {
            stream.readCharacter();
            StringBuilder propValue = new StringBuilder();
            boolean escape = false;

            while (!escape && (stream.peek(false) != ']')) {
                escape = false;
                char nextChar = stream.readCharacter(false);

                if (nextChar == '\\') {
                    escape = true;
                } else {
                    propValue.append(nextChar);
                }
            }

            property.addValue(propValue.toString());
            stream.readCharacter();
        }

        return property;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(propIdent);
        String[] values = getValues();
        for (int i = 0; i < values.length; i++) {
            result.append("[" + escapePropertyValue(values[i]) + "]");
        }
        return result.toString();
    }

}
