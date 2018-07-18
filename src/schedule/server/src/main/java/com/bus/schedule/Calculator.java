package com.bus.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Calculator {
    public  static  double rad(double d){
        return d* Math.PI/180.0;
    }
    //计算两个经纬度之间的距离
    public static double computeDistance(double lon1,double lat1,double lon2,double lat2){
        double a=rad(lat1)-rad(lat2);
        double b=rad(lon1)-rad(lon2);
        double c = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.pow(Math.sin(b / 2), 2)));
        c=c*6378.137;//6378.137km为赤道半径
        return Math.round(c * 10000d) / 10000d;
    }
    //计算时间间隔
    public static int computeDateInterval(String startTime,String endTime)throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        Date start=sdf.parse(startTime);
        Date end=sdf.parse(endTime);
        int interval=(int) (end.getTime()-start.getTime())/1000/60;//时间间隔以分钟为单位
        return interval;
    }
    //计算速度
    public static double computeSpeed(double dis,int ti){
        double speed=dis/ti;
        return speed;
    }
    //对比计划速度和实际速度
    public static String compareSpeed(double planSpeed,double realSpeed) {
        String result;
        if (realSpeed < planSpeed) {
            result = "Slow";
        } else if (realSpeed > planSpeed) {
            result = "Fast";
        } else
            result = "normal";
        return result;
    }

    public static String calculator(double lon1,double lat1,double lon2,double lat2,String starttime,String endtime)throws ParseException{
        double planspeed=0.1;//计划平均速度
        double distance=computeDistance(lon1,lat1,lon2,lat2);
        int interval=computeDateInterval(starttime,endtime);
        double realspeed=distance/interval;
        String result=compareSpeed(planspeed,realspeed);
        return result;
    }

    public static void main(String[] args) throws ParseException {
        double lon1=117.169551;//第一个点的经纬度
        double lat1=39.116067;
        String starttime="8:00:00";//第一个点的时间

        double lon2=117.186156;//第二点的经纬度
        double lat2=39.116524;
        String endtime="8:05:00";//第二个点的时间

        double planspeed=0.1;//计划平均速度

        double distance=computeDistance(lon1,lat1,lon2,lat2);
        int interval=computeDateInterval(starttime,endtime);
        double realspeed=distance/interval;
        String result=compareSpeed(planspeed,realspeed);
        System.out.println("两点间距离："+distance);
        System.out.println("时间间隔："+interval);
        System.out.println("真实速度："+realspeed);
        System.out.println("比计划："+result);

    }
}
