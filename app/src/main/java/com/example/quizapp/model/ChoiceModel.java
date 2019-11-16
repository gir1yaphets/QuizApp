package com.example.quizapp.model;

public class ChoiceModel {
    private String choice;
    private boolean selected;

    private boolean correct;
    private String result;

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
