package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BackwardCommand extends AbstractAction {

    Camera cam;

    public BackwardCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.moveBackward();
    }
}
