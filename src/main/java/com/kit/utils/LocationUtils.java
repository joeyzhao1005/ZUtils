package com.kit.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.kit.app.application.AppMaster;
import com.kit.utils.log.Zog;

import java.util.List;
import java.util.Locale;

public class LocationUtils {
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context 上下文
     * @return true 表示开启 ，false为关闭
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 位置改变的监听
     */
    public static final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(android.location.Location location) {

            GetLatitudeAndLongitude(location);

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    /**
     * 获取位置
     *
     * @param context          上下文
     * @param locationListener 位置改变的监听
     * @return 返回location对象
     * 说明：此处动态权限放在最外层单独处理，可以根据自己的项目使用自己封装的方法
     */
    @SuppressLint("MissingPermission")
    public static Location getLocation(Context context, LocationListener locationListener) {
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) context.getSystemService(serviceName);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //此处得到所有的providers，循环providers分别得到不同的位置信息，再根据位置信息的水平精确度，得到相对精确的位置信息。
            // 好处：1既能解决有时无法获取网络位置。2又能获取相对准确的位置信息
            //该list内的返回不会太多，获取的时间消耗可以忽略
            List<String> providers = locationManager.getAllProviders();
            android.location.Location bestLocation = null;
            for (String provider : providers) {
                //注意此处为上一次定位的信息
                android.location.Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                //获得相对较为精确的位置，getAccuracy()方法可参照源码注释理解
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
            //注册位置更新
            try {
                if (locationListener != null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                }
            } catch (IllegalArgumentException e) {
                Zog.showException(e);
            }
            return bestLocation;
        } else {
            return null;
        }
    }

    /**
     * 得到经纬度
     *
     * @param location
     */
    public static void GetLatitudeAndLongitude(android.location.Location location) {
        double lat = 0;
        double lng = 0;
        if (location != null) {

            lat = location.getLatitude();

            lng = location.getLongitude();
        }
    }


    /**
     * 获取具体的位置信息（城市区域。。。）
     *
     * @param location 位置信息
     * @return 解析出具体城市的位置信息
     */
    public static Address getAddress(android.location.Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(AppMaster.INSTANCE.getAppContext(), Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }

    }

    public static void openGps() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppMaster.INSTANCE.getAppContext().startActivity(intent);
    }
}
