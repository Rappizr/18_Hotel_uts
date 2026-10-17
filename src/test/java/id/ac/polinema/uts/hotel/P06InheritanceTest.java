package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Inheritance: StandardRoom, DeluxeRoom, dan SuiteRoom adalah HotelRoom (IS-A). */
class P06InheritanceTest {

    private static Object deluxe() throws Throwable {
        return R.create("DeluxeRoom", "Skyline Deluxe", 2018, "City");
    }

    private static Object suite() throws Throwable {
        return R.create("SuiteRoom", "Royal Suite", 2021, 3);
    }

    @Test
    @DisplayName("StandardRoom, DeluxeRoom, dan SuiteRoom extends HotelRoom")
    void superclass() {
        Class<?> room = R.cls("HotelRoom");
        assertEquals(room, StandardRoom.class.getSuperclass(), "StandardRoom harus extends HotelRoom");
        assertEquals(room, R.cls("DeluxeRoom").getSuperclass(), "DeluxeRoom harus extends HotelRoom");
        assertEquals(room, R.cls("SuiteRoom").getSuperclass(), "SuiteRoom harus extends HotelRoom");
    }

    @Test
    @DisplayName("HotelRoom menyimpan name dan yearBuilt sebagai protected, dan tidak ada field public")
    void roomFields() {
        Class<?> room = R.cls("HotelRoom");
        for (Field f : room.getDeclaredFields()) {
            if (f.isSynthetic()) {
                continue;
            }
            assertFalse(Modifier.isPublic(f.getModifiers()), "Field " + f.getName() + " tidak boleh public");
        }
        assertTrue(Modifier.isProtected(field(room, "name").getModifiers()), "name harus protected");
        assertTrue(Modifier.isProtected(field(room, "yearBuilt").getModifiers()), "yearBuilt harus protected");
    }

    private static Field field(Class<?> c, String name) {
        try {
            return c.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return org.junit.jupiter.api.Assertions.fail("Field " + name + " tidak ada di " + c.getSimpleName());
        }
    }

    @Test
    @DisplayName("StandardRoom tidak menyimpan ulang name, yearBuilt, dan available (sudah ada di HotelRoom)")
    void standardDoesNotRepeatFields() {
        for (Field f : StandardRoom.class.getDeclaredFields()) {
            String n = f.getName();
            assertFalse(n.equals("name") || n.equals("yearBuilt") || n.equals("available"),
                    "Field " + n + " sudah ada di HotelRoom. Hapus dari StandardRoom.");
        }
        R.assertAllFieldsPrivate(StandardRoom.class);
    }

    @Test
    @DisplayName("Method umum dipindah ke HotelRoom, bukan ditulis ulang di subclass")
    void methodsMovedUp() {
        for (String name : new String[] {"getName", "getYearBuilt", "isAvailable", "reserve", "release"}) {
            assertFalse(R.declaresMethod(StandardRoom.class, name), "StandardRoom tidak perlu mendeklarasikan " + name);
            assertFalse(R.declaresMethod(R.cls("DeluxeRoom"), name), "DeluxeRoom tidak perlu mendeklarasikan " + name);
            assertTrue(R.declaresMethod(R.cls("HotelRoom"), name), "HotelRoom harus punya " + name);
        }
    }

    @Test
    @DisplayName("HotelRoom(name, yearBuilt) menyimpan data dan memvalidasinya")
    void roomConstructor() throws Throwable {
        Object room = R.create("HotelRoom", "Generic", 2000);
        assertEquals("Generic", R.call(room, "getName"));
        assertEquals(2000, R.call(room, "getYearBuilt"));
        assertEquals(true, R.call(room, "isAvailable"));
        assertThrows(IllegalArgumentException.class, () -> R.create("HotelRoom", " ", 2000));
        assertThrows(IllegalArgumentException.class, () -> R.create("HotelRoom", "Generic", 1949));
    }

    @Test
    @DisplayName("DeluxeRoom menyimpan jenis pemandangan dan memakai getter turunan dari HotelRoom")
    void deluxeKeepsView() throws Throwable {
        Object d = deluxe();
        assertEquals("Skyline Deluxe", R.call(d, "getName"));
        assertEquals(2018, R.call(d, "getYearBuilt"));
        assertEquals("City", R.call(d, "getViewType"));
        R.assertAllFieldsPrivate(R.cls("DeluxeRoom"));
    }

    @Test
    @DisplayName("SuiteRoom menyimpan jumlah tempat tidur dan memakai getter turunan")
    void suiteKeepsBeds() throws Throwable {
        Object s = suite();
        assertEquals("Royal Suite", R.call(s, "getName"));
        assertEquals(3, R.call(s, "getBedCount"));
        R.assertAllFieldsPrivate(R.cls("SuiteRoom"));
    }

    @Test
    @DisplayName("reserve dan release diwarisi oleh semua subclass")
    void inheritedBehaviour() throws Throwable {
        Object[] rooms = {new StandardRoom("1", "Garden Room", 2015), deluxe(), suite()};
        for (Object room : rooms) {
            assertEquals(true, R.call(room, "isAvailable"));
            assertEquals(true, R.call(room, "reserve"));
            assertEquals(false, R.call(room, "reserve"));
            R.call(room, "release");
            assertEquals(true, R.call(room, "isAvailable"));
        }
    }

    @Test
    @DisplayName("Validasi setter ikut diwarisi: setName kosong ditolak di DeluxeRoom")
    void validationInherited() throws Throwable {
        Object d = deluxe();
        assertThrows(IllegalArgumentException.class, () -> R.call(d, "setName", ""));
        assertThrows(IllegalArgumentException.class, () -> R.call(d, "setYearBuilt", 1900));
        assertEquals("Skyline Deluxe", R.call(d, "getName"));
    }

    @Test
    @DisplayName("Setiap subclass adalah turunan HotelRoom (extends)")
    void isInstance() throws Throwable {
        Class<?> room = R.cls("HotelRoom");
        assertTrue(room.isInstance(new StandardRoom("1", "Garden Room", 2015)));
        assertTrue(room.isInstance(deluxe()));
        assertTrue(room.isInstance(suite()));
    }
}
