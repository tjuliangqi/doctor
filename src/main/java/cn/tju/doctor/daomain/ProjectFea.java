package cn.tju.doctor.daomain;

public class ProjectFea {
    private String number;
    private String projectID;
    private float mount;
    private float rest;
    private float in;
    private float out;
    private int test;
    private String testtime;
    private String testuser;
    private int ifWork;
    private String workTime;
    private String workUser;
    private String record;
    private String source;
    private String sourceAccount;
    private String go;
    private String goaccount;
    private String applyID;
    private String applyTime;

    public ProjectFea(String number, String projectID, float mount, float rest, float in, float out, int test, String testtime, String testuser, int ifWork, String workTime, String workUser, String record, String source, String sourceAccount, String go, String goaccount, String applyID, String applyTime) {
        this.number = number;
        this.projectID = projectID;
        this.mount = mount;
        this.rest = rest;
        this.in = in;
        this.out = out;
        this.test = test;
        this.testtime = testtime;
        this.testuser = testuser;
        this.ifWork = ifWork;
        this.workTime = workTime;
        this.workUser = workUser;
        this.record = record;
        this.source = source;
        this.sourceAccount = sourceAccount;
        this.go = go;
        this.goaccount = goaccount;
        this.applyID = applyID;
        this.applyTime = applyTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public float getMount() {
        return mount;
    }

    public void setMount(float mount) {
        this.mount = mount;
    }

    public float getRest() {
        return rest;
    }

    public void setRest(float rest) {
        this.rest = rest;
    }

    public float getIn() {
        return in;
    }

    public void setIn(float in) {
        this.in = in;
    }

    public float getOut() {
        return out;
    }

    public void setOut(float out) {
        this.out = out;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getTestuser() {
        return testuser;
    }

    public void setTestuser(String testuser) {
        this.testuser = testuser;
    }

    public int getIfWork() {
        return ifWork;
    }

    public void setIfWork(int ifWork) {
        this.ifWork = ifWork;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkUser() {
        return workUser;
    }

    public void setWorkUser(String workUser) {
        this.workUser = workUser;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public String getGoaccount() {
        return goaccount;
    }

    public void setGoaccount(String goaccount) {
        this.goaccount = goaccount;
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
