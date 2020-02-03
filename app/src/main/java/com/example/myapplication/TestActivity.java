package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class TestActivity extends AppCompatActivity {
    String key = "sS9joESzYsg6XxvLZBn2lgAzqhh6GuMPZ%2BreJj0M8NP7fO1arBNQXWsCP3lVujAA24%2B9ANn%2BRlkwmgfBpGZ5MQ%3D%3D";
    ArrayList<RouteData> mArrayList;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    TestAdapter mAdapter;
    String RouteId;
    getBusRouteSectionList get1;
    String averageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        RouteId = intent.getExtras().getString("RouteId");
        averageTime = intent.getExtras().getString("averageTime");

        mRecyclerView = findViewById(R.id.recyclerView_routeId_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mArrayList = new ArrayList<>();
        mAdapter = new TestAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerDecoration recyclerDecoration = new RecyclerDecoration(-30);
        //아이템 사이 간격 조정.
        mRecyclerView.addItemDecoration(recyclerDecoration);
        get1 = new getBusRouteSectionList();
        get1.execute(RouteId);
    }

    public class getBusRouteSectionList extends AsyncTask<String, Void, Void> {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        ArrayList<String> BSTOPID = new ArrayList<>();
        String routeCount;
        String currentBusCount;
        String x[];
        int i = 0;

        @Override
        protected Void doInBackground(String... strings) {
            String queryUrl = "http://apis.data.go.kr/6280000/busRouteService/getBusRouteSectionList?"//요청 URL
                    + "ServiceKey=" + key
                    + "&pageNo=1" +
                    "&numOfRows=200" +
                    "&routeId=" + strings[0]; // 버스노선 조회

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
                            else if (tag.equals("totalCount")) {//노선의 총 수
                                xpp.next();
                                routeCount = xpp.getText();
                            } else if (tag.equals("BSTOPNM")) {//버스 정류소 명칭
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("BSTOPSEQ")) {//노선의 정류소 순번
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("BSTOPID")) {//노선의 정류소 아이디
                                xpp.next();
                                BSTOPID.add(xpp.getText());
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
                Log.d("XXXX1", e.toString());
            }


//=======================================================================================================================
            String queryUrl1 = "http://apis.data.go.kr/6280000/busLocationService/getBusRouteLocation?"//요청 URL
                    + "ServiceKey=" + key
                    + "&pageNo=1" +
                    "&numOfRows=200" +
                    "&routeId=" + strings[0];//버스위치정보 조회

            try {
                URL url1 = new URL(queryUrl1);//문자열로 된 요청 url을 URL 객체로 생성.
                InputStream is1 = url1.openStream(); //url위치로 입력스트림 연결
                XmlPullParserFactory factory1 = XmlPullParserFactory.newInstance();
                XmlPullParser xpp1 = factory1.newPullParser();
                xpp1.setInput(new InputStreamReader(is1, "UTF-8")); //inputStream 으로부터 xml 입력받기

                String tag1;

                xpp1.next();
                int eventType1 = xpp1.getEventType();

                while (eventType1 != XmlPullParser.END_DOCUMENT) {
                    switch (eventType1) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag1 = xpp1.getName();//태그 이름 얻어오기
                            if (tag1.equals("item")) ;// 첫번째 검색결과
                            else if (tag1.equals("totalCount")) {//현재 운행하는 버스의 수
                                xpp1.next();
                                currentBusCount = xpp1.getText();
                                x = new String[Integer.parseInt(currentBusCount)];
                            } else if (tag1.equals("LATEST_STOPSEQ")) {//	현재 위치 정류소 순번
                                xpp1.next();
                                arrayList2.add(xpp1.getText());
                                if (Integer.parseInt(xpp1.getText()) != Integer.parseInt(routeCount)) {
                                    x[i++] = BSTOPID.get(Integer.parseInt(xpp1.getText()) + 1);
                                } else {
                                    x[i++] = BSTOPID.get(0);
                                }
                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tag1 = xpp1.getName(); //태그 이름 얻어오기
                            break;
                    }
                    eventType1 = xpp1.next();
                }

            } catch (Exception e) {
                Log.d("XXXX2", e.toString());
                e.printStackTrace();
            }

//=======================================================================================================================
            for (int i = 0; i < Integer.parseInt(currentBusCount); i++) {
                String queryUrl2 = "http://apis.data.go.kr/6280000/busArrivalService/getBusArrivalList?"//요청 URL
                        + "ServiceKey=" + key
                        + "&pageNo=1" +
                        "&numOfRows=200" +
                        "&bstopId=" + x[i] + //버스 정류장 입력.
                        "&routeId=" + strings[0];//도착 정보 조회

                try {
                    URL url2 = new URL(queryUrl2);//문자열로 된 요청 url을 URL 객체로 생성.
                    InputStream is2 = url2.openStream(); //url위치로 입력스트림 연결
                    XmlPullParserFactory factory2 = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp2 = factory2.newPullParser();
                    xpp2.setInput(new InputStreamReader(is2, "UTF-8")); //inputStream 으로부터 xml 입력받기

                    String tag2;

                    xpp2.next();
                    int eventType2 = xpp2.getEventType();

                    while (eventType2 != XmlPullParser.END_DOCUMENT) {
                        switch (eventType2) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                tag2 = xpp2.getName();//태그 이름 얻어오기
                                if (tag2.equals("item")) ;// 첫번째 검색결과
                                else if (tag2.equals("ARRIVALESTIMATETIME")) {// 다음 정거장 도착 N초 전.
                                    xpp2.next();
                                    arrayList3.add(xpp2.getText());
                                }
                                break;

                            case XmlPullParser.TEXT:
                                break;

                            case XmlPullParser.END_TAG:
                                tag2 = xpp2.getName(); //태그 이름 얻어오기
                                break;
                        }
                        eventType2 = xpp2.next();
                    }

                } catch (Exception e) {
                    Log.d("XXXX3", e.toString());
                }

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            int x = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                arrayList2.sort(null);
            }

            Iterator it = arrayList.iterator();
            Iterator it1 = arrayList2.iterator();
            Iterator it2 = arrayList3.iterator();
            String LATEST_STOP_ID = it1.next().toString();
            RouteData routeData;
            int time = Integer.parseInt(it2.next().toString()) / 60;


            while (it.hasNext()) {
                String BusStopName = it.next().toString();
                String BusStopSequence = it.next().toString();

                if (Integer.parseInt(BusStopSequence) == Integer.parseInt(LATEST_STOP_ID)) {
                    x = 1;
                    routeData = new RouteData(BusStopName, BusStopSequence, true, -1, false);
                    mArrayList.add(routeData);
                    if (it1.hasNext()) {
                        LATEST_STOP_ID = it1.next().toString();
                    }
                } else {
                    if (x == 1) {
                        routeData = new RouteData(BusStopName, BusStopSequence, false, time, true);
                        if (it2.hasNext()) {
                            time = Integer.parseInt(it2.next().toString()) / 60;
                        }
                        x = 0;
                    } else {
                        routeData = new RouteData(BusStopName, BusStopSequence, false, -1, false);
                    }
                    mArrayList.add(routeData);
                }
                routeData.setTotalCount(routeCount);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}