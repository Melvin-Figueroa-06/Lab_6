package RestApi;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RestApi extends AsyncTask<String, String, String> {
    private static final int ID = ;
    private ArrayList<Params> parameters;
    private String TYPE = "GET";
    private RestApi.RestApi.OnCompleteLoadRest event;



    public RestApi(){
        this.parameters = new ArrayList<Params>();
    }
    public void setMethod(String type){
        TYPE = type;
    }
    private String proccessGetRequest(String url){
        String finalResult = "";
        HttpURLConnection connection = null;
        URL service = null;
        try {
            service = new URL(url);
            connection = (HttpURLConnection)service.openConnection();
            connection.setRequestMethod("GET");
            InputStream result = connection.getInputStream();
            finalResult = new Scanner(result, "UTF-8").useDelimiter("\\A").next();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    @Override
    protected String doInBackground(String... strings) {
        String finalResult = "";
        if (TYPE.equals("GET")){
            finalResult = proccessGetRequest(strings[0]);
        }

        return finalResult;


    }

    public interface OnCompleteLoadRest{
        public void completeLoadRest(JSONObject result, int id) throws JSONException;
    }
    @Override
    protected void onPostExecute(String result){
        try {
            JSONObject obj = new JSONObject(result);
            this.event.completeLoadRest(obj, this.ID);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    RestApi rest = new RestApi();
    rest.setOnCompleteLoadRest(this, 10);
    rest.setMethod("GET");
    rest.execute("http://192.168.1.31:3000/");
}
