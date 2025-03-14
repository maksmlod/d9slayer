package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.projectile.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Green Slime";
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 4;
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
        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }
    public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        if(onPath == false && tileDistance < 5) {
            onPath = true;
        }
        if(onPath == true && tileDistance > 10) {
            onPath = false;
        }
    }
    public void setAction() {
        if(canAttack == true) {
            if(onPath == true) {
                //int goalCol = 32;
                //int goalRow = 28;
                int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
                int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
                searchPath(goalCol, goalRow, true);

                int i = new Random().nextInt(100) + 1;
                if (i > 95 && projectile.alive == false && shotAvailableCounter == 30) {
                    projectile.set(worldX, worldY, direction, "", true, this, null);
                    gp.projectileList.add(projectile);
                    shotAvailableCounter = 0;
                }
            }
            else {
                actionLockCounter++;
                if (actionLockCounter == 120) {
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

}
