package com.example.dell.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class CusArrayAdapter extends BaseAdapter{
    private Context myContext;
    private int myLayout;
    private List<Students> myList;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/students";

    public CusArrayAdapter(Context myContext, int myLayout, List<Students> myList) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.myList = myList;
    }
    private class ViewHolder{
        TextView tv1, tv2;
        Button btn1, btn2;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout,null);
            holder.tv1 = convertView.findViewById(R.id.cus_tv1);
            holder.tv2 = convertView.findViewById(R.id.cus_tv2);
            holder.btn1 = convertView.findViewById(R.id.cus_btn1);
            holder.btn2 = convertView.findViewById(R.id.cus_btn2);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Students students = myList.get(position);
        holder.tv1.setText(students.getFirstName());
        holder.tv2.setText(students.getSalary());

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,XuLyActivity.class);
                intent.putExtra("data", students);
                myContext.startActivity(intent);
            }
        });
        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(students.getFirstName(),students.getId());
            }
        });
        return convertView;
    }
    private void ConfirmDelete(String name, final String id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(myContext);
        dialog.setMessage("Ban co muon xoa"+" "+name);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete(url, id);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
    private void Delete(String url, String id){
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "/" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(myContext, "Sucessfully", Toast.LENGTH_SHORT).show();
                myContext.startActivity(new Intent(myContext, MainActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(myContext);
        requestQueue.add(stringRequest);
    }
}
