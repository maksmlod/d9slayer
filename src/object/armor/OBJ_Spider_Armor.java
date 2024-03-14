package object.armor;

import entity.Entity;
import main.GamePanel;

public class OBJ_Spider_Armor extends Entity {
    public OBJ_Spider_Armor(GamePanel gp) {
        super(gp);

        type = type_accessory;
        name = "Spider Armor";
        down1 = setup("/armor/spider_armor", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "Increase def\nby 2\nSet bonus: +10 def";
        armorSetBonusDescription = "+10 def";
        rarity = "legendary";
        price = 10;
        albumOrigin = spiderr;
        armorSetName = "spider";
        armorType = "armor";
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