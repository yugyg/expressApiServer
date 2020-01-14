package com.yugyg.express.impl;

import com.alibaba.fastjson.JSONArray;
import com.yugyg.util.Constants;
import com.yugyg.util.ExcelUtil;
import com.yugyg.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 快递鸟物流轨迹即时查询接口
 *
 * @技术QQ群: 456320272
 * @see: http://www.kdniao.com/YundanChaxunAPI.aspx
 * @copyright: 深圳市快金数据技术服务有限公司
 *
 * DEMO中的电商ID与私钥仅限测试使用，正式环境请单独注册账号
 * 单日超过500单查询量，建议接入我方物流轨迹订阅推送接口
 *
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */
public class KdniaoTrackQueryAPI {

    //DEMO
    public static void main(String[] args) {
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        try {
            //String result = api.getOrderTracesByJson("SF", "821519266954");
            String result = api.getOrderTracesByJson("YZPY", "9892938911658");
            //			String result = api.getOrderTracesByJson("SF", "1234561");
            //			System.out.println();
            //			System.out.println();
            //			System.out.println();
            //			System.out.println();
            //		    String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
            //		    String ReqURL = "http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";
            //		    String params = "EBusinessID=test1361487&DataType=2&RequestType=1002&RequestData=%7b%22OrderCode%22%3a%22%22%2c%22ShipperCode%22%3a%22SF%22%2c%22LogisticCode%22%3a%221234561%22%2c%22IsHandleInfo%22%3a%220%22%7d&DataSign=NWJmMTZkMzRjYmJjOTJiMzJkNTVmMjdiNmMwMTczMTc=";
            //		    ReqURL = HttpUtil.createUrlParams(ReqURL, params);
            //		    String result = HttpUtil.okSend(ReqURL, HttpUtil.OK_BODY_NULL, HttpUtil.OK_MEDIA_FORM, HttpUtil.OK_METHOD_POST, null, null);
            System.out.print(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //电商ID
    private String EBusinessID = "1361487";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey = "e8ff02a8-5890-4c53-a22f-86fa082e6372";
    //请求url
    //	private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    private String ReqURL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
    //	private String ReqURL="http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";

    /**
     * Json方式 查询订单物流轨迹
     *
     * @param expCode 物流公司编码
     * @param expNo   物流单号
     * @return
     * @throws Exception
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String requestData = "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

    /***
     * 	获得快递鸟快递的公司编码
     * @return
     * @throws Exception
     */
    public JSONArray getCompineCode() {
        return ExcelUtil.getCompanyCode(Constants.kdniao);
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    private String encrypt(String content, String keyValue, String charset)
        throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    private String sendPost(String url, Map<String, String> params) {

        url = HttpUtil.createUrlParams(url, HttpUtil.createParamsByMap(params));
        String result =
            HttpUtil.okSend(url, HttpUtil.OK_BODY_NULL, HttpUtil.OK_MEDIA_JSON, HttpUtil.OK_METHOD_POST, null, null);
        String resultString = result.toString();
        System.out.println("<<<<<<<<<<<<<http  reponse  body:>>>>>>>>>>>>>>>" + resultString);

        return resultString;
    }

    private static char[] base64EncodeChars =
        new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '+', '/'};

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
}
