package android.example.mycontacts;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class CreateNewContactActivity extends AppCompatActivity {

    private Button addContactEdt, addAvatar;
    private ImageView previewImage;
    private int SELECT_PICTURE = 200;
    private RelativeLayout relativeLayout;
    private EditText nameEdt, numberEdt, infoEdt, eText, lastNameEdt;
    private DatePickerDialog picker;
    private Bitmap bitAvatar;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_menu_background));

        }

        realm = Realm.getDefaultInstance();
        relativeLayout = findViewById(R.id.New_con_lay);
        addContactEdt = findViewById(R.id.idBtnAddContact);
        previewImage = findViewById(R.id.PreviewImage);
        addAvatar = findViewById(R.id.idBtnAvatar);
        nameEdt = findViewById(R.id.idEdtName);
        lastNameEdt = findViewById(R.id.idEdtLastName);
        numberEdt = findViewById(R.id.idEdtNumber);
        infoEdt = findViewById(R.id.idEdtInfo);
        eText = findViewById(R.id.idEdtDate);
        registerForContextMenu(addAvatar);

        addContactEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] avatar = ImageViewToByte(previewImage);
                String name = nameEdt.getText().toString();
                String lastname = lastNameEdt.getText().toString();
                String number = numberEdt.getText().toString();
                String date = eText.getText().toString();
                String info = infoEdt.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    nameEdt.setError("Please enter Name");
                }
                else if (TextUtils.isEmpty(lastname)) {
                    lastNameEdt.setError("Please enter Last Name");
                }
                else if (TextUtils.isEmpty(number)) {
                    numberEdt.setError("Please enter Number");
                }
                else if (TextUtils.isEmpty(date)) {
                    eText.setError("Please enter Date");
                }
                else if (TextUtils.isEmpty(info)) {
                    infoEdt.setError("Please enter Short Info");
                }
                else {
                    addDataToDatabase(name, lastname, number, date, info, avatar);
                }
//                User user = new User(name, lastname, date, info, number, avatar);
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("KEY_GOES_HERE", user);
//                int resultCode = 1;
//                setResult(MainActivity.REQ_CODE_CHILD, resultIntent);
                finish();


            }
        });

        eText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(CreateNewContactActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                                eText.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                                eText.setHintTextColor(R.drawable.gradient_fab_background);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        addAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void addDataToDatabase(String name, String lastName, String number, String date, String shortInfo, byte[] avatar) {
        UserRealmItem userRealmItem = new UserRealmItem();
        Number id = realm.where(UserRealmItem.class).max("id");
        long nextId;
        if (id == null) {
            nextId = 1;
        } else {
            nextId = id.intValue() + 1;
        }
//        userRealmItem.setId(nextId);
//        userRealmItem.setName(name);
//        userRealmItem.setLastname(lastName);
//        userRealmItem.setNumber(number);
//        userRealmItem.setDate(date);
//        userRealmItem.setShort_info(shortInfo);
//        userRealmItem.setAvatar(avatar);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                userRealmItem.setId(nextId);
                userRealmItem.setName(name);
                userRealmItem.setLastname(lastName);
                userRealmItem.setNumber(number);
                userRealmItem.setDate(date);
                userRealmItem.setShort_info(shortInfo);
                userRealmItem.setAvatar(avatar);

                realm.copyToRealm(userRealmItem);
            }
        });
    }

    private byte [] ImageViewToByte (ImageView avatar) {
        Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte [] bytes = stream.toByteArray();
        return bytes;
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    previewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}