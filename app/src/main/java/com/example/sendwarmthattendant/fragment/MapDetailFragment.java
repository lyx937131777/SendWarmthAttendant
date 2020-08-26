package com.example.sendwarmthattendant.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.OrderDetailActivity;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.MyApplication;
import com.example.sendwarmthattendant.util.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MapDetailFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index;

    public LocationClient mLocationClient;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;
    private String[] permissions;

    public static MapDetailFragment newInstance(int index)
    {
        MapDetailFragment fragment = new MapDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LogUtil.e("MapDetailFragment","onCreate"+ Utility.dateToString(new Date(),"HH:mm:ss"));
        index = 0;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        mLocationClient = new LocationClient(MyApplication.getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(MyApplication.getContext());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            requestLocation();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LogUtil.e("MapDetailFragment","onCreateView"+ Utility.dateToString(new Date(),"HH:mm:ss"));
        View root = inflater.inflate(R.layout.fragment_map_detail, container, false);
        mapView = root.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        requestLocation();

        //TODO temp
        ImageView tempCircle1 = root.findViewById(R.id.temp_circle1);
        ImageView tempCircle2 = root.findViewById(R.id.temp_circle2);
        ImageView tempCircle3 = root.findViewById(R.id.temp_circle3);
        ImageView tempCircle4 = root.findViewById(R.id.temp_circle4);
        if(getType().equals("running")){
            tempCircle2.setVisibility(View.GONE);
            tempCircle3.setVisibility(View.GONE);
            tempCircle4.setVisibility(View.GONE);
        }
        tempCircle1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                Order order = new Order("007","","","","running","","",0);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
        tempCircle2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                Order order = new Order("007","","","","waiting","","",0);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
        tempCircle3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                Order order = new Order("007","","","","waiting","","",0);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
        tempCircle4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                Order order = new Order("007","","","","waiting","","",0);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
        return root;
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//百度API保护隐私 默认获取火星坐标 加上这一行可直接获得真实坐标
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("MapDetailFragment","onResume"+ Utility.dateToString(new Date(),"HH:mm:ss"));
        mapView.onResume();
        requestLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getContext(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getContext(), "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
//            Toast.makeText(getContext(), "nav to " + location.getAddrStr(), Toast.LENGTH_LONG).show();
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private class MyLocationListener extends BDAbstractLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location) {
//            StringBuilder currentPosition = new StringBuilder();
//            currentPosition.append("纬度：").append(location.getLatitude()).append("    ");
//            currentPosition.append("经线：").append(location.getLongitude()).append("\n");
//            currentPosition.append("国家：").append(location.getCountry()).append("    ");
//            currentPosition.append("省：").append(location.getProvince()).append("    ");
//            currentPosition.append("市：").append(location.getCity()).append("\n");
//            currentPosition.append("区：").append(location.getDistrict()).append("    ");
//            currentPosition.append("街道：").append(location.getStreet()).append("    ");
//            currentPosition.append("定位方式：");
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                currentPosition.append("GPS");
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                currentPosition.append("网络");
//            }
//            positionText.setText(currentPosition);
            if (location.getLocType() == BDLocation.TypeGpsLocation
                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }

    }

    private String getType(){
        switch (index){
            case 0:
                return "running";
            case 1:
                return "all";
            default:
                return "unknown";
        }
    }
}
