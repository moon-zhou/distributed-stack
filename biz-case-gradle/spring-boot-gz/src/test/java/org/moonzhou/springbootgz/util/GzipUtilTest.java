package org.moonzhou.springbootgz.util;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest
public class GzipUtilTest {

    @Test
    public void testCompressStringToByte() throws IOException {
        String input = "Hello, this is a test string to be compressed and decompressed using GZIP.";
        byte[] compressedData = GzipUtil.compressStringToByte(input);
        assertNotNull(compressedData);
        assertNotEquals(0, compressedData.length);
    }

    @Test
    public void testCompressStringToString() throws IOException {
        String input = "Hello, this is a test string to be compressed and decompressed using GZIP.";
        String compressedString = GzipUtil.compressStringToString(input);
        assertNotNull(compressedString);
        assertNotEquals("", compressedString);
    }

    @Test
    public void testDecompressByteToString() throws IOException {
        String input = "Hello, this is a test string to be compressed and decompressed using GZIP.";
        byte[] compressedData = GzipUtil.compressStringToByte(input);
        String decompressedString = GzipUtil.decompressByteToString(compressedData);
        assertEquals(input, decompressedString);
    }

    @Test
    public void testDecompressStringToString() throws IOException {
        String input = "Hello, this is a test string to be compressed and decompressed using GZIP.";
        String compressedString = GzipUtil.compressStringToString(input);
        String decompressedString = GzipUtil.decompressStringToString(compressedString);
        assertEquals(input, decompressedString);
    }

    @Test
    public void testEmptyInput() throws IOException {
        String input = "";
        byte[] compressedData = GzipUtil.compressStringToByte(input);
        assertEquals(0, compressedData.length);
        String compressedString = GzipUtil.compressStringToString(input);
        assertEquals("", compressedString);
        String decompressedString = GzipUtil.decompressByteToString(compressedData);
        assertEquals("", decompressedString);
        decompressedString = GzipUtil.decompressStringToString(compressedString);
        assertEquals("", decompressedString);
    }

    @Test
    public void testNullInput() throws IOException {
        String input = null;
        byte[] compressedData = GzipUtil.compressStringToByte(input);
        assertEquals(0, compressedData.length);
        String compressedString = GzipUtil.compressStringToString(input);
        assertEquals("", compressedString);
        String decompressedString = GzipUtil.decompressByteToString(compressedData);
        assertEquals("", decompressedString);
        decompressedString = GzipUtil.decompressStringToString(compressedString);
        assertEquals("", decompressedString);
    }
}
