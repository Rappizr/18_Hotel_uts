package id.ac.polinema.uts.hotel;

/**
 * Bagian 2: fasilitas hotel (kolam renang, gym, dan sebagainya).
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Di Bagian 2 field boleh public. Di Bagian 3 kamu akan menutupnya.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas constructor:
 *   public ____ name;
 *   public ____ floorLocation;
 */
public class Facility {

    /** Membuat fasilitas dengan nama dan lokasi lantai. */
    public Facility(String name, String floorLocation) {
        // TODO Bantuan: nama parameter sama dengan nama field. Kata kunci apa yang
        // menunjuk ke objek yang sedang dibuat?
        //
        // Kerangka:
        //   this.name = ____;
        //   this.floorLocation = ____;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getFloorLocation() {
        // TODO Bantuan: sama seperti getName.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Teks berbentuk "nama [lantai]". Contoh: Rooftop Gym [Floor 9]. */
    public String getInfo() {
        // TODO Bantuan: gabungkan dua data dengan operator + dan tanda kurung siku dalam String.
        //
        // Kerangka:
        //   return name + " [" + ____ + "]";
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
