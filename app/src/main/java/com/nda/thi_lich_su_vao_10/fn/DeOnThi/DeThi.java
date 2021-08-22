package com.nda.thi_lich_su_vao_10.fn.DeOnThi;

public class DeThi {
    private String topicNumber;
    private String topicTItle;
    private String numQues;

    public DeThi(String topicNumber, String topicTItle, String numQues) {
        this.topicNumber = topicNumber;
        this.topicTItle = topicTItle;
        this.numQues = numQues;
    }

    public String getNumQues() {
        return numQues;
    }

    public void setNumQues(String numQues) {
        this.numQues = numQues;
    }

    public String getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(String topicNumber) {
        this.topicNumber = topicNumber;
    }

    public String getTopicTItle() {
        return topicTItle;
    }

    public void setTopicTItle(String topicTItle) {
        this.topicTItle = topicTItle;
    }
}
