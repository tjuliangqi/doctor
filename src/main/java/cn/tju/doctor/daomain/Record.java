package cn.tju.doctor.daomain;

public class Record {
    private String number;
    private String articleID;
    private int views;
    private int download;
    private int likes;
    private int hides;
    private String publishTime;
    private double viewsMoney;
    private double hidesMoney;
    private double downloadsMoney;
    private double likesMoney;

    public double getHidesMoney() {
        return hidesMoney;
    }

    public void setHidesMoney(double hidesMoney) {
        this.hidesMoney = hidesMoney;
    }

    @Override
    public String toString() {
        return "Record{" +
                "number='" + number + '\'' +
                ", articleID='" + articleID + '\'' +
                ", views=" + views +
                ", download=" + download +
                ", likes=" + likes +
                ", hides=" + hides +
                ", publishTime='" + publishTime + '\'' +
                ", viewsMoney=" + viewsMoney +
                ", hidesMoney=" + hidesMoney +
                ", downloadsMoney=" + downloadsMoney +
                ", likesMoney=" + likesMoney +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getHides() {
        return hides;
    }

    public void setHides(int hides) {
        this.hides = hides;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public double getViewsMoney() {
        return viewsMoney;
    }

    public void setViewsMoney(double viewsMoney) {
        this.viewsMoney = viewsMoney;
    }

    public double getDownloadsMoney() {
        return downloadsMoney;
    }

    public void setDownloadsMoney(double downloadsMoney) {
        this.downloadsMoney = downloadsMoney;
    }

    public double getLikesMoney() {
        return likesMoney;
    }

    public void setLikesMoney(double likesMoney) {
        this.likesMoney = likesMoney;
    }
}
