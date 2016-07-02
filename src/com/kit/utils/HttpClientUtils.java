package com.kit.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpClientUtils {
    // public static final Log logger = LogFactory.getLog("httpclient");
    public static final String CODE = "UTF-8";


    public static String httpGet(String urlStr, HashMap<String, Object> params) {
        String responseStr = null;
        try {
            if (params != null) {
                System.out.println("toGetString(params):" + toGetString(params));
                urlStr = urlStr + "?" + toGetString(params);
            }
            URL url = new URL(urlStr);
            // HttpRequest httpRequest=new
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            if (conn.getResponseCode() == 200) {                //200表示请求成功
                InputStream is = conn.getInputStream();       //以输入流的形式返回
                //将输入流转换成字符串
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                responseStr = baos.toString();
                baos.close();
                is.close();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }


    public String httpPost(String urlStr, HashMap<String, Object> params) {
        String responseStr = null;
        try {
            URL url = new URL(urlStr);
            // HttpRequest httpRequest=new
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);             //允许向服务器输出数据

            String urlParas = toGetString(params);
            byte[] entity = urlParas.getBytes();

            conn.setDoInput(true);              //允许接收服务器数据
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);           // Post 请求不能使用缓存
            conn.setConnectTimeout(5000);

            //设置请求参数
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    //实体参数类型
            conn.setRequestProperty("Content-Length", entity.length+"");                    //实体参数长度

            // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
            // 要注意的是connection.getOutputStream会隐含的进行connect。
            conn.connect();
            //将要上传的参数写入流中
            conn.getOutputStream().write(entity);

            if (conn.getResponseCode() == 200) {                //200表示请求成功
                InputStream is = conn.getInputStream();       //以输入流的形式返回
                //将输入流转换成字符串
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                responseStr = baos.toString();
                baos.close();
                is.close();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;

    }


    public static String httpPost(String serverUrl, String method,
                                  JSONObject params, String code) {
        String url = serverUrl + "/" + method;
        ZogUtils.i(HttpClientUtils.class, "request url: " + url);
        String content = null;
        if (url == null || url.trim().length() == 0)
            return null;
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        postMethod.setRequestHeader("Content-Type",
                "application/json;charset=utf-8");
        if (params != null) {
            Iterator it = params.keys();
            while (it.hasNext()) {
                String key = it.next() + "";
                try {
                    Object o = params.get(key);
                    if (o != null && o instanceof String) {
                        System.out.println(key + ":" + o);
                        postMethod.addParameter(new NameValuePair(key, o
                                .toString()));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        try {
            httpClient.executeMethod(postMethod);
            ZogUtils.i(HttpClientUtils.class, postMethod.getStatusLine()
                    + "");
            content = new String(postMethod.getResponseBody(), code);

        } catch (Exception e) {
            ZogUtils.i(HttpClientUtils.class, "time out");
            e.printStackTrace();
        } finally {
            if (postMethod != null)
                postMethod.releaseConnection();
            postMethod = null;
            httpClient = null;
        }
        return content;

    }

    public static String httpPost(String url, Map paramMap, String code) {
        ZogUtils.i(HttpClientUtils.class, "request url: " + url);
        String content = null;
        if (url == null || url.trim().length() == 0 || paramMap == null
                || paramMap.isEmpty())
            return null;
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(url);

        method.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");

        Iterator it = paramMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next() + "";
            Object o = paramMap.get(key);
            if (o != null && o instanceof String) {
                method.addParameter(new NameValuePair(key, o.toString()));
            }

            System.out.println(key + ":" + o);
            method.addParameter(new NameValuePair(key, o.toString()));

        }
        try {
            httpClient.executeMethod(method);
            ZogUtils.i(HttpClientUtils.class, method.getStatusLine()
                    + "");
            content = new String(method.getResponseBody(), code);

        } catch (Exception e) {
            ZogUtils.i(HttpClientUtils.class, "time out");
            e.printStackTrace();
        } finally {
            if (method != null)
                method.releaseConnection();
            method = null;
            httpClient = null;
        }
        return content;

    }

    public static String toGetString(HashMap<String, Object> params) {

        if (MapUtils.isNullOrEmpty(params)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue().toString());

            }
            return sb.toString();
        }
    }

    public static String toGetString(JSONObject params) {

        String dataStr = params.toString();

        dataStr = dataStr.replaceAll("\"", "");
        dataStr = dataStr.replaceAll("\\{", "");
        dataStr = dataStr.replaceAll("\\}", "");
        dataStr = dataStr.replaceAll("\\:", "=");
        dataStr = dataStr.replaceAll("\\,", "&");

        System.out.println(dataStr);

        return dataStr;

    }
}
