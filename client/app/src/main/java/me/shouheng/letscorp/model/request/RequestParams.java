package me.shouheng.letscorp.model.request;

import org.jsoup.nodes.Document;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author shouh
 * @version $Id: RequestParams, v 0.1 2018/6/23 15:16 shouh Exp$
 */
public class RequestParams {
    public Call call;
    public Callback callback;
    public Response response;
    public Document doc;
    public Exception e;

    RequestParams(Call call, Callback callback) {
        this.call = call;
        this.callback = callback;
    }
}
