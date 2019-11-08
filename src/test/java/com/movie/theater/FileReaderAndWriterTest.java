package com.movie.theater;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Input validator test class
 *
 * @author anurag repaka
 */
public class FileReaderAndWriterTest {

    @Test
    public void shouldValidateWhiteSpacesString() {
        assertFalse(FileReaderAndWriter.isValidData("   "));
    }

    @Test
    public void shouldValidateEmptyString() {
        assertFalse(FileReaderAndWriter.isValidData(""));
    }

    @Test
    public void shouldValidateLengthTwo() {
        assertFalse(FileReaderAndWriter.isValidData("R0001"));
    }

    @Test
    public void shouldValidateSeatsTypeToInteger() {
        assertFalse(FileReaderAndWriter.isValidData("R0001 3.4"));
    }
}