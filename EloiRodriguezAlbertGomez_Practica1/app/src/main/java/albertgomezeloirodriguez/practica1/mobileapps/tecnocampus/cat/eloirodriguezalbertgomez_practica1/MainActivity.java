package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static ArrayList<String> dummyItems;
    private ListView list;
    private static final int OPERATION_CODE = 0;
    private DbAdapter mDbAdapter;
    private SimpleCursorAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.studentList);

        mDbAdapter = DbAdapter.getInstance(getApplicationContext());
        mDbAdapter.open();

        if (mDbAdapter.isEmpty()) {
            mDbAdapter.createTodo("pepe", "gomez", "666666666", "77621235S", "GEI", "1r");
            mDbAdapter.createTodo("pepe", "gomez", "666666666", "77621235S", "GEI", "1r");
            mDbAdapter.createTodo("pepe", "gomez", "666666666", "77621235S", "GEI", "1r");
        }

        fillData();


        /*FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calling = new Intent(MainActivity.this, StudentFormCreate.class);
                startActivityForResult(calling, OPERATION_CODE);
            }
        });*/
    }

    private void fillData() {
        Cursor notesCursor = mDbAdapter.fetchAllTodos();

        //Create an array to specify the fields we want to display in the list
        String[] from = new String[]{DbAdapter.Todo.KEY_NOM, DbAdapter.Todo.KEY_SURNAME, DbAdapter.Todo.KEY_GRAU};

        //and an array of the fields we want to bind those fields to
        int[] to = new int[]{android.R.id.text1};

        //Now create a simple cursor adapter and set it to display
        itemsAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, notesCursor, from, to, 0);

        list.setAdapter(itemsAdapter);
    }

    @Override
    public void onDestroy() {
        mDbAdapter.close();
        super.onDestroy();
    }


    private void changeToCreate() {

    }
/*
   public  ArrayList<String> getDummyItems() {

        dummyItems=new ArrayList<>();
       for (int i = 0; i<13; i++)
            dummyItems.add(new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r").toString());


        return dummyItems;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
