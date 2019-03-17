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
 *	 鑱氬悎鐗╂祦鏌ヨ
 * @author jiangchao1
 *
 */

public class JuheTrackQueryAPI {
	
	//DEMO
	public static void main(String[] args) {
		JuheTrackQueryAPI api = new JuheTrackQueryAPI();
		try {
//			String result = api.getOrderTracesByJson("sto", "9688924333419");
			String result = api.getOrderTracesByJson("sto", "3365364862344");
			System.out.print(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//鐢靛晢ID
//	private String EBusinessID="1361487";
//	鐢靛晢鍔犲瘑绉侀挜锛岃仛鍚堟彁渚涳紝娉ㄦ剰淇濈锛屼笉瑕佹硠婕�
	private String AppKey="84802cf0618f6f7dbedeee949bcaaaa1";
	//璇锋眰url
	private String ReqURL="http://v.juhe.cn/exp/index";	
 
	/**
	 *   Json鏂瑰紡 鏌ヨ璁㈠崟鐗╂祦杞ㄨ抗
	 * @param expCode 鐗╂祦鍏徃缂栫爜
	 * @param expNo   鐗╂祦鍗曞彿
	 * @return
	 * @throws Exception
	 */
	public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("key", AppKey);
		params.put("com", expCode);
		params.put("no", expNo);
		String result=sendPost(ReqURL, params);	
		
		//鏍规嵁鍏徃涓氬姟澶勭悊杩斿洖鐨勪俊鎭�......
		
		return result;
	}
	/***
	 *  鑾峰緱鑱氬悎蹇�掔殑鍏徃缂栫爜
	 * @param expCode
	 * @param expNo
	 * @return
	 * @throws Exception
	 */
	public JSONArray getCompineCode() throws Exception{
		return ExcelUtil.getCompanyCode(Constants.juhe);
	}
	
	 /**
     * 鍚戞寚瀹� URL 鍙戦�丳OST鏂规硶鐨勮姹�     
     * @param url 鍙戦�佽姹傜殑 URL    
     * @param params 璇锋眰鐨勫弬鏁伴泦鍚�     
     * @return 杩滅▼璧勬簮鐨勫搷搴旂粨鏋�
     */
	@SuppressWarnings("unused")
	private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 鍙戦�丳OST璇锋眰蹇呴』璁剧疆濡備笅涓よ
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST鏂规硶
            conn.setRequestMethod("POST");
            // 璁剧疆閫氱敤鐨勮姹傚睘鎬�
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 鑾峰彇URLConnection瀵硅薄瀵瑰簲鐨勮緭鍑烘祦
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 鍙戦�佽姹傚弬鏁�            
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
            // flush杈撳嚭娴佺殑缂撳啿
            out.flush();
            // 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //浣跨敤finally鍧楁潵鍏抽棴杈撳嚭娴併�佽緭鍏ユ祦
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
