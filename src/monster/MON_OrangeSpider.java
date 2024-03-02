package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.projectile.OBJ_Rock;

import java.util.Random;

public class MON_OrangeSpider extends Entity {
    GamePanel gp;
    public MON_OrangeSpider(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Orange Spider";
        defaultSpeed = 3;
        speed = defaultSpeed;
        returningSpeed = defaultSpeed*2;
        maxLife = 10;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 8;
        solidArea.width = 42;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        dropAmount = 2;
    }
    public void getImage() {
        up1 = setup("/monster/orangespider/orangespider_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/orangespider/orangespider_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/orangespider/orangespider_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/orangespider/orangespider_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/orangespider/orangespider_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/orangespider/orangespider_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/orangespider/orangespider_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/orangespider/orangespider_left_2", gp.tileSize, gp.tileSize);
    }
    public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        if(onPath == false && tileDistance < 5) {
            onPath = true;
            returning = false;
        }
        if(onPath == true && tileDistance > 10) {
            returning = true;
        }
    }
    public void setAction() {
        if(canAttack == true) {
            if(onPath == true) {
                if(returning == true) {
                    speed = returningSpeed;
                    goalCol = spawnCol;
                    goalRow = spawnRow;
                    searchPath(goalCol, goalRow, false);
                }
                else {
                    goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
                    goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
                    searchPath(goalCol, goalRow, true);
                    borderXRight = defaultBorderXRight;
                    borderXLeft = defaultBorderXLeft;
                    borderYUp = defaultBorderYUp;
                    borderYDown = defaultBorderYDown;
                }
                int i = new Random().nextInt(100) + 1;
                if (i > 95 && projectile.alive == false && shotAvailableCounter == 30) {
                    projectile.set(worldX, worldY, direction, "", true, this, null);
                    gp.projectileList.add(projectile);

                    String directionTemp = null;
                    if (direction == "left") directionTemp = "right";
                    else if (direction == "right") directionTemp = "left";
                    else if (direction == "up") directionTemp = "down";
                    else directionTemp = "up";
                    projectile4 = new OBJ_Rock(gp);
                    projectile4.set(worldX, worldY, directionTemp, "", true, this, null);
                    projectile4.speed = 15;
                    gp.projectileList.add(projectile4);

                    shotAvailableCounter = 0;
                }
            }
            else {
                actionLockCounter++;
                if (actionLockCounter == 60) {
                    Random random = new Random();
                    int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

                    if (i <= 25) {
                        direction = "up";
                    }
                    if (i > 25 && i <= 50) {
                        direction = "down";
                    }
                    if (i > 50 && i <= 75) {
                        direction = "left";
                    }
                    if (i > 75 && i <= 100) {
                        direction = "right";
                    }
                    actionLockCounter = 0;
                }
            }
        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true;
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
    public void setDefaultBorders() {
        borderYUp = tempBorderYUp;
        borderYDown = tempBorderYDown;
        borderXRight = tempBorderXRight;
        borderXLeft = tempBorderXLeft;
    }

    public void setTempBorders() {
        tempBorderYUp = borderYUp;
        tempBorderYDown = borderYDown;
        tempBorderXRight = borderXRight;
        tempBorderXLeft = borderXLeft;
    }

}
