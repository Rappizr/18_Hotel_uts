package id.ac.polinema.uts.hotel;

/**
 * Bagian 3: tamu hotel.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README
 * (semua field harus private).
 *
 * Kerangka field (lengkapi dari diagram), letakkan di atas constructor:
 *   private ____ guestId;
 *   private ____ name;
 *   private int bookingCount;   // nilai awal 0
 */
public class Guest {
    private String guestId;
    private String name;
    private int bookingCount;
    private GuestCard card;

    /**
     * Membuat tamu yang belum punya pemesanan.
     * guestId dan name tidak boleh null atau kosong: lempar
     * IllegalArgumentException jika tidak valid.
     */
    public Guest(String guestId, String name) {
        if (guestId == null || guestId.trim().isEmpty()){
            throw new IllegalArgumentException("Guess ID tidak boleh kosong");
        }
        this.guestId = guestId;
        setName(name);
        this.bookingCount = 0;
        this.card = new GuestCard("GC-"+guestId);
        // TODO Bantuan: validasi dulu sebelum mengisi field. Aturan nama sama dengan setName.
        // Bagian 4: kartu tamu juga dibuat di sini (lihat diagram Bagian 4).
        //
        // Kerangka:
        //   if (guestId == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.guestId = guestId;
        //   setName(____);
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getGuestId() {
        return guestId;
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        return name;
        // TODO Bantuan: sama seperti getGuestId.
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Nama tidak boleh null atau kosong: lempar IllegalArgumentException dan biarkan nama lama. */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tamu tidak boleh kosong");
        }
        this.name = name;
        // TODO Bantuan: periksa dulu, baru isi field.
        //
        // Kerangka:
        //   if (name == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.name = name;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getBookingCount() {
        return bookingCount;
        // TODO Bantuan: kembalikan jumlah pemesanan saat ini.
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True jika tamu masih boleh memesan (batasnya 2 pemesanan). */
    public boolean canBook() {
        return bookingCount < 2;
        // TODO Bantuan: apakah perlu field baru, atau cukup dihitung dari data yang sudah ada?
        //
        // Kerangka:
        //   return bookingCount ____ 2;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Menambah jumlah pemesanan. Jika sudah di batas, tidak berubah dan hasilnya false. */
    public boolean addBooking() {
        if (canBook()) {
            bookingCount++;
            return true;
        }
        return false;
        // TODO Bantuan: periksa batas dulu. Adakah method lain di kelas ini yang sudah
        // menjawab pertanyaan "boleh atau tidak"?
        //
        // Kerangka:
        //   if (!____) {
        //       return false;
        //   }
        //   bookingCount++;
        //   return true;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Mengurangi jumlah pemesanan. Jika sudah tidak ada pemesanan, tidak berubah dan hasilnya false. */
    public boolean removeBooking() {
        if (bookingCount > 0) {
            bookingCount--;
            return  true;
        }
        return false;
        // TODO Bantuan: batas bawah jumlah pemesanan adalah berapa?
        //
        // Kerangka:
        //   if (bookingCount == ____) {
        //       return false;
        //   }
        //   bookingCount--;
        //   return true;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public GuestCard getCard(){
        return card;
    }
}
