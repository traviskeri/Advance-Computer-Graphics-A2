package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ForwardCommand extends AbstractAction {

    Camera cam;

    public ForwardCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.moveForward();
    }
}
