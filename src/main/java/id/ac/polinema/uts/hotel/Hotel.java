package id.ac.polinema.uts.hotel;

import java.util.Arrays;

public class Hotel {
    private  String name;
    private HotelRoom[] rooms;
    private int roomCount;
    private Guest[] guests;
    private int guestCount;

    public  Hotel(String name){
        this.name = name;
        this.rooms = new HotelRoom[20];
        this.roomCount = 0;
        this.guests = new Guest[10];
        this.guestCount = 0;
    }

    public String getName(){
        return name;
    }

    public boolean addRoom(HotelRoom room){
        if (room == null || roomCount >= 20){
            return false;
        } 
        rooms[roomCount++] = room;
        return true;
    }

    public boolean addGuest(Guest guest){
        if (guest == null || guestCount >= 10 || findGuest(guest.getGuestId()) != null) {
            return false;
        }
        guests[guestCount++] = guest;
        return true;
    }

    public HotelRoom[] getRooms(){
        return Arrays.copyOf(rooms, roomCount);
    }

    public Guest[] getGuests(){
        return Arrays.copyOf(guests, guestCount);
    }

    public HotelRoom findRoomByName(String name){
        if (name == null) return null;
        for(int i = 0; i < roomCount;i++) {
            if (rooms[i].getName().equalsIgnoreCase(name.trim())) {
                return rooms[i];
            }
        }
        return null;
    }

    public Guest findGuest(String guestId){
        if (guestId == null) return null;
        for(int i = 0; i< guestCount; i++) {
            if (guests[i].getGuestId().equals(guestId.trim())) {
                return  guests[i];
            }
        }
        return null;
    }

     public HotelRoom[] searchByKeyword(String keyword) {
        if (keyword == null) return new HotelRoom[0];
        HotelRoom[] temp = new HotelRoom[roomCount];
        int count = 0;
        String kw = keyword.trim().toLowerCase();
        for (int i = 0; i < roomCount; i++) {
            if (rooms[i].getName().toLowerCase().contains(kw)) {
                temp[count++] = rooms[i];
            }
        }
        return Arrays.copyOf(temp, count);
    }

    public boolean checkIn(String guestId, String roomName) {
        Guest guest = findGuest(guestId);
        HotelRoom room = findRoomByName(roomName);
        if (guest != null && room != null && guest.canBook() && room.isAvailable()) {
            if (room.reserve()) {
                guest.addBooking();
                return true;
            }
        }
        return false;
    }

    public boolean checkOut(String guestId, String roomName) {
        Guest guest = findGuest(guestId);
        HotelRoom room = findRoomByName(roomName);
        if (guest != null && room != null && !room.isAvailable() && guest.getBookingCount() > 0) {
            room.release();
            guest.removeBooking();
            return true;
        }
        return false;
    }
    
}
