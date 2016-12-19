package app.utsavstha.com.osmproject;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.SqlTileWriter;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Details extends AppCompatActivity {
    ArrayList<OverlayItem> anotherOverlayItemArray;
    MapView map;
    private double lat;
    private double lon;

    private TextView name;
    private TextView designation;
    private TextView contactEmail;
    private TextView description;
    private TextView phoneNumber;
    private TextView website;
    private TextView addressHouseNumber;
    private TextView addressStreet;
    private TextView openingHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        map = (MapView) findViewById(R.id.map);

        name = (TextView)findViewById(R.id.details_name);
        designation = (TextView) findViewById(R.id.details_designation);
        contactEmail = (TextView) findViewById(R.id.details_contactEmail);
        description = (TextView) findViewById(R.id.details_description);
        phoneNumber = (TextView) findViewById(R.id.details_phoneNumber);
        website = (TextView) findViewById(R.id.details_website);
        addressHouseNumber = (TextView) findViewById(R.id.details_addressHouseNumber);
        addressStreet = (TextView) findViewById(R.id.details_addressStreet);
        openingHours = (TextView) findViewById(R.id.details_openingHours);

        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);

        name.setText("Name: "+getIntent().getStringExtra("name"));
        designation.setText("Designation: "+getIntent().getStringExtra("designation"));
        contactEmail.setText("Email: "+getIntent().getStringExtra("email"));
        description.setText("Description: "+getIntent().getStringExtra("description"));
        phoneNumber.setText("Number: "+getIntent().getStringExtra("phoneNumber"));
        website.setText("Website: "+getIntent().getStringExtra("website"));
        addressHouseNumber.setText("House Number: "+getIntent().getStringExtra("addressHouseNumber"));
        addressStreet.setText("Street: "+getIntent().getStringExtra("addressStreet"));
        openingHours.setText("Opening Hours: "+getIntent().getStringExtra("openingHours"));


        setupMaps();


    }

    @SuppressWarnings("deprecation")
    private void setupMaps() {
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }

        try{
            map.setTileSource(TileSourceFactory.MAPNIK);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(100);
        GeoPoint startPoint = new GeoPoint(lat, lon);
        mapController.setCenter(startPoint);

        anotherOverlayItemArray = new ArrayList<OverlayItem>();
        anotherOverlayItemArray.add(new OverlayItem(
                "School", "US", new GeoPoint(lat, lon)));
        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(
                this, anotherOverlayItemArray, null);
        map.getOverlays().add(anotherItemizedIconOverlay);
        //---

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(map);
        map.getOverlays().add(myScaleBarOverlay);
    }

    // START PERMISSION CHECK
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        String message = "osmdroid permissions:";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            message += "\nLocation to show user location.";
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            message += "\nStorage access to store map tiles.";
        }
        if (!permissions.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } // else: We already have permissions, so handle as normal
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION and WRITE_EXTERNAL_STORAGE
                Boolean location = perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (location && storage) {
                    // All Permissions Granted
                    Toast.makeText(Details.this, "All permissions granted", Toast.LENGTH_SHORT).show();
                } else if (location) {
                    Toast.makeText(this, "Storage permission is required to store map tiles to reduce data usage and for offline usage.", Toast.LENGTH_LONG).show();
                } else if (storage) {
                    Toast.makeText(this, "Location permission is required to show the user's location on map.", Toast.LENGTH_LONG).show();
                } else { // !location && !storage case
                    // Permission Denied
                    Toast.makeText(Details.this, "Storage permission is required to store map tiles to reduce data usage and for offline usage." +
                            "\nLocation permission is required to show the user's location on map.", Toast.LENGTH_SHORT).show();
                }
                updateStorageInfo();
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // END PERMISSION CHECK

    private void updateStorageInfo(){

        long cacheSize = updateStoragePrefreneces(this);
        //cache management ends here

        // TextView tv = (TextView) findViewById(R.id.sdcardstate_value);
        final String state = Environment.getExternalStorageState();

        boolean mSdCardAvailable = Environment.MEDIA_MOUNTED.equals(state);


    }
    public static long updateStoragePrefreneces(Context ctx){

        //loads the osmdroid config from the shared preferences object.
        //if this is the first time launching this app, all settings are set defaults with one exception,
        //the tile cache. the default is the largest write storage partition, which could end up being
        //this app's private storage, depending on device config and permissions

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //also note that our preference activity has the corresponding save method on the config object, but it can be called at any time.


        File dbFile = new File(Configuration.getInstance().getOsmdroidTileCache().getAbsolutePath() + File.separator + SqlTileWriter.DATABASE_FILENAME);
        if (Build.VERSION.SDK_INT >= 9 && dbFile.exists()) {
            return dbFile.length();
        }
        return -1;
    }
}
