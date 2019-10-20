package com.example.greentaxi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.greentaxi.mlkitbarcodescan.Util.BarcodeScanner;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class notification extends AppCompatActivity{

    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAA7ULgec:APA91bHN7mj4A1XuBvCutP70RJJb55yamoDOTavLIMoVoLAyHvAhZcN4iWssGcmr_9ynWrxG4g2PulB5qhSgDAUacuVPwiqdKW66HcsKf7qpsLNkfQckQ01Eq5tSrXhn8cJXyQCc66JB";

    private backPress backpress;


    private Handler mHandler = new Handler();
    private int RECORD_AUDIO_REQUEST_CODE =123 ;

    String partner_id;
    TMapView tmapView = null;

    TextView textView;

    // FireBaseDatabase 와 관련된 변수들
    FirebaseDatabase fb = FirebaseDatabase.getInstance();
    DatabaseReference dr = fb.getReference();
    List partner_list = new ArrayList();
    List notification_list = new ArrayList();

    Double s_LatLng_lati = null, s_LatLng_long = null, d_LatLng_lati = null, d_LatLng_long = null;
    Double c_LatLng_lati = null, c_LatLng_long = null;

    TMapPoint tMapPointStart = null, tMapPointEnd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        tmapView = new TMapView(this);

        LinearLayout tmap = findViewById(R.id.notification_tmap);

        tmapView.setSKTMapApiKey("fb390902-e9ca-4aed-bf74-66f14d37841e");
        tmap.addView(tmapView);

        backpress = new backPress(this);





        dr.child("partner_list").child(autoLogin.getUserName(notification.this)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    String msg = data.getValue().toString();
                    partner_list.add(msg);
                    Log.d("파트너 리스트 목록",partner_list.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dr.child("notification").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    String msg = data.getValue().toString();
                    notification_list.add(msg);
                    Log.d("알림 리스트 목록",notification_list.toString());

                    String a = notification_list.get(0).toString();
                    Log.d("이야이야",a);

                    c_LatLng_lati = Double.parseDouble(a);

                    /*
                    c_LatLng_long = Double.parseDouble(notification_list.get(1).toString());

                    d_LatLng_lati = Double.parseDouble(notification_list.get(2).toString());
                    d_LatLng_lati = Double.parseDouble(notification_list.get(3).toString());

                    s_LatLng_lati = Double.parseDouble(notification_list.get(4).toString());
                    s_LatLng_lati = Double.parseDouble(notification_list.get(5).toString());

                    // 출발 지점 마커 생성
                    TMapMarkerItem markerItem1 = new TMapMarkerItem();
                    TMapPoint tMapPoint1 = new TMapPoint(s_LatLng_lati, s_LatLng_long); //
                    markerItem1.setTMapPoint(tMapPoint1);
                    markerItem1.setName("출발 지점");
                    tmapView.addMarkerItem("markerItem1", markerItem1);

                    // 도착 지점 마커 생성
                    TMapMarkerItem markerItem2 = new TMapMarkerItem();
                    TMapPoint tMapPoint2 = new TMapPoint(d_LatLng_lati, d_LatLng_long); //
                    markerItem2.setTMapPoint(tMapPoint2);
                    markerItem2.setName("도착 지점");
                    tmapView.addMarkerItem("markerItem2", markerItem2);

                    tMapPointStart = new TMapPoint(s_LatLng_lati, s_LatLng_long);
                    tMapPointEnd = new TMapPoint(d_LatLng_lati, d_LatLng_long);
*/
                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("파트너 리스트 목록2 ",partner_list.toString());

    }

    // 중심점과 줌레벨 설정해주는 메소드 정의
    private void setBounds(ArrayList<TMapPoint> alTMapPoint) {
        TMapInfo tMapInfo = tmapView.getDisplayTMapInfo(alTMapPoint);
        tmapView.setCenterPoint(tMapInfo.getTMapPoint().getLongitude(), tMapInfo.getTMapPoint().getLatitude());
        tmapView.setZoomLevel(tMapInfo.getTMapZoomLevel());
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backpress.onBackPressed();
    }



    public void onClick(View view) {
    }
}





