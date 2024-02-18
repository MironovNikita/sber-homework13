package com.application.sberhomework13;

import com.application.sberhomework13.data.ChunkController;
import com.application.sberhomework13.graphics.UserInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GraphicsRunner {
    public static void main(String[] args) {
        ChunkController chunkController = new ChunkController(new ObjectMapper());
        UserInterface ui = new UserInterface(chunkController);
        ui.setVisible(true);
    }
}
