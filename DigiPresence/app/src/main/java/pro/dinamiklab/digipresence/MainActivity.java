package pro.dinamiklab.digipresence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edMatricule;
    Button btnSuivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMatricule = findViewById(R.id.txt_matricule);
        btnSuivant = findViewById(R.id.btn_next);

        btnSuivant.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent allerAuChoix = new Intent(MainActivity.this, ChoixActivity.class);
                        allerAuChoix.putExtra("Matricule", edMatricule.getText().toString());
                        startActivity(allerAuChoix);

                        if (edMatricule != null) edMatricule.setText("");
                    }
                }
        );
    }
}