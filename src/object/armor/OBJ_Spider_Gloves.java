package object.armor;

import entity.Entity;
import main.GamePanel;

public class OBJ_Spider_Gloves extends Entity {
    public OBJ_Spider_Gloves(GamePanel gp) {
        super(gp);

        type = type_accessory;
        name = "Spider Gloves";
        down1 = setup("/armor/spider_gloves", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "Increase def\nby 1\nSet bonus: +10 def";
        rarity = "legendary";
        price = 10;
        albumOrigin = spiderr;
        armorSetName = "spider";
        armorSetOrigin = "spider";
        armorType = "gloves";
    }
    public void effect(Entity user) {
        user.defense += defenseValue;
        user.incrementArmorSetCounter(armorSetName, this);
    }
    public void revertEffect(Entity user) {
        user.defense -= defenseValue;
        user.decrementArmorSetCounter(armorSetName, this);
    }
    public void effectBonus(Entity user) {
        user.defense += 10;
    }
    public void revertEffectBonus(Entity user) {
        user.defense -= 10;
    }
}