package cn.tju.doctor.daomain;

public class RecordDetail {
    private String videoName;
    private String category;
    private int count;
    private double money;
    private int mount;

    public RecordDetail(String videoName, String category, int count, double money, int mount) {
        this.videoName = videoName;
        this.category = category;
        this.count = count;
        this.money = money;
        this.mount = mount;
    }
    public RecordDetail() {
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    @Override
    public String toString() {
        return "RecordDetail{" +
                "videoName='" + videoName + '\'' +
                ", category='" + category + '\'' +
                ", count=" + count +
                ", money=" + money +
                ", mount=" + mount +
                '}';
    }
}
