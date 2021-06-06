package controller.CreateTrip;

import java.io.Serializable;

public class Place implements Serializable {
    private String sNamePlace;
    private String sAddress;
    private int imgPlace;
    private int imgGps;

    public Place(String sNamePlace, String sAddress, int imgPlace, int imgGps) {
        this.sNamePlace = sNamePlace;
        this.sAddress = sAddress;
        this.imgPlace = imgPlace;
        this.imgGps = imgGps;
    }

    public int getImgGps() {
        return imgGps;
    }

    public void setImgGps(int imgGps) {
        this.imgGps = imgGps;
    }

    public String getsNamePlace() {
        return sNamePlace;
    }

    public void setsNamePlace(String sNamePlace) {
        this.sNamePlace = sNamePlace;
    }

    public int getImgPlace() {
        return imgPlace;
    }

    public void setImgPlace(int imgPlace) {
        this.imgPlace = imgPlace;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public Place() {
    }


}
