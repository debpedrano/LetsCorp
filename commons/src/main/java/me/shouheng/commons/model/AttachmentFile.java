package me.shouheng.commons.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WngShhng on 2018/6/7.*/
public class AttachmentFile implements Parcelable {

    private Uri uri;

    private String path;

    private String name;

    private long size;

    private long length;

    private String mineType;

    public AttachmentFile(){}

    private AttachmentFile(Parcel in) {
        setUri(Uri.parse(in.readString()));
        setPath(in.readString());
        setName(in.readString());
        setSize(in.readLong());
        setLength(in.readLong());
        setMineType(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getUri().toString());
        dest.writeString(getPath());
        dest.writeString(getName());
        dest.writeLong(getSize());
        dest.writeLong(getLength());
        dest.writeString(getMineType());
    }

    public static final Creator<AttachmentFile> CREATOR = new Creator<AttachmentFile>() {

        public AttachmentFile createFromParcel(Parcel in) {
            return new AttachmentFile(in);
        }

        public AttachmentFile[] newArray(int size) {
            return new AttachmentFile[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getMineType() {
        return mineType;
    }

    public void setMineType(String mineType) {
        this.mineType = mineType;
    }

    @Override
    public String toString() {
        return "AttachmentFile{" +
                "uri=" + uri +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", length=" + length +
                ", mineType='" + mineType + '\'' +
                '}';
    }
}
