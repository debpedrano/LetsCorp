package me.shouheng.letscorp.model.request;

import org.jsoup.nodes.Document;

/**
 * @author shouh
 * @version $Id: Callback, v 0.1 2018/6/23 15:15 shouh Exp$
 */
public interface Callback {

    void onSuccess(Document doc);

    void onCancelled();

    void onError(Exception e);
}