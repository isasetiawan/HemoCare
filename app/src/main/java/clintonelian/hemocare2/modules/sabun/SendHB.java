package clintonelian.hemocare2.modules.sabun;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendHB {
    static OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public String TAG;

    public static void send(String token, String id, String hb){

        Log.d("Sabun things", "send: sending request "+token+ " " + id + " "+ hb);
        RequestBody formBody = new FormBody.Builder()
                .add("hb", hb)
                .build();
        Request request = new Request.Builder()
                .url("http://dev.bidan.sahabatbundaku.org/api/hamil/hb/"+id+"/"+hb)
                .addHeader("Secret","fEZYTJ8L2K8y94fmJ8c94stx6plDmL62")
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .put(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Sabun things", "onResponse: "+response.body().string());
            }
        });
    }

}
