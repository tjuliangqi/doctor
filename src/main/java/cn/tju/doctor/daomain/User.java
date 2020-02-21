package cn.tju.doctor.daomain;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String authorID;
    private String area;
    private String region;
    private String unit;
    private String part;
    private String wechatQun;
    private int active;
    private int online;
    private int article;
    private String test;
    private String name;
    private String actureID;
    private int ifDoc;
    private String docID;
    private String phone;
    private String address;
    private String bankID;
    private String money;
    private int vaild;
    private String company;
    private String type;
    private String like;
    private String download;
    private String record;
    private String view;
    private String token;
    private String email;
    private String password;
    private String docIDurl;
    private int modify;
    private int getMoney;
    private String modifyString;
    private String getmoneyrecord;
    private int state;


    public User() {
    }

    public User(String username, String authorID, String area, String region, String unit, String part, String wechatQun, int active, int online, int article, String test, String name, String actureID, int ifDoc, String docID, String phone, String address, String bankID, String money, int vaild, String company, String type, String like, String download, String record, String view, String token, String email, String password, String docIDurl, int modify, int getMoney, String modifyString, String getmoneyrecord, int state) {
        this.username = username;
        this.authorID = authorID;
        this.area = area;
        this.region = region;
        this.unit = unit;
        this.part = part;
        this.wechatQun = wechatQun;
        this.active = active;
        this.online = online;
        this.article = article;
        this.test = test;
        this.name = name;
        this.actureID = actureID;
        this.ifDoc = ifDoc;
        this.docID = docID;
        this.phone = phone;
        this.address = address;
        this.bankID = bankID;
        this.money = money;
        this.vaild = vaild;
        this.company = company;
        this.type = type;
        this.like = like;
        this.download = download;
        this.record = record;
        this.view = view;
        this.token = token;
        this.email = email;
        this.password = password;
        this.docIDurl = docIDurl;
        this.modify = modify;
        this.getMoney = getMoney;
        this.modifyString = modifyString;
        this.getmoneyrecord = getmoneyrecord;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getWechatQun() {
        return wechatQun;
    }

    public void setWechatQun(String wechatQun) {
        this.wechatQun = wechatQun;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActureID() {
        return actureID;
    }

    public void setActureID(String actureID) {
        this.actureID = actureID;
    }

    public int getIfDoc() {
        return ifDoc;
    }

    public void setIfDoc(int ifDoc) {
        this.ifDoc = ifDoc;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getVaild() {
        return vaild;
    }

    public void setVaild(int vaild) {
        this.vaild = vaild;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocIDurl() {
        return docIDurl;
    }

    public void setDocIDurl(String docIDurl) {
        this.docIDurl = docIDurl;
    }

    public int getModify() {
        return modify;
    }

    public void setModify(int modify) {
        this.modify = modify;
    }

    public int getGetMoney() {
        return getMoney;
    }

    public void setGetMoney(int getMoney) {
        this.getMoney = getMoney;
    }

    public String getModifyString() {
        return modifyString;
    }

    public void setModifyString(String modifyString) {
        this.modifyString = modifyString;
    }

    public String getGetmoneyrecord() {
        return getmoneyrecord;
    }

    public void setGetmoneyrecord(String getmoneyrecord) {
        this.getmoneyrecord = getmoneyrecord;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
