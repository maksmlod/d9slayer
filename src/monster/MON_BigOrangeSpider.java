package monster;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Spider_Scale;
import object.projectile.OBJ_Rock;

import java.util.Random;

public class MON_BigOrangeSpider extends Entity {
    GamePanel gp;
    public MON_BigOrangeSpider(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Big orange spider";
        speed = 3;
        maxLife = 50;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 40;
        projectile = new OBJ_Rock(gp);
        projectile.attack = 10;

        solidArea.x = 3*2;
        solidArea.y = 8*2;
        solidArea.width = 42*2;
        solidArea.height = 40*2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        dropAmount = 10;

        getImage();
    }
    public void getImage() {
        up1 = setup("/monster/orangespider/orangespider_up_1", gp.tileSize*2, gp.tileSize*2);
        up2 = setup("/monster/orangespider/orangespider_up_2", gp.tileSize*2, gp.tileSize*2);
        down1 = setup("/monster/orangespider/orangespider_down_1", gp.tileSize*2, gp.tileSize*2);
        down2 = setup("/monster/orangespider/orangespider_down_2", gp.tileSize*2, gp.tileSize*2);
        right1 = setup("/monster/orangespider/orangespider_right_1", gp.tileSize*2, gp.tileSize*2);
        right2 = setup("/monster/orangespider/orangespider_right_2", gp.tileSize*2, gp.tileSize*2);
        left1 = setup("/monster/orangespider/orangespider_left_1", gp.tileSize*2, gp.tileSize*2);
        left2 = setup("/monster/orangespider/orangespider_left_2", gp.tileSize*2, gp.tileSize*2);
    }
    public void setAction() {
        if(canAttack == true) {
            actionLockCounter++;

            if (actionLockCounter == 30) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

                if (i <= 25) {
                    direction = "up";
                } else if (i > 25 && i <= 50) {
                    direction = "down";
                } else if (i > 50 && i <= 75) {
                    direction = "left";
                } else if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
            int i = new Random().nextInt(100) + 1;
            if (i > 90 && shotAvailableCounter == 30) {
                projectile6 = new OBJ_Rock(gp);
                projectile6.set(worldX + gp.tileSize / 2, worldY + gp.tileSize / 2, "up", "", true, this, null);
                projectile6.speed = 3;
                projectile6.attack = 10;
                gp.projectileList.add(projectile6);

                projectile4 = new OBJ_Rock(gp);
                projectile4.set(worldX + gp.tileSize / 2, worldY + gp.tileSize / 2, "down", "", true, this, null);
                projectile4.speed = 3;
                projectile4.attack = 10;
                gp.projectileList.add(projectile4);

                projectile3 = new OBJ_Rock(gp);
                projectile3.set(worldX + gp.tileSize / 2, worldY + gp.tileSize / 2, "left", "", true, this, null);
                projectile3.speed = 3;
                projectile3.attack = 10;
                gp.projectileList.add(projectile3);

                projectile5 = new OBJ_Rock(gp);
                projectile5.set(worldX + gp.tileSize / 2, worldY + gp.tileSize / 2, "right", "", true, this, null);
                projectile5.speed = 3;
                projectile5.attack = 10;
                gp.projectileList.add(projectile5);

                shotAvailableCounter = 0;
            }
        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop() {
        for(int j = 0; j < dropAmount; j++) {
            int i = new Random().nextInt(100) + 1;
            if (i < 10) {
                dropItem(new OBJ_Spider_Scale(gp));
            }
            else if (i < 15) {
                dropItem(new OBJ_Spider_Scale(gp));
            }
            else if (i < 100) {
                dropItem(new OBJ_Spider_Scale(gp));
            }
        }
    }

}
