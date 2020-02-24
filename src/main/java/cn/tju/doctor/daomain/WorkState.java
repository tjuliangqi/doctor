package cn.tju.doctor.daomain;

public class WorkState {
    private String state1;
    private String stateValue1;
    private String state2;
    private String stateValue2;
    private String type;
    private String uuid;
    private String workfile;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWorkfile() {
        return workfile;
    }

    public void setWorkfile(String workfile) {
        this.workfile = workfile;
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public String getStateValue1() {
        return stateValue1;
    }

    public void setStateValue1(String stateValue1) {
        this.stateValue1 = stateValue1;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public String getStateValue2() {
        return stateValue2;
    }

    public void setStateValue2(String stateValue2) {
        this.stateValue2 = stateValue2;
    }

    @Override
    public String toString() {
        return "WorkState{" +
                "state1='" + state1 + '\'' +
                ", stateValue1='" + stateValue1 + '\'' +
                ", state2='" + state2 + '\'' +
                ", stateValue2='" + stateValue2 + '\'' +
                ", type='" + type + '\'' +
                ", uuid='" + uuid + '\'' +
                ", workfile='" + workfile + '\'' +
                '}';
    }
}
