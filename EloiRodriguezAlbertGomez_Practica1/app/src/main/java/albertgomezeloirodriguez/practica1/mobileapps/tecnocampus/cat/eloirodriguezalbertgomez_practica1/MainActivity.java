package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Intent;
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

    private static ArrayList<String> dummyItems;
    private ListView list;
    private ArrayAdapter<String> itemsAdapter;
    private static final int OPERATION_CODE = 0;

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
                Intent calling = new Intent(MainActivity.this, StudentFormCreate.class);
                startActivityForResult(calling, OPERATION_CODE);
            }
        });
    }

    public static void createdStudent(Student student){

        dummyItems.add(student.toString());
    }

    private void changeToCreate() {

    }

   public  ArrayList<String> getDummyItems() {

        dummyItems=new ArrayList<>();
       for (int i = 0; i<13; i++)
            dummyItems.add(new Student("pepe", "gomez", "666666666", "77621235S", "GEI", "1r").toString());


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
