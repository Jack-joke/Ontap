package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Students> arr;
    CusArrayAdapter adpter;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv_data);
        arr = new ArrayList<Students>();
        adpter = new CusArrayAdapter(this,R.layout.cus_layout,arr);
        lv.setAdapter(adpter);
        GetData(url);
    }
    private void GetData(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0; i<response.length(); i++){
                    try{
                        JSONObject object = response.getJSONObject(i);
                        arr.add(new Students(
                                object.getString("id"),
                                object.getString("FirstName"),
                                object.getString("LastName"),
                                object.getString("Gender"),
                                object.getString("Salary")
                        ));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adpter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_Add){
            startActivity(new Intent(MainActivity.this, XuLyActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
