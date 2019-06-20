import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightUpCommand extends AbstractAction {

    private Light light;

    public LightUpCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        light.moveLightUp();
    }
}
