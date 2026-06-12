package org.rtsang.test;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.rtsang.core.*;
import org.rtsang.core.entity.Entity;
import org.rtsang.core.entity.Model;
import org.rtsang.core.entity.Texture;
import org.rtsang.cpu.CPU;
import org.rtsang.cpu.Screen;
import org.rtsang.objectLoader.GameObject;
import org.rtsang.objectLoader.Plane;

import java.util.*;

public class TestGame implements ILogic {

    private static final float CAMERA_MOVE_SPEED = 0.05f;
    private static final float MOUSE_SENSITIVITY = 0.1f;
    private RenderManager renderer;
    private ObjectLoader loader;
    private WindowManager window;
    private Camera camera;

    private List<GameObject> gameObjects;
    private Map<Model, List<Entity>> entities = new HashMap<>();

    private KeyboardManager keyboardManager;

    private ArrayList<CPU> cpus;
    private ArrayList<Screen> screens;
    private Texture whiteTexture, blackTexture;
    private HashMap<Plane, Entity> planeEntityMap = new HashMap<>();
    private ArrayList<CPU> CPUQueue;
    private Queue<Byte> color = new ArrayDeque<Byte>();
    private Queue<Byte> colorLen = new ArrayDeque<Byte>();

    private Vector3f cameraInc;
    private ArrayList<String> video;

    public TestGame() {
        gameObjects = new ArrayList<>();
        renderer = new RenderManager();
        loader = new ObjectLoader();
        camera = new Camera();
        keyboardManager = new KeyboardManager();
        cpus = new ArrayList<>();
        screens = new ArrayList<>();
        cameraInc = new Vector3f(0, 0, 0);
        CPUQueue = new ArrayList<CPU>();
    }

    public void setWindow(WindowManager window) {
        this.window = window;
    }
    public void setVideo(ArrayList<String> video) {
        this.video = video;
    }

