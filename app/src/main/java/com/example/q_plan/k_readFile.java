package com.example.q_plan;


import android.app.Application;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class k_readFile extends Application {

    List<Map> area = new ArrayList<>();
    List<Map> key = new ArrayList<>();

    List<List<Map>> result = Arrays.asList(area, key);

    k_readFile() {
        ObjectMapper mapper = new ObjectMapper();
        List<List<Map>> tmp = new ArrayList<>();
        String fileName = "0.json";
        try {
            BufferedReader r = new BufferedReader(new FileReader("/data/data/com.example.q_plan/files/" + fileName));
            String str = "";
            boolean first = true;
            while ((str = r.readLine()) != null) {
                if (first) {
                    str = str.substring(2);
                    first = false;
                }
                tmp.add(mapper.readValue(str, ArrayList.class));
            }
            r.close();
            System.out.println(fileName + "파일 읽기 완료");

            for (List l : tmp) {
                Collections.shuffle(l);
            }

            for (int i = 0; i < tmp.get(0).size(); i++) {
                for (List<Map> l : tmp)
                    area.add(l.get(i));
            }
        } catch (Exception e) {
            Log.i("결과", e + "Error");
        }
    }

    //동기화
    private static k_readFile instance = null;
    public static synchronized k_readFile getInstance() {
        if(instance == null)
            instance = new k_readFile();
        return instance;
    }

    public List<Map> show(int a) {
        return result.get(0);
    }
}

