package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.projectile.OBJ_Rock;

import java.util.Random;

public class MON_WhiteSlime extends Entity {
    GamePanel gp;
    public MON_WhiteSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "White Slime";
        speed = 3;
        maxLife = 40;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 10;
        projectile = new OBJ_Rock(gp);
        projectile.speed = 12;

        solidArea.x = 3;
        solidArea.y = 8;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {
        up1 = setup("/monster/whiteslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/whiteslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/whiteslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/whiteslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/whiteslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/whiteslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/whiteslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/whiteslime_down_2", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        actionLockCounter ++;

        if(actionLockCounter == 20) {
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
        if(i > 80 && projectile.alive == false && shotAvailableCounter == 30) {
            if(direction == "left" || direction == "right") {
                projectile.set(worldX, worldY, direction, "", true, this);
                gp.projectileList.add(projectile);

                projectile4 = new OBJ_Rock(gp);
                projectile4.speed = 12;
                projectile4.set(worldX, worldY - 20, direction, "up", true, this);
                gp.projectileList.add(projectile4);

                projectile5 = new OBJ_Rock(gp);
                projectile5.speed = 12;
                projectile5.set(worldX, worldY + 20, direction, "down", true, this);
                gp.projectileList.add(projectile5);
            }
            else if(direction == "up" || direction == "down") {
                projectile.set(worldX, worldY, direction, "", true, this);
                gp.projectileList.add(projectile);

                projectile4 = new OBJ_Rock(gp);
                projectile4.speed = 12;
                projectile4.set(worldX - 20, worldY, direction, "left", true, this);
                gp.projectileList.add(projectile4);

                projectile5 = new OBJ_Rock(gp);
                projectile5.speed = 12;
                projectile5.set(worldX + 20, worldY, direction, "right", true, this);
                gp.projectileList.add(projectile5);

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
