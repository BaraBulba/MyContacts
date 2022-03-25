package android.example.mycontacts;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

final public class User implements Serializable {



         String name, lastname, date, short_info, number;
         byte [] avatar;


    public User(String name, String lastname, String date, String short_info, String number, byte [] avatar) {
        this.name = name;
        this.lastname = lastname;
        this.date = date;
        this.short_info = short_info;
        this.number = number;
        this.avatar = avatar;


    }


    public static ArrayList<User> mocked(ArrayList<Drawable> drawableArray)  {
        ArrayList<User> userArrayList = new ArrayList<>();
//        Drawable resImg = this.context.getResources().getDrawable(R.drawable.andrey_1);
        User firstUser = new User ("Andrew","Solokhov","24.11.1995","Начинающий Андроид разработчик","+8934086340", convertToByteArray(drawableArray.get(0)));
        User secondUser = new User ("Dmitriy","Grusha","21.07.199","Гуру и наставник","+934694334",convertToByteArray(drawableArray.get(1)));
        User thirdUser = new User ("Andrew","Vashulenko","25.11.1991","Главный распорядитель и наводитель шороха","+947693467934",convertToByteArray(drawableArray.get(2)));
        User fourthUser = new User ("Valeriya","Smorodinova","01.09.1999","Лучшая девочка стримерша","+9456903467934",convertToByteArray(drawableArray.get(3)));
        User fifthUser = new User ("Svetlana","Solokhova","26.11.1968","Главный математик всея Умани","+38543869340",convertToByteArray(drawableArray.get(4)));
        User sixUser = new User ("Mihail","Zverev","26.02.1993","Главный комментатор","+3254756867",convertToByteArray(drawableArray.get(5)));
        userArrayList.add(firstUser);
        userArrayList.add(secondUser);
        userArrayList.add(thirdUser);
        userArrayList.add(fourthUser);
        userArrayList.add(fifthUser);
        userArrayList.add(sixUser);
        return userArrayList;
    }

    public static byte [] convertToByteArray(Drawable drawable) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        drawableToBitmap(drawable).compress(Bitmap.CompressFormat.JPEG, 150, stream);
        return stream.toByteArray();
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        Log.v("Bitmap width - Height :", width + " : " + height);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }






    public String getName() {
        return name;
    }
    public String getLastname() {
        return lastname;
    }
    public String getDate() {
        return date;
    }
    public String getShort_info() {
        return short_info;
    }
    public String getNumber() {
        return number;
    }
    public byte[] getAvatarBytes () {
        return avatar;
    }
//    public int getAvatar () {
//        return ByteBuffer.wrap(this.avatar).getInt();
//    }

}

