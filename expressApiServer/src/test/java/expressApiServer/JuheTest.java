package expressApiServer;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.yugyg.express.impl.juhe.JuheMessage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JuheTest {



	private final OkHttpClient client = new OkHttpClient();

	public void run() throws Exception {
		
		

		String com ="sf";
		String no ="821519266954";
		String appKey ="de6614adae3b4b3237b9d440121a2cf2";
		String getParams ="key="+appKey+"&com="+com+"&no="+no;
		
         System.out.println("==========="+getParams);
		 Request request = new Request.Builder()
			        .url("http://v.juhe.cn/exp/index?"+getParams)
			        .build();
		 


		System.out.println("vvvvvvvvv");
		System.out.println(request.url());
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			String jsonStr =response.body().string();
			
			JuheMessage juheMessage = JSONObject.parseObject(jsonStr, JuheMessage.class);
			
			System.out.println(juheMessage);
		}
	}
	
	public static void main(String[] args) throws Exception {
		new JuheTest().run();
	}
}
