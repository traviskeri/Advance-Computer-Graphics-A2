package a2;

import graphicslib3D.Matrix3D;

import java.util.Vector;

/*

 */
public class SolarSystem {

    private Sun s;
    private Vector<Planet> planets = new Vector<>();
    private Vector<Moon> moons = new Vector<>();
    private Vector<RingWorld> ringSystems = new Vector<>();
    private Vector<Line> lines= new Vector<>();
    private boolean isAxes = false;

    public void theBigBang(){
        s = new Sun(0.0f, 0.0f, 0.0f, 1.0f);

        planets.add(new Planet("Earth", 2.0f, 0.0f, 2.0f, 0.25f,"textures/earth.jpg"));
        moons.add(new Moon("Moon",0.8f, 0.8f, 0.8f, 0.05f, "textures/moon.jpg"));

        planets.add(new Planet("Mars", 3.0f, 0.0f, 3.0f, 0.2f,"textures/mars.jpg"));
        moons.add(new Moon("Phobos", 0.6f, 0.6f, 0.6f, 0.08f, "textures/phobos.jpg"));
        moons.add(new Moon("Deimos", 1.0f, 1.0f, 1.0f, 0.05f,"textures/deimos.jpg")) ;

        planets.add(new Planet("Saturn", 4.0f, 0.0f, 4.0f, 0.4f, "textures/saturn.jpg"));
        ringSystems.add(new RingWorld("A Ring", 0.0f, 0.0f, 0.0f, 1.0f, "textures/ringworld.jpg"));

        lines.add(new Line("textures/red.jpg"));
        lines.add(new Line("textures/green.jpg"));
        lines.add(new Line("textures/blue.jpg"));


    }

    //----------------------Sun----------------------------
    public float getSunX(){return s.getX();}
    public float getSunY(){return s.getY();}
    public float getSunZ(){return s.getZ();}
    public float getSunSize(){return s.getSize();}
    public int getSunTexture(){return  s.getTexture();}

    //----------------------Planet----------------------------
    public float getPlanetX(int p){return planets.get(p).getX();}
    public float getPlanetY(int p){return planets.get(p).getY();}
    public float getPlanetZ(int p){return planets.get(p).getZ();}
    public float getPlanetSixe(int p){return planets.get(p).getSize();}
    public int getPlanetTexture(int p){return planets.get(p).getTexture();}

    //----------------------Moon----------------------------
    public float getMoonX(int m){return moons.get(m).getX();}
    public float getMoonY(int m){return moons.get(m).getY();}
    public float getMoonZ(int m){return moons.get(m).getZ();}
    public float getMoonSize(int m){return moons.get(m).getSize();}
    public int getMoonTexture(int m){return moons.get(m).getTexture();}

    //----------------------Ring Systems----------------------------
    public float getRingX(int r){return ringSystems.get(r).getX();}
    public float getRingY(int r){return ringSystems.get(r).getY();}
    public float getRingZ(int r){return ringSystems.get(r).getZ();}
    public float getRingSize(int r){return ringSystems.get(r).getSize();}
    public int getRingTexture(int r){return ringSystems.get(r).getTexture();}

    public boolean isAxes() {
        return isAxes;
    }
    public void setAxes(boolean axes) {
        isAxes = axes;
    }
    public int getAxisTexture(int a){return lines.get(a).getTexture();}
}
