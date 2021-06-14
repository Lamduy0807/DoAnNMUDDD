package controller.CreateTrip;

public class DanhGia {
    private String uid;
    private String textDanhGia;

    public DanhGia() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTextDanhGia() {
        return textDanhGia;
    }

    public void setTextDanhGia(String textDanhGia) {
        this.textDanhGia = textDanhGia;
    }

    public DanhGia(String uid, String textDanhGia) {
        this.uid = uid;
        this.textDanhGia = textDanhGia;
    }


}
