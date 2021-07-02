package Model;

public class Location {
    String strName, strDetail;
    int image;
    Boolean isSelected = false;

    public Location( int image, String strName, String strDetail) {
        this.strName = strName;
        this.strDetail = strDetail;
        this.image = image;
    }

    public Location() {
    }

    public Location(String strName) {
        this.strName = strName;
    }

    public String getStrName() {
        return strName;
    }

    public String getStrDetail() {
        return strDetail;
    }
}
