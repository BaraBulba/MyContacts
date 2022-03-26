package android.example.mycontacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<User> userArrayList;
    private Context context;

    public ListAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }


    public void filterList(ArrayList<User> filterllist) {
        userArrayList = filterllist;
        notifyDataSetChanged();
    }

    public void refresh (User user) {
        userArrayList.add(user);
        notifyDataSetChanged();
    }

    public ArrayList<Drawable> fillInDrawableArray() {
        ArrayList<Drawable> drawableArrayList = new ArrayList<>();
        Drawable firstUserAvatar = ResourcesCompat.getDrawable(context.getResources(), R.drawable.andrey_1, null);
        Drawable secondUserAvatar = ResourcesCompat.getDrawable(context.getResources(), R.drawable.andrey_1, null);
        Drawable thirdUserAvatar = ResourcesCompat.getDrawable(context.getResources(), R.drawable.andrey_1, null);
        Drawable forthUserAvatar = ResourcesCompat.getDrawable(context.getResources(), R.drawable.andrey_1, null);
        Drawable fifthUserAvatar = ResourcesCompat.getDrawable(context.getResources(), R.drawable.andrey_1, null);
        Drawable sixUserAvatar = ResourcesCompat.getDrawable(context.getResources(), R.drawable.andrey_1, null);
        drawableArrayList.add(firstUserAvatar);
        drawableArrayList.add(secondUserAvatar);
        drawableArrayList.add(thirdUserAvatar);
        drawableArrayList.add(forthUserAvatar);
        drawableArrayList.add(fifthUserAvatar);
        drawableArrayList.add(sixUserAvatar);
        return drawableArrayList;
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        Bitmap bmp = BitmapFactory.decodeByteArray(user.getAvatarBytes(), 0, user.getAvatarBytes().length);
        holder.CIVAvatar.setImageBitmap(Bitmap.createBitmap(bmp));
        holder.name_cont.setText(user.name);
        holder.lastname_cont.setText(user.lastname);
        holder.short_info_cont.setText(user.short_info);
        holder.number_cont.setText(user.number);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UserActivity.class);
                i.putExtra("name", user.getName());
                i.putExtra("lastname", user.getLastname());
                i.putExtra("date", user.getDate());
                i.putExtra("number", user.getNumber());
                i.putExtra("shortinfo", user.getShort_info());
                i.putExtra("avatar", user.getAvatarBytes());

                context.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView CIVAvatar;
        private TextView name_cont;
        private TextView lastname_cont;
        private TextView number_cont;
        private TextView short_info_cont;

        public ViewHolder(View itemView) {
            super(itemView);

            CIVAvatar = itemView.findViewById(R.id.idCIVAvatar);
            name_cont = itemView.findViewById(R.id.PersonName);
            lastname_cont = itemView.findViewById(R.id.LastName);
            number_cont = itemView.findViewById(R.id.idTVNumber);
            short_info_cont = itemView.findViewById(R.id.idTVShortInfo);
        }
    }

}
