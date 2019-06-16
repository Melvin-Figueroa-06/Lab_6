package com.example.objectadapter;

import android.support.annotation.NavigationRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Collection.Item;
import Collection.ListAdapter;
import RestApi.RestApi;
import RestApi.RestApi.OnCompleteLoadRest;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RestApi.RestApi.OnCompleteLoadRest {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestParams params = null;
        loadComponents(params);
    }

    private void loadComponents(RequestParams params) {

        final ListView list = (ListView)this.findViewById(R.id.list_main);
        final ArrayList<Item> list_data = new ArrayList<Item>();
        for (int i = 0; i < 100; i++ ) {
            Item p = new Item();
            p.id = i;
            p.title = "Titulo " + i;
            p.description = "Descripción "+i;
            p.url = "image " + i;
            list_data.add(p);
        }
        ListAdapter adapter = new ListAdapter(this, list_data);
        list.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.31:3000/", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray data = null;
                try {
                    data = response.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < data.length(); i ++) {
                    Item p = new Item();
                    JSONObject obj = null;
                    try {
                        obj = data.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    p.id = i;
                    try {
                        p.title = obj.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        p.description = obj.getString("descripcion");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        p.url = obj.getString("image");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    list_data.add(p);
                }
                ListAdapter adapter = new ListAdapter(this, list_data);
                list.setAdapter(adapter);
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                          JSONObject errorResponse) {

                    }
        });


    }


    @Override
    public void completeLoadRest(JSONObject result, int id) throws JSONException {

    }
}

