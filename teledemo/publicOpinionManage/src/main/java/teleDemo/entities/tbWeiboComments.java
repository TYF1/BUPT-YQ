package teleDemo.entities;

import java.io.Serializable;

public class tbWeiboComments implements Serializable {
    private int commentId;
    private String userName;
    private int userFans;
    private String comments_1;
    private String link;
    private String keyWord;

    public tbWeiboComments() {
    }

    public tbWeiboComments(int commentId, String userName, int userFans, String comments_1,  String link,String keyWord) {
        this.commentId = commentId;
        this.userName = userName;
        this.userFans = userFans;
        this.comments_1 = comments_1;
        this.keyWord = keyWord;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserFans() {
        return userFans;
    }

    public void setUserFans(int userFans) {
        this.userFans = userFans;
    }

    public String getComments_1() {
        return comments_1;
    }

    public void setComments_1(String comments_1) {
        this.comments_1 = comments_1;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
