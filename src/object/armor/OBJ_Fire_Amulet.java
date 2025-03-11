package object.armor;

import entity.Entity;
import main.GamePanel;

public class OBJ_Fire_Amulet extends Entity {
    public OBJ_Fire_Amulet(GamePanel gp) {
        super(gp);

        type = type_accessory;
        name = "Fire amulet";
        down1 = setup("/armor/fire_amulet", gp.tileSize, gp.tileSize);
        defenseValue = 0;
        description = "Increase fire dmg\nby 1";
        rarity = "epic";
        price = 10;
    }
    public void effect(Entity user) {
        user.bonusFireDmg += 1;
    }
    public void revertEffect(Entity user) {
        user.bonusFireDmg -= 1;
    }
}
