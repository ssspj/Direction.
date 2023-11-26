package com.example.directions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.directions.ListViewFunction.ListViewItem;
import com.example.directions.R;
import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;
import com.example.directions.ArraySavingClass.array_saving_class;
import com.example.directions.ListViewFunction.ListViewAdapter;

import static com.example.directions.Activity.MainActivity.findAddressbtn;
import static com.example.directions.Activity.MainActivity.POIitemSize;
import static com.example.directions.Activity.MainActivity.AddressResult;
import static com.example.directions.Activity.MainActivity.POILat;
import static com.example.directions.Activity.MainActivity.POILon;
import static com.example.directions.Activity.MainActivity.POIResult;
import static com.example.directions.Activity.MainActivity.btnClickSize;
import static com.example.directions.ArraySavingClass.array_saving_class.buttonNum;
import static com.example.directions.ArraySavingClass.array_saving_class.alTMapPoint;
import static com.example.directions.ArraySavingClass.array_saving_class.final_Point_size;
import static com.example.directions.ArraySavingClass.array_saving_class.final_location_size;
import static com.example.directions.ArraySavingClass.array_saving_class.nameOfIt;
import static com.example.directions.ArraySavingClass.array_saving_class.overLap;

import com.example.directions.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.poi_item.TMapPOIItem;

public class SearchActivity extends AppCompatActivity {
    //static ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final ListView FavoritesListView ;

        EditText inputText = (EditText)findViewById(R.id.searchText);
        ImageButton searchBtn = (ImageButton) findViewById(R.id.search_btn);
        ListView FavoritesListview = (ListView) findViewById(R.id.favorit_list);

        //검색 버튼 누르면 리스트뷰에 장소 표시
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strData = inputText.getText().toString(); // EditText에서 받은 String값을 strData에 저장

                TMapData tmapData = new TMapData();

                tmapData.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(final ArrayList<TMapPOIItem> poiItem) {

                        if (poiItem.size() == 0) { // 검색결과 없을 시
                            Handler toastHandler = new Handler(Looper.getMainLooper());
                            toastHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_LONG).show();
                                }
                            }, 0);
                        } else {
                            for (int i = 0; i < poiItem.size(); i++) {

                                TMapPOIItem item = poiItem.get(i);

                                POIResult[i] = item.getPOIName(); // POIResult[i]에 item에서 가져온 POI 이름을 저장
                                AddressResult[i] = item.getPOIAddress().replace("null", ""); // AddressResult[i]에 item에서 가져온 POI 주소를 저장
                                POILat[i] = item.getPOIPoint().getLatitude(); // POI지점에 위도를 POILat[i]에 저장
                                POILon[i] = item.getPOIPoint().getLongitude(); // POI지점에 경도를 POILon[i]에 저장
                                POIitemSize = poiItem.size(); // poiItem값을 전해주기 위해 POIitemSize에 저장
                            }

                                // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
                            Intent ListViewIntent = new Intent(getApplicationContext(), ListViewActivity.class);
                            startActivity(ListViewIntent); // 리스트뷰 띄우는 액티비티로 이동
                        }
                    }
                });
            }
        });
    }
}