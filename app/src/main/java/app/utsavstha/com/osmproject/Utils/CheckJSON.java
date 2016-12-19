package app.utsavstha.com.osmproject.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by utsav on 12/18/2016.
 */
public class CheckJSON {

    /*
    * checks if the json data consists the required string value
    * */
    public String jsonCheck(JSONObject s, String data) throws JSONException {
        if(s.has(data) && !s.isNull(data)){
            return s.getString(data);
        }else
            return "N.A.";
    }
}
