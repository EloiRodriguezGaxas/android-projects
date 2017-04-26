package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1.domain.Student;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dummyItems;
    private ListView list;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.studentList);

        this.getDummyItems();
        this.itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                this.dummyItems);
        list.setAdapter(itemsAdapter);


        FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public ArrayList<String> getDummyItems() {
        dummyItems = new ArrayList<String>();
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        dummyItems.add((new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r")).toString());
        return dummyItems;
    }

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
