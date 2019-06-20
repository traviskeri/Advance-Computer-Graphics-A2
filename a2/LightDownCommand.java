import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightDownCommand extends AbstractAction {

    private Light light;

    public LightDownCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            light.moveLightDown();
    }
}
