
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.*;

public class test {

    public static void main(String[] args) {
        // 请求格式（GET请求）
        //http://api.map.baidu.com/place/v2/search?query=ATM机&tag=银行&region=北京&output=json&ak=您的ak

        //String url = "http://api.map.baidu.com/direction/v1?mode=driving&origin=清华大学&destination=北京大学&origin_region=北京&destination_region=北京&output=json&ak=您的ak";
//        String url = "http://api.map.baidu.com/place/v2/search?query=615&tag=公交&region=天津" +
//                "&output=json&ak=m2AcGr3RSGBOuKPb8crVuqBFAlG2BhyK";
        String url = "http://api.map.baidu.com/place/v2/search?query=615&region=天津" +
                "&output=json&ak=m2AcGr3RSGBOuKPb8crVuqBFAlG2BhyK";

        String result =connectURL(url,"");
        System.out.println(result);
        

    }

    public static String connectURL(String dest_url, String commString) {
        String rec_string = "";
        URL url = null;
        HttpURLConnection urlconn = null;
        OutputStream out = null;
        BufferedReader rd = null;
        try {
            url = new URL(dest_url);
            urlconn = (HttpURLConnection) url.openConnection();
            urlconn.setReadTimeout(1000 * 30);
            urlconn.setRequestMethod("GET");
            urlconn.setDoInput(true);
            urlconn.setDoOutput(true);
            out = urlconn.getOutputStream();
            out.write(commString.getBytes("UTF-8"));
            out.flush();
            out.close();
            rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream(),"UTF-8"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1)
                sb.append((char) ch);
            rec_string = sb.toString();
        } catch (Exception e) {

            return "";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (urlconn != null) {
                    urlconn.disconnect();
                }
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e) {

            }
        }
        return rec_string;
    }


    //连接及解析百度地图
    public static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
