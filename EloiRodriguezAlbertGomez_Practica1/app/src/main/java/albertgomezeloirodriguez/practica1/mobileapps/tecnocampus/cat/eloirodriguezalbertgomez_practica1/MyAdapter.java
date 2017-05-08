package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
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
    private DbAdapter mDbAdapter;

    public static final String EXTRA_MESSAGE = "studentDni";

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
    public MyAdapter(Context ctx) {
        mDataset = new ArrayList<Student>();
        mDbAdapter = DbAdapter.getInstance(ctx);
        fillData();
    }

    private void fillData() {

        mDbAdapter.open();

        //mDbAdapter.upgrade();

        Cursor notesCursor = mDbAdapter.fetchAllTodos();

        mDataset = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            mDataset.add(new Student(
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_NOM)),
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_SURNAME)),
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_TELF)),
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_DNI)),
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_GRAU)),
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_CURS))
            ));

            Log.d("SwA", notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_NOM)) +
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_SURNAME)) +
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_TELF)) +
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_DNI)) +
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_GRAU)) +
                    notesCursor.getString(notesCursor.getColumnIndex(DbAdapter.Todo.KEY_CURS)));

        }

        mDbAdapter.close();

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("SwA", "Open PopUp");

                PopupMenu popupMenu = new PopupMenu(holder.mRootView.getContext(), v);

                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;
                        String message;

                        switch (item.getItemId()) {

                            case R.id.viewStudent:
                                Intent intentView = new Intent(holder.mRootView.getContext(), StudentView.class);
                                String messageView = mDataset.get(position).getDni();
                                intentView.putExtra("studentDni", messageView);
                                holder.mRootView.getContext().startActivity(intentView);
                                intent = new Intent(holder.mRootView.getContext(), StudentView.class);
                                message = mDataset.get(position).getDni();
                                intent.putExtra("studentDni", message);
                                holder.mRootView.getContext().startActivity(intent);
                                Toast.makeText
                                        (holder.mRootView.getContext(),
                                                "You clicked: VIEW",
                                                Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.editStudent:
                                Intent intentEdit = new Intent(holder.mRootView.getContext(), EditStudent.class);
                                String messageEdit = mDataset.get(position).getDni();
                                intentEdit.putExtra("studentDni", messageEdit);
                                holder.mRootView.getContext().startActivity(intentEdit);
                                intent = new Intent(holder.mRootView.getContext(), EditStudent.class);
                                message = mDataset.get(position).getDni();
                                intent.putExtra("studentDni", message);
                                holder.mRootView.getContext().startActivity(intent);
                                Toast.makeText
                                        (holder.mRootView.getContext(),
                                                "You clicked: EDIT",
                                                Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.deleteStudent:
                                mDbAdapter.open();
                                Toast.makeText
                                        (holder.mRootView.getContext(),
                                                "You deleted: "
                                                        + mDataset.get(position).getNom()
                                                        + " "
                                                        + mDataset.get(position).getCognom(),
                                                Toast.LENGTH_SHORT).show();
                                Log.d("SwA", mDataset.get(position).getDni());
                                mDbAdapter.deleteTodo(mDataset.get(position).getDni());
                                mDbAdapter.close();
                                fillData();
                                notifyDataSetChanged();
                                break;

                        }

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
        TextView userGrau = (TextView) holder.mRootView.findViewById(R.id.userPhone);
        // Populate the data into the template view using the data object
        userName.setText(user.getNom());
        userSurname.setText(user.getCognom());

        SpannableString content = new SpannableString(user.getGrau());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        userGrau.setText(content);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


