package com.sajed.sajednt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class addUser extends AppCompatActivity {

    EditText name , email , pass;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPass);
        btn = findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isEmpty(name)){
                    name.setError("Fill This Edittex");
                }
                else if(isEmpty(email)){
                    email.setError("Fill This Edittex");

                }
                else if(isEmpty(pass)){
                    pass.setError("Fill This Edittex");

                }
                else{
                    new addUserReq().execute();
                }
            }
        });

    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    public class addUserReq extends AsyncTask<String, String , String> {


        @Override
        protected String doInBackground(String... strings) {


            StringRequest strReq = new StringRequest(Request.Method.POST, constant.domain + constant.add_user, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response.toString());
                    Toast.makeText(addUser.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    addUser.this.finish();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("onErrorResponse", "Error: " + error.getMessage());
                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", "Androidhive");
                    params.put("email", "abc@androidhive.info");
                    params.put("password", "password123");

                    return params;
                }

            };


            AppController.getInstance().addToRequestQueue(strReq);




            return null;
        }
    }
}
