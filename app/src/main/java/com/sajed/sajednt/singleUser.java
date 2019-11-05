package com.sajed.sajednt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class singleUser extends AppCompatActivity {

    TextView name ;
    TextView email;
    ImageView avatar;
    Bundle g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        g = getIntent().getExtras();

        name = findViewById(R.id.textName);
        email =  findViewById(R.id.textEmail);
        avatar = findViewById(R.id.squareImageView);

        name.setText(g.getString(constant.first_name)+ g.getString(constant.last_name));
        email.setText(g.getString(constant.email));
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        imageLoader.get(g.getString(constant.avatar), new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                // TODO Auto-generated method stub
                avatar.setImageBitmap(response.getBitmap());
            }
        });
    }

    public class deleteUser extends AsyncTask<String, String , String> {


        @Override
        protected String doInBackground(String... strings) {


            StringRequest strReq = new StringRequest(Request.Method.DELETE, constant.domain + constant.delete_user + g.getString(constant.id), new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response.toString());
                    Toast.makeText(singleUser.this, "User deleted successfully", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("onErrorResponse", "Error: " + error.getMessage());
                }
            });


            AppController.getInstance().addToRequestQueue(strReq);




            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_delete) {
            new deleteUser().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
