package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Menjalankan HotelApp dengan input skrip (bukan keyboard sungguhan),
 * lalu memeriksa teks yang dicetak.
 */
@Timeout(10)
class P08HotelAppTest {

    private static Object hotel() throws Throwable {
        return R.create("Hotel", "Polinema Hotel");
    }

    private static String run(Object hotel, String... lines) throws Throwable {
        String input = String.join("\n", lines) + "\n";
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Object app = R.create("HotelApp", hotel, new Scanner(input), out);
        R.call(app, "run");
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static void assertHas(String output, String expected) {
        assertTrue(output.contains(expected),
                "Output harus memuat \"" + expected + "\". Output sebenarnya:\n" + output);
    }

    @Test
    @DisplayName("Menu tampil lengkap dan pilihan 0 mencetak 'Thank you, goodbye'")
    void menuAndExit() throws Throwable {
        String out = run(hotel(), "0");
        assertHas(out, "=== Polinema Hotel ===");
        for (String line : new String[] {"1. List rooms", "2. Add standard room", "3. Add deluxe room",
                "4. Add suite", "5. Register guest", "6. Check in", "7. Check out", "8. Search rooms",
                "9. List guests", "0. Exit"}) {
            assertHas(out, line);
        }
        assertHas(out, "Thank you, goodbye");
    }

    @Test
    @DisplayName("Pilihan yang tidak dikenal mencetak 'Unknown option' lalu menu muncul lagi")
    void unknownOption() throws Throwable {
        String out = run(hotel(), "x", "42", "0");
        assertHas(out, "Unknown option");
        assertEquals(3, out.split("0\\. Exit", -1).length - 1, "Menu harus dicetak ulang di setiap putaran");
    }

    @Test
    @DisplayName("Daftar kosong mencetak 'No rooms' dan 'No guests'")
    void emptyLists() throws Throwable {
        String out = run(hotel(), "1", "9", "0");
        assertHas(out, "No rooms");
        assertHas(out, "No guests");
    }

    @Test
    @DisplayName("Tambah kamar standard lalu tampilkan: describe, status, dan harga per malam")
    void addStandardAndList() throws Throwable {
        Object h = hotel();
        String out = run(h, "2", "101", "Garden Room", "2015", "Swimming Pool", "Floor 1", "1", "0");
        assertHas(out, "Room added");
        assertHas(out, "Garden Room (built 2015) facility: Swimming Pool | Available | 400000/night");
        assertEquals(1, ((Object[]) R.call(h, "getRooms")).length);
    }

    @Test
    @DisplayName("Tambah kamar standard tanpa fasilitas: nama fasilitas dikosongkan")
    void addStandardWithoutFacility() throws Throwable {
        String out = run(hotel(), "2", "102", "Lake View", "1999", "", "1", "0");
        assertHas(out, "Room added");
        assertHas(out, "Lake View (built 1999) facility: None | Available | 400000/night");
    }

    @Test
    @DisplayName("Tambah kamar deluxe dan suite")
    void addDeluxeAndSuite() throws Throwable {
        String out = run(hotel(), "3", "Skyline Deluxe", "2018", "City", "4", "Royal Suite", "2021", "3", "1", "0");
        assertHas(out, "Skyline Deluxe (built 2018) [City view] | Available | 650000/night");
        assertHas(out, "Royal Suite (built 2021) | Available | 1200000/night");
    }

    @Test
    @DisplayName("Tahun yang bukan angka positif mencetak 'Not a valid number' dan kamar tidak ditambah")
    void invalidYearNumber() throws Throwable {
        Object h = hotel();
        String out = run(h, "2", "101", "Garden Room", "abc", "4", "Royal Suite", "-5", "3", "0");
        assertHas(out, "Not a valid number");
        assertEquals(0, ((Object[]) R.call(h, "getRooms")).length);
    }

    @Test
    @DisplayName("Tahun sebelum 1950 atau nomor kamar kosong mencetak 'Rejected' dan kamar tidak ditambah")
    void rejectedInput() throws Throwable {
        Object h = hotel();
        String out = run(h, "2", "101", "Old Room", "1949", "2", "", "Garden Room", "2015", "0");
        assertHas(out, "Rejected");
        assertEquals(0, ((Object[]) R.call(h, "getRooms")).length);
    }

    @Test
    @DisplayName("Daftar tamu baru, ID kembar ditolak")
    void registerGuest() throws Throwable {
        String out = run(hotel(), "5", "G001", "Rina", "5", "G001", "Dewi", "9", "0");
        assertHas(out, "Guest registered");
        assertHas(out, "Guest ID already exists");
        assertHas(out, "G001 - Rina (0 bookings)");
    }

    @Test
    @DisplayName("Alur lengkap: tambah tamu dan kamar, check in, lihat status, check out")
    void checkInAndOut() throws Throwable {
        Object h = hotel();
        String out = run(h,
                "5", "G001", "Rina",
                "3", "Skyline Deluxe", "2018", "City",
                "6", "G001", "Skyline Deluxe",
                "1", "9",
                "7", "G001", "Skyline Deluxe",
                "1", "9",
                "0");
        assertHas(out, "Check-in successful");
        assertHas(out, "Skyline Deluxe (built 2018) [City view] | Occupied | 650000/night");
        assertHas(out, "G001 - Rina (1 bookings)");
        assertHas(out, "Check-out successful");
        assertHas(out, "Skyline Deluxe (built 2018) [City view] | Available | 650000/night");
        assertHas(out, "G001 - Rina (0 bookings)");
    }

    @Test
    @DisplayName("Check in dan check out yang tidak valid mencetak 'Check-in failed' dan 'Check-out failed'")
    void failures() throws Throwable {
        String out = run(hotel(), "6", "X", "Y", "7", "X", "Y", "0");
        assertHas(out, "Check-in failed");
        assertHas(out, "Check-out failed");
    }

    @Test
    @DisplayName("Cari kamar: tampilkan yang cocok, atau 'No rooms found'")
    void search() throws Throwable {
        Object h = hotel();
        R.call(h, "addRoom", new StandardRoom("101", "Garden Room", 2015));
        R.call(h, "addRoom", R.create("DeluxeRoom", "Skyline Deluxe", 2018, "City"));
        String out = run(h, "8", "garden", "8", "zzz", "0");
        assertHas(out, "Garden Room (built 2015) facility: None");
        assertHas(out, "No rooms found");
        assertTrue(!out.contains("Skyline Deluxe (built"), "Skyline Deluxe tidak cocok dengan kata kunci 'garden'");
    }

    @Test
    @DisplayName("Main mencetak sapaan, lalu menjalankan HotelApp dengan data contoh")
    void mainStartsTheApp() {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes(StandardCharsets.UTF_8)));
        try {
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        String out = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(out.startsWith("Welcome to Polinema Hotel"),
                "Baris pertama output Main harus sapaan. Output sebenarnya:\n" + out);
        assertHas(out, "=== Polinema Hotel ===");
        assertHas(out, "Thank you, goodbye");
        assertTrue(!out.contains("No rooms"),
                "Main harus mengisi data contoh sebelum menjalankan HotelApp");
    }
}
