package a2;

import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class Controller extends JFrame {

    private View v;
    private SolarSystem ss;
    private Camera cam;

    private ForwardCommand fCommand;
    private BackwardCommand bCommand;
    private StrafeLeftCommand slCommand;
    private StrafeRightCommand srCommand;
    private MoveUpCommand muCommand;
    private MoveDownCommand mdCommand;
    private PanLeftCommand plCommand;
    private PanRightCommand prCommand;
    private PitchUpCommand puCommand;
    private PitchDownCommand pdCommand;
    private ViewAxesCommand vaCommand;
    private BarrelRollRightCommand brrCommand;
    private BarrelRollLeftCommand brlCommand;

    public Controller(){

        cam = new Camera();
        ss = new SolarSystem();
        v = new View(ss, cam);

        fCommand = new ForwardCommand(cam);
        bCommand = new BackwardCommand(cam);
        slCommand = new StrafeLeftCommand(cam);
        srCommand = new StrafeRightCommand(cam);
        muCommand = new MoveUpCommand(cam);
        mdCommand = new MoveDownCommand(cam);
        plCommand= new PanLeftCommand(cam);
        prCommand = new PanRightCommand(cam);
        puCommand = new PitchUpCommand(cam);
        pdCommand = new PitchDownCommand(cam);
        vaCommand = new ViewAxesCommand(ss);
        brrCommand = new BarrelRollRightCommand(cam);
        brlCommand = new BarrelRollLeftCommand(cam);


        setTitle("Assignment 2");
        setSize(600, 600);
        getContentPane().add(v.getMyCanvas());
        this.setVisible(true);
        FPSAnimator animator = new FPSAnimator(v.getMyCanvas(), 50);
        animator.start();

        JComponent contentPane = (JComponent) this.getContentPane();
        int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap imap = contentPane.getInputMap(mapName);
        ActionMap amap = contentPane.getActionMap();

        KeyStroke wKey = KeyStroke.getKeyStroke('w');
        imap.put(wKey, "Forward");
        amap.put("Forward", fCommand);

        KeyStroke sKey = KeyStroke.getKeyStroke('s');
        imap.put(sKey, "Backward");
        amap.put("Backward", bCommand);

        KeyStroke aKey = KeyStroke.getKeyStroke('a');
        imap.put(aKey, "Left Strafe");
        amap.put("Left Strafe", slCommand);

        KeyStroke dKey = KeyStroke.getKeyStroke('d');
        imap.put(dKey, "Right Strafe");
        amap.put("Right Strafe", srCommand);

        KeyStroke qKey = KeyStroke.getKeyStroke('q');
        imap.put(qKey, "Move Up");
        amap.put("Move Up", muCommand);

        KeyStroke eKey = KeyStroke.getKeyStroke('e');
        imap.put(eKey, "Move Down");
        amap.put("Move Down", mdCommand);

        KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
        imap.put(leftKey, "Pan Left");
        amap.put("Pan Left", plCommand);

        KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
        imap.put(rightKey, "Pan Right");
        amap.put("Pan Right", prCommand);

        KeyStroke upKey = KeyStroke.getKeyStroke("UP");
        imap.put(upKey, "Pitch Up");
        amap.put("Pitch Up", puCommand);

        KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
        imap.put(downKey, "Pitch Down");
        amap.put("Pitch Down", pdCommand);

        KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
        imap.put(spaceKey, "Axes");
        amap.put("Axes", vaCommand);

        KeyStroke brKey = KeyStroke.getKeyStroke('x');
        imap.put(brKey, "Barrel Roll Right");
        amap.put("Barrel Roll Right", brrCommand);

        KeyStroke blKey = KeyStroke.getKeyStroke('z');
        imap.put(blKey, "Barrel Roll Left");
        amap.put("Barrel Roll Left", brlCommand);

        this.requestFocus();
    }

}
