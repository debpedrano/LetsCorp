package me.shouheng.letscorp.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shouheng.commons.model.AttachmentFile;
import me.shouheng.letscorp.R;

/**
 * Created by WngShhng on 2018/6/25.*/
public class ModelHelper {

    public static void share(Context context, String title, String content, List<AttachmentFile> attachments) {
        Intent shareIntent = new Intent();
        if (attachments.size() == 0) {
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
        } else if (attachments.size() == 1) {
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType(attachments.get(0).getMineType());
            shareIntent.putExtra(Intent.EXTRA_STREAM, attachments.get(0).getUri());
        } else {
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            ArrayList<Uri> uris = new ArrayList<>();
            Map<String, Boolean> mimeTypes = new HashMap<>();
            for (AttachmentFile attachment : attachments) {
                uris.add(attachment.getUri());
                mimeTypes.put(attachment.getMineType(), true);
            }
            shareIntent.setType(mimeTypes.size() > 1 ? "*/*" : (String) mimeTypes.keySet().toArray()[0]);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        }
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);

        context.startActivity(Intent.createChooser(shareIntent,
                context.getResources().getString(R.string.share_message_chooser)));
    }
}
