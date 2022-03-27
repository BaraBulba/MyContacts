package android.example.mycontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ClipData;
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
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView empty_imageView;
    private TextView no_contactsText;
    private ArrayList<User> userArrayList;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private FloatingActionButton addNewContactFAB;
    private SearchView searchView;
    public final static int REQ_CODE_CHILD = 1;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CHILD) {
            if (data != null) {
                User myObject = (User) data.getExtras().getSerializable("KEY_GOES_HERE");
                adapter.refresh(myObject);
                visibility();
            }
        }
    }

    private void visibility() {
        if (userArrayList.isEmpty()) {
            empty_imageView.setVisibility(View.VISIBLE);
            no_contactsText.setVisibility(View.VISIBLE);
        } else {
            empty_imageView.setVisibility(View.GONE);
            no_contactsText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_menu_background));
        }

        searchView = findViewById(R.id.con_search);
        recyclerView = findViewById(R.id.idRVContact);
        addNewContactFAB = findViewById(R.id.idFABadd);
        empty_imageView = findViewById(R.id.empty_imageview);
        no_contactsText = findViewById(R.id.empty_textview);
        userArrayList = new ArrayList<>();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove (@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target ) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                User deletedCourse = userArrayList.get(viewHolder.getAbsoluteAdapterPosition());
                int position = viewHolder.getAbsoluteAdapterPosition();
                userArrayList.remove(viewHolder.getAbsoluteAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
                Snackbar.make(recyclerView, deletedCourse.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userArrayList.add(position, deletedCourse);
                        adapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);
        prepare();

        addNewContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent child = new Intent(MainActivity.this, CreateNewContactActivity.class);
                startActivityForResult(child, REQ_CODE_CHILD);
            }
        });


    }

    private void prepare() {
        adapter = new ListAdapter(this, userArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        visibility();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.con_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.con_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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
            if ((item.getName() + item.getLastname()).toLowerCase().contains(text.toLowerCase())) {
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
