package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveDownCommand extends AbstractAction {

    Camera cam;

    public MoveDownCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cam.moveDown();
    }
}
