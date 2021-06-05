package controller.mytravel;

public class PhoneNum {
    private String Uid,PhoneNumber;

    public PhoneNum(String uid, String phoneNumber) {
        Uid = uid;
        PhoneNumber = phoneNumber;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
