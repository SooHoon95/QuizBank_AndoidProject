package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;

public class MyPageAddressActivity extends AppCompatActivity implements MapView.POIItemEventListener {

    static double lat; //위도
    static double lon; //경도

    //변수
    String urlAddr = null;
    String urlAddrUpdate = null;
    String tid = ShareVar.LoginID; // 선생님 tid값 (ShareVar)
    String taddress1 = ShareVar.taddress1; // 선생님 taddress1값 (ShareVar)
    String taddress2 = ShareVar.taddress2; // 선생님 taddress2값 (ShareVar)
    String macIP;

    TextView tv_address1, tv_address2;
    Button btn_edit, btn_remove;
    WebView webView_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_address);

        macIP = ShareVar.IPAddress;
        //업데이트용 jsp
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageTeacher_AddressUpdate.jsp?";
        btn_edit = findViewById(R.id.btn_MyPage_AddressAPI_Edit);
        btn_remove = findViewById(R.id.btn_MyPage_AddressAPI_Remove);
        tv_address1 = findViewById(R.id.tv_MyPage_teacher_Address1);
        tv_address2 = findViewById(R.id.tv_MyPage_teacher_Address2);
        //TextView Setting
        tv_address1.setText(taddress1);
        tv_address2.setText(taddress2);
        //맵뷰 띄우기
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        //실행 시
        if(ShareVar.taddress1 == null) { // DB에 들어간 값이 없으면
        }else{
            //위도, 경도 구하기
            Location loc = findGeoPoint(MyPageAddressActivity.this, taddress1);
            String location = loc.toString();
            Log.v("LOCATION ADDR", location);
//            //맵뷰 띄우기
//            MapView mapView = new MapView(MyPageAddressActivity.this);
//            ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//            mapViewContainer.addView(mapView);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat,lon),true);
//            리스너 등록
//        mapView.setMapViewEventListener(MyPageAddressActivity.this); //this에 MapView.MapViewEventListener 구현 // ** 없어도됨**
            mapView.setPOIItemEventListener(MyPageAddressActivity.this);
//            마커추가
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lat,lon); // 좌표 설정
            Log.v("LOCATION", lat + ", " + lon);
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("등록된 위치");
            marker.setTag(0);
            marker.setMapPoint(mapPoint); // 좌표 찍기
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본 제공 마커 모양
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);//마커를 클릭했을 때 나오는 마커모양
            mapView.addPOIItem(marker); // 마커 찍기
        }
        btn_edit.setOnClickListener(onClickListener);
        btn_remove.setOnClickListener(onClickListener);



    }//onCreate

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.btn_MyPage_AddressAPI_Edit:
                    intent = new Intent(MyPageAddressActivity.this, MyPageAddressEditActivity.class);
                    startActivity(intent);
//                    if(linearLayout_address.getVisibility()==View.INVISIBLE){
//                        linearLayout_address.setVisibility(View.VISIBLE);
//                    }else{
//                        linearLayout_address.setVisibility(View.INVISIBLE);
//                    }
                    break;
                case R.id.btn_MyPage_AddressAPI_Remove:
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(MyPageAddressActivity.this);
                    dlg2.setTitle("주소 삭제 확인")
                            .setIcon(R.drawable.mainicon)
                            .setMessage("주소를 삭제하시겠습니까?")
                            .setPositiveButton("삭제", mAddressRemove)
                            .setNegativeButton("취소", mAddressRemove)
                            .show();
                    break;
            }
        }
    };
    //Dialog Division Edit ( 선생님 주소 내용 삭제 )
    DialogInterface.OnClickListener mAddressRemove = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){ //확인 눌렀을 때
                tv_address1.setText("");
                tv_address2.setText("");
                //********여기에 DB 내용 넣어야 함 ********
                taddress1 = "";
                taddress2 = "";
                urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageTeacher_AddressUpdate.jsp?" + "taddress1=" + taddress1 + "&taddress2=" + taddress2 +"&tid=" + tid;
                String result = connectInsertData();
                ShareVar.taddress1 = "";
                ShareVar.taddress2 = "";
//                if(result.equals("1")){
//                    // 정상인 경우 ( 1만 정상이라는 것은 jsp 에서 판단 할 수 있도록 만들 예정임. )
//                    Toast.makeText(MyPageAddressActivity.this, "주소가 삭제되었습니다", Toast.LENGTH_SHORT).show();
//                }else  {/*에러걸렸으면*/
//                    Toast.makeText(MyPageAddressActivity.this, "주소 삭제가 실패되었습니다.",  Toast.LENGTH_SHORT).show();
//                }
            }else{
            };
        }
    };
    //위도, 경도 구하는 메소드
    public static Location findGeoPoint(Context context, String address){
        Location loc = new Location("");
        Geocoder coder = new Geocoder(context);
        List<Address> addr = null; // 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정
        try {
            addr = coder.getFromLocationName(address, 5);
        }catch (IOException e){
            e.printStackTrace();
        }//// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if(addr != null){
            for (int i = 0; i<addr.size(); i++){
                Address lating = addr.get(i);
                lat = lating.getLatitude(); //위도
                lon = lating.getLongitude(); //경도
                loc.setLatitude(lat);
                loc.setLongitude(lon);
            }
        }
        return loc;
    };
    //    private void connectGetdata() {
//        try {
//
//            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageDivisionActivity.this, urlAddr, "select");
//            Object obj = networkTask.execute().get();
//            members = (ArrayList<StudentMyPage>) obj;
//
//            adapter = new StudentAdapter(MyPageDivisionActivity.this, R.layout.activity_my_page_division, members);
//            listView.setAdapter(adapter);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private String connectInsertData(){
        String result = null;
        try{
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageAddressActivity.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
    }
    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
    }
}