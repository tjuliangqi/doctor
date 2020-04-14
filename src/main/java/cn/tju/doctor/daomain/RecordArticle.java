package cn.tju.doctor.daomain;

import java.util.List;

public class RecordArticle {
    private String number;
    private String videoNumber;
    private double mount;
    private List<RecordDetail> details;

    public RecordArticle(String number, String videoNumber, double mount, List<RecordDetail> details) {
        this.number = number;
        this.videoNumber = videoNumber;
        this.mount = mount;
        this.details = details;
    }

    public RecordArticle() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVideoNumber() {
        return videoNumber;
    }

    public void setVideoNumber(String videoNumber) {
        this.videoNumber = videoNumber;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public List<RecordDetail> getDetails() {
        return details;
    }

    public void setDetails(List<RecordDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "RecordArticle{" +
                "number='" + number + '\'' +
                ", videoNumber='" + videoNumber + '\'' +
                ", mount=" + mount +
                ", details=" + details +
                '}';
    }
}
