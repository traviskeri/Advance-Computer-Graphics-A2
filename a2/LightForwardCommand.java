import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightForwardCommand extends AbstractAction {

    private Light light;

    public LightForwardCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            light.moveLightForward();

    }
}
