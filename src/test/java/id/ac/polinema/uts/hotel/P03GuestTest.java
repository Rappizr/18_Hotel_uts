package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P03GuestTest {

    @Test
    @DisplayName("Semua field Guest private")
    void fieldsPrivate() {
        R.assertAllFieldsPrivate(Guest.class);
    }

    @Test
    @DisplayName("Constructor menyimpan id dan nama; jumlah pemesanan awal 0")
    void constructor() {
        Guest g = new Guest("G001", "Rina");
        assertEquals("G001", g.getGuestId());
        assertEquals("Rina", g.getName());
        assertEquals(0, g.getBookingCount());
    }

    @Test
    @DisplayName("Constructor menolak id atau nama null/kosong")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new Guest("", "Rina"));
        assertThrows(IllegalArgumentException.class, () -> new Guest(null, "Rina"));
        assertThrows(IllegalArgumentException.class, () -> new Guest("G001", "  "));
        assertThrows(IllegalArgumentException.class, () -> new Guest("G001", null));
    }

    @Test
    @DisplayName("guestId dan bookingCount read-only: tidak ada setter")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(Guest.class, "setGuestId"));
        assertFalse(R.hasPublicMethod(Guest.class, "setBookingCount"));
    }

    @Test
    @DisplayName("setName menolak nama kosong dan menjaga nama lama")
    void setName() {
        Guest g = new Guest("G001", "Rina");
        assertThrows(IllegalArgumentException.class, () -> g.setName(""));
        assertThrows(IllegalArgumentException.class, () -> g.setName(null));
        assertEquals("Rina", g.getName());
        g.setName("Rina Wijaya");
        assertEquals("Rina Wijaya", g.getName());
    }

    @Test
    @DisplayName("addBooking sampai batas 2; pemesanan ke-3 ditolak")
    void addBookingLimit() {
        Guest g = new Guest("G001", "Rina");
        assertTrue(g.addBooking());
        assertTrue(g.addBooking());
        assertFalse(g.addBooking());
        assertEquals(2, g.getBookingCount());
    }

    @Test
    @DisplayName("canBook dihitung dari jumlah pemesanan (derived getter)")
    void canBookIsDerived() {
        Guest g = new Guest("G001", "Rina");
        assertTrue(g.canBook());
        g.addBooking();
        assertTrue(g.canBook());
        g.addBooking();
        assertFalse(g.canBook());
        g.removeBooking();
        assertTrue(g.canBook());
    }

    @Test
    @DisplayName("removeBooking tidak boleh membuat jumlah pemesanan negatif")
    void removeBookingFloor() {
        Guest g = new Guest("G001", "Rina");
        assertFalse(g.removeBooking());
        assertEquals(0, g.getBookingCount());
        g.addBooking();
        assertTrue(g.removeBooking());
        assertEquals(0, g.getBookingCount());
    }
}
