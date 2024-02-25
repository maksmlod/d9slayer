package entity;

import main.GamePanel;

public class Projectile extends Entity{
    Entity user;
    int diagonalCounter = 0;
    int roundCounter = 0;
    int roundCounterMax = 1;
    int roundLengthCounter = 0;
    String[] directionArray = {"up","right","down","left"};
    int directionArrayIndex = 0;
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
        //collision
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

        this.solidArea.x = tempX;
        this.solidArea.y = tempY;
        this.solidArea.width = tempWidth;
        this.solidArea.height = tempHeight;
        gp.cChecker.checkEntity(this, gp.iTile);
        gp.cChecker.checkEntity(this,gp.npc);

        if(collisionOn == true) {
            life = 0;
        }


        diagonalCounter ++;
        if(diagonalCounter > 1) diagonalCounter = 0;
        if(user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack);
                generateParticle(user.currentWeapon.projectile, gp.monster[gp.currentMap][monsterIndex]);
                generateDamageParticle(user.currentWeapon.projectile, gp.monster[gp.currentMap][monsterIndex],
                        user.currentWeapon.projectile.attack);
                alive = false;
            }
        }
        else {
            boolean contactPlayer  = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                generateParticle(user.projectile, gp.player);
                int value = user.projectile.attack - gp.player.defense;
                if(value < 0) value = 0;
                generateDamageParticle(user.projectile, gp.player, value);
                alive = false;
            }
        }

        if(direction2.equals("round")) {
            switch (direction) {
                case "up": worldY -= speed; directionArrayIndex = 0; break;
                case "down": worldY += speed; directionArrayIndex = 2; break;
                case "left": worldX -= speed; directionArrayIndex = 3; break;
                case "right": worldX += speed; directionArrayIndex = 1; break;
            }
            roundCounter ++;
            roundLengthCounter++;
            if(roundCounter == roundCounterMax) {
                directionArrayIndex ++;
                if(directionArrayIndex == 4) directionArrayIndex = 0;
                direction = directionArray[directionArrayIndex];
                roundCounter = 0;
            }
            if(roundLengthCounter == 2 * roundCounterMax) {
                roundCounterMax ++;
                roundLengthCounter = 0;
            }
        }
        else if(direction2.equals("")) {
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
            diagonalCounter = 0;
            roundCounter = 0;
            roundCounterMax = 1;
            roundLengthCounter = 0;
            directionArrayIndex = 0;
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

    }
}
