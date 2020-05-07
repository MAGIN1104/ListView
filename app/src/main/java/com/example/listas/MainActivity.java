package com.example.listas;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText)findViewById(R.id.entrada);
        pos = (EditText)findViewById(R.id.editTextPos);
        salida = (TextView)findViewById(R.id.textData);
        lista = (ListView)findViewById(R.id.listv);
        ListaArray=new ArrayList<String>();
        list_adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ListaArray);
        lista.setAdapter(list_adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                salida.setText("ITEM SELECCIONADO: "+lista.getItemAtPosition(position)+"");
                n = position;
            }
        });
    }

    public void agregaItem(View view)
    {
        if(pos.getText().toString().isEmpty()) {
            ListaArray.add(entrada.getText().toString() + "");
            list_adaptador.notifyDataSetChanged();
            entrada.setText("");
            Toast.makeText(this, "Agregado, correctamente.", Toast.LENGTH_LONG).show();
        }else if(Integer.parseInt(pos.getText().toString()) < ListaArray.size()){
            ListaArray.add( Integer.parseInt(pos.getText().toString()),entrada.getText().toString()+"");
            list_adaptador.notifyDataSetChanged();
            Toast.makeText(this, "Nuevo elemento Insertado en la pos " + pos.getText().toString()+".", Toast.LENGTH_LONG).show();
            entrada.setText("");
            pos.setText("");
        }

    }
    public void eliminarItem(View view){
        //ListaArray.set(n,"");
        if(ListaArray.size() != 0 || salida.toString()!="") {
            ListaArray.remove(n);
            list_adaptador.notifyDataSetChanged();
            Toast.makeText(this, "Elemento eliminado.", Toast.LENGTH_LONG).show();
            salida.setText("");
        }else{
            Toast.makeText(this, "La lista esta vacia.", Toast.LENGTH_LONG).show();
        }
    }
    public void modificarItem(View view)
    {
        if(salida.toString() != ""){
                ListaArray.remove(n);
                ListaArray.add(n,entrada.getText().toString()+"");
                list_adaptador.notifyDataSetChanged();
                entrada.setText("");
                salida.setText("");
                Toast.makeText(this, "Elemento editado.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Seleccione un Item para editar.", Toast.LENGTH_LONG).show();
        }
    }
}

