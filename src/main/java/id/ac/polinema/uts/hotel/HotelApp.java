package id.ac.polinema.uts.hotel;

import java.io.PrintStream;
import java.util.Scanner;

public class HotelApp {
    private Hotel hotel;
    private Scanner in;
    private PrintStream out;

    public HotelApp(Hotel hotel, Scanner in, PrintStream out) {
        this.hotel = hotel;
        this.in = in;
        this.out = out;
    }

    public void run(){
        while (true) {
            printMenu();
            if (!in.hasNextLine()) {
                return ;
            }
            String choice = in.nextLine().trim();
            switch (choice) {
                case "1": showRooms(); break;
                case "2": addStandardRoom(); break;
                case "3": addDeluxeRoom(); break;
                case "4": addSuite(); break;
                case "5": registerGuest(); break;
                case "6": checkIn(); break;
                case "7": checkOut(); break;
                case "8": searchRooms(); break;
                case "9": showGuests(); break;
                case "0":
                    out.println("Thank you, goodbye");
                    return;
                default:
                    out.println("Unknown option");
            }
        }
    }

        private void printMenu() {
        out.println("=== " + hotel.getName() + " ===");
        out.println("1. List rooms");
        out.println("2. Add standard room");
        out.println("3. Add deluxe room");
        out.println("4. Add suite");
        out.println("5. Register guest");
        out.println("6. Check in");
        out.println("7. Check out");
        out.println("8. Search rooms");
        out.println("9. List guests");
        out.println("0. Exit");
        out.print("Choose: ");
    }

    private String readLine(String prompt){
        out.print(prompt);
        return in.hasNextLine() ? in.nextLine().trim() : "";
    }

    private int readInt(String prompt){
                String text = readLine(prompt);
        if (text.isEmpty() || text.length() > 9) {
            return -1;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return -1;
            }
        }
        int value = Integer.parseInt(text);
        return value > 0 ? value : -1;
    }

    private void printRoomLine(HotelRoom r){
        String status = r.isAvailable() ? "Available" : "Occupied";
        out.println(r.describe() + " | " + status + " | " + r.pricePerNight() + "/night");
    }

        private void showRooms() {
        HotelRoom[] rooms = hotel.getRooms();
        if (rooms.length == 0) {
            out.println("No rooms");
            return;
        }
        for (HotelRoom r : rooms) {
            printRoomLine(r);
        }
    }

    private void addStandardRoom() {
        String roomNo = readLine("Room no: ");
        String name = readLine("Name: ");
        int year = readInt("Year built: ");
        if (year == -1) {
            out.println("Not a valid number");
            return;
        }

        StandardRoom room;
        try {
            room = new StandardRoom(roomNo, name, year);
        } catch (IllegalArgumentException e) {
            out.println("Rejected: " + e.getMessage());
            return;
        }

        String facName = readLine("Facility name (blank for none): ");
        if (!facName.isEmpty()) {
            String floor = readLine("Facility floor: ");
            room.setFacility(new Facility(facName, floor));
        }

        if (hotel.addRoom(room)) {
            out.println("Room added");
        } else {
            out.println("Hotel is full");
        }
    }

    private void addDeluxeRoom() {
        String name = readLine("Name: ");
        int year = readInt("Year built: ");
        if (year == -1) {
            out.println("Not a valid number");
            return;
        }
        String viewType = readLine("View type: ");

        try {
            DeluxeRoom room = new DeluxeRoom(name, year, viewType);
            if (hotel.addRoom(room)) {
                out.println("Room added");
            } else {
                out.println("Hotel is full");
            }
        } catch (IllegalArgumentException e) {
            out.println("Rejected: " + e.getMessage());
        }
    }

    private void addSuite() {
        String name = readLine("Name: ");
        int year = readInt("Year built: ");
        if (year == -1) {
            out.println("Not a valid number");
            return;
        }
        int bedCount = readInt("Bed count: ");
        if (bedCount == -1) {
            out.println("Not a valid number");
            return;
        }

        try {
            SuiteRoom room = new SuiteRoom(name, year, bedCount);
            if (hotel.addRoom(room)) {
                out.println("Room added");
            } else {
                out.println("Hotel is full");
            }
        } catch (IllegalArgumentException e) {
            out.println("Rejected: " + e.getMessage());
        }
    }

    private void registerGuest() {
        String guestId = readLine("Guest ID: ");
        String name = readLine("Name: ");
        try {
            Guest guest = new Guest(guestId, name);
            if (hotel.addGuest(guest)) {
                out.println("Guest registered");
            } else {
                out.println("Guest ID already exists");
            }
        } catch (IllegalArgumentException e) {
            out.println("Rejected: " + e.getMessage());
        }
    }

    private void checkIn(){
        String guestId = readLine("Guest ID");
        String roomName = readLine("Room name");
        if (hotel.checkIn(guestId, roomName)) {
            out.println("Check-in successful");
        } else {
            out.println("Check-in failed");
        }
    }

    private void checkOut() {
        String guestId = readLine("Guest ID: ");
        String roomName = readLine("Room name: ");
        if (hotel.checkOut(guestId, roomName)) {
            out.println("Check-out successful");
        } else {
            out.println("Check-out failed");
        }
    }

    private void searchRooms(){
        String keyword = readLine("Keyword: ");
        HotelRoom[] results = hotel.searchByKeyword(keyword);
        if (results.length == 0) {
            out.println("No rooms found");
            return;
        }
        for (HotelRoom r : results){
            printRoomLine(r);
        }
    }

    private  void showGuests(){
        Guest[] guests = hotel.getGuests();
        if (guests.length == 0) {
            out.println("No guests");
            return;
        }
        for(Guest g : guests){
            out.println(g.getGuestId() + " - " + g.getName() + " (" + g.getBookingCount() + " bookings");
        }
    }
}