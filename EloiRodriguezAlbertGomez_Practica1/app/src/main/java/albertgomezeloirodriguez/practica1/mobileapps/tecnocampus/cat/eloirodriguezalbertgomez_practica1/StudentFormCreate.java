package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1.domain.Student;

public class StudentFormCreate extends AppCompatActivity {

    private EditText nom, surname, dni, telefon;
    private final static String[] graus = {"GEI", "Ade", "CAFE", "GMA", "GV"};
    private final static String[] curs = {"1r", "2n", "3r", "4rt"};
    private String grauEscollit, cursEscollit;
    private Spinner grauSpinner, courseSpinner;
    private Button createStudent, cancelStudent;
    private ArrayAdapter<String> adapterGrau;
    private ArrayAdapter<String> adapterCurs;

    private DbAdapter mDbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form_create);

        mDbAdapter = DbAdapter.getInstance(getApplicationContext());
        mDbAdapter.open();

        nom = (EditText) this.findViewById(R.id.textEditName);
        surname = (EditText) this.findViewById(R.id.editTextSurname);
        dni = (EditText) this.findViewById(R.id.editTextDni);
        telefon = (EditText) this.findViewById(R.id.editTextTelf);

        adapterGrau = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, graus);
        grauSpinner = (Spinner) this.findViewById(R.id.spinnerGrau);
        grauSpinner.setAdapter(adapterGrau);

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

        adapterCurs = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, curs);
        courseSpinner = (Spinner) this.findViewById(R.id.spinnerCurs);
        courseSpinner.setAdapter(adapterCurs);

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

        cancelStudent = (Button) this.findViewById(R.id.cancelStudentBtn);
        cancelStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent calling = new Intent(StudentFormCreate.this, MainActivity.class);
                startActivity(calling);
                finish();

            }
        });

        createStudent = (Button) this.findViewById(R.id.createStudentBtn);
        createStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Log.i("[INFO]", "Create new Student");

                    if (nom.getText().toString().equals("")
                            || surname.getText().toString().equals("")
                            || telefon.getText().toString().equals("")
                            || dni.getText().toString().equals("")) {

                        throw new Exception("Not all fields yet");
                    }

                    Student s = new Student(nom.getText().toString(), surname.getText().toString(), telefon.getText().toString(),
                            dni.getText().toString(), grauEscollit, cursEscollit);

                    Log.i("[INFO]", s.toString());

                    mDbAdapter.createStudent(s.getNom(), s.getCognom(), s.getDni(), s.getTelf(),
                            s.getGrau(), s.getCurs());

                    Intent calling = new Intent(StudentFormCreate.this, MainActivity.class);
                    startActivity(calling);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}
