package com.application.sberhomework13.graphics;

import com.application.sberhomework13.data.ChunkController;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    private ChunkController chunkController;

    public UserInterface(ChunkController chunkController) {
        this.chunkController = chunkController;
        initialize();
    }

    private void initialize() {
        setTitle("JSON Field Viewer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton fetchDataButton = new JButton("Запросить JSON");
        JTextArea displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);

        buttonPanel.add(fetchDataButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        fetchDataButton.addActionListener(e -> fetchDataAndUpdateDisplay(displayArea));

        getContentPane().add(mainPanel);
    }

    private void fetchDataAndUpdateDisplay(JTextArea displayArea) {
        chunkController.getChunks().subscribe(
                json -> {
                    displayArea.append(json + "\n");
                    displayArea.setCaretPosition(displayArea.getDocument().getLength());
                },
                error -> displayArea.append("Ошибка извлечения данных: " + error.getMessage() + "\n"),
                () -> displayArea.append("Извлечение данных завершено\n")
        );
    }
}
