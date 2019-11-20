package www.danapharma.kz;

import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;


public class Map extends Fragment {
    public TextView countTv;
    public Button countBtn;
    MapView map = null;
    private MapController mapController;
    LocationManager locationManager;


    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Context ctx = getActivity().getApplicationContext();
        //Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        mapController = (MapController) map.getController();
        mapController.setZoom(12);



        GeoPoint mPosition = new GeoPoint(51.1801, 71.44598);
        mapController.setCenter(mPosition);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);


        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = getActivity().getString(R.string.base_url)+"pharmacy/all";
        Log.d("MainActivity", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
                        // Display the first 500 characters of the response string.
                       //textView.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONArray response = new JSONArray(res);

                            Log.d("JsonArray",response.toString());
                            for(int i=0;i<response.length();i++){
                                JSONObject jresponse = response.getJSONObject(i);
                                String nickname = jresponse.getString("first_name");
                                Log.d("nickname",nickname);
                                String title = jresponse.getString("title");
                                String email = jresponse.getString("email");
                                String phone = jresponse.getString("phone");
                                String address = jresponse.getString("address");
                                String[] latlan = jresponse.getString("latlan").split(",");
                                Double lat = Double.parseDouble(latlan[0]);
                                Double lon = Double.parseDouble(latlan[1]);

                                GeoPoint startPoint = new GeoPoint(lat, lon);
                                Marker startMarker = new Marker(map);
                                startMarker.setPosition(startPoint);
                                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                startMarker.setTitle(title + "\n" + email + "\n" + phone + "\n" + address);
                                startMarker.setIcon(getActivity().getDrawable(R.drawable.location));
                                startMarker.setTextLabelBackgroundColor(Color.WHITE);
                                map.getOverlays().add(startMarker);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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