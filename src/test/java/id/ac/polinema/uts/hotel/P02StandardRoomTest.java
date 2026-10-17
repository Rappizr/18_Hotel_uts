package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P02StandardRoomTest {

    @Test
    @DisplayName("Constructor menyimpan roomNo, name, dan yearBuilt")
    void constructorAndGetters() {
        StandardRoom r = new StandardRoom("101", "Garden Room", 2015);
        assertEquals("101", r.getRoomNo());
        assertEquals("Garden Room", r.getName());
        assertEquals(2015, r.getYearBuilt());
    }

    @Test
    @DisplayName("Kamar baru langsung tersedia")
    void newRoomIsAvailable() {
        assertTrue(new StandardRoom("1", "Garden Room", 2015).isAvailable());
    }

    @Test
    @DisplayName("reserve: berhasil sekali, lalu gagal karena sudah terisi")
    void reserve() {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        assertTrue(r.reserve());
        assertFalse(r.isAvailable());
        assertFalse(r.reserve());
    }

    @Test
    @DisplayName("release membuat kamar tersedia lagi")
    void release() {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        r.reserve();
        r.release();
        assertTrue(r.isAvailable());
        assertTrue(r.reserve());
    }

    @Test
    @DisplayName("Memesan satu kamar tidak mengubah kamar lain")
    void objectsAreIndependent() {
        StandardRoom a = new StandardRoom("1", "Garden Room", 2015);
        StandardRoom b = new StandardRoom("2", "Lake View", 1999);
        a.reserve();
        assertFalse(a.isAvailable());
        assertTrue(b.isAvailable());
        assertEquals("Lake View", b.getName());
    }
}
