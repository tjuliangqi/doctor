package cn.tju.doctor.daomain;

public class Base {
    private double article;
    private double view;
    private double download;
    private double like;
    private double hide;
    private int id;

    public double getArticle() {
        return article;
    }

    public void setArticle(double article) {
        this.article = article;
    }

    public double getView() {
        return view;
    }

    public void setView(double view) {
        this.view = view;
    }

    public double getDownload() {
        return download;
    }

    public void setDownload(double download) {
        this.download = download;
    }

    public double getLike() {
        return like;
    }

    public void setLike(double like) {
        this.like = like;
    }

    public double getHide() {
        return hide;
    }

    public void setHide(double hide) {
        this.hide = hide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
