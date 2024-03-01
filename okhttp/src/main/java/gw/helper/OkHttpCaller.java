package gw.helper;

import com.gw.exception.GwHttpException;
import com.gw.exception.GwHttpIOException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OkHttpCaller {

  private final OkHttpClient okHttpClient;


  public String call(Request request) {

    Call call = okHttpClient.newCall(request);
    try (Response response = call.execute()) {

      if (!response.isSuccessful()) {
        throw new GwHttpException(response.code(), response.message());
      }

      ResponseBody body = response.body();
      if (null == body) {
        return null;
      }

      return body.string();
    } catch (IOException e) {
      throw new GwHttpIOException(e.getMessage(), e);
    }
  }

}
