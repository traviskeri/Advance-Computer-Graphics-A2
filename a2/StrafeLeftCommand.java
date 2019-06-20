package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StrafeLeftCommand extends AbstractAction {

    Camera cam;

    public StrafeLeftCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.moveLeft();
    }
}
