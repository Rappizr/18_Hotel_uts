package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class P01MainOutputTest {

    @Test
    @Timeout(10)
    @DisplayName("Baris pertama output Main adalah 'Welcome to Polinema Hotel'")
    void firstLineIsWelcome() {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        // Input kosong: jika Main sudah menjalankan HotelApp, aplikasi langsung selesai.
        System.setIn(new ByteArrayInputStream(new byte[0]));
        try {
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        String first = buffer.toString(StandardCharsets.UTF_8).lines().findFirst().orElse("");
        assertEquals("Welcome to Polinema Hotel", first,
                "Baris pertama output harus persis 'Welcome to Polinema Hotel'");
    }
}
