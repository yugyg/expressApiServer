package expressApiServer;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yugyg.express.impl.juhe.JuheCompanyNo;
import com.yugyg.express.impl.juhe.JuheCompanyNo.JeheCompanyInfo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * https://www.juhe.cn/docs/api/id/43 查询所有快递公司
 * 
 * @author sunning
 *
 */
public class JuheTest2 {

	private final OkHttpClient client = new OkHttpClient();

	public void run() throws Exception {

		String appKey = "de6614adae3b4b3237b9d440121a2cf2";
		String getParams = "key=" + appKey;

		System.out.println("===========" + getParams);
		Request request = new Request.Builder().url("http://v.juhe.cn/exp/com?" + getParams).build();

		System.out.println("vvvvvvvvv");
		System.out.println(request.url());
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String jsonStr = response.body().string();
			System.out.println(jsonStr);

			JuheCompanyNo juheCompany = JSONObject.parseObject(jsonStr, JuheCompanyNo.class);

			List<JeheCompanyInfo> list = juheCompany.getResult();
			for (JeheCompanyInfo jeheCompanyInfo : list) {
              System.out.println(jeheCompanyInfo);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new JuheTest2().run();
	}
}
