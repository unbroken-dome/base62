package org.unbrokendome.base62;

import javax.annotation.MatchesPattern;
import javax.annotation.Nonnull;
import java.nio.LongBuffer;
import java.util.UUID;


public class Base62 {

    /**
     * Private constructor, to prevent instantiation
     */
    private Base62() {
    }


    /**
     * Converts a number of {@code long} values to a Base62-encoded string.
     *
     * The length of the resulting string will be 11 times the number of arguments.
     * Calling the method with an empty argument array (i.e. `encode()`)
     * will return an empty string.
     *
     * @param values the {@code long} values to encode
     * @return the Base62-encoded string
     */
    @Nonnull
    @MatchesPattern("([0-9A-Za-z]{11})*")
    public static String encode(long... values) {
        Base62Encoder encoder = new Base62Encoder();
        for (long value : values) {
            encoder.accept(value);
        }
        return encoder.toString();
    }


    /**
     * Converts the remaining contents of a {@link LongBuffer} to a Base62-encoded string.
     *
     * @param buffer the {@code LongBuffer} to read values from
     * @return the Base62-encoded string
     */
    @Nonnull
    @MatchesPattern("([0-9A-Za-z]{11})*")
    public static String encode(LongBuffer buffer) {
        Base62Encoder encoder = new Base62Encoder();
        while (buffer.hasRemaining()) {
            encoder.accept(buffer.get());
        }
        return encoder.toString();
    }


    /**
     * Decodes a Base62-encoded string into a {@link LongBuffer}.
     *
     * The length of the input string must be a multiple of 11, and must only contain
     * alphanumeric ASCII characters. Passing an empty string will result in no modification
     * to the output buffer.
     *
     * @param input a Base62-encoded input string
     * @param output a {@link LongBuffer} that will receive the decoded values
     * @throws IllegalArgumentException if the input is not a valid Base62-encoded string
     * @throws java.nio.BufferOverflowException if the {@code output} buffer has not enough capacity to hold
     *         all decoded values
     */
    public static void decode(
            @MatchesPattern("([0-9A-Za-z]{11})*") CharSequence input,
            LongBuffer output) {
        Base62Decoder decoder = new Base62Decoder(input);
        decoder.decode(output);
    }


    /**
     * Converts a Base62-encoded string to an array of `Long` values.
     *
     * The length of the input string must be a multiple of 11, and must only contain
     * alphanumeric ASCII characters. Passing an empty string will return an empty array.
     *
     * @param input a Base62-encoded input string
     * @return the decoded values
     * @throws IllegalArgumentException if the input is not a valid Base62-encoded string
     */
    @Nonnull
    public static long[] decodeArray(
            @MatchesPattern("([0-9A-Za-z]{11})*") CharSequence input) {
        Base62Decoder decoder = new Base62Decoder(input);
        return decoder.toArray();
    }


    /**
     * Converts a UUID to a Base62-encoded string.
     *
     * The resulting string will always be 22 characters long.
     *
     * @param uuid the input UUID
     * @return the Base62-encoded string
     */
    @Nonnull
    @MatchesPattern("[0-9A-Za-z]{22}")
    public static String encodeUUID(UUID uuid) {
        return encode(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }


    /**
     * Converts a Base62-encoded string to a UUID.
     *
     * The input string must be exactly 22 characters long and must only contain
     * alphanumeric ASCII characters.
     *
     * @param base62 the Base62-encoded input string
     * @return the decoded UUID
     * @throws IllegalArgumentException if the input is not a valid Base62-encoded string
     */
    @Nonnull
    public static UUID decodeUUID(
            @MatchesPattern("[0-9A-Za-z]{22}") String base62) {
        long[] longs = decodeArray(base62);
        return new UUID(longs[0], longs[1]);
    }
}
