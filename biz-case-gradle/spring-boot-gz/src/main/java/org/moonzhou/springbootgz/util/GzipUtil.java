package org.moonzhou.springbootgz.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {

    public static byte[] compressStringToByte(String input) throws IOException {
        if (input == null || input.isEmpty()) {
            return new byte[0];
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            gzipOutputStream.write(input.getBytes(StandardCharsets.UTF_8));
        }
        return outputStream.toByteArray();
    }

    public static String compressStringToString(String input) throws IOException {
        return Base64.getEncoder().encodeToString(compressStringToByte(input));
    }

    public static String decompressByteToString(byte[] compressedData) throws IOException {
        if (compressedData == null || compressedData.length == 0) {
            return "";
        }
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData));
             InputStreamReader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
            StringBuilder decompressedString = new StringBuilder();
            char[] buffer = new char[1024];
            int len;
            while ((len = reader.read(buffer)) > 0) {
                decompressedString.append(buffer, 0, len);
            }
            return decompressedString.toString();
        }
    }

    public static String decompressStringToString(String compressedData) throws IOException {
        if (compressedData == null || compressedData.isEmpty()) {
            return "";
        }
        return decompressByteToString(Base64.getDecoder().decode(compressedData));
    }

    // public static void main(String[] args) throws IOException {
    //     String originalString = "Hello, this is a test string to be compressed and decompressed using GZIP.";
    //     byte[] compressedData = GzipUtil.compressStringToByte(originalString);
    //     System.out.println(new String(compressedData));
    //     String decompressedString = GzipUtil.decompressByteToString(compressedData);
    //     System.out.println("Original String: " + originalString);
    //     System.out.println("Decompressed String: " + decompressedString);
    //
    //     String zipString = GzipUtil.compressStringToString(originalString);
    //     String zipResult = GzipUtil.decompressStringToString(zipString);
    //     System.out.println(zipString);
    //     System.out.println(zipResult);
    // }
}
