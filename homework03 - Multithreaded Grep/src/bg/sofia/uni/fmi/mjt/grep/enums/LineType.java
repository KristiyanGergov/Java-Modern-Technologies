package bg.sofia.uni.fmi.mjt.grep.enums;

import bg.sofia.uni.fmi.mjt.grep.exceptions.UnknownLineTypeException;

public enum LineType {

    NO_EXTRA_PARAMS,
    WHOLE_WORDS,
    CASE_INSENSITIVITY,
    WHOLE_WORDS_CASE_INSENSITIVITY;

    public static LineType getType(String type) {
        switch (type) {
            case "-w":
                return WHOLE_WORDS;
            case "-i":
                return CASE_INSENSITIVITY;
            case "":
                return NO_EXTRA_PARAMS;
            case "-w-i":
                return WHOLE_WORDS_CASE_INSENSITIVITY;
            default:
                throw new UnknownLineTypeException("Provided input was incorrect!");
        }
    }

}
