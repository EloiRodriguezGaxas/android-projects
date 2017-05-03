package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1.domain.Student;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Student> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mRootView;

        public ViewHolder(View v) {
            super(v);
            mRootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Student> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("SwA", "Cliiiiiiiickkkkkk");

                PopupMenu popupMenu = new PopupMenu(holder.mRootView.getContext(), v);

                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        Toast.makeText
                                (holder.mRootView.getContext(),
                                "You clicked: " + item.getTitle(),
                                Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Student user = mDataset.get(position);
        TextView userName = (TextView) holder.mRootView.findViewById(R.id.userName);
        TextView userSurname = (TextView) holder.mRootView.findViewById(R.id.userSurname);
        TextView userPhone = (TextView) holder.mRootView.findViewById(R.id.userPhone);
        // Populate the data into the template view using the data object
        userName.setText(user.getNom());
        userSurname.setText(user.getCognom());
        userPhone.setText(user.getGrau());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


