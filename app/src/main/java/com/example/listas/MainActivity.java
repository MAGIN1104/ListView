package com.example.listas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.Transliterator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText entrada;
    EditText pos;
    ListView lista;
    TextView salida;
    ArrayAdapter<String> list_adaptador;
    ArrayList<String> ListaArray;
    int n;
    private final int valor = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.entrada);
        pos = (EditText) findViewById(R.id.editTextPos);
        salida = (TextView) findViewById(R.id.textData);
        lista = (ListView) findViewById(R.id.listv);
        ListaArray = new ArrayList<String>();
        list_adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaArray);
        lista.setAdapter(list_adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                salida.setText(">  " + lista.getItemAtPosition(position) + "");
                n = position;
                String phoneNumber = salida.getText().toString();
                if (phoneNumber != null){
                    //Verificamos que las versiones de android sean nuevas
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, valor);
                    } else{
                        //de lo contrario solo usa el metodo para versiones antiguas
                        OlderVersions(phoneNumber);
                    }

                }
            }
            //PARA VERSIONES ANTIGUAS DE ANDROID
            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));

                int result = checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
                if ( result == PackageManager.PERMISSION_GRANTED){

                    startActivity(intentCall);}
                else{
                    Toast.makeText(MainActivity.this, "Acceso no autorizado", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void agregaItem(View view) {
        if (pos.getText().toString().isEmpty()) {
            if (!entrada.getText().toString().isEmpty()) {
                ListaArray.add(entrada.getText().toString() + "");
                list_adaptador.notifyDataSetChanged();
                entrada.setText("");
                salida.setText("");
                Toast.makeText(this, "Agregado correctamente.", Toast.LENGTH_SHORT).show();
                n = -1;
            } else {
                Toast.makeText(this, "Para agregar Item. Inserte datos en \"New Item\".", Toast.LENGTH_LONG).show();
            }
        } else if (Integer.parseInt(pos.getText().toString()) < ListaArray.size()) {
            ListaArray.add(Integer.parseInt(pos.getText().toString()), entrada.getText().toString() + "");
            list_adaptador.notifyDataSetChanged();
            Toast.makeText(this, "Nuevo elemento Insertado en la pos " + pos.getText().toString() + ".", Toast.LENGTH_SHORT).show();
            entrada.setText("");
            salida.setText("");
            pos.setText("");
            n = -1;
        }

    }
    //SOLICITAR PERMISOS PARA REALIZAR UNA LLAMADA
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case valor:
                String permisos = permissions[0];
                int result = grantResults[0];
                if (permisos.equals(Manifest.permission.CALL_PHONE)){
                    if (result == PackageManager.PERMISSION_GRANTED){
                        String phoneNumber = salida.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) return;
                        startActivity(intentCall);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Acceso no autorizado", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }

    }

    public void eliminarItem(View view){
        //ListaArray.set(n,"");
        if(ListaArray.size() != 0) {
           if(salida.toString()!="" && n>(-1)){
               ListaArray.remove(n);
               list_adaptador.notifyDataSetChanged();
               Toast.makeText(this, "Elemento eliminado.", Toast.LENGTH_SHORT).show();
               salida.setText("");
           }else{
               Toast.makeText(this, "Debes seleccionar un elemento de la lista.", Toast.LENGTH_SHORT).show();
           }
        }else{
            Toast.makeText(this, "La lista esta vacia.", Toast.LENGTH_SHORT).show();
        }
    }
    public void modificarItem(View view)
    {
        if(ListaArray.size() != 0){
                if(salida.toString() != "" && n>(-1)){
                    ListaArray.remove(n);
                    ListaArray.add(n,entrada.getText().toString()+"");
                    list_adaptador.notifyDataSetChanged();
                    entrada.setText("");
                    salida.setText("");
                    Toast.makeText(this, "Elemento editado.", Toast.LENGTH_SHORT).show();
                    n=-1;
                }else{
                    Toast.makeText(this, "Debes seleccionar un elemento de la lista.", Toast.LENGTH_SHORT).show();
                }
        }else{
            Toast.makeText(this, "Lista Vacia.", Toast.LENGTH_SHORT).show();
        }
    }
}

