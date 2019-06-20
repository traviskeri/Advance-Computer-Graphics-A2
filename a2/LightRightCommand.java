import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightRightCommand extends AbstractAction {

    private Light light;

    public LightRightCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            light.moveLightRight();
    }
}
