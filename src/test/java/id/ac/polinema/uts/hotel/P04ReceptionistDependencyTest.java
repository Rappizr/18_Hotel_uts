package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Dependensi: Receptionist hanya memakai Guest dan StandardRoom lewat parameter. */
class P04ReceptionistDependencyTest {

    private static Object receptionist() throws Throwable {
        return R.create("Receptionist");
    }

    @Test
    @DisplayName("Receptionist tidak menyimpan apa pun sebagai field (dependensi, bukan asosiasi)")
    void noFields() {
        Class<?> c = R.cls("Receptionist");
        assertEquals(0, c.getDeclaredFields().length,
                "Receptionist hanya BERGANTUNG pada Guest dan StandardRoom lewat parameter, jangan simpan sebagai field");
    }

    @Test
    @DisplayName("checkIn berhasil: kamar terisi dan jumlah pemesanan Guest naik")
    void checkInSuccess() throws Throwable {
        Object r = receptionist();
        Guest g = new Guest("G001", "Rina");
        StandardRoom room = new StandardRoom("1", "Garden Room", 2015);
        assertEquals(true, R.call(r, "checkIn", g, room));
        assertFalse(room.isAvailable());
        assertEquals(1, g.getBookingCount());
    }

    @Test
    @DisplayName("checkIn gagal jika kamar sudah terisi; jumlah pemesanan tidak berubah")
    void checkInRoomTaken() throws Throwable {
        Object r = receptionist();
        Guest a = new Guest("G001", "Rina");
        Guest b = new Guest("G002", "Dewi");
        StandardRoom room = new StandardRoom("1", "Garden Room", 2015);
        R.call(r, "checkIn", a, room);
        assertEquals(false, R.call(r, "checkIn", b, room));
        assertEquals(0, b.getBookingCount());
    }

    @Test
    @DisplayName("checkIn gagal jika Guest sudah memesan 2 kamar; kamar tetap tersedia")
    void checkInGuestAtLimit() throws Throwable {
        Object r = receptionist();
        Guest g = new Guest("G001", "Rina");
        for (int i = 1; i <= 2; i++) {
            R.call(r, "checkIn", g, new StandardRoom("" + i, "Room " + i, 2000));
        }
        StandardRoom third = new StandardRoom("3", "Room 3", 2000);
        assertEquals(false, R.call(r, "checkIn", g, third));
        assertTrue(third.isAvailable());
        assertEquals(2, g.getBookingCount());
    }

    @Test
    @DisplayName("checkOut berhasil: kamar tersedia lagi dan jumlah pemesanan turun")
    void checkOutSuccess() throws Throwable {
        Object r = receptionist();
        Guest g = new Guest("G001", "Rina");
        StandardRoom room = new StandardRoom("1", "Garden Room", 2015);
        R.call(r, "checkIn", g, room);
        assertEquals(true, R.call(r, "checkOut", g, room));
        assertTrue(room.isAvailable());
        assertEquals(0, g.getBookingCount());
    }

    @Test
    @DisplayName("checkOut gagal untuk kamar yang tidak sedang terisi")
    void checkOutNotOccupied() throws Throwable {
        Object r = receptionist();
        Guest g = new Guest("G001", "Rina");
        assertEquals(false, R.call(r, "checkOut", g, new StandardRoom("1", "Garden Room", 2015)));
        assertEquals(0, g.getBookingCount());
    }
}
