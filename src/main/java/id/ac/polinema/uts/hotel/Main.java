package id.ac.polinema.uts.hotel;

import java.util.Scanner;

/**
 * Titik awal program (satu-satunya method main di proyek ini).
 *
 * Bagian 1: baris PERTAMA output harus persis "Welcome to Polinema Hotel".
 * Setelah itu kamu bebas menambah baris lain untuk bereksperimen.
 *
 * Bagian 7: di bawah baris sapaan, kamu akan membuat Hotel, mengisi data
 * contoh, lalu menjalankan HotelApp. Kerangkanya ada di README.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Polinema Hotel");

        Hotel hotel = new  Hotel("Polinema Hotel");

        StandardRoom standard = new StandardRoom("101", "Garden Room", 2015);
        standard.setFacility(new Facility("Swimming Pool", "Floor 1"));
        hotel.addRoom(standard);

        hotel.addRoom(new DeluxeRoom("Skyline Deluxe", 2018, "City"));
        hotel.addRoom(new SuiteRoom("Royal Suite", 2021, 2));

        hotel.addGuest(new Guest("G001", "Rina"));

        HotelApp app = new HotelApp(hotel, new Scanner(System.in), System.out);
        app.run();
        // TODO: Tampilkan sebuah teks ke layar.
        // Di Java, perintahnya berbentuk: System.out.println("teks kamu");
        // Ganti teks di dalam tanda kutip dengan teks dari soal. Huruf besar-kecil dan
        // spasi harus persis sama. Tulis perintahnya di dalam kurung kurawal main ini,
        // lalu jalankan programnya.
        //
        // Kerangka:
        //   System.out.println("____");

        // Bagian 7: lanjutkan di sini (buat Hotel, isi data contoh, jalankan HotelApp).
    }
}
