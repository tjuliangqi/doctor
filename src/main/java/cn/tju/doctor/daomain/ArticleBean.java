package cn.tju.doctor.daomain;

public class ArticleBean {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getIfVideo() {
        return ifVideo;
    }

    public void setIfVideo(int ifVideo) {
        this.ifVideo = ifVideo;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getBerecord() {
        return berecord;
    }

    public void setBerecord(int berecord) {
        this.berecord = berecord;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", writeTime='" + writeTime + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", sourceURL='" + sourceURL + '\'' +
                ", fulltext='" + fulltext + '\'' +
                ", picURL='" + picURL + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", label='" + label + '\'' +
                ", part='" + part + '\'' +
                ", ifVideo=" + ifVideo +
                ", like=" + like +
                ", view=" + view +
                ", download=" + download +
                ", berecord=" + berecord +
                '}';
    }

    private String title;
    private String source;
    private String writeTime;
    private String creatTime;
    private String sourceURL;
    private String fulltext;
    private String picURL;
    private String videoURL;
    private String label;
    private String part;
    private int ifVideo;
    private int like;
    private int view;
    private int download;
    private int berecord;

}
