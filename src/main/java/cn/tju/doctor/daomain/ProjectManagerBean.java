package cn.tju.doctor.daomain;

public class ProjectManagerBean {
    private String uuid;
    private String projectID;
    private Double mount;
    private String projectManager;
    private String company ;
    private String process;
    private String dataURL;
    private String companyAccount;
    private String moneyManager;
    private String beginTime;
    private String endtime;
    private String introduce;

    public ProjectManagerBean(){

    }

    public ProjectManagerBean(String uuid, String projectID, Double mount, String projectManager, String company, String process, String dataURL, String companyAccount, String moneyManager, String beginTime, String endtime, String introduce) {
        this.uuid = uuid;
        this.projectID = projectID;
        this.mount = mount;
        this.projectManager = projectManager;
        this.company = company;
        this.process = process;
        this.dataURL = dataURL;
        this.companyAccount = companyAccount;
        this.moneyManager = moneyManager;
        this.beginTime = beginTime;
        this.endtime = endtime;
        this.introduce = introduce;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    public String getCompanyAccount() {
        return companyAccount;
    }

    public void setCompanyAccount(String companyAccount) {
        this.companyAccount = companyAccount;
    }

    public String getMoneyManager() {
        return moneyManager;
    }

    public void setMoneyManager(String moneyManager) {
        this.moneyManager = moneyManager;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
