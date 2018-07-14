package org.unbrokendome.base62;

import java.nio.CharBuffer;
import java.util.function.LongConsumer;


final class Base62Encoder implements LongConsumer {

    private final StringBuilder builder = new StringBuilder();
    private final CharBuffer charBuffer = CharBuffer.allocate(11);


    @Override
    public void accept(long value) {

        long v = value;
        if (v < 0) {
            for (int i = 10; i > 0; i--) {
                putDigit(i, (int) (-(v % 62)));
                v /= 62;
            }
            putDigit(0, (int) (-(v - 31)));

        } else {
            for (int i = 10; i > 0; i--) {
                putDigit(i, (int) (v % 62));
                v /= 62;
            }
            putDigit(0, (int) v);
        }

        // No need to call flip() on the charBuffer because we use absolute put() operations
        // which don't change the buffer's position

        builder.append(charBuffer);
        charBuffer.clear();
    }


    private void putDigit(int index, int charIndex) {
        charBuffer.put(index, Base62Digits.getDigit(charIndex));
    }


    @Override
    public String toString() {
        return builder.toString();
    }
}
