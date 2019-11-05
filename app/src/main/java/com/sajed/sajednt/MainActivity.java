package com.sajed.sajednt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Request.Method;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<userItem> result = new ArrayList<userItem>();
    private RecyclerView mRecyclerItem;
    private GridLayoutManager gridLayout;
    TextView error;
    int page = 1;
    adapterItem rv;
    Boolean refresh = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerItem = (RecyclerView) findViewById(R.id.recycleItem);

        gridLayout = new GridLayoutManager(getApplicationContext() , 2);
        mRecyclerItem.setLayoutManager(gridLayout);
        rv = new adapterItem( result , getApplicationContext() , MainActivity.this );
        mRecyclerItem.swapAdapter(rv, false);

        new getUserItems().execute();

        mRecyclerItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!recyclerView.canScrollVertically(1)) {

                    if(refresh) {
                        Toast.makeText(MainActivity.this, "Loading New Items ...", Toast.LENGTH_SHORT).show();
                        refresh = false;
                        new getUserItems().execute();

                    }
                }
            }
        });

    }


    public class getUserItems extends AsyncTask<String, String , String>{


        @Override
        protected String doInBackground(String... strings) {


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, constant.domain + constant.get_list_user + Integer.toString(page), null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                             Log.e("onResponse", response.toString());
                            try {

                                JSONArray data = response.getJSONArray(constant.data);

                                for(int i=0 ; i < data.length() ; i++){

                                    JSONObject j = data.getJSONObject(i);

                                    userItem ie =new userItem();

                                    ie.setId(j.getString(constant.id));
                                    ie.setFirstName(j.getString(constant.first_name));
                                    ie.setLastName(j.getString(constant.last_name));
                                    ie.setEmail(j.getString(constant.email));
                                    ie.setImage(j.getString(constant.avatar));

                                    result.add(ie);
                                }

                                rv.updateList(result);


                                if(data.length()!=0){
                                    Toast.makeText(MainActivity.this, "New Items Inserted", Toast.LENGTH_SHORT).show();

                                    refresh = true;
                                    page++;
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "End of List", Toast.LENGTH_SHORT).show();

                                }



                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                         Log.e("onErrorResponse", error.getLocalizedMessage());

                }
            });


            AppController.getInstance().addToRequestQueue(jsonObjReq);


            return null;
        }
    }
}
