package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        value = 5;
        type = type_consumable;
        name = "Red Potion";
        down1 = setup("/objects/potion_red",gp.tileSize,gp.tileSize);
        description = "Heals HP: " + value;
        price = 2;
        stackable = true;
    }
    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Pijesz " + name + "\n" + "Przywrociles zdrowia: " + value;
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}
