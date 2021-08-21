package com.nda.thi_lich_su_vao_10.fn.DetailDe_Another;


import java.io.Serializable;

/**
 *  Để chuyền 1 class đối tượng, cần implement Serializable
 */
public class Question implements Serializable {
    private String topicNumber;
    private String questionNumber;
    private String question;
    private String ansA, ansB, ansC,ansD ;
    private String correctAns;
    private String userAns = "";

    public int choiceId = -1; // used  to check ID of radio;

    public Question(String topicNumber, String questionNumber, String question, String ansA,
                    String ansB, String ansC, String ansD, String correctAns, String userAns) {
        this.topicNumber = topicNumber;
        this.questionNumber = questionNumber;
        this.question = question;
        this.ansA = ansA;
        this.ansB = ansB;
        this.ansC = ansC;
        this.ansD = ansD;
        this.correctAns = correctAns;
        this.userAns = userAns;
    }

    public String getUserAns() {
        return userAns;
    }

    public void setUserAns(String userAns) {
        this.userAns = userAns;
    }

    public String getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(String topicNumber) {
        this.topicNumber = topicNumber;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnsA() {
        return ansA;
    }

    public void setAnsA(String ansA) {
        this.ansA = ansA;
    }

    public String getAnsB() {
        return ansB;
    }

    public void setAnsB(String ansB) {
        this.ansB = ansB;
    }

    public String getAnsC() {
        return ansC;
    }

    public void setAnsC(String ansC) {
        this.ansC = ansC;
    }

    public String getAnsD() {
        return ansD;
    }

    public void setAnsD(String ansD) {
        this.ansD = ansD;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }
}
