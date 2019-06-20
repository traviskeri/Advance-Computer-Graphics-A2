package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StrafeRightCommand extends AbstractAction {

    Camera cam;

    public StrafeRightCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.moveRight();
    }
}
