import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightBackwardCommand extends AbstractAction {

    private Light light;

    public LightBackwardCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            light.moveLightBackward();
    }
}
