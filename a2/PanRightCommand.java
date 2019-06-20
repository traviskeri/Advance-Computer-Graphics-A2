package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PanRightCommand extends AbstractAction {

    Camera cam;

    public PanRightCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.panRight();
    }
}
