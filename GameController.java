package controller;

import model.Player;
import model.Question;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class GameController {

    // يخزن بيانات اللاعب الحالي
    private Player currentPlayer;

    // Array تحتوي على جميع الأسئلة
    private ArrayList<Question> questions;

    // رقم السؤال الحالي
    private int currentQuestion;

    // نوع القصة المختارة
    private int storyType;

    // الكونستركتر يبدأ اللعبة ويحمل الأسئلة
    public GameController(String playerName, int storyType) {

        currentPlayer = new Player(playerName);
        questions = new ArrayList<>();
        currentQuestion = 0;

        this.storyType = storyType;

        // تحميل الأسئلة حسب نوع القصة
        loadQuestions();

    }

    // دالة لإضافة الأسئلة
    private void loadQuestions() {

        // أسئلة المستوى الأولى
        if (storyType == 1) {

            Question q1 = new Question(

                    "A building is collapsing nearby. What will you do?",
                    "Help trapped people",
                    "Run away alone"

            );

            Question q2 = new Question(

                    "You find a scared child in the street.",
                    "Protect the child",
                    "Ignore and keep moving"

            );

            Question q3 = new Question(

                    "Your friend is injured during the chaos.",
                    "Carry your friend",
                    "Leave them behind"

            );

            Question q4 = new Question(

                    "Food supplies are running out.",
                    "Share with others",
                    "Keep everything for yourself"

            );

            questions.add(q1);

            questions.add(q2);

            questions.add(q3);

            questions.add(q4);

        }

        // أسئلة المستوى الثانية

        else {

            Question q1 = new Question(

                    "A fire spreads through the city.",

                    "Help people escape",

                    "Save yourself only"

            );

            Question q2 = new Question(

                    "You hear someone calling for help.",

                    "Go help them",

                    "Stay hidden"

            );

            Question q3 = new Question(

                    "A stranger offers you limited food.",

                    "Share fairly",

                    "Take everything"

            );

            Question q4 = new Question(

                    "The city is collapsing around you.",
                    "Stay calm and think",
                    "Panic and run"

            );

            questions.add(q1);

            questions.add(q2);

            questions.add(q3);

            questions.add(q4);

        }

    }

    // ترجع السؤال الحالي

    public Question getCurrentQuestion() {

        return questions.get(currentQuestion);

    }

    // تحديث نقاط اللاعب حسب اختياره

    public void nextQuestion(int userChoice) {

        switch (currentQuestion) {

            case 0:

                if (userChoice == 1)

                    currentPlayer.addEmpathy(2);

                else

                    currentPlayer.addAnger(2);

                break;

            case 1:

                if (userChoice == 1)

                    currentPlayer.addEmpathy(2);

                else

                    currentPlayer.addAnger(1);

                break;

            case 2:

                if (userChoice == 1)

                    currentPlayer.addEmpathy(1);

                else

                    currentPlayer.addAnger(2);

                break;

            case 3:

                if (userChoice == 1)

                    currentPlayer.addEmpathy(2);

                else

                    currentPlayer.addAnger(2);

                break;

        }

        // الانتقال للسؤال التالي
        currentQuestion++;

    }

    // يتحقق إذا باقي أسئلة

    public boolean hasMoreQuestions() {

        return currentQuestion < questions.size();

    }

    // ترجع النتيجة النهائية لي اللاعب

    public String finalResult() {

        return currentPlayer.getPersonality();

    }

    // ترجع رقم السؤال الحالي
    public int getCurrentIndex() {

        return currentQuestion;

    }

    // حفظ النتيجة داخل ملف نصي
    public void saveResult() {

        try {

            FileWriter writer = new FileWriter("results.txt", true);

            writer.write(currentPlayer.getPlayerName()

                    + " - " + finalResult() + "\n");

            writer.close();

        }

        // في حال صار خطأ أثناء الحفظ
        catch (IOException e) {

            System.out.println("Error saving file");

        }

    }

}