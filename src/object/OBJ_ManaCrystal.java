package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;
    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Mana Crystal";
        value = 1;
        type = type_pickupOnly;
        down1 = setup("/objects/manacrystal_full",gp.tileSize,gp.tileSize);
        image = setup("/objects/manacrystal_full",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/manacrystal_half",gp.tileSize,gp.tileSize);
        image3 = setup("/objects/manacrystal_blank",gp.tileSize,gp.tileSize);
    }
    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Picked up mana crystal");
        entity.mana += value;
        return true;
    }
}
