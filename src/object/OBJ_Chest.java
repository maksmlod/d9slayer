package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
    }
}
