package a2;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;

import java.util.Vector;

public class Camera {

    private Point3D uvn;
    private Vector3D u = new Vector3D(1, 0, 0);
    private Vector3D v = new Vector3D(0, 1, 0);
    private Vector3D n = new Vector3D(0, 0, 1);
    private float amtMove = 0.05f;
    private float amtRot =  3.0f;

    public Camera(){ uvn = new Point3D(0, 0, 12);}

    public Matrix3D computerView(){
        Matrix3D view = new Matrix3D();
        Matrix3D r = new Matrix3D();
        u.normalize();
        v.normalize();
        n.normalize();
        view.setRow(0, u);
        view.setRow(1, v);
        view.setRow(2, n);
        view.translate(-getCameraX(), -getCameraY(), -getCameraZ());
        return view;
    }

    public void moveForward(){
        Point3D nMove = new Point3D(n.normalize());
        nMove = nMove.mult(amtMove);
        uvn = uvn.minus(nMove);
    }

    public void moveBackward(){
        Point3D nMove = new Point3D(n.normalize());

        nMove = nMove.mult(amtMove);
        uvn = uvn.add(nMove);
    }

    public void moveRight(){
        Point3D uMove = new Point3D(u.normalize());
        uMove = uMove.mult(amtMove);
        uvn = uvn.add(uMove);
    }

    public void moveLeft(){
        Point3D uMove = new Point3D(u.normalize());
        uMove = uMove.mult(amtMove);
        uvn = uvn.minus(uMove);
    }

    public void moveUp(){
        Point3D vMove = new Point3D(v.normalize());
        vMove = vMove.mult(amtMove);
        uvn = uvn.add(vMove);
    }

    public void moveDown(){
        Point3D vMove = new Point3D(v.normalize());
        vMove = vMove.mult(amtMove);
        uvn = uvn.minus(vMove);
    }

    public void panRight(){
        Matrix3D vRot = new Matrix3D();
        vRot.rotate(-amtRot, v);
        u = u.mult(vRot);
        n = n.mult(vRot);
    }

    public void panLeft(){
        Matrix3D vRot = new Matrix3D();
        vRot.rotate(amtRot, v);
        u = u.mult(vRot);
        n = n.mult(vRot);
    }

    public void pitchUp(){
        Matrix3D uRot = new Matrix3D();
        uRot.rotate(amtRot, u);
        v = v.mult(uRot);
        n = n.mult(uRot);
    }

    public void pitchDown(){
        Matrix3D uRot = new Matrix3D();
        uRot.rotate(-amtRot, u);
        v = v.mult(uRot);
        n = n.mult(uRot);
    }

    public void rollRight(){
        Matrix3D nRot = new Matrix3D();
        nRot.rotate(amtRot, n);
        u = u.mult(nRot);
        v = v.mult(nRot);
    }

    public void rollLeft(){
        Matrix3D nRot = new Matrix3D();
        nRot.rotate(-amtRot, n);
        u = u.mult(nRot);
        v = v.mult(nRot);
    }

    public Vector3D getU(){return u;}
    public Vector3D getV(){return v;}
    public Vector3D getN(){return n;}

    public float getCameraX() {return (float)uvn.getX();}
    public void setCameraX(float cameraX) {uvn.setX(cameraX);}

    public float getCameraY() {return (float)uvn.getY();}
    public void setCameraY(float cameraY) {uvn.setY(cameraY);}

    public float getCameraZ() {return (float)uvn.getZ();}
    public void setCameraZ(float cameraZ) {uvn.setZ(cameraZ);}
}
