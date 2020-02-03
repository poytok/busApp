package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class BusLocation extends AsyncTask<String, Void, Void> {
    String key = "sS9joESzYsg6XxvLZBn2lgAzqhh6GuMPZ%2BreJj0M8NP7fO1arBNQXWsCP3lVujAA24%2B9ANn%2BRlkwmgfBpGZ5MQ%3D%3D";

    @Override
    protected Void doInBackground(String... strings) {
        for (int i = 0; i < strings.length - 1; i += 2) {
            String queryUrl = "http://apis.data.go.kr/6280000/busArrivalService/getBusArrivalList?"//요청 URL
                    + "ServiceKey=" + key
                    + "&pageNo=1"
                    + "&numOfRows=200"
                    + "&bstopId=" + strings[i]
                    + "routeId=" + strings[i + 1];
            try {
                URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
                InputStream is = url.openStream(); //url위치로 입력스트림 연결
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputStream 으로부터 xml 입력받기

                String tag;

                xpp.next();
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = xpp.getName();//태그 이름 얻어오기
                            if (tag.equals("item")) ;// 첫번째 검색결과
                            else if (tag.equals("totalCount")) {
                                xpp.next();
                            } else if (tag.equals("BSTOPNM")) {
                                xpp.next();
                            } else if (tag.equals("BSTOPSEQ")) {
                                xpp.next();
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tag = xpp.getName(); //태그 이름 얻어오기
                            break;
                    }
                    eventType = xpp.next();
                }

            } catch (Exception e) {
                Log.d("XXXX", e.toString());
            }
        }
        return null;
    }
}
