package android.example.mycontacts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.example.mycontacts.databinding.ActivityMainBinding;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {



    private Cursor cursor;
    private Context context;
    private ConstraintLayout itemList;
    private ImageView empty_imageView;
    private TextView no_contactsText;
    private ArrayList<User> userArrayList;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private FloatingActionButton addNewContactFAB;
    public final static int REQ_CODE_CHILD = 1;


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CHILD) {
            if (data != null) {
                User myObject = (User) data.getExtras().getSerializable("KEY_GOES_HERE");
                adapter.refresh(myObject);
                visibility();
            }
        }
    }

    private void visibility () {
        if (userArrayList.isEmpty()) {
            empty_imageView.setVisibility(View.VISIBLE);
            no_contactsText.setVisibility(View.VISIBLE);
        }
        else {
            empty_imageView.setVisibility(View.GONE);
            no_contactsText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_menu_background));

        }

        itemList = findViewById(R.id.itemList);
        recyclerView = findViewById(R.id.idRVContact);
        addNewContactFAB = findViewById(R.id.idFABadd);
        empty_imageView = findViewById(R.id.empty_imageview);
        no_contactsText = findViewById(R.id.empty_textview);
        userArrayList = new ArrayList<>();
        prepare();



        addNewContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent child = new Intent(MainActivity.this, CreateNewContactActivity.class);
//                Intent child = new Intent(getPackageName(), Uri.parse("android.example.mycontacts.CreateNewContactActivity"));
                startActivityForResult(child, REQ_CODE_CHILD);

//                Intent i = new Intent(MainActivity.this, CreateNewContactActivity.class);
//                startActivity(i);
            }
        });

//        customAdapter = new CustomAdapter(MainActivity.this, contact_name, contact_last_name, contact_number, contact_date, contact_info);
//        recyclerView.setAdapter(customAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }


    private void prepare() {
        adapter = new ListAdapter(this, userArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        visibility();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.con_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.con_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText.toLowerCase());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {

        ArrayList<User> filteredlist = new ArrayList<>();

        for (User item : userArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

}