public class Object3D extends WorldObject{

    private String objectFile;

    public Object3D(String name, float x, float y, float z, float size, String texture, String objectFile){
        super(name, x, y, z, size, texture);
        this.objectFile = objectFile;
    }
}
