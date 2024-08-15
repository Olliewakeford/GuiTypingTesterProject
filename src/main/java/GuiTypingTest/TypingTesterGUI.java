package GuiTypingTest;

import GuiTypingTest.InputProviders.*;
import GuiTypingTest.OutputProviders.*;
import GuiTypingTest.Testers.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * TypingTesterGUI is the main class for the Typing Tester application.
 * It sets up the GUI and handles the interaction between the user and the typing test.
 */
public class TypingTesterGUI {
    private final JFrame frame;
    private JTextArea displayArea;
    private JButton restartButton;
    private JLabel timeLabel;
    private TypingTester typingTest;
    private Result result;
    private long timeLimit;
    private int textOption;
    private int formatOption;
    private Timer timer;

    // New fields for storing user data
    private final ArrayList<Integer> speedHistory = new ArrayList<>();
    private final ArrayList<Integer> accuracyHistory = new ArrayList<>();

    /**
     * Constructor for TypingTesterGUI.
     * Initializes the GUI and sets up the test options.
     */
    public TypingTesterGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.getLogger(NonsenseSentenceProvider.class.getName()).log(Level.SEVERE, null, e);
        }
        frame = new JFrame("Typing Tester");
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setupTestOptions();
    }

    /**
     * Sets up the test options for the user to select.
     * This includes time limit, type of text, and format options.
     */
    private void setupTestOptions() {
        frame.getContentPane().removeAll(); // Clear the frame

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(new Color(240, 240, 240));
        optionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Time limit options
        String[] timeOptions = {"30 seconds", "1 minute", "3 minutes"};
        JComboBox<String> timeComboBox = new JComboBox<>(timeOptions);
        styleComboBox(timeComboBox);
        optionsPanel.add(createLabeledComponent("Choose the time limit:", timeComboBox));

        // Text type options
        String[] textOptions = {"Normal Sentences", "Random Sentences/Words", "Nonsense Sentences/Words"};
        JComboBox<String> textComboBox = new JComboBox<>(textOptions);
        styleComboBox(textComboBox);
        optionsPanel.add(createLabeledComponent("Choose the type of text:", textComboBox));

        // Format options
        String[] formatOptions = {"Print 1 word at a time", "Print 1 sentence at a time"};
        JComboBox<String> formatComboBox = new JComboBox<>(formatOptions);
        styleComboBox(formatComboBox);
        optionsPanel.add(createLabeledComponent("Choose the format:", formatComboBox));

        // Disable format options if "Normal Sentences" is selected
        textComboBox.addActionListener(e -> {
            if (textComboBox.getSelectedIndex() == 0) {
                formatComboBox.setSelectedIndex(1);
                formatComboBox.setEnabled(false);
            } else {
                formatComboBox.setEnabled(true);
            }
        });

        // Start button
        JButton startButton = new JButton("Start Test");
        styleButton(startButton, new Color(34, 139, 34), new Color(0, 0, 139)); // Green background, dark blue text
        startButton.addActionListener(e -> {
            timeLimit = convertToMillis(timeComboBox.getSelectedIndex());
            textOption = textComboBox.getSelectedIndex();
            formatOption = formatComboBox.getSelectedIndex();
            initializeTest();
        });
        optionsPanel.add(startButton);

        // Button to show graph
        JButton graphButton = new JButton("Show Progress Graph");
        styleButton(graphButton, new Color(70, 130, 180), Color.BLACK); // Blue background, black text
        graphButton.addActionListener(e -> displayGraph());
        optionsPanel.add(graphButton);

        frame.add(optionsPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Initializes the test by setting up the display area, input field, and other components.
     */
    private void initializeTest() {
        frame.getContentPane().removeAll();

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE);
        displayArea.setForeground(Color.BLACK);
        displayArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel typeHereLabel = new JLabel("Type here:");
        typeHereLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField inputField = new JTextField();
        inputField.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton submitButton = new JButton("Submit");
        styleButton(submitButton, new Color(70, 130, 180), Color.BLACK); // Blue background, white text

        timeLabel = new JLabel("Time remaining: " + timeLimit / 1000 + " seconds", SwingConstants.CENTER);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(new Color(0, 100, 0)); // Dark green background
        timeLabel.setForeground(Color.WHITE); // White text
        timeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GUIInputProvider inputProvider = new GUIInputProvider(inputField, submitButton);
        inputProvider.setDisplayArea(displayArea);

        frame.setLayout(new BorderLayout());
        frame.add(timeLabel, BorderLayout.NORTH);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        frame.add(typeHereLabel, BorderLayout.SOUTH);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.add(submitButton, BorderLayout.EAST);
        frame.revalidate();
        frame.repaint();

        // Initialize the typing test based on user selections
        TextToTypeProvider textToTypeProvider;
        if (textOption == 0) {
            textToTypeProvider = new NormalSentenceProvider();
            typingTest = new TimeLimitTypingTestSentences(textToTypeProvider, inputProvider, timeLimit, this);
        } else if (textOption == 1) {
            if (formatOption == 0) {
                textToTypeProvider = new SingleWordsProvider(0);
                typingTest = new TimeLimitTypingTestWords(textToTypeProvider, inputProvider, timeLimit, this);
            } else {
                textToTypeProvider = new RandomSentenceProvider();
                typingTest = new TimeLimitTypingTestSentences(textToTypeProvider, inputProvider, timeLimit, this);
            }
        } else {
            if (formatOption == 0) {
                textToTypeProvider = new SingleWordsProvider(1);
                typingTest = new TimeLimitTypingTestWords(textToTypeProvider, inputProvider, timeLimit, this);
            } else {
                textToTypeProvider = new NonsenseSentenceProvider();
                typingTest = new TimeLimitTypingTestSentences(textToTypeProvider, inputProvider, timeLimit, this);
            }
        }

        startTest();
    }

    /**
     * Starts the typing test and sets up the timer to update the remaining time.
     */
    private void startTest() {
        displayArea.setText("");

        if (typingTest != null) {
            typingTest.startTest();
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long elapsed = System.currentTimeMillis() - typingTest.getStartTime();
                    long remaining = timeLimit - elapsed;
                    if (remaining <= 0) {
                        timer.cancel();
                        remaining = 0;
                    }
                    final long finalRemaining = remaining;
                    SwingUtilities.invokeLater(() -> timeLabel.setText("Time remaining: " + finalRemaining / 1000 + " seconds"));
                }
            }, 0, 1000);
        } else {
            System.err.println("Error: typingTest is not initialized.");
        }
    }

    /**
     * Completes the typing test and displays the result.
     */
    public void completeTest() {
        if (timer != null) {
            timer.cancel();
        }
        result = typingTest.completeTest();
        if (result != null) {
            // Store the speed and accuracy in the lists
            speedHistory.add(result.calculateSpeed());
            accuracyHistory.add(result.calculateAccuracy());

            SwingUtilities.invokeLater(() -> {
                frame.getContentPane().removeAll();
                JPanel resultPanel = new JPanel();
                resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
                resultPanel.setBackground(new Color(240, 240, 240));
                resultPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
                JLabel resultLabel = new JLabel("<html>" + result.toString().replace("\n", "<br>") + "</html>", SwingConstants.CENTER);
                resultLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                resultPanel.add(resultLabel);

                restartButton = new JButton("Restart");
                styleButton(restartButton, new Color(34, 139, 34), new Color(0, 0, 139)); // Green background, dark blue text
                restartButton.addActionListener(e -> setupTestOptions());
                resultPanel.add(restartButton);

                frame.add(resultPanel, BorderLayout.CENTER);
                frame.add(restartButton, BorderLayout.SOUTH);

                frame.revalidate();
                frame.repaint();
            });
        }
    }

    /**
     * Converts the selected time option to milliseconds.
     *
     * @param option the selected time option index
     * @return the time limit in milliseconds
     */
    private long convertToMillis(int option) {
        return switch (option) {
            case 1 -> 60000;
            case 2 -> 180000;
            default -> 30000;
        };
    }

    /**
     * Creates a labeled component with a label and a component.
     *
     * @param labelText the text for the label
     * @param component the component to be labeled
     * @return a JPanel containing the label and the component
     */
    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Styles a JComboBox with padding.
     *
     * @param comboBox the JComboBox to style
     */
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    /**
     * Styles a JButton with background and text colors, and adds hover effects.
     *
     * @param button          the JButton to style
     * @param backgroundColor the background color
     * @param textColor       the text color
     */
    private void styleButton(JButton button, Color backgroundColor, Color textColor) {
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(backgroundColor),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    /**
     * Displays the progress graph for speed and accuracy.
     */
    private void displayGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < speedHistory.size(); i++) {
            dataset.addValue(speedHistory.get(i), "Speed", "Test " + (i + 1));
            dataset.addValue(accuracyHistory.get(i), "Accuracy", "Test " + (i + 1));
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Typing Test Progress",
                "Test Number",
                "Value",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame graphFrame = new JFrame("Progress Graph");
        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.add(chartPanel);
        graphFrame.pack();
        graphFrame.setVisible(true);
    }

    /**
     * Main method to run the TypingTesterGUI application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TypingTesterGUI::new);
    }

}
