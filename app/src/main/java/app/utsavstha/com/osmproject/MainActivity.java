package app.utsavstha.com.osmproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;


import app.utsavstha.com.osmproject.DataClass.SchoolData;
import app.utsavstha.com.osmproject.Utils.AppController;
import app.utsavstha.com.osmproject.Utils.CheckJSON;
import app.utsavstha.com.osmproject.Utils.RVAdapter;
import app.utsavstha.com.osmproject.Utils.RecyclerTouchListener;
import app.utsavstha.com.osmproject.Utils.Url;


/*
* This class is responsible for fetching data from server and
* displaying list of schools.
* */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;  //recycler view variable
    private TextView loading;
    private List<SchoolData> schoolData = new ArrayList<>() ; //creating list of the Notice data class

    CheckJSON checkJSON;

    private String name;
    private String designation;
    private String contactEmail;
    private String description;
    private String phoneNumber;
    private String website;
    private String addressHouseNumber;
    private String addressStreet;
    private String openingHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkJSON = new CheckJSON();
        recyclerView = (RecyclerView) findViewById(R.id.school_list);
        loading = (TextView)findViewById(R.id.loading);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext()); //this will make the recycler view work as list view

        recyclerView.setLayoutManager(llm);

        fetchData();

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(MainActivity.this, Details.class);
                        intent.putExtra("lat", schoolData.get(position).getLat());
                        intent.putExtra("lon", schoolData.get(position).getLon());
                        intent.putExtra("name", schoolData.get(position).getName());
                        intent.putExtra("designation", schoolData.get(position).getDesignation());
                        intent.putExtra("contactEmail", schoolData.get(position).getContactEmail());
                        intent.putExtra("description", schoolData.get(position).getDescription());
                        intent.putExtra("phoneNumber", schoolData.get(position).getPhoneNumber());
                        intent.putExtra("website", schoolData.get(position).getWebsite());
                        intent.putExtra("addressHouseNumber", schoolData.get(position).getAddressHouseNumber());
                        intent.putExtra("addressStreet", schoolData.get(position).getAddressStreet());
                        intent.putExtra("openingHours", schoolData.get(position).getOpeningHours());

                        startActivity(intent);
                    }
                })
        );

    }

    /*
    * fetches data from server
    * */
    private void fetchData(){
        // Request a string response from the provided URL.
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.URL_QUERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            JSONArray elements = jObj.getJSONArray("elements");
                            JSONObject data;
                            JSONObject tags;

                            for(int i = 0; i < elements.length(); i++){
                                data = elements.getJSONObject(i);
                                tags = data.getJSONObject("tags");
                                int id = data.getInt("id");
                                double lat = data.getDouble("lat");
                                double lon= data.getDouble("lon");

                                name = checkJSON.jsonCheck(tags,"name");
                                if(name.equalsIgnoreCase("N.A.")){
                                    name = checkJSON.jsonCheck(tags,"name:en");
                                }
                                designation = checkJSON.jsonCheck(tags,"designation");
                                contactEmail = checkJSON.jsonCheck(tags,"contact:email");
                                description = checkJSON.jsonCheck(tags,"description");
                                phoneNumber = checkJSON.jsonCheck(tags,"phone");
                                website = checkJSON.jsonCheck(tags,"website");
                                addressHouseNumber = checkJSON.jsonCheck(tags,"addr:housenumber");
                                addressStreet = checkJSON.jsonCheck(tags,"addr:street");
                                openingHours = checkJSON.jsonCheck(tags,"opening_hours");

                                schoolData.add(new SchoolData(id, lat, lon, name,
                                        designation, contactEmail,description,phoneNumber, website,
                                        addressHouseNumber, addressStreet, openingHours));


                            }
                            populateRecyclerView(schoolData);
                            //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_LONG).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }
    private void populateRecyclerView(List<SchoolData> schoolData) {

        loading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        RVAdapter adapter = new RVAdapter(schoolData);

        recyclerView.setAdapter(adapter);
    }

}
