package com.example.q_plan;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import io.grpc.internal.JsonParser;

public class k_getApi extends AsyncTask<String, Void, String> {

    private Context context;
    String clientKey = "#########################";
    private String str, receiveMsg;
    private final String ID = "########";
    private Map<?, ?> sequence = new HashMap();

    k_getApi(Context context) {
        this.context = context;
    }

    public void set (Map<?, ?> sequence) {
        this.sequence = sequence;
        System.out.println("set");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    static String querySet(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public String querySet() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : sequence.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    querySet(entry.getKey().toString()),
                    querySet(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    public void setJson(String str){
        JSONArray json = null;
        AssetManager manager = context.getAssets();
        String filename = "data.json";

        try {
            json = new JSONArray(str);
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(json.toString());
            dos.flush();
            dos.close();
            System.out.println("저장");
        } catch(Exception e) {
            Log.i("결과", e.toString() + "Error");}
    }


    @Override
    protected String doInBackground(String... params) {
        System.out.println(params);
        String api = "https://ojbineyaldsjpfu2yfyqraqplu0ctpbl.lambda-url.ap-northeast-2.on.aws/";
        URL url = null;
        try {
            url = new URL(api + "?" + querySet()); // 서버 URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("x-waple-authorization", clientKey);

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }

                setJson(buffer.toString());
                reader.close();

            } else {
                Log.i("결과", conn.getResponseCode() + "Error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("doInBackground 종료");
        return receiveMsg;
    }
}