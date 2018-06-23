package me.shouheng.letscorp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author shouh
 * @version $Id: PostItem, v 0.1 2018/6/23 14:27 shouh Exp$
 */
public class PostItem implements Parcelable {
    private int id;
    private String title;
    private String href;
    private String img;
    private String content;
    private int commentCount;
    private long timestamp;
    private boolean readn;
    private int category;

    public static final Creator<PostItem> CREATOR = new Creator<PostItem>() {
        @Override
        public PostItem createFromParcel(Parcel in) {
            return new PostItem(in);
        }

        @Override
        public PostItem[] newArray(int size) {
            return new PostItem[size];
        }
    };

    public PostItem(int id, String title, String href, String img, String content, int ccount, long timestamp) {
        this.id = id;
        this.title = title;
        this.href = href;
        this.img = img;
        this.content = content;
        this.commentCount = ccount;
        this.timestamp = timestamp;
        readn = false;
        category = 0;
    }

    public PostItem(int id, String title, String href, String img, String content, int ccount, long timestamp, int cate) {
        this(id, title, href, img, content, ccount, timestamp);
        category = cate;
    }

    public PostItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        href = in.readString();
        img = in.readString();
        content = in.readString();
        commentCount = in.readInt();
        timestamp = in.readLong();
        readn = in.readInt() > 0;
        category = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(href);
        dest.writeString(img);
        dest.writeString(content);
        dest.writeInt(commentCount);
        dest.writeLong(timestamp);
        dest.writeInt(readn ? 1 : 0);
        dest.writeInt(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isReadn() {
        return readn;
    }

    public void setReadn(boolean readn) {
        this.readn = readn;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "PostItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                ", commentCount=" + commentCount +
                ", timestamp=" + timestamp +
                ", readn=" + readn +
                ", category=" + category +
                '}';
    }
}

