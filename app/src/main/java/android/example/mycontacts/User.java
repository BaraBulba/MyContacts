package android.example.mycontacts;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User {
    public User(){}
        @PrimaryKey
        long id;
        String name, lastname, date, short_info, number;
        byte [] avatar;

    public User(String name, String lastname, String number, String date, String short_info, byte [] avatar) {
        this.name = name;
        this.lastname = lastname;
        this.number = number;
        this.date = date;
        this.short_info = short_info;
        this.avatar = avatar;
    }


//    public static byte [] convertToByteArray(Drawable drawable) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        drawableToBitmap(drawable).compress(Bitmap.CompressFormat.JPEG, 150, stream);
//        return stream.toByteArray();
//    }

//    public static Bitmap drawableToBitmap(Drawable drawable) {
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable) drawable).getBitmap();
//        }
//        int width = !drawable.getBounds().isEmpty() ? drawable
//                .getBounds().width() : drawable.getIntrinsicWidth();
//
//        int height = !drawable.getBounds().isEmpty() ? drawable
//                .getBounds().height() : drawable.getIntrinsicHeight();
//
//        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
//                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);
//
//        Log.v("Bitmap width - Height :", width + " : " + height);
//
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//
//        return bitmap;
//    }



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


//    public String getName() {
//        return name;
//    }
//    public String getLastname() {
//        return lastname;
//    }
//    public String getDate() {
//        return date;
//    }
//    public String getShort_info() {
//        return short_info;
//    }
//    public String getNumber() {
//        return number;
//    }
//    public byte[] getAvatarBytes () {
//        return avatar;
//    }
}

