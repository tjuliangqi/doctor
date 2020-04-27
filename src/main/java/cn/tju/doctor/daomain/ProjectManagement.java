package cn.tju.doctor.daomain;

public class ProjectManagement {
    private String uuid;
    private double mount;
    private double money;
    private String name;
    private String creatuser;
    private String company;
    private String dataURL;
    private int ifWork;
    private int test;
    private String creattime;
    private String testtime;
    private String worktime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatuser() {
        return creatuser;
    }

    public void setCreatuser(String creatuser) {
        this.creatuser = creatuser;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    public int getIfWork() {
        return ifWork;
    }

    public void setIfWork(int ifWork) {
        this.ifWork = ifWork;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    @Override
    public String toString() {
        return "ProjectManagement{" +
                "uuid='" + uuid + '\'' +
                ", mount=" + mount +
                ", money=" + money +
                ", name='" + name + '\'' +
                ", creatuser='" + creatuser + '\'' +
                ", company='" + company + '\'' +
                ", dataURL='" + dataURL + '\'' +
                ", ifWork=" + ifWork +
                ", test=" + test +
                ", creattime='" + creattime + '\'' +
                ", testtime='" + testtime + '\'' +
                ", worktime='" + worktime + '\'' +
                '}';
    }

}
