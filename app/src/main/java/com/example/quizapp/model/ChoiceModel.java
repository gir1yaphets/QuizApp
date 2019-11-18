package com.example.quizapp.model;

import java.util.ArrayList;
import java.util.List;

public class ChoiceModel {
    private String questionContent;
    private boolean isQuestion;

    private String choice;

    private boolean isCheckedChoice;

    private boolean selected;
    private boolean correct;

    private String result;


    public boolean isCheckedChoice() {
        return isCheckedChoice;
    }

    public void setCheckedChoice(boolean checkedChoice) {
        isCheckedChoice = checkedChoice;
    }

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

    public static List<ChoiceModel> createMockData() {
        List<ChoiceModel> selectionList = new ArrayList<>();

        //question1
        ChoiceModel questionModel1 = new ChoiceModel();
        questionModel1.setQuestion(true);
        questionModel1.setQuestionContent("Which language is the best one in the world?");

        ChoiceModel choiceModel1 = new ChoiceModel();
        choiceModel1.setChoice("A. Java");
        choiceModel1.setCorrect(false);

        ChoiceModel choiceModel2 = new ChoiceModel();
        choiceModel1.setCheckedChoice(false);
        choiceModel2.setChoice("B. Python");
        choiceModel2.setCorrect(false);

        ChoiceModel choiceModel3 = new ChoiceModel();
        choiceModel3.setChoice("C. Swift");
        choiceModel3.setCorrect(true);

        ChoiceModel choiceModel4 = new ChoiceModel();
        choiceModel4.setChoice("D. Kotlin");
        choiceModel4.setCorrect(false);

        //question2
        ChoiceModel questionModel2 = new ChoiceModel();
        questionModel2.setQuestion(true);
        questionModel2.setQuestionContent("What is the mascot of UCI?");

        ChoiceModel checkedModel1 = new ChoiceModel();
        checkedModel1.setChoice("A. Anteater");
        checkedModel1.setCheckedChoice(true);
        checkedModel1.setCorrect(true);

        ChoiceModel checkedModel2 = new ChoiceModel();
        checkedModel2.setCheckedChoice(true);
        checkedModel2.setChoice("B. Python");
        checkedModel2.setCorrect(false);

        ChoiceModel checkedModel3 = new ChoiceModel();
        checkedModel3.setChoice("C. Monkey");
        checkedModel3.setCheckedChoice(true);
        checkedModel3.setCorrect(false);

        ChoiceModel checkedModel4 = new ChoiceModel();
        checkedModel4.setCheckedChoice(true);
        checkedModel4.setChoice("D. Fish");
        checkedModel4.setCorrect(false);

        selectionList.add(questionModel1);
        selectionList.add(choiceModel1);
        selectionList.add(choiceModel2);
        selectionList.add(choiceModel3);
        selectionList.add(choiceModel4);

        selectionList.add(questionModel2);
        selectionList.add(checkedModel1);
        selectionList.add(checkedModel2);
        selectionList.add(checkedModel3);
        selectionList.add(checkedModel4);

        return selectionList;
    }
}
