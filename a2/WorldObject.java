import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;

public abstract class WorldObject {

    private float x;
    private float y;
    private float z;
    private int texture;
    private float size;
    private String name;


    public WorldObject(String name, float x, float y, float z, float size, String texture){
        this.name = name;
        this .x = x;
        this.y = y;
        this.z = z;
        this.size = size;

        Texture joglTexture = loadTexture(texture);
        this.texture = joglTexture.getTextureObject();
    }

    public Texture loadTexture(String textureFileName)
    {	Texture tex = null;
        try { tex = TextureIO.newTexture(new File(textureFileName), false); }
        catch (Exception e) { e.printStackTrace(); }
        return tex;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public int getTexture() {
        return texture;
    }


    public float getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
