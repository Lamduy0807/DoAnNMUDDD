package Model;

public class TravelLocation {
    public String title, location;
    String imgURL;
    public float starrating;

    public TravelLocation(String title, String location, String imgURL, float starrating) {
        this.title = title;
        this.location = location;
        this.imgURL = imgURL;
        this.starrating = starrating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public float getStarrating() {
        return starrating;
    }

    public void setStarrating(float starrating) {
        this.starrating = starrating;
    }
}
