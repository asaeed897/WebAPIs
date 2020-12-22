package com.example.webapis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    
    private TextView text;
    private ProgressBar progressBar;
    private ImageView imageView;
    
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);

        mRequestQueue = Volley.newRequestQueue(this);
        fetchJsonResponse();




        Glide.with(this).load("data:image/jpeg;base64,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").into(imageView);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (count<100){
                    count++;
                    int finalCount = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(finalCount + "");
                            progressBar.setProgress(finalCount);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread.start();
    }

    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://run.mocky.io/v3/27a10eb7-9a0d-4462-b53b-3dea1f1c3d5a", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        
                        Gson gson = new Gson();
                        
                        Users users = gson.fromJson(response.toString(),Users.class);

                        Log.i("AWAIS", "onResponse: "+users.getUsers().get(0).getName());
                        Log.i("AWAIS", "onCreate: Response" + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AWAIS", "onCreate: Error" + error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }



}