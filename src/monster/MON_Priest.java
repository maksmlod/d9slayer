package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.projectile.OBJ_Divineshot;
import object.projectile.OBJ_Rock;

import java.util.Random;

public class MON_Priest extends Entity {
    GamePanel gp;
    public MON_Priest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Priest";
        speed = 1;
        maxLife = 60;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 50;
        projectile = new OBJ_Divineshot(gp);

        solidArea.x = 3;
        solidArea.y = 8;
        solidArea.width = 42*2;
        solidArea.height = 40*2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        dropAmount = 2;
    }
    public void getImage() {
        up1 = setup("/monster/priest/priest_up_1", (int)(gp.tileSize*1.5), gp.tileSize*2);
        up2 = setup("/monster/priest/priest_up_2", (int)(gp.tileSize*1.5), gp.tileSize*2);
        down1 = setup("/monster/priest/priest_down_1", (int)(gp.tileSize*1.5), gp.tileSize*2);
        down2 = setup("/monster/priest/priest_down_2", (int)(gp.tileSize*1.5), gp.tileSize*2);
        left1 = setup("/monster/priest/priest_left_1", (int)(gp.tileSize*1.5), gp.tileSize*2);
        left2 = setup("/monster/priest/priest_left_2", (int)(gp.tileSize*1.5), gp.tileSize*2);
        right1 = setup("/monster/priest/priest_right_1", (int)(gp.tileSize*1.5), gp.tileSize*2);
        right2 = setup("/monster/priest/priest_right_2", (int)(gp.tileSize*1.5), gp.tileSize*2);
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
            if(direction == "left" || direction == "right") {
                projectile.set(worldX, worldY, direction, "", true, this);
                projectile.speed = 12;
                gp.projectileList.add(projectile);

                projectile4 = new OBJ_Divineshot(gp);
                projectile4.speed = 12;
                projectile4.set(worldX, worldY - 20, direction, "up", true, this);
                gp.projectileList.add(projectile4);

                projectile5 = new OBJ_Divineshot(gp);
                projectile5.speed = 12;
                projectile5.set(worldX, worldY + 20, direction, "down", true, this);
                gp.projectileList.add(projectile5);

                projectile6 = new OBJ_Divineshot(gp);
                projectile6.speed = 12;
                projectile6.set(worldX, worldY, direction, "round", true, this);
                gp.projectileList.add(projectile6);

                projectile7 = new OBJ_Divineshot(gp);
                projectile7.speed = 12;
                projectile7.set(worldX + 30, worldY + 30, direction, "round", true, this);
                gp.projectileList.add(projectile7);
            }
            else if(direction == "up" || direction == "down") {
                projectile.set(worldX, worldY, direction, "", true, this);
                projectile.speed = 12;
                gp.projectileList.add(projectile);

                projectile4 = new OBJ_Divineshot(gp);
                projectile4.speed = 12;
                projectile4.set(worldX - 20, worldY, direction, "left", true, this);
                gp.projectileList.add(projectile4);

                projectile5 = new OBJ_Divineshot(gp);
                projectile5.speed = 12;
                projectile5.set(worldX + 20, worldY, direction, "right", true, this);
                gp.projectileList.add(projectile5);

                projectile6 = new OBJ_Divineshot(gp);
                projectile6.speed = 12;
                projectile6.set(worldX, worldY, direction, "round", true, this);
                gp.projectileList.add(projectile6);

                projectile7 = new OBJ_Divineshot(gp);
                projectile7.speed = 12;
                projectile7.set(worldX + 30, worldY + 30, direction, "round", true, this);
                gp.projectileList.add(projectile7);
            }
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
            if (i < 70) {
                dropItem(new OBJ_Coin(gp));
            }
            if (i >= 70 && i < 85) {
                dropItem(new OBJ_Heart(gp));
            }
            if (i >= 85 && i < 100) {
                dropItem(new OBJ_ManaCrystal(gp));
            }
        }
    }

}
