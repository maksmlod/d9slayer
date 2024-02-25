package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.projectile.OBJ_Rock;

import java.util.Random;

public class MON_GreySlime extends Entity {
    GamePanel gp;
    public MON_GreySlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Grey Slime";
        speed = 2;
        maxLife = 15;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 5;
        projectile = new OBJ_Rock(gp);
        projectile.speed = 14;

        solidArea.x = 3;
        solidArea.y = 8;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dropAmount = 4;

        getImage();
    }
    public void getImage() {
        up1 = setup("/monster/greyslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greyslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greyslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greyslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greyslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greyslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greyslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greyslime_down_2", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        actionLockCounter ++;

        if(actionLockCounter == 60) {
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick up a number from 1 to 100

            if(i <= 25) {
                direction = "up";
            }
            else if(i > 25 && i <= 50) {
                direction = "down";
            }
            else if(i > 50 && i <= 75) {
                direction = "left";
            }
            else if(i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100) + 1;
        if(i > 90 && projectile.alive == false && shotAvailableCounter == 30) {
            projectile.set(worldX,worldY,direction,"",true,this, null);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop() {
        for(int j = 0; j < dropAmount; j++) {
            int i = new Random().nextInt(100) + 1;
            if (i < 50) {
                dropItem(new OBJ_Coin(gp));
            }
            if (i >= 50 && i < 75) {
                dropItem(new OBJ_Heart(gp));
            }
            if (i >= 75 && i < 100) {
                dropItem(new OBJ_ManaCrystal(gp));
            }
        }
    }

}
