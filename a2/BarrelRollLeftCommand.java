package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BarrelRollLeftCommand extends AbstractAction {

    Camera cam;

    public BarrelRollLeftCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.rollLeft();
    }
}
