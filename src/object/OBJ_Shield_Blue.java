package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        type = type_accessory;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "Blue Shield\n\nIncrease max mana\nand mana recovery speed\nby 2";
        rarity = "rare";
        price = 10;
    }
    public void effect(Entity user) {
        user.maxMana += 2;
        user.manaRecoverySpeed -= 60;
    }
    public void revertEffect(Entity user) {
        user.maxMana -= 2;
        user.manaRecoverySpeed += 60;
    }
}
