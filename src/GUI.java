
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class GUI {
    private static int set = 0;

    private static ArrayList<FlashCard2> flashCards = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flash Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Flashcards");
        addButton.setPreferredSize(new Dimension(300, 200));
        addButton.setBackground(new Color(100, 149, 237));
        addButton.setForeground(Color.BLUE);

        JButton button2 = new JButton("Review Flashcards");
        button2.setPreferredSize(new Dimension(300, 200));
        button2.setBackground(new Color(255, 165, 0));
        button2.setForeground(Color.BLUE);

        JButton button3 = new JButton("See all Flashcards");
        button3.setPreferredSize(new Dimension(300, 200));
        button3.setBackground(new Color(255, 182, 193));
        button3.setForeground(Color.BLUE);

        addButton.addActionListener(e -> addFlashcard());
        button2.addActionListener(e -> reviewFlashcards());
        button3.addActionListener(e -> seeAllFlashcards());

        frame.add(addButton);
        frame.add(button2);
        frame.add(button3);

        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void addFlashcard() {
        JFrame addFlashcardFrame = new JFrame("Add FlashCard");
        addFlashcardFrame.setSize(500, 500); // Increased frame size
        addFlashcardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFlashcardFrame.setLayout(null);

        JLabel questionLabel = new JLabel("Enter Flash Card Question:");
        questionLabel.setBounds(50, 15, 400, 30); // Adjusted to match the frame size
        addFlashcardFrame.add(questionLabel);

        JTextArea questionArea = new JTextArea(10, 30); // Larger text area (10 rows)
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);

        JScrollPane questionScrollPane = new JScrollPane(questionArea);
        questionScrollPane.setBounds(50, 50, 400, 100); // Bigger scroll pane to make the text area appear larger
        addFlashcardFrame.add(questionScrollPane);

        JLabel answerLabel = new JLabel("Enter Flash Card Answer:");
        answerLabel.setBounds(50, 160, 400, 30); // Adjusted position for the larger components
        addFlashcardFrame.add(answerLabel);

        JTextArea answerArea = new JTextArea(10, 30);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);

        JScrollPane answerScrollPane = new JScrollPane(answerArea);
        answerScrollPane.setBounds(50, 200, 400, 100);
        addFlashcardFrame.add(answerScrollPane);

        JButton saveButton = new JButton("Save Flash Card");
        saveButton.setBounds(175, 320, 150, 30); // Adjusted position for the button
        saveButton.addActionListener(e -> {
            String questionText = questionArea.getText().trim();
            String answerText = answerArea.getText().trim();

            if (!questionText.isEmpty() && !answerText.isEmpty()) {
                FlashCard2 flashCard = new FlashCard2(questionText, answerText);
                flashCards.add(flashCard);
                JOptionPane.showMessageDialog(addFlashcardFrame, "Flash Card Saved Successfully!");
                questionArea.setText("");
                answerArea.setText("");
            } else {
                JOptionPane.showMessageDialog(addFlashcardFrame, "Please enter both a question and an answer.");
            }
        });

        addFlashcardFrame.add(saveButton);

        addFlashcardFrame.setLocationRelativeTo(null);
        addFlashcardFrame.setVisible(true);
    }

    public static void reviewFlashcards() {
        if (flashCards.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No flashcards available to review.");
        } else {
            JFrame reviewFlashcardsFrame = new JFrame("Review FlashCard");
            reviewFlashcardsFrame.setSize(400, 400);
            reviewFlashcardsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            reviewFlashcardsFrame.setLayout(null);
            Font mFont = new Font("Helvetica Neue", Font.BOLD, 20);

            // Use an array to hold mutable variables (effectively final workaround) - (part of the newCardButton to chatGPT'd)
            // had to change the way cards we stored and retrieved
            final int[] randomIndex = {new Random().nextInt(flashCards.size())};
            final FlashCard2[] currentFlashCard = {flashCards.get(randomIndex[0])};

            JLabel DisplayLabel1 = new JLabel("Question: ");
            DisplayLabel1.setFont(mFont);
            DisplayLabel1.setBounds(150, 15, 300, 30);

            JTextArea questionDisplayText = new JTextArea(currentFlashCard[0].getQuestion());
            questionDisplayText.setBounds(50, 50, 300, 150);
            questionDisplayText.setWrapStyleWord(true);
            questionDisplayText.setEditable(false);

            // Flip Button
            JButton flipButton = new JButton("Flip card");
            flipButton.setBounds(150, 200, 100, 30);
            flipButton.addActionListener(e -> {
                if (set == 0) {
                    set += 1;
                    DisplayLabel1.setText("Answer: ");
                    questionDisplayText.setText(currentFlashCard[0].getAnswer());
                } else {
                    DisplayLabel1.setText("Question: ");
                    questionDisplayText.setText(currentFlashCard[0].getQuestion());
                    set -= 1;
                }
            });

            // New Card Button
            JButton newCardButton = new JButton("New Card");
            newCardButton.setBounds(150, 250, 100, 30);
            newCardButton.addActionListener(e -> {

                //ChatGPT written (couldn't figure it out :/ )
                int newRandomIndex;
                do {
                    newRandomIndex = new Random().nextInt(flashCards.size()); // gets new card
                } while (newRandomIndex == randomIndex[0]); // Ensure it's a different card
                randomIndex[0] = newRandomIndex;
                currentFlashCard[0] = flashCards.get(randomIndex[0]);
                questionDisplayText.setText(currentFlashCard[0].getQuestion());
                set = 0; // Reset flip state
            });

            reviewFlashcardsFrame.add(DisplayLabel1);
            reviewFlashcardsFrame.add(questionDisplayText);
            reviewFlashcardsFrame.add(flipButton);
            reviewFlashcardsFrame.add(newCardButton);
            reviewFlashcardsFrame.setLocationRelativeTo(null);
            reviewFlashcardsFrame.setVisible(true);
        }
    }

    public static void seeAllFlashcards() {
        JFrame seeAllFlashcardsFrame = new JFrame("See All Flashcards");
        seeAllFlashcardsFrame.setSize(600, 600);
        seeAllFlashcardsFrame.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        if (flashCards.isEmpty()) {
            displayArea.setText("No flashcards available.");
        } else {
            for (FlashCard2 flashCard : flashCards) {
                displayArea.append("Q: " + flashCard.getQuestion() + "\n");
                displayArea.append("A: " + flashCard.getAnswer() + "\n\n");
            }
        }

        seeAllFlashcardsFrame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        seeAllFlashcardsFrame.setLocationRelativeTo(null);
        seeAllFlashcardsFrame.setVisible(true);
    }
}

class FlashCard2 {
    private String question;
    private String answer;

    public FlashCard2(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}