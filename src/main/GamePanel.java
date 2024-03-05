package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import object.projectile.OBJ_Fireball;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //  Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    //for fullscreen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreanOn = false;

    final int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sound = new Sound();
    Map map = new Map(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][100];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][100];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public int occupiedDropPlaces[][] = new int[maxWorldCol*tileSize][maxWorldRow*tileSize];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();


    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int skinsState = 8;
    public final int inventoryState = 9;
    public final int tradeState = 10;
    public final int mapState = 11;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame() {
        Entity entity = new Entity(this);
        entity.loadImages();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster(999, true);
        aSetter.setInteractiveTile();

        playMusic(0);
        stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if(fullScreanOn == true) setFullScreen();
        else if(fullScreanOn == false) setSmallScreen();
    }
    public void setFullScreen() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screenWidth2 = (int) width;
        screenHeight2 = (int) height;

        Main.window.dispose();
        Main.window.setUndecorated(true);
        Main.window.setVisible(true);
        //offset factor to be used by mouse listener or mouse motion listener if you are using cursor in your game. Multiply your e.getX()e.getY() by this.
      //  fullScreenOffsetFactor = (float) screenWidth / (float) screenWidth2;
    }
    public void setSmallScreen() {

        screenWidth2 = screenWidth;
        screenHeight2 = screenHeight;
        Main.window.repaint();
        Main.window.setSize(screenWidth, screenHeight);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - Main.window.getWidth()) / 2;
        int y = (screenSize.height - Main.window.getHeight()) / 2;
        Main.window.setLocation(x,y);

        Main.window.dispose();
        Main.window.setUndecorated(false);
        Main.window.setVisible(true);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void retry() {
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        //aSetter.setNPC();
        eHandler.teleportWithoutTransition(0,player.worldX/tileSize,player.worldY/tileSize);
    }
    public void restart() {
        player.setDefaultValues();
        player.setItems();
        aSetter.setNPC();
        aSetter.setMonster(999, true);
        aSetter.setObject();
        aSetter.setInteractiveTile();
        eHandler.teleportWithoutTransition(0,player.worldX/tileSize,player.worldY/tileSize);
        gameState = titleState;
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
              //  repaint();
                try {
                    drawToTempScreen(); // draw to buffered image
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                drawToScreen(); //draw buffered image to screen
                delta--;
            }
        }
    }
    public void update() {
        if(gameState == playState) {
            //player
            player.update();
            //npc
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) monster[currentMap][i].update();
                    if(monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive == true) projectileList.get(i).update();
                    if(projectileList.get(i).alive == false) projectileList.remove(i);
                }
            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive == true) particleList.get(i).update();
                    if(particleList.get(i).alive == false) particleList.remove(i);
                }
            }
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
        if(gameState == pauseState) {}

    }
    public void drawToTempScreen() throws IOException {

        //debug
        long drawStart = 0;
        if(keyH.showDebugText == true) {
            drawStart = System.nanoTime();
        }

        //title screen
        if(gameState == titleState) {
            ui.draw(g2);

        }
        else if(gameState == mapState) {
            map.drawFullMapScreen(g2);
        }
        else {
            // Tile
            tileM.draw(g2);

            //interactive tile
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // add entity to the list
            entityList.add(player);
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for(int i = 0; i < obj[1].length; i++) {
                if(obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            //sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //draw
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();

            //map
            map.drawMiniMap(g2);

            // UI
            ui.draw(g2);
        }
        if(keyH.showDebugText == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setFont(new Font("Arial", Font.PLAIN, 15));
            g2.setColor(Color.white);
            int x = 10, y = 400;
            g2.drawString("WorldX: " + player.worldX, x, y);
            y += 15;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y += 15;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y += 15;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y);
            y += 15;
            g2.drawString("Draw Time: " + passed, x, y);
        }
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
  /*  public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //debug
        long drawStart = 0;
        if(keyH.showDebugText == true) {
            drawStart = System.nanoTime();
        }

        //title screen
        if(gameState == titleState) {
            ui.draw(g2);

        }
        else {
            // Tile
            tileM.draw(g2);

            //interactive tile
            for(int i = 0; i < iTile.length; i++) {
                if(iTile[i] != null) {
                    iTile[i].draw(g2);
                }
            }

            // add entity to the list
            entityList.add(player);
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            //sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //draw
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();



            // UI
            ui.draw(g2);
        }
        if(keyH.showDebugText == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setFont(new Font("Arial", Font.PLAIN, 15));
            g2.setColor(Color.white);
            int x = 10, y = 400;
            g2.drawString("WorldX: " + player.worldX, x, y);
            y += 15;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y += 15;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y += 15;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y);
            y += 15;
            g2.drawString("Draw Time: " + passed, x, y);
        }
        g2.dispose();
    }
*/
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void pauseMusic() {
        music.clipTime = music.clip.getMicrosecondPosition();
        music.stop();
    }
    public void resumeMusic() {
        music.clip.setMicrosecondPosition(music.clipTime);
        music.clip.start();
        music.loop();
    }
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public void deleteProjectilesAndParticles() {
        projectileList.clear();
        particleList.clear();
    }


}
