package cn.tju.doctor.daomain;

public class Eachfunding {
    private String number;
    private String creatuser;
    private String workuser;
    private String uuid;
    private int test;
    private int ifWork;
    private double mount;
    private double applymount;
    private String testtime;
    private double percent;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreatuser() {
        return creatuser;
    }

    public void setCreatuser(String creatuser) {
        this.creatuser = creatuser;
    }

    public String getWorkuser() {
        return workuser;
    }

    public void setWorkuser(String workuser) {
        this.workuser = workuser;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public int getIfWork() {
        return ifWork;
    }

    public void setIfWork(int ifWork) {
        this.ifWork = ifWork;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public double getApplymount() {
        return applymount;
    }

    public void setApplymount(double applymount) {
        this.applymount = applymount;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Eachfunding{" +
                "number='" + number + '\'' +
                ", creatuser='" + creatuser + '\'' +
                ", workuser='" + workuser + '\'' +
                ", uuid='" + uuid + '\'' +
                ", test=" + test +
                ", ifWork=" + ifWork +
                ", mount=" + mount +
                ", applymount=" + applymount +
                ", testtime='" + testtime + '\'' +
                ", percent=" + percent +
                '}';
    }
}
