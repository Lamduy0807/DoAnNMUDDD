package controller.CreateTrip;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Place implements Serializable {
    private String title;
    private String linkHinh;
    private String description;
    private String sAddress;
    private double kinhDo;
    private double viDo;
    private DanhGia danhGia;

    public Place(String title, String linkHinh, String description, String sAddress, double kinhDo, double viDo, DanhGia danhGia) {
        this.title = title;
        this.linkHinh = linkHinh;
        this.description = description;
        this.sAddress = sAddress;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.danhGia = danhGia;
    }

    public DanhGia getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(DanhGia danhGia) {
        this.danhGia = danhGia;
    }

    public Place() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }
    public double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public double getViDo() {
        return viDo;
    }

    public void setViDo(double viDo) {
        this.viDo = viDo;
    }
}
