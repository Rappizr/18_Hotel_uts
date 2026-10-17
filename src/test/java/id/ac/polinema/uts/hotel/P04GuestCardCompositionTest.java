package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Komposisi: GuestCard dibuat di dalam Guest dan tidak datang dari luar. */
class P04GuestCardCompositionTest {

    @Test
    @DisplayName("GuestCard ada dan field-nya private")
    void cardClass() {
        R.assertAllFieldsPrivate(R.cls("GuestCard"));
    }

    @Test
    @DisplayName("Guest punya kartu bernomor 'GC-' + guestId")
    void guestHasCard() throws Throwable {
        Guest g = new Guest("G001", "Rina");
        Object card = R.call(g, "getCard");
        assertNotNull(card, "Guest harus membuat GuestCard sendiri di constructor");
        assertEquals("GC-G001", R.call(card, "getNumber"));
    }

    @Test
    @DisplayName("getCard selalu mengembalikan kartu yang sama untuk satu Guest")
    void sameCardEveryTime() throws Throwable {
        Guest g = new Guest("G001", "Rina");
        assertSame(R.call(g, "getCard"), R.call(g, "getCard"));
    }

    @Test
    @DisplayName("Dua Guest punya dua kartu berbeda")
    void differentGuestsDifferentCards() throws Throwable {
        Guest a = new Guest("G001", "Rina");
        Guest b = new Guest("G002", "Dewi");
        assertNotSame(R.call(a, "getCard"), R.call(b, "getCard"));
        assertEquals("GC-G002", R.call(R.call(b, "getCard"), "getNumber"));
    }

    @Test
    @DisplayName("Constructor Guest tidak menerima GuestCard dari luar (komposisi)")
    void cardIsNotPassedIn() {
        Class<?> card = R.cls("GuestCard");
        for (Constructor<?> k : Guest.class.getConstructors()) {
            for (Class<?> p : k.getParameterTypes()) {
                assertNotEquals(card, p,
                        "Guest harus MEMBUAT GuestCard sendiri, bukan menerimanya dari parameter");
            }
        }
    }
}
