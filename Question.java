 package model;

public class Question {

    private String text;

    private String choice1;

    private String choice2;

    public Question(String text, String c1, String c2) {

        this.text = text;

        this.choice1 = c1;

        this.choice2 = c2;

    }

    public String getText() {

        return text;

    }

    public String getChoice1() {

        return choice1;

    }

    public String getChoice2() {

        return choice2;

    }

}