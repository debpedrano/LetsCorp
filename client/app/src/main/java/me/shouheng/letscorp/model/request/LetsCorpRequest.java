package me.shouheng.letscorp.model.request;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.shouheng.letscorp.common.Constants;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author shouh
 * @version $Id: LetsCorpRequest, v 0.1 2018/6/23 15:05 shouh Exp$
 */
@Singleton
public class LetsCorpRequest {

    private OkHttpClient okHttpClient;

    @Inject
    public LetsCorpRequest(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Call get(String url, Callback callback) {
        Log.d("get", url);
        Request request = new Request.Builder()
                .addHeader("User-Agent", Constants.HTTP_USER_AGENT)
                .addHeader("Accept", Constants.HTTP_ACCEPT)
                .url(url)
                .build();
        return execute(request, callback);
    }

    public Call post(String url, RequestBody body, Callback callback) {
        Log.d("post", url);
        Request request = new Request.Builder()
                .addHeader("User-Agent", Constants.HTTP_USER_AGENT)
                .addHeader("Accept", Constants.HTTP_ACCEPT)
                .url(url)
                .method("POST", body)
                .build();
        return execute(request, callback);
    }

    private Call execute(okhttp3.Request request, Callback callback) {
        Call call = okHttpClient.newCall(request);
        new RequestTask().execute(new RequestParams(call, callback));
        return call;
    }
}
