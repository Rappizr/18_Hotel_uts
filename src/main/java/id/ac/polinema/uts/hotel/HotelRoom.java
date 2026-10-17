package id.ac.polinema.uts.hotel;

public class HotelRoom {
    protected String name;
    protected int yearBuilt;
    private boolean available;
    private int extraNights;

    public HotelRoom(String name, int yearBuilt){
        setName(name);
        setYearBuilt(yearBuilt);
        this.available = true;
        this.extraNights = 0;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }
        this.name = name;
    }

    public int getYearBuilt(){
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt){
        if (yearBuilt < 1950) {
            throw new IllegalArgumentException("Tahun dibangun minimal 1950");
        }
        this.yearBuilt = yearBuilt;
    }

    public boolean isAvailable(){
        return available;
    }

    public boolean reserve(){
        if (available) {
            available = false;
            return true;
        }
        return false;
    }

    public void release(){
        this.available = true;
    }

    public int pricePerNight(){
        return 400000;
    }

    public String describe(){
        return name + " (built " + yearBuilt + ")";
    }

    @Override 
    public String toString(){
        return describe();
    }

    public void extendStay(int nights){
        if (nights <= 0) {
            throw new IllegalArgumentException("Perpanjangan malam harus lebih dari 0");
        }
        this.extraNights += nights;
    }

    public void extendStay(){
        extendStay(2);
    }

    public int getTotalNight(){
        return 1 + extraNights;
    }

    public int getTotalPrice(){
        return pricePerNight() * getTotalNight();
    }
}
