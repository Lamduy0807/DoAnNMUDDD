package controller.mytravel;

public class TravelLocation {
    public String title, location;
    int imgURL;
    public float starrating;

    public TravelLocation(String title, String location, int imgURL, float starrating) {
        this.title = title;
        this.location = location;
        this.imgURL = imgURL;
        this.starrating = starrating;
    }
}
