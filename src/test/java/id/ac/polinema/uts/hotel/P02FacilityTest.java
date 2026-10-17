package id.ac.polinema.uts.hotel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class P02FacilityTest {

    @Test
    @DisplayName("Constructor menyimpan nama dan lokasi lantai, getter mengembalikannya")
    void constructorAndGetters() {
        Facility f = new Facility("Swimming Pool", "Floor 1");
        assertEquals("Swimming Pool", f.getName());
        assertEquals("Floor 1", f.getFloorLocation());
    }

    @Test
    @DisplayName("getInfo mengembalikan 'nama [lantai]'")
    void getInfo() {
        Facility f = new Facility("Rooftop Gym", "Floor 9");
        assertEquals("Rooftop Gym [Floor 9]", f.getInfo());
    }

    @Test
    @DisplayName("Dua objek Facility punya data sendiri-sendiri")
    void objectsAreIndependent() {
        Facility a = new Facility("Swimming Pool", "Floor 1");
        Facility b = new Facility("Rooftop Gym", "Floor 9");
        assertNotEquals(a.getName(), b.getName());
        assertEquals("Swimming Pool", a.getName());
        assertEquals("Floor 9", b.getFloorLocation());
    }
}
