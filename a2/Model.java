import graphicslib3D.shape.Sphere;
import graphicslib3D.shape.Torus;

public class Model {

    private Torus myTorus;
    private Sphere mySphere;
    private ImportedModel myObj;
    private boolean isAxes = false;

    public void createObjects(){
        myObj = new ImportedModel("Objects/shuttle.obj");
        myTorus = new Torus(0.5f, 0.2f, 48);
        mySphere = new Sphere(24);
    }

    public Torus getMyTorus(){return myTorus;}

    public Sphere getMySphere(){return mySphere;}

    public ImportedModel getMyObj(){return myObj;}

    public boolean isAxes() {
        return isAxes;
    }

    public void setAxes(boolean axes) {
        isAxes = axes;
    }
}
