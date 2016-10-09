package vn.nguyen.andrew.appchat;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by trunganh on 10/09/2016.
 */
public class ServerRequest {
    private InputStream is = null;
    private JSONObject jObj = null;
    private String json = "";
    private JSONObject jobj;
    public ServerRequest(){

    }

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params){
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            jObj = new JSONObject(json);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return jObj;
    }

    public JSONObject getJSON(String url, List<NameValuePair> params){
        Params param = new Params(url,params);
        Request myTask = new Request();
        try{
            jobj = new JSONObject();
            jobj= myTask.execute(param).get();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return jobj;
    }

    private static class Params{
        String url;
        List<NameValuePair> params;

        Params(String url, List<NameValuePair> params){
            this.params = params;
            this.url = url;
        }
    }

    private static class Request extends AsyncTask<Params, String, JSONObject>{


        @Override
        protected JSONObject doInBackground(Params... params) {
            ServerRequest request = new ServerRequest();
            JSONObject jsonObject = request.getJSONFromUrl(params[0].url, params[0].params);

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }
}
