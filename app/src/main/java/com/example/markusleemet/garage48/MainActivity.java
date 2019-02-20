package com.example.markusleemet.garage48;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleOpacity;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;


public class MainActivity extends AppCompatActivity implements PermissionsListener {
    private MapView mapView;
    private LocationManager locationManager;
    private  static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String URL = "http://lvnap.lv:8011/";
    private Random random = new Random();
    private int id;

    private static MapboxMap mapbox;
    private int alreadyCentered=0;
    private ListView listView;
    private ArrayAdapter adapter;

    private List<Feature> markers;

    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    //Strings
    private static final String MARKER_SOURCE = "markers-source";
    private static final String MARKER_STYLE_LAYER = "markers-style-layer";
    private static final String MARKER_IMAGE = "custom-marker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //peame uuesti centerima
        alreadyCentered = 0;
        Log.i("gpsLocation", "application was created");
        Mapbox.getInstance(this, "pk.eyJ1IjoibWFya3VzbGVlbWV0IiwiYSI6ImNqc2M2OW9xbDA1dmM0M254aGJsMWd6a3oifQ.Tk8i1j5_Bsy3ZGxykgYDpw");
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.busitem, R.id.textView, new ArrayList<>(Arrays.asList("buss nr1", "buss nr2", "buss nr3", "buss nr4", "buss nr5")));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent bussStopIntent = new Intent(MainActivity.this, BussStopActivity.class);
                startActivity(bussStopIntent);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapbox = mapboxMap;


                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {

                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);

                        //kood markeri lisamiseks
                        style.addImage("custom-marker", BitmapFactory.decodeResource(
                                MainActivity.this.getResources(), R.drawable.custom_marker));
                        addMarkers(style);
                    }
                });
            }

        });



        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("gpsLocation", "location was changes");
                //paneme kaardi keskele
                //muutmine välja
/*                if(alreadyCentered == 0) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(10)
                            .tilt(20)
                            .build();
                    mapbox.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
                }
                alreadyCentered = 1;*/


                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()), Toast.LENGTH_LONG);
                toast.show();


                try{
                    run(teeGeoJSON(Float.valueOf(String.valueOf(location.getLongitude()).replaceAll(",", ".")), Double.valueOf(String.valueOf(location.getLatitude()).replaceAll(",", ".")), id));
                }catch (IOException e){
                    Log.i("gpsLocation", "exepto");
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        //onLocationChanged(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

        //generate random id for the device
        id = random.nextInt(1000000);

    }


    void run(String json) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);

        final Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("gpsLocation", "Failure");
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("gpsLocation", response.body().string());

            }
        });
    }

    //kood validatsiooni jms küsimiseks
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
    // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
    // Activate the MapboxMap LocationComponent to show user location
    // Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapbox.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
    // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "This app needs location permissions in order to show its functionality", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapbox.getStyle());
        } else {
            Toast.makeText(this, "You didn\\'t grant location permissions.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void addMarkers(@NonNull Style loadedMapStyle) {
        markers = new ArrayList<>();
        markers.add(Feature.fromGeometry(Point.fromLngLat(24.1, 56.97)));
        markers.add(Feature.fromGeometry(Point.fromLngLat(23.995, 56.8)));
        markers.add(Feature.fromGeometry(Point.fromLngLat(24.04, 56.78)));
        markers.add(Feature.fromGeometry(Point.fromLngLat(24.09, 56.79)));


        /* Source: A data source specifies the geographic coordinate where the image marker gets placed. */

        loadedMapStyle.addSource(new GeoJsonSource(MARKER_SOURCE, FeatureCollection.fromFeatures(markers)));


        /* Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. */
        loadedMapStyle.addLayer(new SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconImage(MARKER_IMAGE),
        // Adjust the second number of the Float array based on the height of your marker image.
        // This is because the bottom of the marker should be anchored to the coordinate point, rather
        // than the middle of the marker being the anchor point on the map.
                        PropertyFactory.iconOffset(new Float[] {0f, 0f})
                ));

        /*//teise kihi lisame(ringid)---jätsime hetkel ikka pointerid
        CircleLayer circleLayer = new CircleLayer("trees-style", MARKER_SOURCE);
        circleLayer.withProperties(
                circleOpacity(0.6f),
                circleColor(Color.parseColor("#66b2ff")),
                circleRadius(40f)


        );
        loadedMapStyle.addLayer(circleLayer);*/
    }

    private float getRandomRadius(){
        Random r = new Random();
        return (50 + r.nextFloat() * (150 - 50));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private static String teeGeoJSON(double arg1, double arg2, int arg3){
        String json = String.format(Locale.US,"{\n" +
                        "  \"type\": \"Feature\",\n" +
                        "  \"geometry\": {\n" +
                        "    \"type\": \"Point\",\n" +
                        "    \"coordinates\": [%f, %f]\n" +
                        "  },\n" +
                        "  \"properties\": {\n" +
                        "    \"name\": \"%d\"\n" +
                        "  }\n" +
                        "}",
                arg1,arg2, arg3);
        return json;
    }
}
