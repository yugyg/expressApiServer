package expressApiServer;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.yugyg.message.ExpressRequest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class KdiniaoTest {

	public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json;charset=utf-8");

	private final OkHttpClient client = new OkHttpClient();

	public void run() throws Exception {
		RequestBody requestBody = new RequestBody() {
			@Override
			public MediaType contentType() {
				return MEDIA_TYPE_MARKDOWN;
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				ExpressRequest request =new ExpressRequest();
				request.setExpCode("ZTO");
				request.setExpNo("214608203021");
				sink.writeUtf8(JSONObject.toJSONString(request));
				
			}

		
			
		};

		Request request = new Request.Builder().url("http://localhost:8181").post(requestBody).build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);

			System.out.println(response.body().string());
		}
	}
	
	public static void main(String[] args) throws Exception {
		new KdiniaoTest().run();
	}
}
