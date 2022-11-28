package br.com.envolvedesenvolve.driveserver;

import static br.com.envolvedesenvolve.driveserver.Configuration.LIST_USER_URL;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UserList extends AppCompatActivity {

    private static final String TAG = UserList.class.getName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        listView = findViewById(R.id.list_view);
//        try {
            sendRequest();
//        } catch (Exception e){
//            e.printStackTrace();
//            Log.e(TAG, "ERRO sendRequest() " + e.toString());
//        }
    }

    private void sendRequest() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(LIST_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "ser image" + response);
                        showJSON(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "ERRO stringRequest " + error.toString());
                        Toast.makeText(UserList.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        try {
            JsonParser pj = new JsonParser(json);
            pj.parseJSON();
//            Log.e("uImage", "ser image" + json);
//            Log.e("uImage", "ser image" + JsonParser.uImages[1]);
            UserListAdapter userListAdapter = new UserListAdapter(this, JsonParser.uIds, JsonParser.uNames, JsonParser.uImages);
            listView.setAdapter(userListAdapter);
        } catch(Exception e){
            Log.e(TAG, "ERRO showJSON() " + e.toString());
            e.printStackTrace();
        }
    }
}

