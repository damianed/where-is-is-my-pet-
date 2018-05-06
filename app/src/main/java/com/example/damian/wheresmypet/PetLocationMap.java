package com.example.damian.wheresmypet;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class PetLocationMap extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap gmap;
    private String id_pet;
    private Marker marker;
    RequestQueue queue;
    private LatLng location;

    private static String url = "http://tec.codigobueno.org/WMP/query.php";

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(PetLocationMap.this);
        setContentView(R.layout.activity_pet_location_map);
        mapView = findViewById(R.id.mapView);
        id_pet = getIntent().getStringExtra("id");


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);

        gmap.setIndoorEnabled(true);
        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        petLocation();
    }

    private void petLocation() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getPetLocation();
            }
        }, 0, 10000);
    }

    private void getPetLocation() {
        StringRequest postRequest = updateLocation();
        queue.add(postRequest);
    }

    @NonNull
    private StringRequest updateLocation() {
        return new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //TODO esto ya no sirve
                                System.out.println(response);
                                JSONArray locationCoords = (JSONArray) (new JSONArray(response)).get(0);
                                Double latitude = Double.parseDouble(locationCoords.get(0).toString());
                                Double longitude = Double.parseDouble(locationCoords.get(1).toString());
                                System.out.println("Latitud:" + latitude + "  Longitude: " + longitude);
                                location = new LatLng(latitude,longitude);
                                moveMarker(location);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.getMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("query", "SELECT latitude, longitude from PET_LOCATION where id_pet = " + id_pet);

                    return params;
                }
            };
    }


    private void moveMarker(LatLng pos) {
            final LatLng newPos = new LatLng(pos.latitude+0.001,pos.longitude);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(marker == null) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(location);
                        marker = gmap.addMarker(markerOptions);
                    }else {
                        marker.setPosition(newPos);
                    }

                    gmap.moveCamera(CameraUpdateFactory.newLatLng(newPos));
                }
            });
    }


}
