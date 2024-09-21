import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGameAWT extends Frame implements ActionListener {
    private Random random = new Random();
    private int numberToGuess;
    private int attemptsLeft = 10;
    private int score = 0;
    private int round = 1;
    private final int maxRounds = 5;


    private Label promptLabel, guessLabel, feedbackLabel, scoreLabel, attemptsLabel;
    private TextField guessField;
    private Button submitButton, resetButton, playAgainButton;

    public NumberGuessingGameAWT() {
        setLayout(new BorderLayout());


        Panel mainPanel = new Panel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));


        promptLabel = new Label("Guess the number between 1 and 100:");
        guessLabel = new Label("Enter your guess:");
        feedbackLabel = new Label("");
        scoreLabel = new Label("Score: " + score);
        attemptsLabel = new Label("Attempts left: " + attemptsLeft);
        guessField = new TextField(15);
        submitButton = new Button("Submit");
        resetButton = new Button("Reset Game");
        playAgainButton = new Button("Play Again");


        mainPanel.add(promptLabel);
        mainPanel.add(new Label()); 
        mainPanel.add(guessLabel);
        mainPanel.add(guessField);
        mainPanel.add(new Label()); 
        mainPanel.add(submitButton);
        mainPanel.add(scoreLabel);
        mainPanel.add(attemptsLabel);
        mainPanel.add(new Label()); 
        mainPanel.add(feedbackLabel);
        mainPanel.add(resetButton);
        mainPanel.add(playAgainButton);


        add(mainPanel, BorderLayout.CENTER);


        submitButton.addActionListener(this);
        resetButton.addActionListener(this);
        playAgainButton.addActionListener(this);

     
        numberToGuess = random.nextInt(100) + 1;


        setTitle("Number Guessing Game");
        setSize(400, 300);
        setVisible(true);
        

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            handleGuess();
        } else if (e.getSource() == resetButton) {
            resetGame();
        } else if (e.getSource() == playAgainButton) {
            playAgain();
        }
    }

    private void handleGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());

            if (guess == numberToGuess) {
                feedbackLabel.setText("Correct! You guessed the number.");
                score++;
                scoreLabel.setText("Score: " + score);
                startNextRound();
            } else if (guess > numberToGuess) {
                feedbackLabel.setText("Too high! Try again.");
            } else {
                feedbackLabel.setText("Too low! Try again.");
            }

            attemptsLeft--;
            attemptsLabel.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft == 0) {
                feedbackLabel.setText("Out of attempts! The number was " + numberToGuess);
                startNextRound();
            }

        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private void startNextRound() {
        round++;
        if (round <= maxRounds) {
            numberToGuess = random.nextInt(150) + 1;
            attemptsLeft = 10;
            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            feedbackLabel.setText("Starting round " + round + ".");
            guessField.setText("");
        } else {
            feedbackLabel.setText("Game over! Final score: " + score);
            disableGame();
        }
    }

    private void resetGame() {
        score = 0;
        round = 1;
        numberToGuess = random.nextInt(100) + 1;
        attemptsLeft = 10;
        scoreLabel.setText("Score: " + score);
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        feedbackLabel.setText("Game reset. Start guessing!");
        guessField.setText("");
        enableGame();
    }

    private void playAgain() {
        resetGame();
    }

    private void disableGame() {
        submitButton.setEnabled(false);
        guessField.setEnabled(false);
    }

    private void enableGame() {
        submitButton.setEnabled(true);
        guessField.setEnabled(true);
    }

    public static void main(String[] args) {
        new NumberGuessingGameAWT();
    }
}
