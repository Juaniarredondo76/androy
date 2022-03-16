package com.example.operaciones_basicas_globant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText jetnumero1,jetnumero2;
    TextView jtvsuma,jtvresta,jtvmultiplicacion,jtvdivision;
    Button jbtcalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        jetnumero1=findViewById(R.id.etnumero1);
        jetnumero2=findViewById(R.id.etnumero2);
        jtvsuma=findViewById(R.id.tvsuma);
        jtvresta=findViewById(R.id.tvresta);
        jtvmultiplicacion=findViewById(R.id.tvmultiplicacion);
        jtvdivision=findViewById(R.id.tvdivision);
        jbtcalcular=findViewById(R.id.btcalcular);
    }

    public void CalcularOperaciones(View view){
        String numero1,numero2;
        numero1=jetnumero1.getText().toString();
        numero2=jetnumero2.getText().toString();
        if(numero1.isEmpty()|| numero2.isEmpty()){
            jetnumero1.requestFocus();
            Toast.makeText(this,"los dos numeros son requeridos ",Toast.LENGTH_LONG).show();
        }
        else {
            float num1,num2,resta,suma,division,multiplicacion;

            num1=Float.parseFloat(numero1);
            num2=Float.parseFloat(numero2);

            suma=num1+num2;
            resta=num1-num2;
            multiplicacion=num1*num2;
            jtvsuma.setText(String.valueOf(suma) );
            jtvresta.setText(String.valueOf(resta) );
            jtvmultiplicacion.setText(String.valueOf(multiplicacion) );
            if(num2==0){
                jetnumero2.requestFocus();

                Toast.makeText(this, "operacion no valida ", Toast.LENGTH_SHORT).show();
            }
            else{
                division=num1/num2;
                jtvdivision.setText(String.valueOf(division));
            }

        }
    }
}