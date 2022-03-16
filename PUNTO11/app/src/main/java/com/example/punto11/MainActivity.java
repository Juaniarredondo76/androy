package com.example.punto11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText jtxt1;
    TextView jmeses,jdias;
    Button jboton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jtxt1=findViewById(R.id.et1);
        jmeses=findViewById(R.id.tx2);
        jdias=findViewById(R.id.tx3);
        jboton=findViewById(R.id.calcularboton);

    }
    public void  calcularEdad(View view){

        String edad;

        edad=jtxt1.getText().toString();

        if(edad.isEmpty()){
            jtxt1.requestFocus();
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
        else{

            float ed , meses , dias;

            ed=Float.parseFloat(edad);

            dias=ed *365;

            meses=ed*12;

            jdias.setText(String.valueOf(dias));
            jmeses.setText(String.valueOf(meses));

        }


    }
}