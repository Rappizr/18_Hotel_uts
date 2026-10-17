package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Overloading: nama method atau constructor sama, parameter berbeda. */
class P07OverloadTest {

    @Test
    @DisplayName("HotelRoom punya dua method extendStay dengan parameter berbeda")
    void twoExtendStay() {
        int count = 0;
        for (Method m : R.cls("HotelRoom").getDeclaredMethods()) {
            if (m.getName().equals("extendStay")) {
                count++;
            }
        }
        assertEquals(2, count, "Harus ada extendStay() dan extendStay(int nights)");
    }

    @Test
    @DisplayName("extendStay() menambah 2 malam")
    void extendDefault() throws Throwable {
        Object deluxe = R.create("DeluxeRoom", "Skyline Deluxe", 2018, "City");
        assertEquals(1, R.call(deluxe, "getTotalNights"));
        R.call(deluxe, "extendStay");
        assertEquals(3, R.call(deluxe, "getTotalNights"));
    }

    @Test
    @DisplayName("extendStay(int) menambah sebanyak malam yang diberikan dan bisa berulang")
    void extendNights() throws Throwable {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        assertEquals(1, R.call(r, "getTotalNights"));
        R.call(r, "extendStay", 3);
        R.call(r, "extendStay", 2);
        assertEquals(6, R.call(r, "getTotalNights"));
    }

    @Test
    @DisplayName("getTotalPrice = pricePerNight() x getTotalNights(), sesuai jenis kamar")
    void totalPrice() throws Throwable {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        Object suite = R.create("SuiteRoom", "Royal Suite", 2021, 3);
        R.call(r, "extendStay", 2);
        R.call(suite, "extendStay");
        assertEquals(1200000, R.call(r, "getTotalPrice"));
        assertEquals(3600000, R.call(suite, "getTotalPrice"));
    }

    @Test
    @DisplayName("extendStay(int) menolak 0 dan negatif; total tidak berubah")
    void extendRejectsNonPositive() throws Throwable {
        Object s = R.create("SuiteRoom", "Royal Suite", 2021, 3);
        assertThrows(IllegalArgumentException.class, () -> R.call(s, "extendStay", 0));
        assertThrows(IllegalArgumentException.class, () -> R.call(s, "extendStay", -4));
        assertEquals(1, R.call(s, "getTotalNights"));
    }

    @Test
    @DisplayName("Constructor StandardRoom(roomNo, name, yearBuilt, facility) langsung mengisi fasilitas")
    void standardConstructorWithFacility() throws Throwable {
        Facility pool = new Facility("Swimming Pool", "Floor 1");
        Object r = R.create("StandardRoom", "101", "Garden Room", 2015, pool);
        assertSame(pool, R.call(r, "getFacility"));
        assertEquals("Garden Room", R.call(r, "getName"));
        assertEquals("101", R.call(r, "getRoomNo"));
        assertEquals(true, R.call(r, "isAvailable"));
    }

    @Test
    @DisplayName("Constructor SuiteRoom(name, yearBuilt) memberi 2 tempat tidur; versi 3 parameter tetap jalan")
    void suiteConstructors() throws Throwable {
        Object a = R.create("SuiteRoom", "Royal Suite", 2021);
        assertEquals(2, R.call(a, "getBedCount"));
        Object b = R.create("SuiteRoom", "Royal Suite", 2021, 4);
        assertEquals(4, R.call(b, "getBedCount"));
    }
}
