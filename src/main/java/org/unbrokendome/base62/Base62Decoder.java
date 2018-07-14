package org.unbrokendome.base62;

import java.nio.CharBuffer;
import java.nio.LongBuffer;


final class Base62Decoder {

    private final CharBuffer charBuffer;


    Base62Decoder(CharSequence input) {
        if (input.length() % 11 != 0) {
            throw new IllegalArgumentException("input length must be a multiple of 11");
        }

        this.charBuffer = CharBuffer.wrap(input);
    }


    private long decodeLong() {
        boolean negative = false;
        long value;

        char ch = charBuffer.get();
        int digit = Base62Digits.getDigitIndex(ch);
        if (digit >= 31) {
            digit -= 31;
            negative = true;
        }
        value = (long) digit;

        for (int j = 1; j < 11; j++) {
            ch = charBuffer.get();
            digit = Base62Digits.getDigitIndex(ch);
            value = value * 62 + digit;
        }
        if (negative) {
            value = -value;
        }

        return value;
    }


    void decode(LongBuffer output) {
        while (charBuffer.hasRemaining()) {
            output.put(decodeLong());
        }
    }


    long[] toArray() {
        int numLongs = charBuffer.remaining() / 11;
        long[] result = new long[numLongs];
        for (int i = 0; i < numLongs; i++) {
            result[i] = decodeLong();
        }
        return result;
    }
}
