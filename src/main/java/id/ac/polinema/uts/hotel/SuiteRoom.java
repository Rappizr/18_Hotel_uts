package id.ac.polinema.uts.hotel;

public class SuiteRoom extends HotelRoom {
    private int bedCount;

    public  SuiteRoom(String name, int yearBuilt, int bedCount){
        super(name, yearBuilt);
        this.bedCount = bedCount;
    }

    public SuiteRoom(String name, int yearBuilt){
        this(name, yearBuilt, 2);
    }

    public int getBedCount(){
        return bedCount;
    }

    public void setBedCount(int bedCount){
        this.bedCount = bedCount;
    }

    @Override 
    public int pricePerNight(){
        return 1200000;
    }
}
