package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveUpCommand extends AbstractAction {

    Camera cam;

    public MoveUpCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.moveUp();
    }
}
