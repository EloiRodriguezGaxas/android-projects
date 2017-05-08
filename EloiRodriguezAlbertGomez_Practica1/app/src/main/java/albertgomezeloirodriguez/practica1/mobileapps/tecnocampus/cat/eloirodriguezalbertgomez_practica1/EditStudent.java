package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1.domain.Student;

public class EditStudent extends AppCompatActivity {

    private EditText nom, surname, telefon;
    private final static String[] graus = {"GEI", "ADE", "CAFE", "GMA", "GV"};
    private final static String[] curs = {"1r", "2n", "3r", "4rt"};
    private String grauEscollit, cursEscollit;
    private Spinner grauSpinner, courseSpinner;
    private Button createStudent, cancelStudent;
    private ArrayAdapter<String> adapterGrau;
    private ArrayAdapter<String> adapterCurs;
    private TextView dni;

    private DbAdapter mDbAdapter;

    private String dniGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        Intent intent = getIntent();
        this.dniGet = intent.getExtras().getString("studentDni");

        Log.d("SwA", dniGet);

        mDbAdapter = DbAdapter.getInstance(getApplicationContext());
        mDbAdapter.open();

        this.findComponents();

        this.writeAllComponents();

        this.prepareSpinersListeners();

        cancelStudent = (Button) this.findViewById(R.id.cancelStudentBtnE);
        cancelStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent calling = new Intent(EditStudent.this, MainActivity.class);
                startActivity(calling);
                finish();

            }
        });

        createStudent = (Button) this.findViewById(R.id.createStudentBtnE);
        createStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCreateListener();
            }
        });

    }

    //Find all components
    private void findComponents() {

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        nom = (EditText) this.findViewById(R.id.textEditNameE);
        surname = (EditText) this.findViewById(R.id.editTextSurnameE);
        dni = (TextView) this.findViewById(R.id.editTextDniE);
        telefon = (EditText) this.findViewById(R.id.editTextTelfE);

        //Spiner graus
        adapterGrau = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, graus);
        grauSpinner = (Spinner) this.findViewById(R.id.spinnerGrauE);
        grauSpinner.setAdapter(adapterGrau);

        //Spiner courses
        adapterCurs = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, curs);
        courseSpinner = (Spinner) this.findViewById(R.id.spinnerCursE);
        courseSpinner.setAdapter(adapterCurs);

    }

    //Set the spiners listeners
    private void prepareSpinersListeners() {
        grauSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grauEscollit = graus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                grauEscollit = graus[0];
            }
        });


        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cursEscollit = curs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cursEscollit = curs[0];
            }
        });
    }

    private void buttonCreateListener() {
        try {

            Log.i("[INFO]", "Create new Student");

            if (nom.getText().toString().equals("")
                    || surname.getText().toString().equals("")
                    || telefon.getText().toString().equals("")
                    || dni.getText().toString().equals("")) {

                throw new Exception("Not all fields yet");
            }

            Student s = new Student(nom.getText().toString(), surname.getText().toString(), telefon.getText().toString(),
                    dniGet, grauEscollit, cursEscollit);

            Log.i("[INFO]", s.toString());

            mDbAdapter.updateStudent(s.getNom(), s.getCognom(), s.getDni(), s.getTelf(),
                    s.getGrau(), s.getCurs());

            Intent calling = new Intent(EditStudent.this, MainActivity.class);
            startActivity(calling);
            finish();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_LONG).show();
        }
    }

    private void writeAllComponents() {

        Cursor c = mDbAdapter.fetchTodo(this.dniGet);

        this.nom.setText(c.getString(DbAdapter.Todo.ROW_NOM));
        this.surname.setText(c.getString(DbAdapter.Todo.ROW_SURNAME));
        this.dni.setText(dniGet);
        this.telefon.setText(c.getString(DbAdapter.Todo.ROW_TELF));

        switch (c.getString(DbAdapter.Todo.ROW_GRAU)) {
            case "GEI":
                this.grauSpinner.setSelection(0);
                break;
            case "ADE":
                this.grauSpinner.setSelection(1);
                break;
            case "CAFE":
                this.grauSpinner.setSelection(2);
                break;
            case "GMA":
                this.grauSpinner.setSelection(3);
                break;
            case "GV":
                this.grauSpinner.setSelection(4);
                break;
        }

        switch (c.getString(DbAdapter.Todo.ROW_CURS)) {
            case "1r":
                this.courseSpinner.setSelection(0);
                break;
            case "2n":
                this.courseSpinner.setSelection(1);
                break;
            case "3r":
                this.courseSpinner.setSelection(2);
                break;
            case "4rt":
                this.courseSpinner.setSelection(3);
                break;
        }

    }

}
