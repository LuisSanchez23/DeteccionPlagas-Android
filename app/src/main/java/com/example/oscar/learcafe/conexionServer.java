package com.example.oscar.learcafe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class conexionServer extends StringRequest {
    private static final String REQUEST_SERVER_URL="192.168.1.12";
    public Map<String,String>parametros;
    public conexionServer(String imagen, Response.Listener<String>listener){
        super(Method.POST,REQUEST_SERVER_URL,listener,null);
          parametros= new HashMap<>();
          parametros.put("imagen",imagen);

    }
    @Override
    public  Map<String,String> getParams(){
        return parametros;
    }
}
