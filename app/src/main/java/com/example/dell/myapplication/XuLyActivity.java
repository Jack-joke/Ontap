package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class XuLyActivity extends AppCompatActivity {
    EditText edtfn, edtln, edtsalary;
    RadioGroup rdG;
    RadioButton rdM, rdF;
    Button btnok, btncancel;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/students";
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly);
        anhxa();
        Intent intent = getIntent();
        if (intent.getSerializableExtra("data") != null){
            Students students = (Students)intent.getSerializableExtra("data");
            id = students.getId();
            edtfn.setText(students.getFirstName());
            edtln.setText(students.getLastName());
            edtsalary.setText(students.getSalary());
            if (students.getGender().toString().equals("Male")){
                rdM.setChecked(true);
            }else rdF.setChecked(true);
            btnok.setText("Update");
        }
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnok.getText().equals("OK")){
                    Add(url);
                }else Update(url, id);
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void anhxa(){
        edtfn = findViewById(R.id.edt_fn);
        edtln = findViewById(R.id.edt_ln);
        edtsalary = findViewById(R.id.edt_salary);
        rdG = findViewById(R.id.rd_G);
        rdM = findViewById(R.id.rd_M);
        rdF = findViewById(R.id.rd_F);
        btnok = findViewById(R.id.btn_OK);
        btncancel = findViewById(R.id.btn_Cancel);
    }
    private void Add(String url){
        HashMap data = new HashMap();
        data.put("FirstName",edtfn.getText().toString());
        data.put("LastName",edtln.getText().toString());
        data.put("Salary",edtsalary.getText().toString());
        int k = rdG.getCheckedRadioButtonId();
        if (k == R.id.rd_M){
            data.put("Gender", "Male");
        }else data.put("Gender", "Female");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(XuLyActivity.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(XuLyActivity.this,MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(XuLyActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    private void Update(String url, String id){
        HashMap data = new HashMap();
        data.put("FirstName", edtfn.getText().toString());
        data.put("LastName", edtln.getText().toString());
        data.put("Salary", edtsalary.getText().toString());
        int k = rdG.getCheckedRadioButtonId();
        if (k == R.id.rd_M){
            data.put("Gender", "Male");
        }else data.put("Gender", "Female");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + id, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(XuLyActivity.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(XuLyActivity.this, MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(XuLyActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
