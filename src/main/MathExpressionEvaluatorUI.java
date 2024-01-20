package main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MathExpressionEvaluatorUI {
    private JFrame frame;
    private JTextField expressionField;
    private JLabel resultLabel;
    private JPanel graphPanel;

    private Lexer lexer;
    private Parser parser;
    private Evaluator evaluator;

    private String expression;

    // Konstruktor für die Initialisierung der Benutzeroberfläche
    public MathExpressionEvaluatorUI() {
        // Initialisiere das Hauptfenster
        frame = new JFrame("Math Expression Evaluator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(null);

        // Initialisiere die GUI-Komponenten
        expressionField = new JTextField();
        expressionField.setBounds(50, 30, 250, 30);
        expressionField.setEditable(false);

        resultLabel = new JLabel("Result:");
        resultLabel.setBounds(50, 80, 300, 30);

        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCoordinateSystem(g);
                if (expression != null && !expression.isEmpty()) {
                    drawExpression(g, expression);
                }
            }
        };
        graphPanel.setBounds(50, 120, 1100, 600);

        // Füge GUI-Komponenten zum Hauptfenster hinzu
        frame.add(expressionField);
        frame.add(resultLabel);
        frame.add(graphPanel);

        // Initialisiere Lexer, Parser und Evaluator
        lexer = new Lexer();
        parser = new Parser();
        evaluator = new Evaluator();
    }

    // Vorbedingung: Der Ausdruck sollte nicht null sein
    // Methode zum Setzen des mathematischen Ausdrucks in das Eingabefeld
    public void setExpression(String expression) {
        assert expression != null : "Expression should not be null";
        this.expression = expression;
        expressionField.setText(expression);
    }

    // Vorbedingung: Der Evaluator sollte nicht null sein
    // Methode zum Setzen des Evaluator
    public void setEvaluator(Evaluator evaluator) {
        assert evaluator != null : "Evaluator should not be null";
        this.evaluator = evaluator;
    }

    // Methode zum Anzeigen des Hauptfensters
    public void show() {
        frame.setVisible(true);
    }

    // Methode zum Zeichnen des Koordinatensystems im Graphen
    private void drawCoordinateSystem(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, graphPanel.getWidth(), graphPanel.getHeight());

        g.setColor(Color.BLACK);
        int scaleX = graphPanel.getWidth() / 40;
        int scaleY = graphPanel.getHeight() / 40;

        int centerX = graphPanel.getWidth() / 2;
        int centerY = graphPanel.getHeight() / 2;

        // Draw x-axis
        g.drawLine(0, centerY, graphPanel.getWidth(), centerY);
        for (int i = -20; i <= 20; i++) {
            int x = centerX + i * scaleX;
            g.drawLine(x, centerY - 3, x, centerY + 3);
            g.drawString(Integer.toString(i), x - 5, centerY + 15);
        }

        // Draw y-axis
        g.drawLine(centerX, 0, centerX, graphPanel.getHeight());
        for (int i = -20; i <= 20; i++) {
            int y = centerY - i * scaleY;
            g.drawLine(centerX - 3, y, centerX + 3, y);
            g.drawString(Integer.toString(i), centerX + 5, y + 5);
        }
    }

    // Methode zum Zeichnen des Funktionsgraphen
    private void drawExpression(Graphics g, String expression) {
        List<Token> tokens = lexer.lex(expression);
        ASTNode ast = parser.parse(tokens);

        int scaleX = graphPanel.getWidth() / 40; // Änderung der Skalierung für x-Achse
        int scaleY = graphPanel.getHeight() / 40; // Änderung der Skalierung für y-Achse

        int centerX = graphPanel.getWidth() / 2;
        int centerY = graphPanel.getHeight() / 2;

        double prevX = -20;
        double prevY = evaluator.evaluate(ast, -20);

        for (double x = -19.99; x <= 20; x += 0.01) { // Änderung des Startwerts für x
            double y = evaluator.evaluate(ast, x);

            int pixelX = centerX + (int) (x * scaleX);
            int pixelY = centerY - (int) (y * scaleY);
            int prevPixelX = centerX + (int) (prevX * scaleX);
            int prevPixelY = centerY - (int) (prevY * scaleY);

            g.drawLine(prevPixelX, prevPixelY, pixelX, pixelY);
            prevX = x;
            prevY = y;
        }
    }

    // Vorbedingung: Der Ausdruck und der Evaluator sollten nicht null sein
    // Methode zum Öffnen eines neuen Fensters mit demselben Ausdruck und Evaluator
    public void openNewFrame() {
        frame.dispose();
        MathExpressionEvaluatorUI newUI = new MathExpressionEvaluatorUI();
        newUI.setExpression(expression);
        newUI.setEvaluator(evaluator);
        newUI.show();
    }
}
