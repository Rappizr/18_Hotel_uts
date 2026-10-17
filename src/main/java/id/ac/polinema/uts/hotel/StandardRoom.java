package id.ac.polinema.uts.hotel;

/**
 * Bagian 2 dan 3: kamar standard hotel.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Method untuk bagian berikutnya (setFacility, getFacility, dan seterusnya) kamu
 * tambahkan sendiri sesuai diagram di bagian tersebut.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas constructor:
 *   public String roomNo;
 *   public ____ name;
 *   public ____ yearBuilt;
 *   public ____ available;
 */
public class StandardRoom {
    private  String roomNo;
    private String name;
    private int yearBuilt;
    private boolean available;

    /**
     * Membuat kamar baru yang masih tersedia (belum dipesan).
     * Bagian 3: roomNo tidak boleh kosong; name dan yearBuilt divalidasi seperti setter-nya.
     */
    public StandardRoom(String roomNo, String name, int yearBuilt) {
        this.roomNo = roomNo;
        this.name = name;
        this.yearBuilt = yearBuilt;
        this.available = available;
        // TODO Bantuan: isi semua field. Kamar baru: apakah sedang terisi atau tidak?
        // Bagian 3: aturan validasi nama dan tahun sama dengan setter. Bisakah constructor
        // memakai setter-nya?
        //
        // Kerangka (Bagian 2):
        //   this.roomNo = roomNo;
        //   this.name = ____;
        //   this.yearBuilt = ____;
        //   this.available = ____;
        //
        // Kerangka (Bagian 3): ganti dua baris name dan yearBuilt dengan pemanggilan setter.
        //   setName(____);
        //   setYearBuilt(____);
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getRoomNo() {
        return roomNo;
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        return name;
        // TODO Bantuan: sama seperti getRoomNo.
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getYearBuilt() {
        return yearBuilt;
        // TODO Bantuan: sama seperti getRoomNo.
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True jika kamar kosong (belum dipesan). */
    public boolean isAvailable() {
        return available;
        // TODO Bantuan: field apa yang menyimpan status kamar?
        //
        // Kerangka:
        //   return ____;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Memesan kamar. Berhasil (true) jika kamar tersedia. Jika kamar sudah
     * terisi, hasilnya false dan keadaan kamar tidak berubah.
     */
    public boolean reserve() {
        if (available) {
            available = false;
            return true;
        }
        return false;
        // TODO Bantuan: apa yang harus kamu periksa lebih dulu sebelum mengubah status?
        // Method ini punya dua jalur return yang berbeda.
        //
        // Kerangka:
        //   if (____) {
        //       return false;
        //   }
        //   ____ = false;
        //   return true;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Mengosongkan kamar: kamar bisa dipesan lagi. */
    public void release() {
        this.available = true;
        // TODO Bantuan: satu baris saja. Status apa yang berubah?
        //
        // Kerangka:
        //   ____ = true;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Bagian 3. Mengubah nama kamar. Nama null, kosong, atau hanya spasi ditolak
     * dengan IllegalArgumentException, dan nama lama tetap.
     */
    public void setName(String name) {
        // TODO Bantuan: periksa dulu, baru isi field. Class String punya method untuk
        // memeriksa teks kosong atau hanya spasi. Jangan lupa kemungkinan null.
        //
        // Kerangka:
        //   if (name == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.name = name;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Bagian 3. Mengubah tahun dibangun. Tahun di bawah 1950 ditolak dengan
     * IllegalArgumentException, dan tahun lama tetap.
     */
    public void setYearBuilt(int yearBuilt) {
        // TODO Bantuan: pola yang sama dengan setName. Apa syarat tahun yang valid?
        //
        // Kerangka:
        //   if (yearBuilt ____ 1950) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.yearBuilt = yearBuilt;
        // throw new UnsupportedOperationException("Not implemented yet");
    }
}
