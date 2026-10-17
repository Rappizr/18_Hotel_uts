package id.ac.polinema.uts.hotel;

public class HotelRoom {
    protected String name;
    protected int yearBuilt;
    private boolean available;

    public HotelRoom(String name, int yearBuilt){
        setName(name);
        setYearBuilt(yearBuilt);
        this.available = true;
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
}
