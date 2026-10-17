package id.ac.polinema.uts.hotel;

public class DeluxeRoom extends HotelRoom {
    private String viewType;

    public DeluxeRoom(String name, int yearBuilt, String viewType){
        super(name, yearBuilt);
        setViewType(viewType);
    }

    public String getViewType(){
        return viewType;
    }

    public void setViewType(String viewType){
        if (viewType == null || viewType.trim().isEmpty()) {
            throw new IllegalArgumentException("View type tidak boleh kosong");
        }
        this.viewType = viewType;
    }
}
