package www.danapharma.kz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicinesActivity extends AppCompatActivity {
    List<PharmacyItem> mList = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        String medicine_id = getIntent().getStringExtra("MED_ID");
        recyclerView = findViewById(R.id.my_recycler_view);
        ImageView closeImageView = findViewById(R.id.iv_close);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        RequestQueue queue = Volley.newRequestQueue(this);

        String url = getString(R.string.base_url)+"pharmacy/bymedicine?medicine_id="+medicine_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {

                        try {
                            JSONArray response = new JSONArray(res);

                            for(int i=0;i<response.length();i++){
                                JSONObject jresponse = response.getJSONObject(i);
                                String id = jresponse.getString("id");
                                String first_name = jresponse.getString("first_name");
                                String last_name = jresponse.getString("last_name");
                                String email = jresponse.getString("email");
                                String phone = jresponse.getString("phone");
                                String title = jresponse.getString("title");
                                String address = jresponse.getString("address");
                                String latlan = jresponse.getString("latlan");
                                String price = jresponse.getString("price");
                                mList.add(new PharmacyItem(id,first_name,last_name,email,phone,title,address,latlan,price));
                                Toast.makeText(MedicinesActivity.this, "" +mList.size(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MedicinesActivity.this, "Error parsing", Toast.LENGTH_SHORT).show();
                        }
                        final Adapter adapter = new Adapter(getApplicationContext(), mList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
