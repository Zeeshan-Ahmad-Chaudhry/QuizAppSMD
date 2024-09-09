package com.example.quizapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView, scoreTextView, timerTextView,endTextView;
    private RadioGroup answerRadioGroup;
    private Button prevButton, nextButton, showAnswerButton, endExamButton;

    private String[] questions = {
            "What is the capital of France?",
            "Which planet is known as the Red Planet?",
            "What is the largest mammal in the world?",
            "Who painted the Mona Lisa?",
            "What is the chemical symbol for gold?",
            "Which ocean is the largest in the world?",
            "How many continents are there?",
            "What is the tallest mountain in the world?",
            "Who is the author of 'Harry Potter' series?",
            "What is the smallest prime number?",
            "Which element is necessary for the production of nuclear energy?",
            "What is the square root of 64?",
            "Which language is the most spoken in the world?",
            "What gas do plants absorb during photosynthesis?",
            "Which animal is known as the 'King of the Jungle'?",
            "What is the name of the highest peak in Pakistan?",
            "In which year did the first man land on the moon?",
            "Who invented the telephone?",
            "What is the most abundant gas in the Earth's atmosphere?",
            "Who is known as the founder of Pakistan?",
            "How many bones are there in the adult human body?",
            "Which metal is used in making coins and jewelry and has the chemical symbol 'Ag'?",
            "Who wrote 'Romeo and Juliet'?",
            "What is the hardest natural substance on Earth?",
            "How many players are there in a football (soccer) team?",
            "What is the boiling point of water in Celsius?",
            "Which planet is closest to the Sun?",
            "What is the largest desert in Pakistan?",
            "Which vitamin is produced when a person is exposed to sunlight?",
            "Which organ in the human body is responsible for pumping blood?"
    };

    private String[][] options = {
            {"London", "Berlin", "Paris", "Madrid"},
            {"Venus", "Mars", "Jupiter", "Saturn"},
            {"Elephant", "Blue Whale", "Giraffe", "Hippopotamus"},
            {"Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Michelangelo"},
            {"Au", "Ag", "Fe", "Cu"},
            {"Atlantic", "Pacific", "Indian", "Arctic"},
            {"5", "6", "7", "8"},
            {"Mount Everest", "K2", "Kangchenjunga", "Makalu"},
            {"J.R.R. Tolkien", "J.K. Rowling", "George R.R. Martin", "C.S. Lewis"},
            {"0", "1", "2", "3"},
            {"Uranium", "Plutonium", "Hydrogen", "Helium"},
            {"6", "7", "8", "9"},
            {"English", "Mandarin", "Spanish", "Hindi"},
            {"Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"},
            {"Tiger", "Elephant", "Lion", "Cheetah"},
            {"Nanga Parbat", "K2", "Rakaposhi", "Broad Peak"},
            {"1965", "1969", "1972", "1959"},
            {"Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "Isaac Newton"},
            {"Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"},
            {"Allama Iqbal", "Quaid-e-Azam", "Liaquat Ali Khan", "Sir Syed Ahmed Khan"},
            {"198", "206", "212", "220"},
            {"Gold", "Silver", "Copper", "Platinum"},
            {"William Shakespeare", "Charles Dickens", "Mark Twain", "Homer"},
            {"Gold", "Iron", "Diamond", "Granite"},
            {"9", "10", "11", "12"},
            {"90°C", "100°C", "110°C", "120°C"},
            {"Mercury", "Venus", "Earth", "Mars"},
            {"Thar", "Cholistan", "Kharan", "Makran"},
            {"Vitamin A", "Vitamin B", "Vitamin C", "Vitamin D"},
            {"Lungs", "Kidneys", "Heart", "Brain"}
    };

    private int[] correctAnswers = {2, 1, 1, 1, 0, 1, 2, 0, 1, 1, 0, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 0, 2, 1, 1, 0, 3, 2, 2};


    private int currentQuestion = 0;
    private int score = 0;
    private boolean[] answeredQuestions;
    private CountDownTimer timer;
    private long timeLeftInMillis = 10 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        showAnswerButton = findViewById(R.id.showAnswerButton);
        endExamButton = findViewById(R.id.endExamButton);
        endTextView = findViewById(R.id.endTextView);

        answeredQuestions = new boolean[questions.length];

        displayQuestion();
        startTimer();

        prevButton.setOnClickListener(v -> showPreviousQuestion());
        nextButton.setOnClickListener(v -> showNextQuestion());
        showAnswerButton.setOnClickListener(v -> showAnswer());
        endExamButton.setOnClickListener(v -> endExam());
    }

    private void displayQuestion() {
        questionTextView.setText(questions[currentQuestion]);
        ((RadioButton)answerRadioGroup.getChildAt(0)).setText(options[currentQuestion][0]);
        ((RadioButton)answerRadioGroup.getChildAt(1)).setText(options[currentQuestion][1]);
        ((RadioButton)answerRadioGroup.getChildAt(2)).setText(options[currentQuestion][2]);
        ((RadioButton)answerRadioGroup.getChildAt(3)).setText(options[currentQuestion][3]);
        answerRadioGroup.clearCheck();

        prevButton.setEnabled(currentQuestion > 0);
        nextButton.setEnabled(currentQuestion < questions.length - 1);
    }

    private void showPreviousQuestion() {
        if (currentQuestion > 0) {
            currentQuestion--;
            displayQuestion();
        }
    }

    private void showNextQuestion() {
        if (currentQuestion < questions.length - 1) {
            checkAnswer();
            currentQuestion++;
            displayQuestion();
        }
    }

    private void checkAnswer() {
        int selectedAnswer = answerRadioGroup.indexOfChild(findViewById(answerRadioGroup.getCheckedRadioButtonId()));
        if (selectedAnswer != -1 && !answeredQuestions[currentQuestion]) {
            answeredQuestions[currentQuestion] = true;
            if (selectedAnswer == correctAnswers[currentQuestion]) {
                score += 5;
            } else {
                score -= 1;
            }
            updateScore();
        }
    }

    private void showAnswer() {
        if (!answeredQuestions[currentQuestion]) {
            ((RadioButton) answerRadioGroup.getChildAt(correctAnswers[currentQuestion])).setChecked(true);
            score -= 1;
            answeredQuestions[currentQuestion] = true;
            updateScore();
        }
    }

    private void updateScore() {
        scoreTextView.setText("Score: " + score);
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                endExam();
            }
        }.start();
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText("Time: " + timeLeftFormatted);
    }

    private void endExam() {
        if (timer != null) {
            timer.cancel();
        }
        double percentage = (double) score / (30*5) * 100;
        String result = String.format("Exam finished!\nTotal Score: %d\nPercentage: %.2f%%", score, percentage);
        endTextView.setText(result);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}