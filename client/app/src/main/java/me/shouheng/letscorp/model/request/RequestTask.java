package me.shouheng.letscorp.model.request;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.Call;

/**
 * @author shouh
 * @version $Id: RequestTask, v 0.1 2018/6/23 15:16 shouh Exp$
 */
public class RequestTask extends AsyncTask<RequestParams, Void, RequestParams> {

    @Override
    protected RequestParams doInBackground(RequestParams... datas) {
        RequestParams data = datas[0];
        Call call = data.call;
        try {
            data.response = call.execute();
            String body = data.response.body().string();
            data.doc = Jsoup.parse(body);
        } catch (IOException e) {
            e.printStackTrace();
            data.e = e;
        }
        return data;
    }

    @Override
    protected void onPostExecute(RequestParams data) {
        Callback callback = data.callback;
        if (data.call.isCanceled()) {
            Log.d("request", "cancelled");
            callback.onCancelled();
        } else if (data.e == null && data.doc != null) {
            callback.onSuccess(data.doc);
        } else {
            callback.onError(data.e);
        }
    }
}
