import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightLeftCommand extends AbstractAction {

    private Light light;

    public LightLeftCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            light.moveLightLeft();
    }
}
