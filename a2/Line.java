package a2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;

public class Line{

    private int texture;
    private Texture joglTexture;

    public Line(String textureFile){
        joglTexture = loadTexture(textureFile);
        texture = joglTexture.getTextureObject();

    }

    public Texture loadTexture(String textureFileName)
    {	Texture tex = null;
        try { tex = TextureIO.newTexture(new File(textureFileName), false); }
        catch (Exception e) { e.printStackTrace(); }
        return tex;
    }

    public int getTexture() {return texture;}

}
