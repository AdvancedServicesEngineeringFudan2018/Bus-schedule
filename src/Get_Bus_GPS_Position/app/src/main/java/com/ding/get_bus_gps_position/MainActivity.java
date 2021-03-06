package com.ding.get_bus_gps_position;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean flag;
    private Button gsp_btn;

    //    private Button best_btn;
    public Context context;

    public TextView Jingdu;
    public  TextView Weidu;

    private static int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        initView();
        initListener();

        mTimeHandler.sendEmptyMessageDelayed(0, 1000);
//        new TimeThread().start();
    }

    private void initListener() {
        gsp_btn.setOnClickListener(this);
//        network_btn.setOnClickListener(this);
//        best_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermission();//针对6.0以上版本做权限适配
    }

    private void initView() {
        gsp_btn = (Button) findViewById(R.id.gps);
//        network_btn = (Button) findViewById(R.id.net);
//        best_btn = (Button) findViewById(R.id.best);
        Jingdu = (TextView) findViewById(R.id.JingDuTextview);
        Weidu = (TextView) findViewById(R.id.WeiDuTextview);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
    }

    /**
     * 权限的结果回调函数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            flag = grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gps:
                if (flag) {
                    getGPSLocation();
                } else {
                    Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
                }
                break;

           /* case R.id.best:
                if (flag) {
                    getBestLocation();
                } else {
                    Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
                }
                break;*/
        }
    }

    /**
     * 通过GPS获取定位信息
     */
    public void getGPSLocation() {
        Location gps = LocationUtils.getGPSLocation(this);
        if (gps == null) {
            //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationUtils.addLocationListener(context, LocationManager.GPS_PROVIDER, new LocationUtils.ILocationListener() {
                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        Jingdu.setText(""+location.getLatitude());
                        Weidu.setText(""+location.getLongitude());
                        number++;
                        Toast.makeText(MainActivity.this, "No."+number+"  gps onSuccessLocation location:  lat==" + location.getLatitude()
                                + "     lng==" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "gps location is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Jingdu.setText(""+gps.getLatitude());
            Weidu.setText(""+gps.getLongitude());
            number++;
            Toast.makeText(this,"No."+number+"  gps onSuccessLocation location:  lat==" + gps.getLatitude() + "  lng==" + gps.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过网络等获取定位信息
     */
    private void getNetworkLocation() {
        Location net = LocationUtils.getNetWorkLocation(this);
        if (net == null) {
            Toast.makeText(this, "net location is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "network location: lat==" + net.getLatitude() + "  lng==" + net.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 采用最好的方式获取定位信息
     */
    private void getBestLocation() {
        Criteria c = new Criteria();//Criteria类是设置定位的标准信息（系统会根据你的要求，匹配最适合你的定位供应商），一个定位的辅助信息的类
        c.setPowerRequirement(Criteria.POWER_LOW);//设置低耗电
        c.setAltitudeRequired(true);//设置需要海拔
        c.setBearingAccuracy(Criteria.ACCURACY_COARSE);//设置COARSE精度标准
        c.setAccuracy(Criteria.ACCURACY_LOW);//设置低精度
        //... Criteria 还有其他属性，就不一一介绍了
        Location best = LocationUtils.getBestLocation(this, c);
        if (best == null) {
            Toast.makeText(this, " best location is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "best location: lat==" + best.getLatitude() + " lng==" + best.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    //在类里声明一个Handler
    Handler mTimeHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
//                tvTime.setText(TimeHelper.formatter_m.format(new Date(System.currentTimeMillis())));
                gsp_btn.performClick();
                sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    /*
    class TimeThread extends Thread{

        Message msg = new Message();

        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(1000);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        private Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (msg.what){
                    case 1:
                    gsp_btn.performClick();
                        break;
                }
                return false;
            }
        });

    }*/

}


