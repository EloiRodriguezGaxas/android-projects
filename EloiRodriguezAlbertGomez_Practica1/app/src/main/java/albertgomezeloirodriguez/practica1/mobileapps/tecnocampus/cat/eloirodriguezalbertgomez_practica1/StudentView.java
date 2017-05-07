package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        final Cursor c=dbAdapter.fetchTodo(this.putDni);
        this.nom.setText(c.getString(DbAdapter.Todo.ROW_NOM));
        this.surname.setText(c.getString(DbAdapter.Todo.ROW_SURNAME));
        this.dni.setText(c.getString(DbAdapter.Todo.ROW_DNI));
        this.telf.setText(c.getString(DbAdapter.Todo.ROW_TELF));
        this.grau.setText(c.getString(DbAdapter.Todo.ROW_GRAU));
        this.curs.setText(c.getString(DbAdapter.Todo.ROW_CURS));


        deleteBtnListener();

        editBtnListener(c);

        backBtnListener();

        dbAdapter.close();

    }

    private void backBtnListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calling = new Intent(StudentView.this, MainActivity.class);
                startActivity(calling);
                finish();

            }
        });
    }

    private void editBtnListener(final Cursor c) {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageEdit = c.getString(4);
                Intent caliing = new Intent(StudentView.this, EditStudent.class);
                caliing.putExtra("studentDni", messageEdit);
                startActivity(caliing);
                finish();
            }
        });
    }

    private void deleteBtnListener() {
        delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.open();
                dbAdapter.deleteTodo(putDni);
                Intent calling = new Intent(StudentView.this, MainActivity.class);
                startActivity(calling);
                finish();
                dbAdapter.close();
            }
        });
    }


}