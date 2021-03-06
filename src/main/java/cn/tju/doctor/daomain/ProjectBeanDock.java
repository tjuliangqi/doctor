package cn.tju.doctor.daomain;

public class ProjectBeanDock {

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

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
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
        return endTime;
    }

    public void setEndtime(String endtime) {
        this.endTime = endtime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "ProjectBeanDock{" +
                "uuid='" + uuid + '\'' +
                ", projectID='" + projectID + '\'' +
                ", mount='" + mount + '\'' +
                ", projectManager='" + projectManager + '\'' +
                ", company='" + company + '\'' +
                ", process='" + process + '\'' +
                ", dataURL='" + dataURL + '\'' +
                ", companyAccount='" + companyAccount + '\'' +
                ", moneyManager='" + moneyManager + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", introduce='" + introduce + '\'' +
                ", accounting='" + accounting + '\'' +
                ", ifWork='" + ifWork + '\'' +
                '}';
    }

    private String uuid;
    private String projectID;
    private String name;
    private double mount;
    private String projectManager;
    private String company;
    private String process;
    private String dataURL;
    private String companyAccount;
    private String moneyManager;
    private String beginTime;
    private String endTime;
    private String introduce;
    private int ifWork;
    private int label;
    private String accounting;
    private String createuser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
    public int getIfWork() {
        return ifWork;
    }

    public void setIfWork(int ifWork) {
        this.ifWork = ifWork;
    }


    public String getAccounting() {
        return accounting;
    }

    public void setAccounting(String accounting) {
        this.accounting = accounting;
    }




}
