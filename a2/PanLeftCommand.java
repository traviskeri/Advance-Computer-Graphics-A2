package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PanLeftCommand extends AbstractAction {

    Camera cam;

    public PanLeftCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.panLeft();
    }
}
