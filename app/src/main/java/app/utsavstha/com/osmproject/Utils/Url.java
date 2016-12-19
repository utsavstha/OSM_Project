package app.utsavstha.com.osmproject.Utils;

/**
 * Created by utsav on 12/18/2016.
 */
public class Url {
     private static final String CITY  = "Kathmandu";
    private static final String AMENITY  = "school";
    // Server client list
    public static String URL_QUERY = "http://overpass-api.de/api/interpreter?data=[out:json];area[name="
            + CITY +
            "];node(area)[amenity="
            + AMENITY +
            "];out;";
}
