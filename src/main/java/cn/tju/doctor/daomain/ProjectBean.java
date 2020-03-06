package cn.tju.doctor.daomain;

public class ProjectBean {

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getAcceptuser() {
        return acceptuser;
    }

    public void setAcceptuser(String acceptuser) {
        this.acceptuser = acceptuser;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIfWork() {
        return ifWork;
    }

    public void setIfWork(int ifWork) {
        this.ifWork = ifWork;
    }

    public String getProjectaccount() {
        return projectaccount;
    }

    public void setProjectaccount(String projectaccount) {
        this.projectaccount = projectaccount;
    }

    public String getProjectmoney() {
        return projectmoney;
    }

    public void setProjectmoney(String projectmoney) {
        this.projectmoney = projectmoney;
    }

    public String getUserdataURL() {
        return userdataURL;
    }

    public void setUserdataURL(String userdataURL) {
        this.userdataURL = userdataURL;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public String toString() {
        return "ProjectBean{" +
                "projectID='" + projectID + '\'' +
                ", name='" + name + '\'' +
                ", data='" + data + '\'' +
                ", dataURL='" + dataURL + '\'' +
                ", introduce='" + introduce + '\'' +
                ", createuser='" + createuser + '\'' +
                ", acceptuser='" + acceptuser + '\'' +
                ", endTime='" + endTime + '\'' +
                ", ifWork=" + ifWork +
                ", projectaccount='" + projectaccount + '\'' +
                ", projectmoney='" + projectmoney + '\'' +
                ", userdataURL='" + userdataURL + '\'' +
                ", process='" + process + '\'' +
                ", userType='" + userType + '\'' +
                ", company='" + company + '\'' +
                ", actor='" + actor + '\'' +
                '}';
    }

    private String projectID;
    private String name;
    private String data;
    private String dataURL;
    private String introduce;
    private String createuser;
    private String acceptuser;
    private String endTime;
    private int ifWork;
    private String projectaccount;
    private String projectmoney;
    private String userdataURL;
    private String process;
    private String userType;
    private String company;
    private String actor;


}
