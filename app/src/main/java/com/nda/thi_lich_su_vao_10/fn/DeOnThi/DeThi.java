package com.nda.thi_lich_su_vao_10.fn.DeOnThi;

public class DeThi {
    private String topicNumber;
    private String topicTItle;

    public DeThi(String topicNumber, String topicTItle) {
        this.topicNumber = topicNumber;
        this.topicTItle = topicTItle;
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
