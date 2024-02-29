package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean opened = false;
    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        name = "Chest";
        right1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
        right2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = right1;
        collision = true;
        type = type_obstacle;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        gp.gameState = gp.dialogueState;
        if(opened == false) {
            gp.playSE(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");
            if(gp.player.canObtainItem(loot) == false) {
                sb.append("\n ... you cannnot carry any more!");
            }
            else {
                sb.append("\n You obtain the " + loot.name + "!");
                down1 = right2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else {
            gp.ui.currentDialogue = "Its empty";
        }
    }
}
