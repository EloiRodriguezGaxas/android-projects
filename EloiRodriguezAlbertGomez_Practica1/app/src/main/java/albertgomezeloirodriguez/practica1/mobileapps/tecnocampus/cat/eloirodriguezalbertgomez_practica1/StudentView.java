package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StudentView extends AppCompatActivity {

    private TextView nom, surname, dni, telf, grau, curs;
    private String putDni;
    private DbAdapter dbAdapter;
    private Button delte, back, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
        Intent intent = getIntent();
        this.putDni= intent.getExtras().getString("studentDni");

        Log.d("SwA", this.putDni);

        this.nom=(TextView)this.findViewById(R.id.studentViewEditName);
        this.surname=(TextView)this.findViewById(R.id.studentViewEditSurname);
        this.dni=(TextView)this.findViewById(R.id.studentViewEditDni);
        this.telf=(TextView)this.findViewById(R.id.studentViewEditTelf);
        this.grau=(TextView)this.findViewById(R.id.studentViewEditGrau);
        this.curs=(TextView)this.findViewById(R.id.studentViewEditCurs);
        this.delte=(Button)this.findViewById(R.id.studentViewBtnDelete);
        this.back=(Button)this.findViewById(R.id.studentViewBtnBack);
        this.edit=(Button)this.findViewById(R.id.studentViewBtnEdit);

        dbAdapter=DbAdapter.getInstance(this);
        dbAdapter.open();

        Cursor c=dbAdapter.fetchTodo(this.putDni);
        this.nom.setText(c.getString(1));
        this.surname.setText(c.getString(2));
        this.dni.setText(c.getString(3));
        this.telf.setText(c.getString(4));
        this.grau.setText(c.getString(5));
        this.curs.setText(c.getString(6));


        delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.deleteTodo(putDni);
                Intent calling = new Intent(StudentView.this, MainActivity.class);
                startActivity(calling);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calling = new Intent(StudentView.this, MainActivity.class);
                startActivity(calling);
                finish();

            }
        });

        dbAdapter.close();









    }
}
