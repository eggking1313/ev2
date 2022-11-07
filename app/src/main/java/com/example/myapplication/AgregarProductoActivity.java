package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AgregarProductoActivity extends AppCompatActivity {
    private EditText nombreProducto, precioProductoUnidad, cantidadProducto, ventaProducto;
    private Spinner Spinnertipo;
    private int contador=0, cantidad;
    private String nombre, tipo, seleccion;
    private double precioUnidad, precioConjunto, ventaUnidad;
    private ArrayList<Producto> productos = new ArrayList<Producto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        cargarProducto();
        nombreProducto = (EditText) findViewById(R.id.e_nombreproductos);
        precioProductoUnidad = (EditText) findViewById(R.id.e_preecio);
        cantidadProducto = (EditText) findViewById(R.id.e_cantidad);
        ventaProducto = (EditText) findViewById(R.id.e_ventaunidad);
        Spinnertipo = (Spinner) findViewById(R.id.spinner);
        String[] opciones = {"Limpieza","Farmacia","Consumo diario","Desechables"};
        ArrayAdapter<String> arreglo1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        Spinnertipo.setAdapter(arreglo1);
    }

    public void agregar(View view){
        nombre = nombreProducto.getText().toString();
        ventaUnidad = Double.parseDouble(ventaProducto.getText().toString());
        cantidad = Integer.parseInt(cantidadProducto.getText().toString());
        precioUnidad = Double.parseDouble(precioProductoUnidad.getText().toString());
        seleccion = Spinnertipo.getSelectedItem().toString();

        productos.add(contador, new Producto(nombre,seleccion, cantidad, precioUnidad,ventaUnidad));
        contador+=1;
        guardarProducto();
        Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show();

    }
    public void guardarProducto(){
        try {
            FileOutputStream fos = openFileOutput("productos.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(productos);
            oos.close();
        } catch (Exception e){
        }
    }
    public void cargarProducto(){
        try {
            FileInputStream fin = openFileInput("productos.dat");
            ObjectInputStream oin = new ObjectInputStream(fin);
            productos = (ArrayList<Producto>) oin.readObject();
            oin.close();
            contador = productos.size()-1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}