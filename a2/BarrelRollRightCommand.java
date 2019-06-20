package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BarrelRollRightCommand extends AbstractAction {

    Camera cam;

    public BarrelRollRightCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.rollRight();
}
}
