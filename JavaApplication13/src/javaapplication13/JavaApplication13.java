/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication13;

/**
 *
 * @author joser
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JavaApplication13 extends JFrame implements ActionListener {
    private JButton[] unitsButtons;
    private JButton[] tensButtons;
    private JButton[] hundredsButtons;
    private JLabel resultLabel;

    public JavaApplication13() {
        setTitle("Ábaco Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajustar el tamaño de la ventana
        int ventanaAncho = 1000;
        int ventanaAlto = 400;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - ventanaAncho) / 2, (screenSize.height - ventanaAlto) / 2);
        setSize(ventanaAncho, ventanaAlto);

        setLayout(new BorderLayout());

        JPanel abacusPanel = new JPanel(new GridLayout(3, 11));
        unitsButtons = new JButton[10];
        tensButtons = new JButton[10];
        hundredsButtons = new JButton[10];

        for (int i = 9; i >= 0; i--) {
            unitsButtons[i] = createBeadButton(i + 1);
            abacusPanel.add(unitsButtons[i]);
        }
        JLabel unitsLabel = new JLabel("Unidades");
        unitsLabel.setFont(unitsLabel.getFont().deriveFont(20f));
        abacusPanel.add(unitsLabel, 0);

        for (int i = 9; i >= 0; i--) {
            tensButtons[i] = createBeadButton(i + 1);
            abacusPanel.add(tensButtons[i]);
        }
        JLabel tensLabel = new JLabel("Decenas");
        tensLabel.setFont(tensLabel.getFont().deriveFont(20f));
        abacusPanel.add(tensLabel, 11);

        for (int i = 9; i >= 0; i--) {
            hundredsButtons[i] = createBeadButton(i + 1);
            abacusPanel.add(hundredsButtons[i]);
        }
        JLabel hundredsLabel = new JLabel("Centenas");
        hundredsLabel.setFont(hundredsLabel.getFont().deriveFont(20f));
        abacusPanel.add(hundredsLabel, 22);

        add(abacusPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton sumButton = new JButton("+");
        JButton subtractButton = new JButton("-");
        JButton multiplyButton = new JButton("x");
        JButton clearButton = new JButton("Limpiar");

        // Modificar el tamaño de los botones
        sumButton.setPreferredSize(new Dimension(100, 50));
        subtractButton.setPreferredSize(new Dimension(100, 50));
        multiplyButton.setPreferredSize(new Dimension(100, 50));
        clearButton.setPreferredSize(new Dimension(100, 50));

        // Modificar el tamaño del texto en los botones
        Font buttonFont = new Font(sumButton.getFont().getName(), Font.PLAIN, 30);
        sumButton.setFont(buttonFont);
        subtractButton.setFont(buttonFont);
        multiplyButton.setFont(buttonFont);
        clearButton.setFont(new Font(clearButton.getFont().getName(), Font.PLAIN, 20));

        sumButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        clearButton.addActionListener(this);

        buttonPanel.add(sumButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(clearButton);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultLabel = new JLabel("Resultado: ");
        resultLabel.setFont(new Font(resultLabel.getFont().getName(), Font.PLAIN, 30));

        resultPanel.add(resultLabel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(resultPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createBeadButton(int value) {
        JButton button = new JButton(String.valueOf(value));
        button.addActionListener(this);

        // Quitar el color de fondo del botón
        button.setBackground(null);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        if (sourceButton.getText().equals("+")) {
            int sum = sumar();
            resultLabel.setText("Resultado: " + sum);
        } else if (sourceButton.getText().equals("-")) {
            int result = restar();
            resultLabel.setText("Resultado: " + result);
        } else if (sourceButton.getText().equals("x")) {
            int multiplication = multiplicar();
            resultLabel.setText("Resultado: " + multiplication);
        } else if (sourceButton.getText().equals("Limpiar")) {
            limpiar();
        } else {
            sourceButton.setSelected(!sourceButton.isSelected());
            sourceButton.setEnabled(false); // Deshabilitar el botón seleccionado
        }
    }

    private int sumar() {
        int sum = 0;
        for (int i = 9; i >= 0; i--) {
            if (unitsButtons[i].isSelected()) {
                sum += (i + 1);
            }
            if (tensButtons[i].isSelected()) {
                sum += (i + 1) * 10;
            }
            if (hundredsButtons[i].isSelected()) {
                sum += (i + 1) * 100;
            }
        }
        return sum;
    }

    private int restar() {
        int result = 0;
        boolean isFirst = true;
        for (int i = 0; i < 10; i++) {
            int unitsValue = i + 1;
            int tensValue = (i + 1) * 10;
            int hundredsValue = (i + 1) * 100;

            if (unitsButtons[i].isSelected()) {
                if (isFirst) {
                    result = unitsValue;
                    isFirst = false;
                } else {
                    result -= unitsValue;
                }
            }
            if (tensButtons[i].isSelected()) {
                if (isFirst) {
                    result = tensValue;
                    isFirst = false;
                } else {
                    result -= tensValue;
                }
            }
            if (hundredsButtons[i].isSelected()) {
                if (isFirst) {
                    result = hundredsValue;
                    isFirst = false;
                } else {
                    result -= hundredsValue;
                }
            }
        }
        return result;
    }

    private int multiplicar() {
        int multiplicacion = 1;

        for (int i = 0; i < 10; i++) {
            if (unitsButtons[i].isSelected()) {
                multiplicacion *= (i + 1);
            }
            if (tensButtons[i].isSelected()) {
                multiplicacion *= (i + 1) * 10;
            }
            if (hundredsButtons[i].isSelected()) {
                multiplicacion *= (i + 1) * 100;
            }
        }

        return multiplicacion;
    }

    private void limpiar() {
        for (JButton button : unitsButtons) {
            button.setSelected(false);
            button.setEnabled(true); // Habilitar todos los botones de unidades
        }
        for (JButton button : tensButtons) {
            button.setSelected(false);
            button.setEnabled(true); // Habilitar todos los botones de decenas
        }
        for (JButton button : hundredsButtons) {
            button.setSelected(false);
            button.setEnabled(true); // Habilitar todos los botones de centenas
        }
        resultLabel.setText("Resultado: ");
    }

    public static void main(String[] args) {
        new JavaApplication13();
    }
}