    @Override
    public void init() throws Exception {
        renderer = new RenderManager();
        renderer.setWindow(window);
        renderer.init();

        if (video != null) {
            for(int i = 0; i < video.size();i+=2){
                colorLen.add((byte) Integer.parseInt(video.get(i)));
                color.add((byte) Integer.parseInt(video.get(i+1)));
            }
        }

        float scale = 0.04f;
        Vector3f pos = new Vector3f(0.25f,-1.7f,0.6f);
        Vector3f rot =  new Vector3f(0,180,0);

        Model sciTableLegs = loader.loadOBJModel("textures\\models\\sciTableLegs.obj");
        sciTableLegs.setTexture(new Texture(loader.loadTexture("textures\\colors\\light_brown.png")));
        Entity sciTableLegsE = new Entity(sciTableLegs,pos,rot,scale);
        processEntity(sciTableLegsE);

        Model sciTableTops = loader.loadOBJModel("textures\\models\\sciTableTops.obj");
        sciTableTops.setTexture(new Texture(loader.loadTexture("textures\\colors\\black.png")));
        Entity sciTableTopsE = new Entity(sciTableTops,pos,rot,scale);
        processEntity(sciTableTopsE);

        Model walls = loader.loadOBJModel("textures\\models\\walls.obj");
        walls.setTexture(new Texture(loader.loadTexture("textures\\colors\\midGray.png")));
        Entity wallsE = new Entity(walls,pos,rot,scale);
        processEntity(wallsE);

        Model floor = loader.loadOBJModel("textures\\models\\floor.obj");
        floor.setTexture(new Texture(loader.loadTexture("textures\\colors\\gray.png")));
        Entity floorE = new Entity(floor,pos,rot,scale);
        processEntity(floorE);

        Model ceilingThings = loader.loadOBJModel("textures\\models\\ceilingThings.obj");
        ceilingThings.setTexture(new Texture(loader.loadTexture("textures\\colors\\light_gray.png")));
        Entity ceilingThingsE = new Entity(ceilingThings,pos,rot,scale);
        processEntity(ceilingThingsE);

        Model ceilingThingHolder = loader.loadOBJModel("textures\\models\\ceilingHolder.obj");
        ceilingThingHolder.setTexture(new Texture(loader.loadTexture("textures\\colors\\midGray.png")));
        Entity ceilingThingsHolderE = new Entity(ceilingThingHolder,pos,rot,scale);
        processEntity(ceilingThingsHolderE);


        Model ceiling = loader.loadOBJModel("textures\\models\\ceiling.obj");
        ceiling.setTexture(new Texture(loader.loadTexture("textures\\colors\\black.png")));
        Entity ceilingE = new Entity(ceiling,pos,rot,scale);
        processEntity(ceilingE);

        Model board = loader.loadOBJModel("textures\\models\\board.obj");
        board.setTexture(new Texture(loader.loadTexture("textures\\colors\\white.png")));
        Entity boardE = new Entity(board,pos,rot,scale);
        processEntity(boardE);

        Model labStools = loader.loadOBJModel("textures\\models\\labStools.obj");
        labStools.setTexture(new Texture(loader.loadTexture("textures\\colors\\black.png")));
        Entity labStoolsE = new Entity(labStools,pos,rot,scale);
        processEntity(labStoolsE);

        Model cabinetTops = loader.loadOBJModel("textures\\models\\cabinetTops.obj");
        cabinetTops.setTexture(new Texture(loader.loadTexture("textures\\colors\\black.png")));
        Entity cabinetTopsE = new Entity(cabinetTops,pos,rot,scale);
        processEntity(cabinetTopsE);

        Model cabinets = loader.loadOBJModel("textures\\models\\cabinets.obj");
        cabinets.setTexture(new Texture(loader.loadTexture("textures\\colors\\light_brown.png")));
        Entity cabinetsE = new Entity(cabinets,pos,rot,scale);
        processEntity(cabinetsE);

        Model doors = loader.loadOBJModel("textures\\models\\doors.obj");
        doors.setTexture(new Texture(loader.loadTexture("textures\\colors\\gray.png")));
        Entity doorsE = new Entity(doors,pos,rot,scale);
        processEntity(doorsE);

        Model row5_6Tops = loader.loadOBJModel("textures\\models\\row5_6Tops.obj");
        row5_6Tops.setTexture(new Texture(loader.loadTexture("textures\\colors\\brown2.png")));
        Entity row5_6TopsE = new Entity(row5_6Tops,pos,rot,scale);
        processEntity(row5_6TopsE);

        Model row5_6Legs = loader.loadOBJModel("textures\\models\\row5_6Legs.obj");
        row5_6Legs.setTexture(new Texture(loader.loadTexture("textures\\colors\\brown.png")));
        Entity row5_6LegsE = new Entity(row5_6Legs,pos,rot,scale);
        processEntity(row5_6LegsE);

        Model frontRowsTops = loader.loadOBJModel("textures\\models\\rowFrontTops.obj" );
        frontRowsTops.setTexture(new Texture(loader.loadTexture("textures\\colors\\gray.png")));
        Entity frontRowsTopsE = new Entity(frontRowsTops, pos,rot,scale);
        processEntity(frontRowsTopsE);

        Model frontRowsLegs = loader.loadOBJModel("textures\\models\\rowFrontLegs.obj" );
        frontRowsLegs.setTexture(new Texture(loader.loadTexture("textures\\colors\\light_brown.png")));
        Entity frontRowsLegsE = new Entity(frontRowsLegs, pos,rot,scale);
        processEntity(frontRowsLegsE);

        Model chairTops = loader.loadOBJModel("textures\\models\\chairTops.obj" );
        chairTops.setTexture(new Texture(loader.loadTexture("textures\\colors\\blue.png")));
        Entity chairTopsE = new Entity(chairTops, pos,rot,scale);
        processEntity(chairTopsE);

        Model chairLegs = loader.loadOBJModel("textures\\models\\chairLegs.obj" );
        chairLegs.setTexture(new Texture(loader.loadTexture("textures\\colors\\midGray.png")));
        Entity chairLegsE = new Entity(chairLegs, pos,rot,scale);
        processEntity(chairLegsE);

        Model jooDeskTop = loader.loadOBJModel("textures\\models\\jooDeskTop.obj");
        jooDeskTop.setTexture(new Texture(loader.loadTexture("textures\\colors\\gray.png")));
        Entity jooDeskTopE = new Entity(jooDeskTop, pos,rot,scale);
        processEntity(jooDeskTopE);

        Model jooDeskLegs = loader.loadOBJModel("textures\\models\\jooDeskLegs.obj");
        jooDeskLegs.setTexture(new Texture(loader.loadTexture("textures\\colors\\midGray.png")));
        Entity jooDeskLegsE = new Entity(jooDeskLegs, pos,rot,scale);
        processEntity(jooDeskLegsE);

        Model monitor = loader.loadOBJModel("textures\\models\\monitor.obj");
        monitor.setTexture(new Texture(loader.loadTexture("textures\\colors\\black.png")));
        Entity monitorE = new Entity(monitor, pos,rot,scale);
        processEntity(monitorE);

        Model pc = loader.loadOBJModel("textures\\models\\pc.obj");
        pc.setTexture(new Texture(loader.loadTexture("textures\\colors\\midGray.png")));
        Entity pcE = new Entity(pc, pos,rot,scale);
        processEntity(pcE);



        camera = new Camera();
        keyboardManager = new KeyboardManager();

        cpus = new ArrayList<>();
        screens = new ArrayList<>();



        for (CPU cpu : CPUQueue) {
            addCPU(cpu, 64, 36,0.3f);
        }

        try {
            whiteTexture = new Texture(loader.loadTexture("textures\\colors\\white.png"));
            blackTexture = new Texture(loader.loadTexture("textures\\colors\\black.png"));
        } catch (Exception e) {
            System.err.println("Could not load structural fallback textures: " + e.getMessage());
        }
    }

