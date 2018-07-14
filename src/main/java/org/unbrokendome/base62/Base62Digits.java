package org.unbrokendome.base62;

import java.util.Arrays;


final class Base62Digits {

    private static final char[] BASE_DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int[] DIGIT_INDICES;


    static {
        DIGIT_INDICES = new int['z' + 1];
        Arrays.fill(DIGIT_INDICES, -1);

        for (int i = 0; i < BASE_DIGITS.length; i++) {
            char ch = BASE_DIGITS[i];
            DIGIT_INDICES[ch] = i;
        }
    }


    /** Private constructor to prevent instantiation of this class */
    private Base62Digits() {
    }


    /**
     * Gets the Base62 digit for an index.
     *
     * @param index the index, must be between 0 and 61
     */
    static char getDigit(int index) {
        return BASE_DIGITS[index];
    }


    /**
     * Gets the index of a character inside the Base62 character set.
     *
     * @param ch the character
     * @throws IllegalArgumentException if {@code ch} is not a valid Base62 character
     */
    static int getDigitIndex(char ch) {
        int index = (ch < DIGIT_INDICES.length) ? DIGIT_INDICES[ch] : -1;
        if (index < 0) {
            throw new IllegalArgumentException("Not a valid Base62 character: '" + ch + "'");
        }
        return index;
    }
}
