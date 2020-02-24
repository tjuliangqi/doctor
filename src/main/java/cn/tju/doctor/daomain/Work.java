package cn.tju.doctor.daomain;

import java.util.Date;

public class Work {
    private String uuid;
    private String workID;
    private String name;
    private String publishID;
    private String publishName;
    private String publishTime;
    private String acceptID;
    private String acceptName;
    private String acceptTime;
    private String ifRead;
    private String ifFinish;
    private String finishTime;
    private String introduce;
    private String workfile;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishID() {
        return publishID;
    }

    public void setPublishID(String publishID) {
        this.publishID = publishID;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAcceptID() {
        return acceptID;
    }

    public void setAcceptID(String acceptID) {
        this.acceptID = acceptID;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getIfRead() {
        return ifRead;
    }

    public void setIfRead(String ifRead) {
        this.ifRead = ifRead;
    }

    public String getIfFinish() {
        return ifFinish;
    }

    public void setIfFinish(String ifFinish) {
        this.ifFinish = ifFinish;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getWorkfile() {
        return workfile;
    }

    public void setWorkfile(String workfile) {
        this.workfile = workfile;
    }

    @Override
    public String toString() {
        return "Work{" +
                "uuid='" + uuid + '\'' +
                ", workID='" + workID + '\'' +
                ", name='" + name + '\'' +
                ", publishID='" + publishID + '\'' +
                ", publishName='" + publishName + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", acceptID='" + acceptID + '\'' +
                ", acceptName='" + acceptName + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", ifRead='" + ifRead + '\'' +
                ", ifFinish='" + ifFinish + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", introduce='" + introduce + '\'' +
                ", workfile='" + workfile + '\'' +
                '}';
    }
}
