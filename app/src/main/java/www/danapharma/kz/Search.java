package www.danapharma.kz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
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
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Arrays;


public class Search extends Fragment {
    public TextView countTv;
    public Button countBtn;
    private String[] medicine = {};
    private AppCompatAutoCompleteTextView autoTextView;
    ArrayList<Medicine> medicinesList = new ArrayList<>();
    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = getActivity().getString(R.string.base_url)+"pharmacy/medicine";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {

                        try {

                            JSONArray response = new JSONArray(res);

                            Log.d("JsonArray",response.toString());
                            medicine = new String[response.length()];
                            for(int i=0;i<response.length();i++){
                                JSONObject jresponse = response.getJSONObject(i);
                                String title = jresponse.getString("title");
                                String id = jresponse.getString("_id");
                                medicinesList.add(new Medicine(id, title));
                                medicine[i] = title;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        autoTextView = (AppCompatAutoCompleteTextView) view.findViewById(R.id.autoTextView);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getActivity(), android.R.layout.select_dialog_item, medicine);
                        autoTextView.setThreshold(1); //will start working from first character
                        autoTextView.setAdapter(adapter);


                        //onclick listener

                        autoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selected = (String) parent.getItemAtPosition(position);
                                int pos = Arrays.asList(medicine).indexOf(selected);
                                Medicine med = medicinesList.get(pos);


                                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                String history = sharedPref.getString(getString(R.string.history),"");


                                if(history.length() == 0){
                                    history += med.id + ":" + med.title;
                                }else{
                                    history = med.id + ":" + med.title + "," + history;
                                }
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.history), history);
                                editor.commit();

                                Intent intent = new Intent(getActivity(), MedicinesActivity.class);
                                intent.putExtra("MED_ID", med.id);
                                startActivity(intent);
                            }
                        });
                        //
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return view;

    }

    private void increaseCount() {
        int current = Integer.parseInt((String) countTv.getText());
        countTv.setText(String.valueOf(current + 1));

    }
}
