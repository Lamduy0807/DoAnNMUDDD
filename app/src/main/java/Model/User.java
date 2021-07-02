package Model;

public class User {
//    private String Name ,Email, PassWord, Phone, UserName;
    private String Permission;

    public User() {
    }

//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public String getEmail() {
//        return Email;
//    }
//
//    public void setEmail(String email) {
//        Email = email;
//    }
//
//    public String getPassWord() {
//        return PassWord;
//    }
//
//    public void setPassWord(String passWord) {
//        PassWord = passWord;
//    }
//
//    public String getPhone() {
//        return Phone;
//    }
//
//    public void setPhone(String phone) {
//        Phone = phone;
//    }
//
//    public String getUserName() {
//        return UserName;
//    }
//
//    public void setUserName(String userName) {
//        UserName = userName;
//    }
//
//    public User(String name, String email, String passWord, String phone, String userName) {
//        Name = name;
//        Email = email;
//        PassWord = passWord;
//        Phone = phone;
//        UserName = userName;
//    }

    public String getPermission() {
        return Permission;
    }

    public void setPermission(String permission) {
        Permission = permission;
    }

    public User( String permission) {
//        Name = name;
//        Email = email;
//        PassWord = passWord;
//        Phone = phone;
//        UserName = userName;
        Permission = permission;
    }
}
