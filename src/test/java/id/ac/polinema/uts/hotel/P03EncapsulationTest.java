package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class P03EncapsulationTest {

    @Test
    @DisplayName("Semua field StandardRoom private")
    void roomFieldsPrivate() {
        R.assertAllFieldsPrivate(StandardRoom.class);
    }

    @Test
    @DisplayName("Semua field Facility private")
    void facilityFieldsPrivate() {
        R.assertAllFieldsPrivate(Facility.class);
    }

    @Test
    @DisplayName("roomNo read-only: StandardRoom tidak punya setRoomNo, dan status tidak bisa di-set langsung")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(StandardRoom.class, "setRoomNo"), "roomNo tidak boleh punya setter");
        assertFalse(R.hasPublicMethod(StandardRoom.class, "setAvailable"),
                "status tersedia hanya boleh berubah lewat reserve dan release");
    }

    @Test
    @DisplayName("setName menolak null, kosong, dan hanya spasi; nama lama tetap")
    void setNameRejectsBlank() {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        assertThrows(IllegalArgumentException.class, () -> r.setName(null));
        assertThrows(IllegalArgumentException.class, () -> r.setName(""));
        assertThrows(IllegalArgumentException.class, () -> r.setName("   "));
        assertEquals("Garden Room", r.getName());
    }

    @Test
    @DisplayName("setName menerima nama yang valid")
    void setNameAcceptsValid() {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        r.setName("Lake View");
        assertEquals("Lake View", r.getName());
    }

    @Test
    @DisplayName("setYearBuilt menolak tahun di bawah 1950; tahun lama tetap")
    void setYearBuiltRejectsBefore1950() {
        StandardRoom r = new StandardRoom("1", "Garden Room", 2015);
        assertThrows(IllegalArgumentException.class, () -> r.setYearBuilt(1949));
        assertThrows(IllegalArgumentException.class, () -> r.setYearBuilt(0));
        assertThrows(IllegalArgumentException.class, () -> r.setYearBuilt(-5));
        assertEquals(2015, r.getYearBuilt());
        r.setYearBuilt(1950);
        assertEquals(1950, r.getYearBuilt());
    }

    @Test
    @DisplayName("Constructor StandardRoom memvalidasi roomNo, name, dan yearBuilt")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new StandardRoom("", "Garden Room", 2015));
        assertThrows(IllegalArgumentException.class, () -> new StandardRoom(null, "Garden Room", 2015));
        assertThrows(IllegalArgumentException.class, () -> new StandardRoom("1", " ", 2015));
        assertThrows(IllegalArgumentException.class, () -> new StandardRoom("1", "Garden Room", 1949));
    }
}
