import javax.swing.*;
import java.awt.event.ActionEvent;

public class LightOnOffCommand extends AbstractAction {

    private Light light;

    public LightOnOffCommand(Light light){
        super();
        this.light = light;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(light.getLightStatus() == 0){
            light.setLightStatus(1);
        }
        else{
            light.setLightStatus(0);
        }
        System.out.println(light.getLightStatus());
    }
}
