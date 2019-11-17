package com.example.quizapp.model;

public class ChoiceModel {
    private String questionContent;
    private boolean isQuestion;

    private String choice;

    public boolean isCheckedChoice() {
        return isCheckedChoice;
    }

    public void setCheckedChoice(boolean checkedChoice) {
        isCheckedChoice = checkedChoice;
    }

    private boolean isCheckedChoice;
    private boolean selected;

    private boolean correct;
    private String result;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String question) {
        this.choice = question;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
