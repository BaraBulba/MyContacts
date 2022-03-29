package android.example.mycontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    private ImageView empty_imageView;
    private TextView no_contactsText;
    private ArrayList<User> userArrayList;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private FloatingActionButton addNewContactFAB;
    private SearchView searchView;
    private Realm realm;
    public final static int REQ_CODE_CHILD = 1;
    public boolean isClickable = true;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CHILD) {
//            if (data != null) {
//                User myObject = (User) data.getExtras().getSerializable("KEY_GOES_HERE");
//                adapter.refresh(myObject);
            showData();
            adapter = new ListAdapter(this, userArrayList);
            visibility();
            recyclerView.setAdapter(adapter);
//            }
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
        realm = Realm.getDefaultInstance();
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
                if (isClickable == false){
                    return;
                }
                isClickable = false;
                User user = userArrayList.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                System.out.println("Position =" + position);
                System.out.println("Userarraylist =" + userArrayList);
                userArrayList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar snackbar = Snackbar.make(recyclerView, user.getName() + "       been Removed! ", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isClickable = true;
                        userArrayList.add(position, user);
                        adapter.notifyItemInserted(position);
                    }
                });
                snackbar.setActionTextColor(Color.BLACK);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        long id = userArrayList.get(position).getId();
                        deleteContact(id);
                    }
                });
                snackbar.show();
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);
        showData();
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

    private void deleteContact(long id) {
        UserRealmItem userItem = realm.where(UserRealmItem.class).equalTo("id", id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                userItem.deleteFromRealm();
            }
        });
    }

    private void showData() {
        userArrayList.removeAll(userArrayList);
        List<UserRealmItem> realmList = realm.where(UserRealmItem.class).findAll();
        for (UserRealmItem item: realmList) {
            User user = new User(item.getId(), item.getName(), item.getLastname(), item.getNumber(), item.getDate(), item.getShort_info(), item.getAvatar());
            userArrayList.add(user);
        }
    }

    private void update() {
        showData();
        adapter = new ListAdapter(this, userArrayList);
        visibility();
        recyclerView.setAdapter(adapter);
    }

}
