package com.testapp;

import com.testapp.model.Result;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceMatchHandlerTest {

    @ParameterizedTest
    @MethodSource("stringArrayProvider")
    void handle(String[] args) throws IOException {
        PriceMatchHandler priceMatchHandler = new PriceMatchHandler();
        Result result = priceMatchHandler.handle(args);
        assertEquals(args[3], priceMatchHandler.getStringResult(result));
    }

    @ParameterizedTest
    @MethodSource("stringArrayExceptionProvider")
    void handle_should_return_exception(Object[] args) {
        PriceMatchHandler priceMatchHandler = new PriceMatchHandler();
        assertThrows((Class<? extends Throwable>) args[args.length - 1], () -> {
            String[] newArray = Arrays.copyOfRange(args, 0, args.length - 1, String[].class);
            priceMatchHandler.handle(newArray);
        });
    }

    static Stream<Arguments> stringArrayProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "2000", "2", "Candy Bar 500, Headphones 1400"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "1400", "2", "Candy Bar 500, Paperback Book 700"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "1000", "2", "Not possible"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "2900", "2", "Paperback Book 700, Earmuffs 2000"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "14000", "5", "Candy Bar 500, Detergent 1000, Headphones 1400, Earmuffs 2000, Bluetooth HiFi Stereo 9000"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "2000", "3", "Not possible"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "10400", "4", "Detergent 1000, Headphones 1400, Earmuffs 2000, Bluetooth Stereo 6000"}),
                Arguments.of((Object) new String[]{"src/test/resources/prices_test.txt", "10400", "1", "Bluetooth HiFi Stereo 9000"})
        );
    }

    static Stream<Arguments> stringArrayExceptionProvider() {
        return Stream.of(
                Arguments.of((Object) new Object[]{"src/test/resources/prices.tx", "2000", "2", NoSuchFileException.class}),
                Arguments.of((Object) new Object[]{"src/test/resources/prices_test.txt", IllegalArgumentException.class}),
                Arguments.of((Object) new Object[]{"src/test/resources/prices_test.txt", "10.0", IllegalArgumentException.class}),
                Arguments.of((Object) new Object[]{"src/test/resources/prices_test_incorrect.txt", "10000", IllegalArgumentException.class})
        );
    }
}