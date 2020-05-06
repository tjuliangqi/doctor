package cn.tju.doctor.daomain;

import java.util.Date;

public class Record2 {
    private String number;
    private int registNum;
    private int onlineNum;
    private int forwardNum;
    private String publishTime;
    private double registMoney;
    private double onlineMoney;
    private double forwardMoney;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getRegistNum() {
        return registNum;
    }

    public void setRegistNum(int registNum) {
        this.registNum = registNum;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(int forwardNum) {
        this.forwardNum = forwardNum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public double getRegistMoney() {
        return registMoney;
    }

    public void setRegistMoney(double registMoney) {
        this.registMoney = registMoney;
    }

    public double getOnlineMoney() {
        return onlineMoney;
    }

    public void setOnlineMoney(double onlineMoney) {
        this.onlineMoney = onlineMoney;
    }

    public double getForwardMoney() {
        return forwardMoney;
    }

    public void setForwardMoney(double forwardMoney) {
        this.forwardMoney = forwardMoney;
    }

    @Override
    public String toString() {
        return "Record2{" +
                "number='" + number + '\'' +
                ", registNum=" + registNum +
                ", onlineNum=" + onlineNum +
                ", forwardNum=" + forwardNum +
                ", publishTime=" + publishTime +
                ", registMoney=" + registMoney +
                ", onlineMoney=" + onlineMoney +
                ", forwardMoney=" + forwardMoney +
                '}';
    }
}
