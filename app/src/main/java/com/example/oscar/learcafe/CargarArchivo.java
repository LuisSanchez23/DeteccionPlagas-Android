package com.example.oscar.learcafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;

public class CargarArchivo extends AppCompatActivity {
    ImageView img, agregarImagen;
    Bitmap foto;
    Button subir;
    private int PICK_IMAGE_REQUEST = 1;
    private String KEY_IMAGEN = "foto";
   // private String WEB_SERVICE="https://dialis.000webhostapp.com/subirImagen.php";
    public static String WEB_SERVICE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_archivo);
        img= findViewById(R.id.ImagenCargada);
        agregarImagen= findViewById(R.id.AgregarImagen);
        subir= findViewById(R.id.BotonEnviar);
        agregarImagen.setOnClickListener(new View.OnClickListener() { //evento onclick para abrir camara
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// se define que sera un Intent de tipo camara
                startActivityForResult(inte,0);//se ejecuta la accion y se abre camara
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         foto= (Bitmap) data.getExtras().get("data");
         img.setImageBitmap(foto);
    }

    public String getStringImagen(Bitmap bmp){//metodo para convertir a base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//instancia arreglo de byte
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);//el objeto bitmap se convierte en imagen con formato JPEG
        byte[] imageBytes = baos.toByteArray();//La imagen es convertida a byte

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT); //Se convierte en base64

        return encodedImage;//Retorna la cadena
    }
    private void uploadImage(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Analizando...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_SERVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {//Metodo que espera la respuesta del servidor
                        loading.dismiss();
                        int res= Integer.parseInt(s);
                        if(res <= 10){
                            Toast.makeText(CargarArchivo.this, "tu planta esta sana", Toast.LENGTH_LONG).show();
                        }else
                        if(res >= 11 && res <= 80) {// En esta linea comparo
                            //VISTA 50 MENOS
                            Toast.makeText(CargarArchivo.this, "Plaga detectada debil", Toast.LENGTH_LONG).show();
                            Intent recomendacion = new Intent(CargarArchivo.this, recomendacionPlaga.class);
                            startActivity(recomendacion);
                        }else
                        {
                            Toast.makeText(CargarArchivo.this, "Plaga detectada avanzada", Toast.LENGTH_LONG).show();
                            Intent recomendacion = new Intent(CargarArchivo.this, recomendacio100.class);
                            startActivity(recomendacion);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        Toast.makeText(CargarArchivo.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
                      protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(foto);
                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();
                //Agregando parámetros y clave
                params.put(KEY_IMAGEN, imagen);
             //Parámetros de retorno
                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    public void SubirArchivo(View v){
        uploadImage();

    }

    }
