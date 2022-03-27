package android.example.mycontacts;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserRealm extends RealmObject {

    public UserRealm(){}
    @PrimaryKey
    long id;
    String name, lastname, date, short_info, number;
    byte [] avatar;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getShort_info() { return short_info; }
    public void setShort_info(String short_info) { this.short_info = short_info; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public byte[] getAvatarBytes() { return avatar; }
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }
}