    public void addEntity(GameObject gameObject) {
        this.gameObjects.add(gameObject);

        if (gameObject.getFaces() != null) {
            for (Plane face : gameObject.getFaces()) {
                try {
                    float[] vertexArray = new float[face.getVertices().length * 3];
                    for (int i = 0; i < face.getVertices().length; i++) {
                        vertexArray[i * 3] = face.getVertices()[i].x;
                        vertexArray[i * 3 + 1] = face.getVertices()[i].y;
                        vertexArray[i * 3 + 2] = face.getVertices()[i].z;
                    }

                    Model model = loader.loadModel(vertexArray, face.getTextureCords(), face.getIndices());
                    int textureId = loader.loadTexture(face.getTexture());
                    model.setTexture(new Texture(textureId));

                    Entity entity = new Entity(
                            model,
                            new Vector3f(gameObject.getPosition()),
                            new Vector3f(gameObject.getRotX(), gameObject.getRotY(), gameObject.getRotZ()),
                            1.0f
                    );

                    planeEntityMap.put(face, entity);
                    processEntity(entity);

                } catch (Exception e) {
                    System.err.println("Failed to load geometry face for object: " + e.getMessage());
                }
            }
        }
    }

    public void queueCPU(CPU cpu) {
        CPUQueue.add(cpu);
    }

    public void addCPU(CPU cpu, int width, int height,float scale) {
        cpus.add(cpu);

        Screen cpuScreen = new Screen(width, height, loader);
        screens.add(cpuScreen);
        float aspectRatio = (float) width/height;

        float[] vertices = new float[]{
                -aspectRatio * scale, scale, 0.0f,
                -aspectRatio * scale, -scale, 0.0f,
                aspectRatio * scale, -scale, 0.0f,
                aspectRatio * scale, scale, 0.0f
        };
        float[] textureCoords = new float[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };
        int[] indices = new int[]{0, 1, 2, 2, 3, 0};

        Model screenMesh = loader.loadModel(vertices, textureCoords, indices);
        Texture screenTexture = new Texture(cpuScreen.getTextureId());
        screenMesh.setTexture(screenTexture);

        Entity screenEntity = new Entity(screenMesh, new Vector3f(0, 0, -5f), new Vector3f(0, 0, 0), 2.0f);
        processEntity(screenEntity);
    }

