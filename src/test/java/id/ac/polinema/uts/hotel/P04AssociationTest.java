package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Asosiasi: StandardRoom mengenal Facility, tetapi Facility hidup sendiri. */
class P04AssociationTest {

    @Test
    @DisplayName("Kamar baru belum punya fasilitas (null)")
    void newRoomHasNoFacility() throws Throwable {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        assertNull(R.call(r, "getFacility"));
    }

    @Test
    @DisplayName("getFacility mengembalikan objek Facility yang sama (asosiasi)")
    void sameFacilityObject() throws Throwable {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        Facility pool = new Facility("Swimming Pool", "Floor 1");
        R.call(r, "setFacility", pool);
        assertSame(pool, R.call(r, "getFacility"));
    }

    @Test
    @DisplayName("Dua kamar bisa berbagi satu Facility")
    void roomsShareFacility() throws Throwable {
        StandardRoom a = new StandardRoom("1", "Garden Room", 2015);
        StandardRoom b = new StandardRoom("2", "Lake View", 2017);
        Facility pool = new Facility("Swimming Pool", "Floor 1");
        R.call(a, "setFacility", pool);
        R.call(b, "setFacility", pool);
        assertSame(R.call(a, "getFacility"), R.call(b, "getFacility"));
        assertEquals("Swimming Pool", pool.getName());
    }

    @Test
    @DisplayName("setFacility(null) melepas fasilitas")
    void clearFacility() throws Throwable {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        R.call(r, "setFacility", new Facility("Swimming Pool", "Floor 1"));
        R.call(r, "setFacility", (Object) null);
        assertNull(R.call(r, "getFacility"));
    }
}
