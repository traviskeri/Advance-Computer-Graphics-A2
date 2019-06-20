import graphicslib3D.Point3D;
import graphicslib3D.light.PositionalLight;

import java.awt.*;

public class Light {

    private PositionalLight currentLight;
    private Point3D lightLoc;
    private float[] globalAmbient = new float[] {0.7f, 0.7f, 0.7f, 1.0f};
    private float amt = 0.25f;
    private int lightStatus = 1;

    public Light(float x, float y, float z) {
        currentLight = new PositionalLight();
        lightLoc = new Point3D(x, y, z);
    }

    public void updateLight(){
        currentLight.setPosition(lightLoc);
    }

    public PositionalLight getCurrentLight() {
        return currentLight;
    }

    public Point3D getLightLoc() {
        return lightLoc;
    }

    public float[] getGlobalAmbient() {
        return globalAmbient;
    }

    public void moveLightBackward(){
        lightLoc.setZ(lightLoc.getZ()+amt);
    }

    public void moveLightForward(){
        lightLoc.setZ(lightLoc.getZ()-amt);
    }

    public void moveLightRight(){
        lightLoc.setX(lightLoc.getX()+amt);
    }

    public void moveLightLeft(){
        lightLoc.setX(lightLoc.getX()-amt);
    }

    public void moveLightUp(){
        lightLoc.setY(lightLoc.getY()+amt);
    }

    public void moveLightDown(){
        lightLoc.setY(lightLoc.getY()-amt);
    }

    public int getLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(int lightStatus) {
        this.lightStatus = lightStatus;
    }
}
