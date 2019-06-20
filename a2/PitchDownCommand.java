package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PitchDownCommand extends AbstractAction {

    Camera cam;

    public PitchDownCommand(Camera cam){
        super();
        this.cam = cam;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        cam.pitchDown();
    }
}
