package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Spider_Scale extends Entity {
    GamePanel gp;
    public OBJ_Spider_Scale(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_item;
        name = "Spider scale";
        value = 2;
        down1 = setup("/objects/spider_scale", gp.tileSize, gp.tileSize);
        image = setup("/objects/spider_scale", gp.tileSize, gp.tileSize);
        stackable = true;
    }

}