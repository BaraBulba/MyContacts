package android.example.mycontacts;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserRealmItem extends RealmObject {
    @PrimaryKey
    private long id;
    private String name, lastname, date, short_info, number;
    private byte [] avatar;

    public UserRealmItem(){}

    public long getId() { return id; }
    public String getName() { return name; }
    public String getLastname() { return lastname; }
    public String getDate() { return date; }
    public String getShort_info() { return short_info; }
    public String getNumber() { return number; }
    public byte[] getAvatar() { return avatar; }
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setDate(String date) { this.date = date; }
    public void setShort_info(String short_info) { this.short_info = short_info; }
    public void setNumber(String number) { this.number = number; }
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }
}
