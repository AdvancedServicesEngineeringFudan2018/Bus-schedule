package com.ding.get_bus_gps_position;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 范哲豪 on 2018/7/18.
 */

public class UpdateDataTask extends AsyncTask{
    private URL url;
    private HttpURLConnection connection;

    @Override
    protected Object doInBackground(Object[] objects) {

        String uname=objects[0].toString();
        String upass=objects[1].toString();
        String type=objects[2].toString();
        try {

            if("GET".equals(type)){
                //用GET方式请求
                url = new URL("http://193.168.3.134:8080/datatoclient/loginActionlogin.action?uname="+uname+"&upass="+upass);
                Log.i("test","get方式");
            }else if("POST".equals(type)){
                Log.i("test","post方式");
                //用POST方式请求
                url = new URL("http://193.168.3.134:8080/datatoclient/loginActionlogin.action");
            }
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(type);
            connection.setConnectTimeout(5000);


            if("POST".equals(type)){
                //设置可以允许对外输出数据
                connection.setDoOutput(true);

                String str="uname="+uname+"&upass="+upass;

                //添加请求头
                //Content-Length:24
                connection.setRequestProperty("Content-Length",""+str.length());
                //Content-Type:application/x-www-form-urlencoded
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                //将内容提交到服务器
                connection.getOutputStream().write(str.getBytes());
            }


            if(connection.getResponseCode()==200){
                InputStream is= connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String str=br.readLine();
                return str;
                //
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /*//更新UI
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        String s= (String) o;
        if("success".equals(s.trim())){
            Toast.makeText(MainActivity.this, "跳转到主界面", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
        //创建时间  修改时间
    }*/
}


