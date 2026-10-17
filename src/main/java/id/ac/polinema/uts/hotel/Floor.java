package id.ac.polinema.uts.hotel;

public class Floor {
    private String code;
    private StandardRoom[] rooms;
    private int roomCount;

    public Floor(String count){
        this.code = code;
        this.rooms = new StandardRoom[5];
        this.roomCount = 0;
    }

    public String getCode(){
        return code;
    }

    public int getRoomCount(){
        return roomCount;
    }

    public boolean addRoom(StandardRoom room){
        if (room == null || roomCount >= 5) {
            return false;
        }
        rooms[roomCount++] = room;
        return true;
    }

    public StandardRoom findByRoomNo(String roomNo){
        if (roomNo == null) {
            return null;
        }
        for(int i = 0; i < roomCount; i++){
            if (rooms[i] != null && roomNo.equals(rooms[i].getRoomNo())) {
                return rooms[i];
            }
        }
        return null;
    }

    public int countAvailable(){
        int count = 0;
        for(int i = 0; i < roomCount; i++){
            if (rooms[i] != null && rooms[i].isAvailable()) {
                count++;   
            }
        }
        return count;
    }
}
