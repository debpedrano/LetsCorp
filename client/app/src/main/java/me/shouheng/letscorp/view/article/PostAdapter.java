package me.shouheng.letscorp.view.article;

import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

import me.shouheng.commons.manager.ModelFactory;
import me.shouheng.commons.model.AttachmentFile;
import me.shouheng.commons.util.ToastUtils;
import me.shouheng.letscorp.PalmApp;
import me.shouheng.letscorp.R;
import me.shouheng.letscorp.common.Constants;

/**
 * @author shouh
 * @version $Id: PostAdapter, v 0.1 2018/6/23 23:45 shouh Exp$
 */
public class PostAdapter extends BaseMultiItemQuickAdapter<PostAdapter.Segment, BaseViewHolder> {

    PostAdapter() {
        super(new LinkedList<>());
        addItemType(SegmentType.PLAIN.id, R.layout.item_palin_text);
        addItemType(SegmentType.QUOTE.id, R.layout.item_quote);
        addItemType(SegmentType.IMAGE.id, R.layout.item_image);
    }

    public static List<Segment> wrap(String content) {
        List<Segment> segments = new LinkedList<>();
        Document doc = Jsoup.parseBodyFragment(content);
        for (Element e : doc.body().children()) {
            String tag = e.tagName();
            if ("p".equals(tag)) {
                segmentsFromP(segments, e, false);
            } else if ("blockquote".equals(tag)) {
                for (Element p : e.children()) {
                    if ("p".equals(p.tagName())) {
                        segmentsFromP(segments, p, true);
                    }
                }
            } else {
                ToastUtils.makeToast(String.format("unknown tag %s, please file a bug in the GITHUB", tag));
            }
        }
        return segments;
    }

    private static void segmentsFromP(List<Segment> segments, Element e, boolean quote) {
        Elements imgs = e.select(">a>img");
        if (!imgs.isEmpty()) {
            for (Element img : imgs) {
                String url = img.attr("data-original");
                Glide.with(PalmApp.getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .preload();
                segments.add(new Segment(SegmentType.IMAGE, url, null));
            }
        } else {
            // todo handle the paragraph tab
            SpannableStringBuilder sb = new SpannableStringBuilder();
            if (!e.html().startsWith("<p>　")) sb.append("\t\t\t\t");
            sb.append(Html.fromHtml(e.html().replace("<br>", "<br>&nbsp;&nbsp;&nbsp;&nbsp;")));
            segments.add(new Segment(quote ? SegmentType.QUOTE : SegmentType.PLAIN, e.html(), sb));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Segment item) {
        switch (item.type) {
            case PLAIN: convertPlainText(helper, item);break;
            case QUOTE: convertQuoteText(helper, item);break;
            case IMAGE: convertImage(helper, item);break;
        }
    }

    private void convertPlainText(BaseViewHolder helper, Segment item) {
        helper.setText(R.id.tv, item.sb);
    }

    private void convertQuoteText(BaseViewHolder helper, Segment item) {
        helper.setText(R.id.tv, item.sb);
    }

    private void convertImage(BaseViewHolder helper, Segment item) {
        String url = item.data;
        if (TextUtils.isEmpty(url)) {
            return;
        }
        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("User-Agent", Constants.HTTP_USER_AGENT)
                .build());
        BitmapTypeRequest req = Glide.with(PalmApp.getContext()).load(glideUrl).asBitmap();
        req.placeholder(R.mipmap.ic_photo_black);
        req.error(R.mipmap.ic_broken_image_black)
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into((ImageView) helper.getView(R.id.iv));
    }

    public List<Segment> getImageSegments() {
        List<Segment> segments = new LinkedList<>();
        for (Segment segment : getData()) {
            if (segment.type == SegmentType.IMAGE) {
                segments.add(segment);
            }
        }
        return segments;
    }

    public static AttachmentFile getAttachmentFile(Segment segment) {
        AttachmentFile attachmentFile = ModelFactory.getAttachment();
        attachmentFile.setMineType(Constants.MIME_TYPE_IMAGE);
        attachmentFile.setUri(Uri.parse(segment.data));
        return attachmentFile;
    }

    public static List<AttachmentFile> getAttachmentFilss(List<Segment> segments) {
        List<AttachmentFile> attachmentFiles = new LinkedList<>();
        for (Segment segment : segments) {
            attachmentFiles.add(getAttachmentFile(segment));
        }
        return attachmentFiles;
    }

    public static class Segment implements MultiItemEntity {

        public final SegmentType type;

        public final String data;

        public final SpannableStringBuilder sb;

        public Segment(SegmentType t, String d, SpannableStringBuilder sb) {
            type = t;
            data = d;
            this.sb = sb;
        }

        @Override
        public int getItemType() {
            return type.id;
        }
    }

    public enum SegmentType {
        PLAIN(0),
        QUOTE(1),
        IMAGE(2);

        public final int id;

        SegmentType(int id) {
            this.id = id;
        }
    }
}
