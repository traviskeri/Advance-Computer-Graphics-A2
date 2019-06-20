package a2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ViewAxesCommand extends AbstractAction {

    SolarSystem ss;

    public ViewAxesCommand(SolarSystem ss){
        super();
        this.ss = ss;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        ss.setAxes(!ss.isAxes());
        System.out.println("View Axes");
    }
}
