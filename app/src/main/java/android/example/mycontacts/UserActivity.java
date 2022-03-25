package android.example.mycontacts;

import android.content.Intent;
import android.example.mycontacts.databinding.ActivityUserBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.ByteBuffer;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
//     private String name, lastname, number, date, info;
//     private TextView nameTV, lastnameTv, numberTv, dateTv, infoTv;
//     private int avatarBig;



         @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null) {

            String name = intent.getStringExtra("name");
            String lastname = intent.getStringExtra("lastname");
            String date = intent.getStringExtra("date");
            String number = intent.getStringExtra("number");
            String shortinfo = intent.getStringExtra("shortinfo");
            byte [] avatarBytes = intent.getByteArrayExtra("avatar");


            binding.nameProfile.setText(name);
            binding.lastnameProfile.setText(lastname);
            binding.phoneProfile.setText(number);
            binding.dateProfile.setText(date);
            binding.infoProfile.setText(shortinfo);
            Bitmap bmp = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
            binding.idProfileAvatar.setImageBitmap(Bitmap.createBitmap(bmp));
//            binding.idProfileAvatar.setImageResource(ByteBuffer.wrap(avatarBytes).getInt());






        }
    }

}


