package controller.CreateTrip;

public class DanhGia {
    private String uid;
    private String textDanhGia;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public DanhGia(String uid, String textDanhGia, String email) {
        this.uid = uid;
        this.textDanhGia = textDanhGia;
        this.email = email;
    }


}
