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
public class StandardRoom extends HotelRoom {
    private  String roomNo;
    private Facility facility;

    /**
     * Membuat kamar baru yang masih tersedia (belum dipesan).
     * Bagian 3: roomNo tidak boleh kosong; name dan yearBuilt divalidasi seperti setter-nya.
     */
    public StandardRoom(String roomNo, String name, int yearBuilt) {
        super(name, yearBuilt);
        if (roomNo == null || roomNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong");
        }
        this.roomNo = roomNo;
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

    public StandardRoom(String roomNo, String name, int yearBuilt, Facility facility){
        this(roomNo, name, yearBuilt);
        this.facility = facility;
    }

    public String getRoomNo() {
        return roomNo;
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        // throw new UnsupportedOperationException("Not implemented yet");
    }

    public Facility getFacility(){
        return facility;
    }

    public void setFacility(Facility facility){
        this.facility = facility;
    }

    @Override 
    public String describe(){
        String facName = (facility != null) ? facility.getName() : "None";
        return name + " (built " + yearBuilt + ") facikity: " + facName;
    }
}
