package me.shouheng.letscorp.common;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.helper.FileHelper;
import me.shouheng.commons.model.AttachmentFile;
import me.shouheng.commons.util.IntentUtils;
import me.shouheng.commons.util.ToastUtils;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.view.gallery.GalleryActivity;

/**
 * @author shouh
 * @version $Id: AttachmentResolver, v 0.1 2018/6/24 12:27 shouh Exp$
 */
public class AttachmentResolver {

    public static void resolveClickEvent(Context context, AttachmentFile attachment, List<AttachmentFile> attachments, String galleryTitle) {
        if (attachment == null) {
            ToastUtils.makeToast(R.string.file_not_exist);
            return;
        }
        switch (attachment.getMineType()) {
            case BaseConstants.MIME_TYPE_FILES: {
                resolveFiles(context, attachment);
                break;
            }
            case BaseConstants.MIME_TYPE_IMAGE:
            case BaseConstants.MIME_TYPE_SKETCH:
            case BaseConstants.MIME_TYPE_VIDEO: {
                resolveImages(context, attachment, attachments, galleryTitle);
                break;
            }
        }
    }

    private static void resolveFiles(Context context, AttachmentFile attachment) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(attachment.getUri(), FileHelper.getMimeType(context, attachment.getUri()));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (IntentUtils.isAvailable(context.getApplicationContext(), intent, null)) {
            context.startActivity(intent);
        } else {
            ToastUtils.makeToast(R.string.activity_not_found_to_resolve);
        }
    }

    private static void resolveImages(Context context, AttachmentFile attachment, List<AttachmentFile> attachments, String galleryTitle) {
        int clickedImage = 0;
        ArrayList<AttachmentFile> images = new ArrayList<>();
        for (AttachmentFile mAttachment : attachments) {
            if (Constants.MIME_TYPE_IMAGE.equals(mAttachment.getMineType())
                    || Constants.MIME_TYPE_SKETCH.equals(mAttachment.getMineType())
                    || Constants.MIME_TYPE_VIDEO.equals(mAttachment.getMineType())) {
                images.add(mAttachment);
                if (mAttachment.equals(attachment)) {
                    clickedImage = images.size() - 1;
                }
            }
        }
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(GalleryActivity.EXTRA_GALLERY_TITLE, galleryTitle);
        intent.putParcelableArrayListExtra(GalleryActivity.EXTRA_GALLERY_IMAGES, images);
        intent.putExtra(GalleryActivity.EXTRA_GALLERY_CLICKED_IMAGE, clickedImage);
        context.startActivity(intent);
    }
}
