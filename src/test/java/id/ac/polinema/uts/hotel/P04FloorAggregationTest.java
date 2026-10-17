package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Agregasi: Floor menyimpan StandardRoom, tetapi StandardRoom ada di luar Floor juga. */
class P04FloorAggregationTest {

    private static Object floor() throws Throwable {
        return R.create("Floor", "F1");
    }

    @Test
    @DisplayName("Floor ada, semua field-nya private, dan menyimpan kode lantai")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Floor"));
        Object f = floor();
        assertEquals("F1", R.call(f, "getCode"));
        assertEquals(0, R.call(f, "getRoomCount"));
    }

    @Test
    @DisplayName("addRoom menambah kamar dan getRoomCount ikut naik")
    void addRoom() throws Throwable {
        Object f = floor();
        assertEquals(true, R.call(f, "addRoom", new StandardRoom("101", "Garden Room", 2015)));
        assertEquals(true, R.call(f, "addRoom", new StandardRoom("102", "Lake View", 1999)));
        assertEquals(2, R.call(f, "getRoomCount"));
    }

    @Test
    @DisplayName("Kapasitas lantai 5 kamar; kamar ke-6 ditolak")
    void capacity() throws Throwable {
        Object f = floor();
        for (int i = 1; i <= 5; i++) {
            assertEquals(true, R.call(f, "addRoom", new StandardRoom("" + i, "Room " + i, 2000)));
        }
        assertEquals(false, R.call(f, "addRoom", new StandardRoom("6", "Room 6", 2000)));
        assertEquals(5, R.call(f, "getRoomCount"));
    }

    @Test
    @DisplayName("addRoom(null) ditolak")
    void rejectsNull() throws Throwable {
        Object f = floor();
        assertEquals(false, R.call(f, "addRoom", (Object) null));
        assertEquals(0, R.call(f, "getRoomCount"));
    }

    @Test
    @DisplayName("findByRoomNo mengembalikan objek StandardRoom yang sama (agregasi), atau null")
    void findByRoomNo() throws Throwable {
        Object f = floor();
        StandardRoom garden = new StandardRoom("101", "Garden Room", 2015);
        StandardRoom lake = new StandardRoom("202", "Lake View", 1999);
        R.call(f, "addRoom", garden);
        R.call(f, "addRoom", lake);
        assertSame(lake, R.call(f, "findByRoomNo", "202"));
        assertSame(garden, R.call(f, "findByRoomNo", "101"));
        assertNull(R.call(f, "findByRoomNo", "999"));
    }

    @Test
    @DisplayName("countAvailable mengikuti status kamar yang dipesan lewat referensi luar")
    void countAvailable() throws Throwable {
        Object f = floor();
        StandardRoom a = new StandardRoom("1", "Garden Room", 2015);
        StandardRoom b = new StandardRoom("2", "Lake View", 1999);
        R.call(f, "addRoom", a);
        R.call(f, "addRoom", b);
        assertEquals(2, R.call(f, "countAvailable"));
        assertTrue(a.reserve());
        assertEquals(1, R.call(f, "countAvailable"));
        a.release();
        assertEquals(2, R.call(f, "countAvailable"));
        assertTrue(b.isAvailable());
    }
}
