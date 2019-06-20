package a2;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.common.nio.Buffers;
import graphicslib3D.GLSLUtils;
import graphicslib3D.*;
import graphicslib3D.shape.Sphere;
import java.nio.*;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES2.*;
import static com.jogamp.opengl.GL2ES3.GL_COLOR;

public class View implements GLEventListener {

    private GLCanvas myCanvas;
    private int rendering_program;
    private GLSLUtils util = new GLSLUtils();
    private ErrorHandling eh = new ErrorHandling();

    private int vao[] = new int[2];
    private int vbo[] = new int[13];
    private MatrixStack mvStack = new MatrixStack(20);

    private Sphere mySphere = new Sphere(24);
    private Ring myRing = new Ring(4.0f, 0.05f, 48);

    private SolarSystem ss;
    private Camera cam;

    public View(SolarSystem ss, Camera cam){

        myCanvas = new GLCanvas();
        myCanvas.addGLEventListener(this);
        this.ss =ss;
        this.cam = cam;
    }

    public void display(GLAutoDrawable glAutoDrawable){
        GL4 gl  = (GL4) GLContext.getCurrentGL();

        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        float bkg[] = {0.0f, 0.0f, 0.0f, 1.0f};
        FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
        gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);

        gl.glClear(GL_DEPTH_BUFFER_BIT);

        gl.glUseProgram(rendering_program);

        int mv_loc = gl.glGetUniformLocation(rendering_program, "mv_matrix");
        int proj_loc = gl.glGetUniformLocation(rendering_program, "proj_matrix");

        float aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
        Matrix3D pMat = perspective(60.0f, aspect, 0.1f, 1000.0f);

        //Pushing view matrix onto the stack
        mvStack.pushMatrix();
        mvStack.multMatrix(cam.computerView());

        double amt = (double)(System.currentTimeMillis())/5000.0;

        gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);

        if(ss.isAxes()){
            //Red x-axis
            gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
            gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(0);

            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[8]);
            gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(1);

            gl.glActiveTexture(GL_TEXTURE0);
            gl.glBindTexture(GL_TEXTURE_2D, ss.getAxisTexture(0));

            gl.glDrawArrays(GL_TRIANGLES, 0, 9);

            //Green y-axis
            gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
            gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(0);

            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
            gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(1);

            gl.glActiveTexture(GL_TEXTURE0);
            gl.glBindTexture(GL_TEXTURE_2D, ss.getAxisTexture(1));

            gl.glDrawArrays(GL_TRIANGLES, 0, 9);

            //Blue z-axis
            gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
            gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(0);

            gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[12]);
            gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            gl.glEnableVertexAttribArray(1);

            gl.glActiveTexture(GL_TEXTURE0);
            gl.glBindTexture(GL_TEXTURE_2D, ss.getAxisTexture(2));

            gl.glDrawArrays(GL_TRIANGLES, 0, 9);

            gl.glPointSize(1.0f);
        }

        //------------------------Sun---------------------------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(ss.getSunX(), ss.getSunY(), ss.getSunZ());
        mvStack.pushMatrix();
        mvStack.rotate(System.currentTimeMillis()/50.0, 0.0, 1.0, 0.0);

        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getSunTexture());

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        int numVerts = mySphere.getIndices().length;
        gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
        mvStack.popMatrix();


        //--------------------------------Earth---------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(Math.sin(amt)*ss.getPlanetX(0), ss.getPlanetY(0),
                          Math.cos(amt)*ss.getPlanetZ(0));
        mvStack.pushMatrix();
        mvStack.rotate(System.currentTimeMillis()/20.0, 0.0, 1.0, 0.0);
        mvStack.scale(ss.getPlanetSixe(0), ss.getPlanetSixe(0), ss.getPlanetSixe(0));


        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getPlanetTexture(0));

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
        mvStack.popMatrix();

        //--------------------------Moon----------------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(Math.sin(amt*10)*ss.getMoonX(0)/2, Math.sin(amt*10)*ss.getMoonY(0)/5,
                          Math.cos(amt*10)*ss.getMoonZ(0)/2);
        mvStack.pushMatrix();
        mvStack.rotate(System.currentTimeMillis()/100.0, 1.0, 0.0, 0.0);
        mvStack.scale(ss.getMoonSize(0), ss.getMoonSize(0), ss.getMoonSize(0));
        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getMoonTexture(0));

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
        mvStack.popMatrix();
        mvStack.popMatrix();
        mvStack.popMatrix();


        //-------------------Mars----------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(Math.sin(amt*1.5)*ss.getPlanetX(1), ss.getPlanetY(1),
                          Math.cos(amt*1.5)*ss.getPlanetZ(1));
        mvStack.pushMatrix();
        mvStack.rotate(System.currentTimeMillis()/10.0, 0.0, 1.0, 0.0);
        mvStack.scale(ss.getPlanetSixe(1), ss.getPlanetSixe(1), ss.getPlanetSixe(1));
        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getPlanetTexture(1));

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
        mvStack.popMatrix();

        //---------------------------------Phobos--------------------------------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(Math.sin(amt*10)*ss.getMoonX(1)/2, Math.sin(amt*10)*ss.getMoonY(1)/5,
                          Math.cos(amt*10)*ss.getMoonZ(1)/2);
        mvStack.pushMatrix();
        mvStack.rotate(System.currentTimeMillis()/150.0, 0.0, 1.0, 0.0);
        mvStack.scale(ss.getMoonSize(1), ss.getMoonSize(1), ss.getMoonSize(1));
        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getMoonTexture(1));

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
        mvStack.popMatrix();
        mvStack.popMatrix();

        //-----------------------------Deimos----------------------------------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(Math.sin(amt*5)*ss.getMoonX(2)/2, Math.sin(amt*5)*ss.getMoonY(2)/5,
                          Math.cos(amt*5)*ss.getMoonZ(2)/2);
        mvStack.pushMatrix();
        mvStack.rotate(System.currentTimeMillis()/150.0, 0.0, 1.0, 0.0);
        mvStack.scale(ss.getMoonSize(2), ss.getMoonSize(2), ss.getMoonSize(2));
        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getMoonTexture(1));

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
        mvStack.popMatrix();
        mvStack.popMatrix();
        mvStack.popMatrix();

        //------------------Ring A---------------------------------------------------
        mvStack.pushMatrix();
        mvStack.translate(ss.getRingX(0), ss.getRingY(0), ss.getRingZ(0));
        mvStack.rotate(System.currentTimeMillis()/2f, 0.0f, 1.0f, 0.0f);
        gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
        gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
        gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
        gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, ss.getRingTexture(0));

        gl.glEnable(GL_CULL_FACE);
        gl.glFrontFace(GL_CCW);

        int numIndices = myRing.getIndices().length;
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo[6]);
        gl.glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, 0);

        mvStack.popMatrix();
        mvStack.popMatrix();
        mvStack.popMatrix();
    }

    public void init(GLAutoDrawable glAutoDrawable){
        GL4 gl = (GL4) GLContext.getCurrentGL();
        rendering_program = createShaderProgram();
        setupVertices();
        ss.theBigBang();
    }

    public int createShaderProgram(){
        int[] vertCompiled = new int[1];
        int[] fragCompiled = new int[1];
        int[] linked = new int[1];

        GL4 gl = (GL4) GLContext.getCurrentGL();

        String vshaderSource[] = util.readShaderSource("shaders/vert.shader");
        String fshaderSource[] = util.readShaderSource("shaders/frag.shader");
        int lengths[];

        int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
        int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

        gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
        gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

        gl.glCompileShader(vShader);
        eh.checkOpenGLError();
        gl.glGetShaderiv(vShader, GL_COMPILE_STATUS, vertCompiled, 0);
        if(vertCompiled[0]==1){
            System.out.println("...vertex compilation success.");
        }
        else{
            System.out.println("...vertex compilation failed");
            eh.printShaderLog(vShader);
        }

        gl.glCompileShader(fShader);
        eh.checkOpenGLError();
        gl.glGetShaderiv(fShader, GL_COMPILE_STATUS, fragCompiled, 0);
        if(fragCompiled[0]==1){
            System.out.println("...fragment compilation success.");
        }
        else{
            System.out.println("...fragment compilation failed");
            eh.printShaderLog(fShader);
        }

        if((vertCompiled[0] != 1) || (fragCompiled[0] != 1)){
            System.out.println("/n Compilation error; return-flags");
            System.out.println(" vertCompiled = " + vertCompiled[0] +"; fragCompiled = " + fragCompiled[0]);
        }
        else{
            System.out.println("Successful compilation");
        }


        int vfprogram = gl.glCreateProgram();
        gl.glAttachShader(vfprogram, vShader);
        gl.glAttachShader(vfprogram, fShader);

        gl.glLinkProgram(vfprogram);
        eh.checkOpenGLError();
        gl.glGetProgramiv(vfprogram, GL_LINK_STATUS, linked, 0);
        if(linked[0] == 1){
            System.out.println("...linking succeeded");
        }
        else{
            System.out.println("...linking failed");
            eh.printProgramLog(vfprogram);
        }

        return vfprogram;
    }

    private Matrix3D perspective(float fovy, float aspect, float n, float f)
    {	float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
        float A = q / aspect;
        float B = (n + f) / (n - f);
        float C = (2.0f * n * f) / (n - f);
        Matrix3D r = new Matrix3D();
        r.setElementAt(0,0,A);
        r.setElementAt(1,1,q);
        r.setElementAt(2,2,B);
        r.setElementAt(3,2,-1.0f);
        r.setElementAt(2,3,C);
        return r;
    }

    private void setupVertices()
    {	GL4 gl = (GL4) GLContext.getCurrentGL();


    //----------------------Sphere---------------------------------
        Vertex3D[] sVertices = mySphere.getVertices();
        int[] sIndices = mySphere.getIndices();

        float[] sPvalues = new float[sIndices.length*3];
        float[] sTvalues = new float[sIndices.length*2];
        float[] sNvalues = new float[sIndices.length*3];

        for (int i=0; i<sIndices.length; i++)
        {	sPvalues[i*3] = (float) (sVertices[sIndices[i]]).getX();
            sPvalues[i*3+1] = (float) (sVertices[sIndices[i]]).getY();
            sPvalues[i*3+2] = (float) (sVertices[sIndices[i]]).getZ();
            sTvalues[i*2] = (float) (sVertices[sIndices[i]]).getS();
            sTvalues[i*2+1] = (float) (sVertices[sIndices[i]]).getT();
            sNvalues[i*3] = (float) (sVertices[sIndices[i]]).getNormalX();
            sNvalues[i*3+1]= (float)(sVertices[sIndices[i]]).getNormalY();
            sNvalues[i*3+2]=(float) (sVertices[sIndices[i]]).getNormalZ();
        }

        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);
        gl.glGenBuffers(vbo.length, vbo, 0);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        FloatBuffer sVertBuf = Buffers.newDirectFloatBuffer(sPvalues);
        gl.glBufferData(GL_ARRAY_BUFFER, sVertBuf.limit()*4, sVertBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
        FloatBuffer sTexBuf = Buffers.newDirectFloatBuffer(sTvalues);
        gl.glBufferData(GL_ARRAY_BUFFER, sTexBuf.limit()*4, sTexBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
        FloatBuffer sNorBuf = Buffers.newDirectFloatBuffer(sNvalues);
        gl.glBufferData(GL_ARRAY_BUFFER, sNorBuf.limit()*4,sNorBuf, GL_STATIC_DRAW);

        //----------------------Ring--------------------------------------------------
        Vertex3D[] rVertices = myRing.getVertices();
        int[] rIndices = myRing.getIndices();

        float[] rPvalues = new float[rVertices.length*3];
        float[] rTvalues = new float[rVertices.length*2];
        float[] rNvalues = new float[rVertices.length*3];

        for (int i=0; i<rVertices.length; i++)
        {	rPvalues[i*3] = (float) (rVertices[i]).getX();
            rPvalues[i*3+1] = (float) (rVertices[i]).getY();
            rPvalues[i*3+2] = (float) (rVertices[i]).getZ();
            rTvalues[i*2] = (float) (rVertices[i]).getS();
            rTvalues[i*2+1] = (float) (rVertices[i]).getT();
            rNvalues[i*3] = (float) (rVertices[i]).getNormalX();
            rNvalues[i*3+1]= (float)(rVertices[i]).getNormalY();
            rNvalues[i*3+2]=(float) (rVertices[i]).getNormalZ();
        }

        //gl.glBindVertexArray(vao[1]);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
        FloatBuffer rVertBuf = Buffers.newDirectFloatBuffer(rPvalues);
        gl.glBufferData(GL_ARRAY_BUFFER, rVertBuf.limit()*4, rVertBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
        FloatBuffer rTexBuf = Buffers.newDirectFloatBuffer(rTvalues);
        gl.glBufferData(GL_ARRAY_BUFFER, rTexBuf.limit()*4, rTexBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
        FloatBuffer rNorBuf = Buffers.newDirectFloatBuffer(rNvalues);
        gl.glBufferData(GL_ARRAY_BUFFER, rNorBuf.limit()*4, rNorBuf, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo[6]);  // indices
        IntBuffer rIdxBuf = Buffers.newDirectIntBuffer(rIndices);
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, rIdxBuf.limit()*4, rIdxBuf, GL_STATIC_DRAW);

        //---------------------Axes---------------------------------------
        float[] xLinePositions = {-100.0f, 0.0f, 0.05f, 100.0f, 0.0f, 0.05f, 0.0f, 0.05f, 0.0f};

        float[] xLineTexCoords = {0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};

        float[] yLinePositions = {0.0f, -100.0f, 0.0f, 0.05f, 0.0f, 0.0f, 0.0f, 100.0f, 0.0f};

        float[] yLineTexCoords = {0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};

        float[] zLinePositions = {0.0f, 0.0f, -100.0f, 0.0f, 0.0f, 100.0f, 0.0f, -0.05f, 0.0f};

        float[] zLineTexCoords = {0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f};

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
        FloatBuffer xLine_pBuffer = Buffers.newDirectFloatBuffer(xLinePositions);
        gl.glBufferData(GL_ARRAY_BUFFER, xLine_pBuffer.limit()*4, xLine_pBuffer, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[8]);
        FloatBuffer red_texBuffer = Buffers.newDirectFloatBuffer(xLineTexCoords);
        gl.glBufferData(GL_ARRAY_BUFFER, red_texBuffer.limit()*4, red_texBuffer, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
        FloatBuffer yLine_pBuffer = Buffers.newDirectFloatBuffer(yLinePositions);
        gl.glBufferData(GL_ARRAY_BUFFER, yLine_pBuffer.limit()*4, yLine_pBuffer, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
        FloatBuffer green_texBuffer = Buffers.newDirectFloatBuffer(yLineTexCoords);
        gl.glBufferData(GL_ARRAY_BUFFER, green_texBuffer.limit()*4, green_texBuffer, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
        FloatBuffer zLine_pBuffer = Buffers.newDirectFloatBuffer(zLinePositions);
        gl.glBufferData(GL_ARRAY_BUFFER, zLine_pBuffer.limit()*4, zLine_pBuffer, GL_STATIC_DRAW);

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[12]);
        FloatBuffer blue_texBuffer = Buffers.newDirectFloatBuffer(zLineTexCoords);
        gl.glBufferData(GL_ARRAY_BUFFER, blue_texBuffer.limit()*4, blue_texBuffer, GL_STATIC_DRAW);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int heigth){}

    public void dispose(GLAutoDrawable drawable){}

    public GLCanvas getMyCanvas(){return myCanvas;}
}
