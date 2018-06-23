package me.shouheng.letscorp.model.article;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @author shouh
 * @version $Id: Comment, v 0.1 2018/6/23 22:45 shouh Exp$
 */
public class Comment {
    private int id;
    private String username;
    private String avatar;
    private long timestamp;
    private String content;
    private CommentCite cite;
    private List<Comment> children;

    public static int getChildrenSize(List<Comment> children) {
        int size = 0;
        for (Comment c : children) {
            size += c.size();
        }
        return size;
    }

    public Comment(int id, String username, String avatar, long timestamp, String content, CommentCite cite, List<Comment> children) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.timestamp = timestamp;
        this.content = content;
        this.cite = cite;
        this.children = children;
    }

    public String getDatetime() {
        if (timestamp <= 0) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(timestamp);
    }

    public int size() {
        return 1 + getChildrenSize(children);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentCite getCite() {
        return cite;
    }

    public void setCite(CommentCite cite) {
        this.cite = cite;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                ", cite=" + cite +
                ", children=" + children +
                '}';
    }

    public static class CommentCite {
        public String id;
        public String username;
        public String content;

        public CommentCite(String id, String name, String content) {
            this.id = id;
            this.username = name;
            this.content = content;
        }
    }
}
