package com.example.myapplication;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class SearchFragment extends Fragment implements onItemClick {
    String key = "sS9joESzYsg6XxvLZBn2lgAzqhh6GuMPZ%2BreJj0M8NP7fO1arBNQXWsCP3lVujAA24%2B9ANn%2BRlkwmgfBpGZ5MQ%3D%3D";
    ArrayList<MyData> mArrayList;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    CustomAdapter mAdapter;
    EditText et;
    String busNo;
    String minTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView_main_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mArrayList = new ArrayList<>();
        mAdapter = new CustomAdapter(mArrayList, this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        //구분선 추가
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        et = getActivity().findViewById(R.id.et);

        getActivity().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busNo = et.getText().toString();
                if (et.getText().toString().replace(" ", "").equals(""))
                    Toast.makeText(getActivity().getApplicationContext(), "버스번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                else {
                    getXmlData getXD = new getXmlData();
                    getXD.execute(busNo);
                }
            }
        });

        return rootView;
    }

    @Override//중요!! 어댑터에서 값을 불러와서 사용.
    public void onClick(String data) {
        Intent intent = new Intent(getActivity(), TestActivity.class);
        intent.putExtra("RouteId", data);
        intent.putExtra("averageTime", minTime); // 도착 최소시간을 TestActivity에 넘김
        startActivity(intent);
    }

    class getXmlData extends AsyncTask<String, Void, Void> {
        ArrayList<String> arrayList = new ArrayList<>();

        @Override
        protected Void doInBackground(String... strings) {
            String queryUrl = "http://apis.data.go.kr/6280000/busRouteService/getBusRouteNo?"//요청 URL
                    + "ServiceKey=" + key
                    + "&pageNo=1" +
                    "&numOfRows=200" +
                    "&routeNo=" + strings[0];
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
                            else if (tag.equals("ROUTENO")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("ORIGIN_BSTOPNM")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("DEST_BSTOPNM")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("FBUS_DEPHMS")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("LBUS_DEPHMS")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("MIN_ALLOCGAP")) {
                                xpp.next();
                                minTime = xpp.getText();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("MAX_ALLOCGAP")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
                            } else if (tag.equals("ROUTEID")) {
                                xpp.next();
                                arrayList.add(xpp.getText());
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
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            Iterator it = arrayList.iterator();
            if (mAdapter.getItemCount() == 0) ;
            else {
                mArrayList.clear();
                mAdapter.notifyDataSetChanged();
            }

            while (it.hasNext()) {
                String ROUTENO, ORIGIN_BSTOPNM, DEST_BSTOPNM, FBUS_DEPHMS, LBUS_DEPHMS, MIN_ALLOCGAP, MAX_ALLOCGAP, ROUTEID;
                DEST_BSTOPNM = it.next().toString();
                FBUS_DEPHMS = it.next().toString();
                LBUS_DEPHMS = it.next().toString();
                MAX_ALLOCGAP = it.next().toString();
                MIN_ALLOCGAP = it.next().toString();
                ORIGIN_BSTOPNM = it.next().toString();
                ROUTEID = it.next().toString();
                ROUTENO = it.next().toString();
                MyData myData = new MyData(ROUTENO, ORIGIN_BSTOPNM, DEST_BSTOPNM, FBUS_DEPHMS,
                        LBUS_DEPHMS, MIN_ALLOCGAP + "분", MAX_ALLOCGAP + "분", ROUTEID, false);
                mArrayList.add(myData);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
