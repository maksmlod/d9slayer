package animals;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.projectile.OBJ_Rock;

import java.awt.image.BufferedImage;
import java.util.Random;

public class GuineaPig extends Entity {
    GamePanel gp;
    int leavingCounter = 180;
    boolean isLeaving = false;
    BufferedImage littleheart = null;

    public GuineaPig(GamePanel gp) {
        super(gp);
        this.gp = gp;

        direction = "down";
        name = "Guinea pig";
        defaultSpeed = 7;
        followingSpeed = 4;
        speed = defaultSpeed;

        solidArea.x = 10;
        solidArea.y = 10;
        solidArea.width = 28;
        solidArea.height = 28;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        littleheart = setup("/objects/little_heart",gp.tileSize,gp.tileSize);

        getImage();
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0] = "pi pi pi";
    }
    public void getImage() {
        up1 = setup("/animals/guineapig_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/animals/guineapig_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/animals/guineapig_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/animals/guineapig_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/animals/guineapig_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/animals/guineapig_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/animals/guineapig_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/animals/guineapig_right_2", gp.tileSize, gp.tileSize);
    }
    public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        if(isLeaving == true) {
            if(gp.player.pet == this) gp.player.pet = null;
            onPath = false;
            leavingCounter --;
            speed = defaultSpeed;
            if(leavingCounter == 0) {
                isLeaving = false;
                leavingCounter = 180;
            }
        }
        else {
            if (onPath == false && tileDistance < 1) {
                onPath = true;
                borderYDown = defaultBorderYDown;
                borderYUp = defaultBorderYUp;
                borderXLeft = defaultBorderXLeft;
                borderXRight = defaultBorderXRight;
                gp.player.pet = this;
            }
            if (onPath == true) {
                speed = followingSpeed;
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i > 99) {
                    gp.playSE(18);
                    generateImageParticle(this,this,littleheart);
                }
            } else {
                speed = defaultSpeed;
            }
        }
    }
    public void setAction() {
        if(onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow, true);
        }
        else {
                actionLockCounter++;
                if (actionLockCounter == 30) {
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
    public void speak() {
        super.speak();
        onPath = false;
        isLeaving = true;
    }


}

