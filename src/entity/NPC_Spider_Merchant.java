package entity;

import main.GamePanel;
import object.OBJ_Spider_Scale;
import object.projectile.OBJ_Webshot;
import object.weapon.OBJ_Web_Staff;

public class NPC_Spider_Merchant extends Entity {
    public NPC_Spider_Merchant(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        up1 = setup("/npc/spidermerchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/spidermerchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/spidermerchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/spidermerchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/spidermerchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/spidermerchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/spidermerchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/spidermerchant_down_2", gp.tileSize, gp.tileSize);

    }
    public void setDialogue() {
        dialogues[0] = "Wanna trade?";
    }
    public void setItems() {
        inventory.add(new OBJ_Spider_Scale(gp));
        inventory.add(new OBJ_Web_Staff(gp));
    }
    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
