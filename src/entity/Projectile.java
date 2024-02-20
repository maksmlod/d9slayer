package entity;

import main.GamePanel;

public class Projectile extends Entity{
    Entity user;
    int diagonalCounter;
    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, String direction2, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.direction2 = direction2;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }
    public void update() {
        diagonalCounter ++;
        if(diagonalCounter > 1) diagonalCounter = 0;
        if(user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack);
                generateParticle(user.currentWeapon.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        else {
            boolean contactPlayer  = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                generateParticle(user.projectile, gp.player);
                alive = false;
            }
        }
        if(direction2.equals("")) {
            switch (direction) {
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed;break;
            }
        }
        else if(!direction2.equals("") && diagonalCounter == 1) {
            switch (direction2) {
                case "up": worldY -= speed;break;
                case "down": worldY += speed;break;
                case "left": worldX -= speed;break;
                case "right": worldX += speed;break;
            }
        }
        else if(!direction2.equals("") && diagonalCounter == 0) {
            switch (direction) {
                case "up": worldY -= speed*2;break;
                case "down": worldY += speed*2;break;
                case "left": worldX -= speed*2;break;
                case "right": worldX += speed*2;break;
            }
        }

        life--;
        if(life <= 0) {
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        int tempX = this.solidArea.x;
        int tempY = this.solidArea.y;
        int tempWidth = this.solidArea.width;
        int tempHeight = this.solidArea.height;
        this.solidArea.x = this.projectileTileArea.x;
        this.solidArea.y = this.projectileTileArea.y;
        this.solidArea.width = this.projectileTileArea.width;
        this.solidArea.height = this.projectileTileArea.height;
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if(collisionOn == true) life = 0;
        this.solidArea.x = tempX;
        this.solidArea.y = tempY;
        this.solidArea.width = tempWidth;
        this.solidArea.height = tempHeight;
    }
}
