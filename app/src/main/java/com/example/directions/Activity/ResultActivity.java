package com.example.directions.Activity;

import static com.example.directions.Activity.ListViewActivity.markerImage;
import static com.example.directions.Activity.MainActivity.btnClickSize;
import static com.example.directions.ArraySavingClass.array_saving_class.alTMapPoint;
import static com.example.directions.ArraySavingClass.array_saving_class.autoZoomLocation;
import static com.example.directions.ArraySavingClass.array_saving_class.buttonNumArr;
import static com.example.directions.ArraySavingClass.array_saving_class.nameOfIt;
import static com.example.directions.ArraySavingClass.array_saving_class.buttonNum;
import static com.example.directions.ArraySavingClass.array_saving_class.buttonNumArr;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.directions.ArraySavingClass.array_saving_class;
import com.example.directions.Directions_Mark;
import com.example.directions.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity{
    private final String TMAP_API_KEY = "WggO9W2KT1HepbHAYgbt76NghWmyn5S5XASd57Xd";

    TMapData tmapdata;
    boolean findInfraFlag = false;
    static boolean transBtnClicked = false;
    static boolean cafeBtnClicked = false;
    static boolean foodBtnClicked = false;
    static boolean cultureBtnClicked = false;

    static int transSize = 0;
    static int cafeSize = 0;
    static int foodSize = 0;
    static int cultureSize = 0;
    static int POI_itemFlag = 0;
    static boolean transExistData = false;
    static boolean cafeExistData = false;
    static boolean foodExistData = false;
    static boolean cultureExistData = false;
    TMapView tmap;
    Bitmap centerMarkerImage;
    TMapPoint centerP;
    TextView subway_tv;
    TextView time_tv;

    double leftTopLat = 0;
    double leftTopLon = 0;
    double rightBottomLat = 0;
    double rightBottomLon = 0;

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tmap = new TMapView(this);
        LinearLayout tmaplayout = (LinearLayout)findViewById(R.id.main_layout_tmap);
        ImageButton btn1 = (ImageButton)findViewById(R.id.imageButton);
        ImageButton share_btn = (ImageButton) findViewById(R.id.share_btn);
        time_tv = (TextView)findViewById(R.id.estimated_time);
        subway_tv = (TextView)findViewById(R.id.subway);
        Button findInfraBtn = (Button) findViewById(R.id.button1);

        tmaplayout.addView(tmap);

        centerMarkerImage = BitmapFactory.decodeResource(this.getResources(),R.drawable.marker_icon);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity. class);
                startActivity(intent);
            }
        });

        TMapMarkerItem center = new TMapMarkerItem();


        centerP = new TMapPoint(array_saving_class.center_point.getLatitude(), array_saving_class.center_point.getLongitude());

        center.setIcon(centerMarkerImage); // 중간지점 마커 아이콘 지정
        center.setCanShowCallout(true); // 풍선뷰의 사용여부를 설정(사용).
        //center.setCalloutSubTitle("중간지점 입니다."); // 풍선뷰 보조메시지 설정

        /*leftTopLat = centerP.getLatitude();
        leftTopLon = centerP.getLongitude();
        rightBottomLat = centerP.getLatitude();
        rightBottomLon = centerP.getLongitude();

        // 자동으로 줌을 해주기 위해 leftTop의 위도를 설정
        for(int i = 0; i< buttonNumArr.size(); i++) {
            if(autoZoomLocation[buttonNumArr.get(i)].getLatitude() <= leftTopLat) {

                leftTopLat = autoZoomLocation[buttonNumArr.get(i)].getLatitude();
            } else{ }
        }
        // 자동으로 줌을 해주기 위해 leftTop의 경도를 설정
        for(int i = 0; i< buttonNumArr.size(); i++) {
            if(autoZoomLocation[buttonNumArr.get(i)].getLongitude() >= leftTopLon) {
                leftTopLon = autoZoomLocation[buttonNumArr.get(i)].getLongitude();
            } else{ }
        }
        // 자동으로 줌을 해주기 위해 rightBottom 위도를 설정
        for(int i = 0; i< buttonNumArr.size(); i++) {
            if(autoZoomLocation[buttonNumArr.get(i)].getLatitude() >= rightBottomLat){
                rightBottomLat = autoZoomLocation[buttonNumArr.get(i)].getLatitude();
            } else{ }
        }
        // 자동으로 줌을 해주기 위해 rightBottom 경도를 설정
        for(int i = 0; i< buttonNumArr.size(); i++) {
            if(autoZoomLocation[buttonNumArr.get(i)].getLongitude() <= rightBottomLon ) {
                rightBottomLon = autoZoomLocation[buttonNumArr.get(i)].getLongitude();
            } else{ }
        }

        TMapPoint leftTop = new TMapPoint(leftTopLat, leftTopLon);
        TMapPoint rightBottom = new TMapPoint(rightBottomLat, rightBottomLon);*/


        center.setCalloutTitle("중간지점 "); // 풍선뷰 제목 설정
        center.setAutoCalloutVisible(true); // 풍선뷰 자동 활성화

        // 마커의 좌표 지정
        center.setTMapPoint(centerP); // 마커 위,경도 설정
        center.setVisible(TMapMarkerItem.VISIBLE); // 마커 아이콘을 보이게 설정



        //지도에 마커 추가
        //tmap.addMarkerItem("markerItem", center);
        //tmap.setCenterPoint(centerP.getLongitude(), centerP.getLatitude()); // 위도경도 바꿔서

        //자동으로 최적화된 줌을 해줌
        //tmap.zoomToTMapPoint(leftTop, rightBottom );

        // 중간지점에 자동차경로 추가
        //Dutch_Mark.polyLineReturn(tMapView, alTMapPoint_size, centerP);

        // 모든마크 표시
        Directions_Mark.allMarkReturn(markerImage, tmap, alTMapPoint, nameOfIt, buttonNum, btnClickSize, buttonNumArr);

        TMapData forFindStation = new TMapData(); // 인근 지하철을 찾은 데이터를 넣을 TMapData
        TMapPoint centerPoint = centerP;


        //주변에 있는 지하철역을 검색
        forFindStation.findAroundNamePOI(centerPoint, "지하철", 3, 1, new TMapData.FindAroundNamePOIListenerCallback() {
            @Override
            public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {
                try{
                    if(POI_item != null) {
                        subway_tv.setText("인근 지하철 역 : " + POI_item.get(0).getPOIName()); // 중간지점에서 가장 가까운 지하철역을 textView에 보여줌
                        TMapPoint point = POI_item.get(0).getPOIPoint();
                        TMapMarkerItem C_marker = new TMapMarkerItem();
                        C_marker.setTMapPoint(point);
                        C_marker.setName(POI_item.get(0).getPOIName());
                        C_marker.setVisible(TMapMarkerItem.VISIBLE);
                        tmap.addMarkerItem(POI_item.get(0).getPOIName(), C_marker);
                        tmap.setCenterPoint(C_marker.longitude, C_marker.latitude); // 위도경도 바꿔서

                        C_marker.setCalloutTitle(POI_item.get(0).getPOIName());
                        C_marker.setCanShowCallout(true);
                        C_marker.setIcon(centerMarkerImage);

                        double sub_lat = C_marker.getTMapPoint().getLatitude();

                        ImageButton btnOpenKakaoMap = findViewById(R.id.navi_btn);
                        btnOpenKakaoMap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String kakaoMapUrl = "kakaomap://route?sp=&ep=" + C_marker.getTMapPoint().getLatitude() + "," + C_marker.getTMapPoint().getLongitude() + "&by=PUBLICTRANSIT";

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(kakaoMapUrl));
                                startActivity(intent);
                            }
                        });

                        share_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String searchQuery = C_marker.getName();
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("naversearchapp://keywordsearch?mode=result&query=" + searchQuery+"&version=10"));
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "네이버 앱이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        subway_tv.setText("인근 지하철 역 정보가 없습니다."); // 중간지점에서 주변 지하철을 검색했을 때 결과값이 하나도 없다면 textView에 지하철 역 정보가 없다고 알려줌
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // 주변시설 확인하기 버튼클릭 이벤트 처리
        findInfraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findInfraFlag == false) {
                    findInfraFlag = true;
                    TMapData tmapdata1 = new TMapData();
                    TMapPoint point1 = centerP; // 현재 TmapView에 CenterPoint를 가져옴

                    // CenterPoint를 기준으로 근처 대중교통 관련 주변시설들을 검색함. (주변 검색 허용거리를 3, 찾을 수 있는 주변시설의 개수를 20개로 설정)
                    tmapdata1.findAroundNamePOI(point1, "지하철;버스;버스정류장;", 3, 20, new TMapData.FindAroundNamePOIListenerCallback() {
                        @Override
                        public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {

                            if (POI_item == null) { // 주변시설을 검색할 수 없을 때


                                POI_itemFlag++; // 아이템이 없다는걸 확인시켜주는 Flag값을 올림
                                transExistData = false; // 대중교통 검색결과 데이터가 존재하는지 값을 저장해줌

                            }
                            else { // 주변시설이 정상적으로 검색되었을 때

                                for (int i = 0; i < POI_item.size(); i++) {

                                    TMapPOIItem item = POI_item.get(i);
                                    array_saving_class.transList[i] = item.getPOIName(); // 주변시설 리스트에 주변 대중교통 데이터들을 추가
                                    array_saving_class.transAddress[i] = item.getPOIAddress().replace("null", ""); // 주변시설 주소 리스트에 대중교통 주소 데이터들을 추가
                                    array_saving_class.transTMapPoint[i] = item.getPOIPoint(); // 대중교통 좌표 리스트에 주변시설 좌표 데이터들을 추가
                                    array_saving_class.transLat[i]= item.getPOIPoint().getLatitude(); // 대중교통 위도 리스트에 주변시설 위도 데이터들을 추가
                                    array_saving_class.transLon[i]= item.getPOIPoint().getLongitude(); // 대중교통 경도 리스트에 주변시설 경도 데이터들을 추가
                                    transSize = POI_item.size(); // 주변시설중 대중교통을 검색한 크기를 transSize 담음

                                }
                            }
                        }
                    });

                    TMapData tmapdata2 = new TMapData();
                    TMapPoint point2 = centerP; // 현재 TmapView에 CenterPoint를 가져옴

                    // CenterPoint를 기준으로 카페 관련 주변시설들을 검색함. (주변 검색 허용거리를 3, 찾을 수 있는 주변시설의 개수를 20개로 설정)
                    tmapdata2.findAroundNamePOI(point2, "카페;스터디카페", 3, 20, new TMapData.FindAroundNamePOIListenerCallback() {
                        @Override
                        public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {

                            if (POI_item == null) { // 주변시설을 검색할 수 없을 때


                                POI_itemFlag++;  // 아이템이 없다는걸 확인시켜주는 Flag값을 올림
                                cafeExistData = false; // 카페 검색결과 데이터가 존재하는지 값을 저장해줌

                            }
                            else { // 주변시설이 정상적으로 검색되었을 때

                                for (int i = 0; i < POI_item.size(); i++) {

                                    TMapPOIItem item = POI_item.get(i);
                                    array_saving_class.cafeList[i] = item.getPOIName(); // 주변시설 리스트에 주변 카페 데이터들을 추가
                                    array_saving_class.cafeAddress[i] = item.getPOIAddress().replace("null", ""); // 주변시설 주소 리스트에 카페 주소 데이터들을 추가
                                    array_saving_class.cafeTMapPoint[i] = item.getPOIPoint(); // 주변시설 좌표 리스트에 카페 좌표 데이터들을 추가
                                    array_saving_class.cafeLat[i]= item.getPOIPoint().getLatitude(); // 주변시설 위도 리스트에 카페 위도 데이터들을 추가
                                    array_saving_class.cafeLon[i]= item.getPOIPoint().getLongitude(); // 주변시설 경도 리스트에 카페 경도 데이터들을 추가
                                    cafeSize = POI_item.size(); // 주변시설중 카페를 검색한 크기를 cafeSize에 담음
                                }
                            }
                        }
                    });




                    TMapData tmapdata3 = new TMapData();
                    TMapPoint point3 = centerP; // 현재 TmapView에 CenterPoint를 가져옴

                    // CenterPoint를 기준으로 음식 관련 주변시설들을 검색함. (주변 검색 허용거리를 3, 찾을 수 있는 주변시설의 개수를 20개로 설정)
                    tmapdata3.findAroundNamePOI(point3, "음식;한식;중식;양식;", 3, 20, new TMapData.FindAroundNamePOIListenerCallback() {
                        @Override
                        public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {

                            if (POI_item == null) { // 주변시설을 검색할 수 없을 때

                                POI_itemFlag++; // 아이템이 없다는걸 확인시켜주는 Flag값을 올림
                                foodExistData = false; // 음식점 검색결과 데이터가 존재하는지 값을 저장해줌

                            }
                            else { // 주변시설이 정상적으로 검색되었을 때

                                for (int i = 0; i < POI_item.size(); i++) {

                                    TMapPOIItem item = POI_item.get(i);
                                    array_saving_class.foodList[i] = item.getPOIName(); // 주변시설 리스트에 주변 음식점 데이터들을 추가
                                    array_saving_class.foodAddress[i] = item.getPOIAddress().replace("null", ""); // 주변시설 주소 리스트에 음식점 주소 데이터들을 추가
                                    array_saving_class.foodTMapPoint[i] = item.getPOIPoint(); // 주변시설 좌표 리스트에 음식점 좌표 데이터들을 추가
                                    array_saving_class.foodLat[i]= item.getPOIPoint().getLatitude(); // 주변시설 위도 리스트에 음식점 위도 데이터들을 추가
                                    array_saving_class.foodLon[i]= item.getPOIPoint().getLongitude(); // 주변시설 경도 리스트에 음식점 경도 데이터들을 추가
                                    foodSize = POI_item.size(); // 주변시설을 검색한 크기를 foodSize 담음

                                }
                            }
                        }
                    });






                    TMapData tmapdata4 = new TMapData();
                    TMapPoint point4 = centerP; // 현재 TmapView에 CenterPoint를 가져옴

                    // CenterPoint를 기준으로 문화시설 관련 주변시설들을 검색함. (주변 검색 허용거리를 3, 찾을 수 있는 주변시설의 개수를 20개로 설정)
                    tmapdata4.findAroundNamePOI(point4, "주요시설물;문화시설;영화관;놀거리;", 3, 20, new TMapData.FindAroundNamePOIListenerCallback() {
                        @Override
                        public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {

                            if (POI_item == null) { // 주변시설을 검색할 수 없을 때

                                POI_itemFlag++; // 아이템이 없다는걸 확인시켜주는 Flag값을 올림
                                cultureExistData = false; // 문화시설 검색결과 데이터가 존재하는지 값을 저장해줌

                            }
                            else { // 주변시설이 정상적으로 검색되었을 때

                                for (int i = 0; i < POI_item.size(); i++) {

                                    TMapPOIItem item = POI_item.get(i);
                                    array_saving_class.cultureList[i] = item.getPOIName(); // 주변시설 리스트에 주변 문화시설 데이터들을 추가
                                    array_saving_class.cultureAddress[i] = item.getPOIAddress().replace("null", ""); // 주변시설 주소 리스트에 문화시설 주소 데이터들을 추가
                                    array_saving_class.cultureTMapPoint[i] = item.getPOIPoint(); // 주변시설 좌표 리스트에 문화시설 좌표 데이터들을 추가
                                    array_saving_class.cultureLat[i]= item.getPOIPoint().getLatitude(); // 주변시설 위도 리스트에 문화시설 위도 데이터들을 추가
                                    array_saving_class.cultureLon[i]= item.getPOIPoint().getLongitude(); // 주변시설 경도 리스트에 문화시설 경도 데이터들을 추가
                                    cultureSize = POI_item.size(); // 주변시설을 검색한 크기를 cultureSize 담음

                                }
                            }
                        }
                    });


                }
                else{

                }

                if(POI_itemFlag == 4) { // 검색한 4가지의 카테고리의 검색결과가 다 없을 경우
                    Handler toastHandler = new Handler(Looper.getMainLooper());
                    toastHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "근처에 검색할 주변시설이 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }
                else{
                    Intent InfraListIntent = new Intent(getApplicationContext(), infraListActivity.class);
                    startActivity(InfraListIntent);
                    findInfraFlag = false; // 계속해서 값이 씌워지는걸 방지하는 flag
                }
            }
        });
    }
}




        /*cafebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { getAroundBizPoi1(); }
        });
        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAroundBizPoi2();
            }
        });
        studybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAroundBizPoi3();
            }
        });
        etcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAroundBizPoi4();
            }
        });
    }


    public void getAroundBizPoi1() {
        tmap.removeAllMarkerItem();
        TMapData tmapdata = new TMapData();

        TMapPoint point = tmap.getCenterPoint();

        TMapPoint centerPoint = new TMapPoint(centerP.getLatitude(), centerP.getLongitude());
        tmapdata.findAroundNamePOI(centerPoint, "카페", 3, 20,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItems) {
                        if (poiItems != null) {
                            for (TMapPOIItem item : poiItems) {

                                TMapPoint point = item.getPOIPoint();
                                TMapMarkerItem marker = new TMapMarkerItem();
                                marker.setTMapPoint(point);
                                marker.setName(item.getPOIName());
                                marker.setVisible(TMapMarkerItem.VISIBLE);

                                tmap.addMarkerItem(item.getPOIName(), marker);

                                marker.setCalloutTitle("카페");
                                marker.setCalloutSubTitle(marker.getName());
                                marker.setCanShowCallout(true);
                                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cafe1);
                                int newWidth = 70;
                                int newHeight = 70;
                                Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                                marker.setIcon(scaledBitmap1);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "카페가 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void getAroundBizPoi2() {
        tmap.removeAllMarkerItem();
        TMapData tmapdata = new TMapData();

        TMapPoint point = tmap.getCenterPoint();

        TMapPoint centerPoint = new TMapPoint(centerP.getLatitude(), centerP.getLongitude());
        tmapdata.findAroundNamePOI(centerPoint, "음식점", 3, 10,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItems) {
                        if (poiItems != null) {
                            for (TMapPOIItem item : poiItems) {

                                TMapPoint point = item.getPOIPoint();

                                TMapMarkerItem marker = new TMapMarkerItem();
                                marker.setTMapPoint(point);
                                marker.setName(item.getPOIName());
                                marker.setVisible(TMapMarkerItem.VISIBLE);

                                tmap.addMarkerItem(item.getPOIName(), marker);

                                marker.setCalloutTitle("음식점");
                                marker.setCalloutSubTitle(marker.getName());
                                marker.setCanShowCallout(true);
                                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.food);
                                int newWidth = 70;
                                int newHeight = 70;
                                Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                                marker.setIcon(scaledBitmap1);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "음식점이 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void getAroundBizPoi3() {
        tmap.removeAllMarkerItem();
        TMapData tmapdata = new TMapData();

        TMapPoint point = tmap.getCenterPoint();

        TMapPoint centerPoint = new TMapPoint(centerP.getLatitude(), centerP.getLongitude());
        tmapdata.findAroundNamePOI(centerPoint, "스터디카페", 3, 10,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItems) {
                        if (poiItems != null) {
                            for (TMapPOIItem item : poiItems) {

                                TMapPoint point = item.getPOIPoint();

                                TMapMarkerItem marker = new TMapMarkerItem();
                                marker.setTMapPoint(point);
                                marker.setName(item.getPOIName());
                                marker.setVisible(TMapMarkerItem.VISIBLE);

                                tmap.addMarkerItem(item.getPOIName(), marker);

                                tmap.addMarkerItem(item.getPOIName(), marker);
                                marker.setCalloutTitle("스터디카페");
                                marker.setCalloutSubTitle(marker.getName());
                                marker.setCanShowCallout(true);
                                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.study);
                                int newWidth = 70;
                                int newHeight = 70;
                                Bitmap scaledBitmap3 = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                                marker.setIcon(scaledBitmap3);

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "스터디카페가 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void getAroundBizPoi4() {
        tmap.removeAllMarkerItem();
        TMapData tmapdata = new TMapData();

        TMapPoint point = tmap.getCenterPoint();

        TMapPoint centerPoint = new TMapPoint(centerP.getLatitude(), centerP.getLongitude());
        tmapdata.findAroundNamePOI(centerPoint, "문화시설", 3, 10,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItems) {
                        if (poiItems != null) {
                            for (TMapPOIItem item : poiItems) {

                                TMapPoint point = item.getPOIPoint();

                                TMapMarkerItem marker = new TMapMarkerItem();
                                marker.setTMapPoint(point);
                                marker.setName(item.getPOIName());
                                marker.setVisible(TMapMarkerItem.VISIBLE);

                                tmap.addMarkerItem(item.getPOIName(), marker);
                                marker.setCalloutTitle("문화시설");
                                marker.setCalloutSubTitle(marker.getName());
                                marker.setCanShowCallout(true);
                                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.movie);
                                int newWidth = 70;
                                int newHeight = 70;
                                Bitmap scaledBitmap4 = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                                marker.setIcon(scaledBitmap4);                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "문화시설이 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
