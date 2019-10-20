package com.example.greentaxi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class main extends AppCompatActivity {

    private EditText login_id;
    private EditText login_pass;
    private Button login_ok;
    private Button signup;
    private CheckBox autoLogin_checkBox;

    DatabaseReference userName = null;
    DatabaseReference userPassword = null;

    private List check = new ArrayList();

    private static final String TAG2 = "";

    TMapView tmapView = null;

    String ONOFF = null;
    int dis;

    List partner_list = new ArrayList();
    List notification_list = new ArrayList();

    Double s_LatLng_lati = null, s_LatLng_long = null, d_LatLng_lati = null, d_LatLng_long = null;
    Double c_LatLng_lati = null, c_LatLng_long = null;

    TMapPoint tMapPointStart = null, tMapPointEnd = null;

    String user;

    TMapMarkerItem markerItem1;

    TMapMarkerItem markerItem2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2;
        databaseReference2 = firebaseDatabase.getReference();
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("notification").getChildren()) {
                    check.clear();
                    check.add(snapshot.getKey());
                    Log.d("hello", String.valueOf(check));

                    user = (String) snapshot.child("user").getValue();
                    Log.d("user찾자", user + " ");
                    ONOFF = snapshot.child("status").getValue().toString();
                   String a;

                    a = String.valueOf(snapshot.child("time").getValue());
                    if(a != "null") {
                        dis = Integer.parseInt(a);
                    }
                }


                Log.d("hello out", String.valueOf(check));

                List list = new ArrayList();

                for (DataSnapshot data : dataSnapshot.child("partner_list").child(autoLogin.getUserName(main.this)).getChildren()) {

                    list.add(data.getKey().toString());
                    Log.d("hello2 in", String.valueOf(list));
                }

                Log.d("auto user name", autoLogin.getUserName(main.this) + " 0 ");
                Log.d("hello2", String.valueOf(list));


                if (check(list, check) == true && ONOFF.equals("on")) {
/*
                    Intent intent = new Intent(getApplicationContext(),notification.class);
                    startActivity(intent);
                    */
                    Log.d("문제 찾기", check.toString());


                    setContentView(R.layout.notification);


                    TextView predict_time = findViewById(R.id.noti_time);

                    tmapView = new TMapView(main.this);

                    LinearLayout tmap = findViewById(R.id.notification_tmap);

                    tmapView.setSKTMapApiKey("fb390902-e9ca-4aed-bf74-66f14d37841e");
                    tmap.addView(tmapView);


                    databaseReference2.child("notification").child("1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                String msg = data.getValue().toString();
                                notification_list.add(msg);
                                Log.d("알림 리스트 목록", notification_list.toString());
                            }
                            String a = notification_list.get(0).toString();
                            String b = notification_list.get(1).toString();
                            String c = notification_list.get(2).toString();
                            String d = notification_list.get(3).toString();
                            String e = notification_list.get(4).toString();
                            String f = notification_list.get(5).toString();
                            Log.d("이야이야", " c_LatLng_lati " + a);
                            Log.d("이야이야2", b);
                            Log.d("이야이야3", " d_LatLng_lati " + c);

                            Log.d("이야이야2", d);
                            Log.d("이야이야2", e);
                            Log.d("이야이야2", f);

                            d_LatLng_lati = Double.parseDouble(c);
                            d_LatLng_long = Double.parseDouble(d);

                            s_LatLng_lati = Double.parseDouble(e);
                            s_LatLng_long = Double.parseDouble(f);

                            c_LatLng_lati = Double.parseDouble(a);
                            c_LatLng_long = Double.parseDouble(b);

                            Log.d("test222", c_LatLng_lati + " 입니다");
                            tMapPointStart = new TMapPoint(s_LatLng_lati, s_LatLng_long);
                            tMapPointEnd = new TMapPoint(d_LatLng_lati, d_LatLng_long);

                            // 출발 지점 마커 생성
                            markerItem1 = new TMapMarkerItem();
                            TMapPoint tMapPoint1 = new TMapPoint(s_LatLng_lati, s_LatLng_long); //
                            markerItem1.setTMapPoint(tMapPoint1);
                            markerItem1.setName("출발 지점");


                            // 도착 지점 마커 생성
                            markerItem2 = new TMapMarkerItem();
                            TMapPoint tMapPoint2 = new TMapPoint(d_LatLng_lati, d_LatLng_long); //
                            markerItem2.setTMapPoint(tMapPoint2);
                            markerItem2.setName("도착 지점");

                            tmapView.addMarkerItem("markerItem1", markerItem1);
                            tmapView.addMarkerItem("markerItem2", markerItem2);

                            findPathData();

                            // 현재 시간을 msec으로 구한다.
                            long now = System.currentTimeMillis();

                            // 현재 시간을 저장 한다.
                            Date date = new Date(now);


                            SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
                            SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
                            String strCurHour = CurHourFormat.format(date);
                            String strCurMinute = CurMinuteFormat.format(date);

                            int bun, bun2;
                            bun = dis / 60;
                            bun2 = bun + Integer.parseInt(strCurMinute);

                            predict_time.setText("예상 도착 시간은 \n" + strCurHour + " 시" + bun2 + " 분입니다.");
                            //    predict_time.setText("시발");

                            TextView ok = findViewById(R.id.noti_ok);


                            tmapView.setIconVisibility(true); // 현재위치에 아이콘을 표시할지 여부 설정

                            setGps();

                            // 메인 스레드가 아닌 새로운 스레드를 생성해서 실행
                            // findViewById(R.id.notification_tmap).post(() -> findPathData());


                            //tmapView.setCenterPoint(c_LatLng_long,c_LatLng_lati);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });


                } else {

                    setContentView(R.layout.main);

                    login_id = findViewById(R.id.inputId);
                    login_pass = findViewById(R.id.inputPassword);
                    login_ok = findViewById(R.id.login);
                    signup = findViewById(R.id.signUp);
                    autoLogin_checkBox = findViewById(R.id.login_autoCheck);

                    userName = FirebaseDatabase.getInstance().getReference().child("member_info").child(login_id.getText().toString());
                    userPassword = FirebaseDatabase.getInstance().getReference().child("member_info").child(login_id.getText().toString());


                    // 자동 로그인
                    if (autoLogin.checkisEmpty(main.this) == 1) {
                        login_id.setText(autoLogin.getUserName(main.this));
                        login_pass.setText(autoLogin.getUserName(main.this));

                    }
                    /*if (!(autoLogin_checkBox.isChecked())) {
                        autoLogin.clearUserName(main.this);
                    }*/


                    signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), signUp.class);
                            startActivity(intent);

                        }
                    });

                    login_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (login_id.getText().toString().isEmpty() || login_pass.getText().toString().isEmpty()) {
                                Toast.makeText(main.this, "공백없이 입력해주세요.", Toast.LENGTH_SHORT).show();

                            } else {

                                userPassword.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Object info = dataSnapshot.getKey();
                                        Log.d("datasnapshot", info + " ");
                                        if (dataSnapshot.child(login_id.getText().toString()).exists()) {
                                            member_info user = dataSnapshot.child(login_id.getText().toString()).getValue(member_info.class);
                                            if (user.getPassword().equals(login_pass.getText().toString())) {

                                                currentUserInfo c_user = new currentUserInfo();
                                                c_user.setId(login_id.getText().toString());
                                                c_user.setName(dataSnapshot.child(login_id.getText().toString()).child("userName").getValue().toString());
                                                Log.d("userId 출력테스트", c_user.getName());

                                                // 로그인시 token 업데이트
                                                String token = FirebaseInstanceId.getInstance().getToken();
                                                Map<String, Object> taskMap = new HashMap<String, Object>();
                                                taskMap.put("token", token);

                                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                DatabaseReference dataReference = firebaseDatabase.getReference("member_info").child(c_user.getId());
                                                dataReference.updateChildren(taskMap);

                                                //자동 로그인 부분
                                                if (autoLogin_checkBox.isChecked()) {
                                                    autoLogin.setUserName(main.this, login_id.getText().toString());
                                                    autoLogin.setUserPassWord(main.this, login_pass.getText().toString());
                                                    Toast.makeText(getApplicationContext(), "로그인 정보가 저장되었습니다..", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    autoLogin.setUserName(main.this, "");
                                                    autoLogin.setUserPassWord(main.this, "");
                                                    Toast.makeText(main.this, "로그인 정보를 저장하지않습니다..", Toast.LENGTH_SHORT).show();
                                                }


                                                Intent intent = new Intent(getApplicationContext(), main_logined.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(main.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {
                                            Toast.makeText(main.this, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public Boolean check(List a, List b) { //list 가 파트너 값, check 가 알림에 있는값
        for (int i = 0; i < a.size(); i++) {
            if (a.isEmpty() || b.isEmpty()) {
                Log.d("여기", "여기임");
                return false;
            } else if (a.contains(b.get(i))) {
                Log.d("alarm", "list 에는 " + b.get(i) + " 가 있다.");
                return true;
            } else {
                Log.d("alarm", "list 에는 " + b.get(i) + " 가 없다.");
                return false;
            }
        }

        return false;
    }

    ;

    // 자동차기준 경로 표시를 해주는 메소드
    private void findPathData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               /*
                TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
                TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)
               */

                try {
                    TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
                    tMapPolyLine.setLineColor(Color.GREEN);
                    tMapPolyLine.setLineWidth(2);
                    tmapView.addTMapPolyLine("Line1", tMapPolyLine);

                    // 중심점과 줌레벨 설정
                    setBounds(tMapPolyLine.getLinePoint());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 중심점과 줌레벨 설정해주는 메소드 정의
    private void setBounds(ArrayList<TMapPoint> alTMapPoint) {
        TMapInfo tMapInfo = tmapView.getDisplayTMapInfo(alTMapPoint);
        tmapView.setCenterPoint(tMapInfo.getTMapPoint().getLongitude(), tMapInfo.getTMapPoint().getLatitude());
        tmapView.setZoomLevel(tMapInfo.getTMapZoomLevel());
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            if (location != null) {
                double latitude = c_LatLng_lati;
                double longitude = c_LatLng_long;
                tmapView.setLocationPoint(longitude, latitude);

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
    }


}



