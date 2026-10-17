package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Overriding: subclass mengganti perilaku method yang diwarisi. */
class P07OverrideTest {

    private static Object room() throws Throwable {
        return R.create("HotelRoom", "Generic", 2000);
    }

    private static Object deluxe() throws Throwable {
        return R.create("DeluxeRoom", "Skyline Deluxe", 2018, "City");
    }

    private static Object suite() throws Throwable {
        return R.create("SuiteRoom", "Royal Suite", 2021, 3);
    }

    @Test
    @DisplayName("pricePerNight: HotelRoom 400000, StandardRoom mewarisi 400000, DeluxeRoom 650000, SuiteRoom 1200000")
    void pricePerNight() throws Throwable {
        assertEquals(400000, R.call(room(), "pricePerNight"));
        assertEquals(400000, R.call(new StandardRoom("1", "Garden Room", 2015), "pricePerNight"));
        assertEquals(650000, R.call(deluxe(), "pricePerNight"));
        assertEquals(1200000, R.call(suite(), "pricePerNight"));
    }

    @Test
    @DisplayName("DeluxeRoom dan SuiteRoom meng-override pricePerNight; StandardRoom tidak")
    void whoOverrides() {
        assertTrue(R.declaresMethod(R.cls("DeluxeRoom"), "pricePerNight"), "DeluxeRoom harus override pricePerNight");
        assertTrue(R.declaresMethod(R.cls("SuiteRoom"), "pricePerNight"), "SuiteRoom harus override pricePerNight");
        assertFalse(R.declaresMethod(StandardRoom.class, "pricePerNight"),
                "StandardRoom tidak perlu override pricePerNight, ia memakai versi HotelRoom");
    }

    @Test
    @DisplayName("describe di HotelRoom: 'name (built yearBuilt)'")
    void roomDescribe() throws Throwable {
        assertEquals("Generic (built 2000)", R.call(room(), "describe"));
    }

    @Test
    @DisplayName("describe di StandardRoom memakai super.describe() lalu menambah ' facility: nama'")
    void standardDescribe() throws Throwable {
        StandardRoom r = new StandardRoom("101", "Garden Room", 2015);
        assertEquals("Garden Room (built 2015) facility: None", R.call(r, "describe"));
        R.call(r, "setFacility", new Facility("Swimming Pool", "Floor 1"));
        assertEquals("Garden Room (built 2015) facility: Swimming Pool", R.call(r, "describe"));
    }

    @Test
    @DisplayName("describe di DeluxeRoom menambah jenis pemandangan")
    void deluxeDescribe() throws Throwable {
        assertEquals("Skyline Deluxe (built 2018) [City view]", R.call(deluxe(), "describe"));
    }

    @Test
    @DisplayName("describe di SuiteRoom memakai versi HotelRoom")
    void suiteDescribe() throws Throwable {
        assertEquals("Royal Suite (built 2021)", R.call(suite(), "describe"));
        assertFalse(R.declaresMethod(R.cls("SuiteRoom"), "describe"), "SuiteRoom tidak perlu override describe");
    }

    @Test
    @DisplayName("toString mengembalikan describe() untuk semua jenis kamar")
    void toStringUsesDescribe() throws Throwable {
        Object[] rooms = {room(), new StandardRoom("1", "Garden Room", 2015), deluxe(), suite()};
        for (Object it : rooms) {
            assertEquals(R.call(it, "describe"), it.toString(),
                    "toString harus mengembalikan describe() pada " + it.getClass().getSimpleName());
        }
    }
}
