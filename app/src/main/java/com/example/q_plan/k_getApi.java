package com.example.q_plan;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.grpc.internal.JsonParser;

public class k_getApi extends AsyncTask<String, Void, String> {

    private Context context;
    String clientKey = "#########################";
    private String str, receiveMsg;
    private final String ID = "########";
    private Map<String, String> sequence = new HashMap();

    k_getApi(Context context) {
        this.context = context;
    }

    public void set (Map<String, String> sequence) {
        this.sequence = sequence;
        this.sequence.put("numOfRows", "30");
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

    public void saveJson(String str){





        AssetManager manager = context.getAssets();
        String filename = sequence.get("category") + ".json";
        System.out.println(filename);
        try {
            JSONObject json = new JSONObject(str);

            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeUTF(json.toString());
            dos.flush();
            dos.close();

            FileReader r = new FileReader("files/" + filename);
            Object ob = JsonParser.parse(String.valueOf(r));

            json = (JSONObject) ob;
            System.out.println(ob);
            r.close();


        } catch(Exception e) {
            Log.i("결과", e + "Error");}
    }


    @Override
    protected String doInBackground(String... params) {
        System.out.println(params);
        String api = "https://ojbineyaldsjpfu2yfyqraqplu0ctpbl.lambda-url.ap-northeast-2.on.aws";
        try {
            String result = "";
            for (Object i : Arrays.asList(12, 14, 15, 25, 28, 32, 38, 39)) {
                URL url = new URL(api + "?" + querySet() + "&contentTypeId=" + i.toString()); // 서버 URL
                System.out.println(url);
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
                    //buffer.append(",");
                    result += buffer;
                    reader.close();
                } else {
                    Log.i("결과", conn.getResponseCode() + "Error");
                }
            }
            saveJson(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("doInBackground 종료");
        return receiveMsg;
    }
}