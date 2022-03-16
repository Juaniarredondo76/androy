package com.example.aplicacion_concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FacturaActivity extends AppCompatActivity {

    EditText jetcodigo,jetfecha,jetidentificacion,jetplaca;
    Button  jbtguardar,jbtconsultar,jbtanular, jbtcancelar,jbtregresar;
    String placa;
    long resp,sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        getSupportActionBar().hide();
        jetcodigo=findViewById(R.id.etcodigo);
        jetfecha=findViewById(R.id.etfecha);
        jetidentificacion=findViewById(R.id.etidentificacion);
        jetplaca=findViewById(R.id.etplaca);
        jbtguardar=findViewById(R.id.btguardar);
        jbtconsultar=findViewById(R.id.btconsultar);
        jbtanular=findViewById(R.id.btanular);
        jbtcancelar=findViewById(R.id.btcancelar);
        jbtregresar=findViewById(R.id.btregresar);
        sw=0;
    }
    public void Guardar(View view) {
        String codigo, fecha, identificacion, placa;
        placa = jetplaca.getText().toString();
        codigo = jetcodigo.getText().toString();
        fecha = jetfecha.getText().toString();
        identificacion = jetidentificacion.getText().toString();
        if (placa.isEmpty() || codigo.isEmpty() || fecha.isEmpty() || identificacion.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else{
            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila1=db.rawQuery("select * from TblCliente where idcliente='" + identificacion + "'",null);
            if (fila1.moveToNext()){
                Cursor fila2=db.rawQuery("select * from TblVehiculo where placa='" + placa + "'",null);
                if (fila2.moveToNext()){
                    db.close();
                    Conexion_Sqlite admin1=new Conexion_Sqlite(this,"concesionario.db",null,1);
                    SQLiteDatabase db1=admin1.getWritableDatabase();
                    ContentValues registro=new ContentValues();
                    registro.put("placa",placa);
                    registro.put("codigo",codigo);
                    registro.put("fecha",fecha);
                    registro.put("identificacion",identificacion);
                    resp=db1.insert("TblFactura",null,registro);
                    if (resp > 0){
                        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                        limpiar_campos();
                    }
                    else{
                        Toast.makeText(this, "Error Guardando registro", Toast.LENGTH_SHORT).show();
                    }
                    db1.close();

                }
                else{
                    Toast.makeText(this, "Automovil no esta registrado", Toast.LENGTH_SHORT).show();
                    jetplaca.requestFocus();
                }


            }
            else{
                Toast.makeText(this, "Cliente no esta registrado", Toast.LENGTH_SHORT).show();
                jetidentificacion.requestFocus();
            }


        }
    }

    public void Consultar(View view){
        Consultar_vehiculo();
    }

    public void Anular(View view){
        Consultar_vehiculo();
        if (sw == 1){
            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario.db",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("placa",placa);
            registro.put("activo","no");
            resp=db.update("TblFactura",registro,"placa='" + placa + "'",null);
            if (resp > 0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                limpiar_campos();
            }
            else{
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    public void Cancelar(View view){
        limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmenu=new Intent(this,MenuActivity.class);
        startActivity(intmenu);
    }

    private void Consultar_vehiculo(){
        placa=jetplaca.getText().toString();
        if (placa.isEmpty()){
            Toast.makeText(this, "La placa es requerida para la busqueda", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else{
            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblVehiculo where placa='" + placa + "'",null);
            if (fila.moveToNext()){
                sw=1;
                jetidentificacion.setText(fila.getString(1));
                jetcodigo.setText(fila.getString(2));
                jetfecha.setText(fila.getString(3));
                jetplaca.setText(fila.getString(4));
            }
            else{
                Toast.makeText(this, "Registro no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    private void limpiar_campos(){
        jetplaca.setText("");
        jetcodigo.setText("");
        jetfecha.setText("");
        jetidentificacion.setText("");
        jetplaca.requestFocus();
        sw=0;
    }
}