    public void processEntity(Entity entity) {
        Model model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(model, newBatch);
        }
    }

    @Override
    public void input(MouseInput mouseInput) {


        //keyboardManager.printPos();

        camera.moveRotation(
                mouseInput.getDisplayVec().x * -MOUSE_SENSITIVITY,
                mouseInput.getDisplayVec().y * MOUSE_SENSITIVITY
        );

    }

    @Override
    public void update(float interval) {

        for(int i = 0 ; i < 2 ; i++)
        {
            cameraInc.set(0, 0, 0);

            if (window.isKeyPressed(GLFW.GLFW_KEY_W)) cameraInc.z = 1;
            if (window.isKeyPressed(GLFW.GLFW_KEY_S)) cameraInc.z = -1;
            if (window.isKeyPressed(GLFW.GLFW_KEY_A)) cameraInc.x = -1;
            if (window.isKeyPressed(GLFW.GLFW_KEY_D)) cameraInc.x = 1;
            if (window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) cameraInc.y = 1;
            if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) cameraInc.y = -1;
            if (window.isKeyPressed(GLFW.GLFW_KEY_LEFT)) camera.moveRotation(0, -CAMERA_MOVE_SPEED * 50);
            if (window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) camera.moveRotation(0, CAMERA_MOVE_SPEED * 50);
            if (window.isKeyPressed(GLFW.GLFW_KEY_UP)) camera.moveRotation(CAMERA_MOVE_SPEED * 50, 0);
            if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) camera.moveRotation(-CAMERA_MOVE_SPEED * 50, 0);
            if (window.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
            camera.movePosition(cameraInc.x * CAMERA_MOVE_SPEED, cameraInc.y * CAMERA_MOVE_SPEED, cameraInc.z * CAMERA_MOVE_SPEED);
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_E) && gameObjects.size() > 1) {
            gameObjects.get(1).setCenter(camera.getPositionLookingAt(1.0f));
        }

        for (GameObject obj : gameObjects) {
            obj.update(interval);
        }
        clockCPUs();

        for (GameObject obj : gameObjects) {
            Vector3f currentPos = obj.getPosition();
            Vector3f currentRot = new Vector3f(obj.getRotX(), obj.getRotY(), obj.getRotZ());
            if (obj.getFaces() == null) continue;

            for (Plane face : obj.getFaces()) {
                Entity entity = planeEntityMap.get(face);
                if (entity == null) continue;

                entity.setPos(currentPos);
                entity.setRotation(currentRot);

                if (face.getTexture().contains("white.png") && whiteTexture != null) {
                    entity.getModel().setTexture(whiteTexture);
                } else if (face.getTexture().contains("black.png") && blackTexture != null) {
                    entity.getModel().setTexture(blackTexture);
                }
            }
        }

    }

    @Override
    public void render() {
        if (window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }


        window.setClearColor(0.0f, 0.6f, 0.7f, 0.0f);
        renderer.render(entities, camera);
    }
    public void clockCPUs()
    {
        int cpuCounter = 0;
        for (CPU cpu : cpus) {
            if (!cpu.isStop()) {


                for(int i = 0; i < 100;i++) {
                    if(window.isKeyPressed(GLFW.GLFW_KEY_I))
                        cpu.ioPorts.write((byte) 1,5);
                    if(window.isKeyPressed(GLFW.GLFW_KEY_J))
                        cpu.ioPorts.write((byte) 2,5);
                    if(window.isKeyPressed(GLFW.GLFW_KEY_K))
                        cpu.ioPorts.write((byte) 3,5);
                    if(window.isKeyPressed(GLFW.GLFW_KEY_L))
                        cpu.ioPorts.write((byte) 4,5);
                    if(color.size() == 0)
                        cpu.ioPorts.write((byte) -1,6);
                    if(cpu.ioPorts.read(9)==1)
                    {
                        cpu.ioPorts.write(colorLen.poll(),7);
                        cpu.ioPorts.write(color.poll(),8);
                    }

                    cpu.clock();
                    int screenX = cpu.ioPorts.read(1);
                    int screenY = cpu.ioPorts.read(2);
                    int colorIndex = cpu.ioPorts.read(3);
                    int pushBuffer = cpu.ioPorts.read(4);

                    byte r = (byte) 0, g = (byte) 0, b = (byte) 0; // black by default
                    if (colorIndex == 1) { //1 is white
                        r = (byte) 255;
                        g = (byte) 255;
                        b = (byte) 255;
                    } else if (colorIndex == 2) { // red
                        r = (byte) 255;
                        g = (byte) 0;
                        b = (byte) 0;
                    }

                    Screen currentScreen = screens.get(cpuCounter);
                    currentScreen.setPixel(screenX, screenY, r, g, b, (byte) 1);

                    if (pushBuffer == 1) {
                        currentScreen.pushBuffer(loader);
                        cpu.ioPorts.write((byte) 0, 4);
                    }

                }
                cpu.ioPorts.write((byte) 0,5);


            }
            cpuCounter++;
        }
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }

    public ObjectLoader getLoader() {
        return loader;
    }
}