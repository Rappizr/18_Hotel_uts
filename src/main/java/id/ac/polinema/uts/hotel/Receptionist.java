package id.ac.polinema.uts.hotel;

public class Receptionist {
    public boolean checkIn(Guest guest, StandardRoom room){
        if (guest != null && room != null && guest.canBook() && room.isAvailable()) {
            if (room.reserve()) {
                guest.addBooking();
                return true;
            }
        }
        return false;
    }

    public boolean checkOut(Guest guest, StandardRoom room){
        if (guest != null && room != null && !room.isAvailable() && guest.getBookingCount() > 0) {
            room.release();
            guest.removeBooking();
            return true;
        }
        return false;
    }
    
}
