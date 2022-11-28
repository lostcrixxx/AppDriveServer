package br.com.envolvedesenvolve.driveserver;

import static br.com.envolvedesenvolve.driveserver.Configuration.KEY_ID;
import static br.com.envolvedesenvolve.driveserver.Configuration.KEY_IMAGE;
import static br.com.envolvedesenvolve.driveserver.Configuration.KEY_NAME;
import static br.com.envolvedesenvolve.driveserver.Configuration.KEY_USERS;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static String[] uIds;
    public static String[] uNames;
    public static String[] uImages;

    private JSONArray users = null;

    private String json;

    public JsonParser(String json) {
        this.json = json;
    }

    protected void parseJSON() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(KEY_USERS);

            uIds = new String[users.length()];
            uNames = new String[users.length()];
            uImages = new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                uIds[i] = jo.getString(KEY_ID);
                uNames[i] = jo.getString(KEY_NAME);
                uImages[i] = jo.getString(KEY_IMAGE);
            }

//            Log.e("uImage", "ser image" + uImages[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
