package skill_tree;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Talent {
    public GamePanel gp;
    public int depth;
    public int indexInRow;
    public BufferedImage image;
    public String description;
    public String title;
    public boolean obtained = false;
    public Talent requirement;
    public int x = 0;
    public int y = 0;
    public Talent(GamePanel gp, int depth, int indexInRow) {
        this.gp = gp;
        this.depth = depth;
        this.indexInRow = indexInRow;
    }
    public void effect() {}
    public void revertEffect() {}
}
