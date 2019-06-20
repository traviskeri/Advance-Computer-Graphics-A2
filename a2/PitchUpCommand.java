package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PitchUpCommand extends AbstractAction{

    Camera cam;

    public PitchUpCommand(Camera cam){
        super();
        this.cam = cam;
    }

    public void actionPerformed(ActionEvent e){
        cam.pitchUp();
    }
}
