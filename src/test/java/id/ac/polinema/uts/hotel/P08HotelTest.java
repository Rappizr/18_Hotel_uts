package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Hotel berisi logika saja: tidak boleh mencetak apa pun. */
@Timeout(10)
class P08HotelTest {

    private static Object hotel() throws Throwable {
        return R.create("Hotel", "Polinema Hotel");
    }

    private static Object deluxe(String name) throws Throwable {
        return R.create("DeluxeRoom", name, 2018, "City");
    }

    private static Object suite(String name) throws Throwable {
        return R.create("SuiteRoom", name, 2021, 2);
    }

    @Test
    @DisplayName("Hotel menyimpan nama dan semua field-nya private")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Hotel"));
        assertEquals("Polinema Hotel", R.call(hotel(), "getName"));
    }

    @Test
    @DisplayName("addRoom menerima StandardRoom, DeluxeRoom, dan SuiteRoom dalam satu array")
    void mixedRooms() throws Throwable {
        Object h = hotel();
        StandardRoom standard = new StandardRoom("101", "Garden Room", 2015);
        Object deluxe = deluxe("Skyline Deluxe");
        Object suite = suite("Royal Suite");
        assertEquals(true, R.call(h, "addRoom", standard));
        assertEquals(true, R.call(h, "addRoom", deluxe));
        assertEquals(true, R.call(h, "addRoom", suite));
        Object[] rooms = (Object[]) R.call(h, "getRooms");
        assertEquals(3, rooms.length, "getRooms hanya mengembalikan kamar yang terisi");
        assertSame(standard, rooms[0]);
        assertSame(deluxe, rooms[1]);
        assertSame(suite, rooms[2]);
    }

    @Test
    @DisplayName("addRoom menolak null dan menolak jika sudah penuh (20 kamar)")
    void roomCapacity() throws Throwable {
        Object h = hotel();
        assertEquals(false, R.call(h, "addRoom", (Object) null));
        for (int i = 1; i <= 20; i++) {
            assertEquals(true, R.call(h, "addRoom", suite("Suite " + i)));
        }
        assertEquals(false, R.call(h, "addRoom", suite("One too many")));
        assertEquals(20, ((Object[]) R.call(h, "getRooms")).length);
    }

    @Test
    @DisplayName("addGuest menolak id kembar dan null; findGuest mengembalikan objek yang sama")
    void guests() throws Throwable {
        Object h = hotel();
        Guest rina = new Guest("G001", "Rina");
        assertEquals(true, R.call(h, "addGuest", rina));
        assertEquals(false, R.call(h, "addGuest", new Guest("G001", "Orang Lain")));
        assertEquals(false, R.call(h, "addGuest", (Object) null));
        assertEquals(1, ((Object[]) R.call(h, "getGuests")).length);
        assertSame(rina, R.call(h, "findGuest", "G001"));
        assertNull(R.call(h, "findGuest", "G999"));
    }

    @Test
    @DisplayName("addGuest menolak jika sudah penuh (10 tamu)")
    void guestCapacity() throws Throwable {
        Object h = hotel();
        for (int i = 1; i <= 10; i++) {
            assertEquals(true, R.call(h, "addGuest", new Guest("G" + i, "Guest " + i)));
        }
        assertEquals(false, R.call(h, "addGuest", new Guest("G11", "Guest 11")));
    }

    @Test
    @DisplayName("findRoomByName tidak peduli huruf besar-kecil; null jika tidak ada")
    void findRoom() throws Throwable {
        Object h = hotel();
        StandardRoom room = new StandardRoom("101", "Garden Room", 2015);
        R.call(h, "addRoom", room);
        assertSame(room, R.call(h, "findRoomByName", "garden room"));
        assertSame(room, R.call(h, "findRoomByName", "GARDEN ROOM"));
        assertNull(R.call(h, "findRoomByName", "Unknown"));
    }

    @Test
    @DisplayName("searchByKeyword mengembalikan semua kamar yang namanya memuat kata kunci")
    void search() throws Throwable {
        Object h = hotel();
        StandardRoom garden = new StandardRoom("101", "Garden Room", 2015);
        StandardRoom gardenView = new StandardRoom("102", "Room With Garden View", 2016);
        Object deluxe = deluxe("Skyline Deluxe");
        R.call(h, "addRoom", garden);
        R.call(h, "addRoom", deluxe);
        R.call(h, "addRoom", gardenView);
        Object[] found = (Object[]) R.call(h, "searchByKeyword", "garden");
        assertEquals(2, found.length);
        assertSame(garden, found[0]);
        assertSame(gardenView, found[1]);
        Object[] none = (Object[]) R.call(h, "searchByKeyword", "zzz");
        assertEquals(0, none.length, "Tanpa hasil, kembalikan array kosong, bukan null");
    }

    @Test
    @DisplayName("checkIn berhasil: kamar terisi dan jumlah pemesanan Guest naik")
    void checkInSuccess() throws Throwable {
        Object h = hotel();
        Guest rina = new Guest("G001", "Rina");
        StandardRoom room = new StandardRoom("101", "Garden Room", 2015);
        R.call(h, "addGuest", rina);
        R.call(h, "addRoom", room);
        assertEquals(true, R.call(h, "checkIn", "G001", "Garden Room"));
        assertEquals(false, room.isAvailable());
        assertEquals(1, rina.getBookingCount());
    }

    @Test
    @DisplayName("checkIn gagal untuk Guest atau nama kamar yang tidak ada, dan kamar yang sudah terisi")
    void checkInFailures() throws Throwable {
        Object h = hotel();
        R.call(h, "addGuest", new Guest("G001", "Rina"));
        R.call(h, "addGuest", new Guest("G002", "Dewi"));
        R.call(h, "addRoom", new StandardRoom("101", "Garden Room", 2015));
        assertEquals(false, R.call(h, "checkIn", "G999", "Garden Room"));
        assertEquals(false, R.call(h, "checkIn", "G001", "Unknown"));
        assertEquals(true, R.call(h, "checkIn", "G001", "Garden Room"));
        assertEquals(false, R.call(h, "checkIn", "G002", "Garden Room"));
    }

    @Test
    @DisplayName("checkIn gagal jika Guest sudah memesan 2 kamar")
    void checkInLimit() throws Throwable {
        Object h = hotel();
        Guest rina = new Guest("G001", "Rina");
        R.call(h, "addGuest", rina);
        for (int i = 1; i <= 3; i++) {
            R.call(h, "addRoom", suite("Suite " + i));
        }
        assertEquals(true, R.call(h, "checkIn", "G001", "Suite 1"));
        assertEquals(true, R.call(h, "checkIn", "G001", "Suite 2"));
        assertEquals(false, R.call(h, "checkIn", "G001", "Suite 3"));
        assertEquals(2, rina.getBookingCount());
        Object third = R.call(h, "findRoomByName", "Suite 3");
        assertEquals(true, R.call(third, "isAvailable"));
    }

    @Test
    @DisplayName("checkOut berhasil lalu gagal jika kamar tidak sedang terisi atau data tidak ada")
    void checkOut() throws Throwable {
        Object h = hotel();
        Guest rina = new Guest("G001", "Rina");
        Object deluxe = deluxe("Skyline Deluxe");
        R.call(h, "addGuest", rina);
        R.call(h, "addRoom", deluxe);
        assertEquals(false, R.call(h, "checkOut", "G001", "Skyline Deluxe"));
        R.call(h, "checkIn", "G001", "Skyline Deluxe");
        assertEquals(true, R.call(h, "checkOut", "G001", "Skyline Deluxe"));
        assertEquals(true, R.call(deluxe, "isAvailable"));
        assertEquals(0, rina.getBookingCount());
        assertEquals(false, R.call(h, "checkOut", "G999", "Skyline Deluxe"));
        assertEquals(false, R.call(h, "checkOut", "G001", "Unknown"));
    }

    @Test
    @DisplayName("Hotel tidak boleh mencetak ke layar (pisahkan logika dan tampilan)")
    void hotelDoesNotPrint() throws Throwable {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            Object h = hotel();
            R.call(h, "addGuest", new Guest("G001", "Rina"));
            R.call(h, "addRoom", new StandardRoom("101", "Garden Room", 2015));
            R.call(h, "checkIn", "G001", "Garden Room");
            R.call(h, "checkOut", "G001", "Garden Room");
            R.call(h, "searchByKeyword", "garden");
            R.call(h, "getRooms");
        } finally {
            System.setOut(original);
        }
        assertTrue(buffer.toString(StandardCharsets.UTF_8).isEmpty(),
                "Hotel hanya berisi logika. Semua System.out.println ada di HotelApp");
    }
}
