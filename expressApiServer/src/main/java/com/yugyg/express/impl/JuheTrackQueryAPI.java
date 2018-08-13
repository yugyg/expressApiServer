package com.yugyg.express.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.yugyg.util.Constants;
import com.yugyg.util.ExcelUtil; 

/**
 *	 聚合物流查询
 * @author jiangchao1
 *
 */

public class JuheTrackQueryAPI {
	
	//DEMO
	public static void main(String[] args) {
		JuheTrackQueryAPI api = new JuheTrackQueryAPI();
		try {
			String result = api.getOrderTracesByJson("tt", "668611128225");
			System.out.print(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//电商ID
//	private String EBusinessID="1361487";
//	电商加密私钥，聚合提供，注意保管，不要泄漏
	private String AppKey="84802cf0618f6f7dbedeee949bcaaaa1";
	//请求url
	private String ReqURL="http://v.juhe.cn/exp/index";	
 
	/**
	 *   Json方式 查询订单物流轨迹
	 * @param expCode 物流公司编码
	 * @param expNo   物流单号
	 * @return
	 * @throws Exception
	 */
	public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", AppKey);
		params.put("com", expCode);
		params.put("no", expNo);
		String result=sendPost(ReqURL, params);	
		
		//根据公司业务处理返回的信息......
		
		return result;
	}
	/***
	 *  获得聚合快递的公司编码
	 * @param expCode
	 * @param expNo
	 * @return
	 * @throws Exception
	 */
	public JSONArray getCompineCode() throws Exception{
		return ExcelUtil.getCompanyCode(Constants.juhe);
	}
	
	 /**
     * 向指定 URL 发送POST方法的请求     
     * @param url 发送请求的 URL    
     * @param params 请求的参数集合     
     * @return 远程资源的响应结果
     */
	@SuppressWarnings("unused")
	private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		        	  System.out.println(entry.getKey()+":"+entry.getValue());
		          }
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        String resultString = result.toString();
        System.out.println("<<<<<<<<<<<<<http  reponse  body:>>>>>>>>>>>>>>>" +resultString);
        
        return resultString;
    }
	
	
}
