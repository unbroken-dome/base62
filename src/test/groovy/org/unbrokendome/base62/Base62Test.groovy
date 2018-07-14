package org.unbrokendome.base62

import spock.lang.Specification

import java.nio.LongBuffer


class Base62Test extends Specification {

    def "Encode Long values to Base62"(long[] values, String expected) {
        when:
            def base62 = Base62.encode(values)
        then:
            base62 == expected

        where:
            values                  | expected
            []                      | ''
            [1L]                    | '00000000001'
            [1L, 42L]               | '000000000010000000000g'
            [-4468979493829131317L] | 'aK7yM6vAKKD'
    }


    def "Encode LongBuffer to Base62"(long[] values, String expected) {
        given:
            def buffer = LongBuffer.wrap(values)
        when:
            def base62 = Base62.encode(buffer)
        then:
            base62 == expected

        where:
            values                  | expected
            []                      | ''
            [1L]                    | '00000000001'
            [1L, 42L]               | '000000000010000000000g'
            [-4468979493829131317L] | 'aK7yM6vAKKD'
    }


    def "Decode Long values from Base62 into buffer"(String base62, long[] expected) {
        given:
            def longBuffer = LongBuffer.allocate(2)
        when:
            Base62.decode(base62, longBuffer)
        and:
            longBuffer.flip()
            long[] actual = new long[expected.length]
            longBuffer.get(actual)
        then:
            actual == expected

        where:
            base62                   | expected
            ''                       | []
            '00000000001'            | [1L]
            '000000000010000000000g' | [1L, 42L]
            'aK7yM6vAKKD'            | [-4468979493829131317L]
    }


    def "Decode Long values from Base62 into array"(String base62, long[] expected) {
        when:
            def values = Base62.decodeArray(base62)
        then:
            values == expected

        where:
            base62                   | expected
            ''                       | []
            '00000000001'            | [1L]
            '000000000010000000000g' | [1L, 42L]
            'aK7yM6vAKKD'            | [-4468979493829131317L]
    }


    def "Decode should throw exception if input length is not multiple of 11"() {
        when:
            Base62.decodeArray('foo')
        then:
            thrown IllegalArgumentException
    }


    def "Decode should throw exception for invalid characters"() {
        when:
            Base62.decodeArray('1234!@asdff')
        then:
            thrown IllegalArgumentException
    }


    def "Encode a UUID to Base62"(String uuid, String expected) {
        when:
            def base62 = Base62.encodeUUID UUID.fromString(uuid)
        then:
            base62 == expected

        where:
            uuid                                    | expected
            '00000000-0000-0000-0000-000000000000'  | '0000000000000000000000'
            '200b3c60-efb3-44cb-b730-47fddd18a5df'  | '2kZGknymqM7bFZY4thLzjV'
            '977d66bd-56af-4ad9-a0f7-b0dbe51e25fc'  | 'dyIujXEusfXd9r2hZoE6zM'
    }


    def "Decode a UUID from Base62"(String base62, String expected) {
        given:
            def expectedUUID = UUID.fromString(expected)
        when:
            def uuid = Base62.decodeUUID(base62)
        then:
            uuid == expectedUUID

        where:
            base62                   | expected
            '0000000000000000000000' | '00000000-0000-0000-0000-000000000000'
            '2kZGknymqM7bFZY4thLzjV' | '200b3c60-efb3-44cb-b730-47fddd18a5df'
            'dyIujXEusfXd9r2hZoE6zM' | '977d66bd-56af-4ad9-a0f7-b0dbe51e25fc'
    }
}
