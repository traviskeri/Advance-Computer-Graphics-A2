package a2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;

public abstract class SpaceObject {

    private String name;
    private float x, y, z;
    private float size;
    private int texture;
    private Texture joglTexture;

    public SpaceObject(String name, float x, float y, float z, float size, String textureFile){
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;

        joglTexture = loadTexture(textureFile);
        texture = joglTexture.getTextureObject();

    }

    public float getX() {return x;}

    public float getY() {return y;}

    public float getZ() {return z;}

    public float getSize() {return size;}

    public int getTexture() {return texture;}

    public Texture loadTexture(String textureFileName)
    {	Texture tex = null;
        try { tex = TextureIO.newTexture(new File(textureFileName), false); }
        catch (Exception e) { e.printStackTrace(); }
        return tex;
    }


}
