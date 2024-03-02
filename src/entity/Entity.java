package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle projectileTileArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public String dialogues[] = new String[20];
    public BufferedImage image, image2, image3;

    //state
    public int worldX, worldY;
    public String direction = "down";
    public String direction2 = "";
    public String attackDirection = "down";
    public boolean collision = false;
    public boolean invincible = false;
    public int spriteNum = 1;
    int dialogueIndex = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;

    //ai
    public int spawnCol;
    public int spawnRow;
    public boolean returning = false;
    public int goalCol;
    public int goalRow;
    public int returningSpeed;
    public int followingSpeed;
    //borders for movement
    public int borderXRight = 0;
    public int borderXLeft = 0;
    public int borderYUp = 0;
    public int borderYDown = 0;
    public int defaultBorderXRight = 0;
    public int defaultBorderXLeft = 0;
    public int defaultBorderYUp = 0;
    public int defaultBorderYDown = 0;
    public int tempBorderXRight = 0;
    public int tempBorderXLeft = 0;
    public int tempBorderYUp = 0;
    public int tempBorderYDown = 0;


    //counter
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int castCounter = 0;
    public int dyingCounter = 0;
    public int attackImageCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    int knockBackCounter = 0;

    //character atributes
    public int speed;
    public String name;
    public int defaultSpeed;
    public int type;
    public Entity[] accessories;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_wand = 8;
    public final int type_accessory = 9;
    public final int type_obstacle = 10;
    public int weapon_id;
    public boolean canMeleeAttack = false;
    public boolean haveProjectile = false;
    public boolean canAttack = true;

    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int manaRecoverySpeed = 180;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int price;
    public BufferedImage albumOrigin = null;
    public String rarity = "common"; //common uncommon rare epic legendary
    public int dropAmount = 1;
    public String damageText;
    public Entity currentWeapon;
    public Entity currentShield;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Projectile projectile;
    public Projectile projectile2;
    public Projectile projectile3;
    public Projectile projectile4;
    public Projectile projectile5;
    public Projectile projectile6;
    public Projectile projectile7;
    public static BufferedImage fireball_up1, fireball_up2, fireball_down1, fireball_down2,
            fireball_left1, fireball_left2, fireball_right1, fireball_right2;
    public static BufferedImage divineshot_up1, divineshot_up2, divineshot_down1, divineshot_down2,
            divineshot_left1, divineshot_left2, divineshot_right1, divineshot_right2;
    public static BufferedImage iceball_up1, iceball_up2, iceball_down1, iceball_down2,
            iceball_left1, iceball_left2, iceball_right1, iceball_right2;
    public static BufferedImage rock_up1, rock_up2, rock_down1, rock_down2,
            rock_left1, rock_left2, rock_right1, rock_right2;
    public static BufferedImage bullet_down1;
    public static BufferedImage spiderr;
    public static BufferedImage crest;






    // item attributes
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int manaCounter = 0;
    public int castSpeed;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;

    public Entity(GamePanel gp) {
        this.gp = gp;
        borderXRight = gp.maxWorldCol * gp.tileSize;
        borderXLeft = 0;
        borderYUp = 0;
        borderYDown = gp.maxWorldRow * gp.tileSize;
        defaultBorderXRight = gp.maxWorldCol * gp.tileSize;
        defaultBorderXLeft = 0;
        defaultBorderYUp = 0;
        defaultBorderYDown = gp.maxWorldRow * gp.tileSize;
        tempBorderXRight = gp.maxWorldCol * gp.tileSize;
        tempBorderXLeft = 0;
        tempBorderYUp = 0;
        tempBorderYDown = gp.maxWorldRow * gp.tileSize;

    }
    public int getLeftX() {return worldX + solidArea.x;}
    public int getRightX() {return worldX + solidArea.x + solidArea.width;}
    public int getTopY() {return worldY + solidArea.y;}
    public int getBottomY() {return worldY + solidArea.y + solidArea.height;}
    public int getCol() {return (worldX+solidArea.x)/gp.tileSize;}
    public int getRow() {return (worldY+solidArea.y)/gp.tileSize;}
    public void setAction() {}
    public void damageReaction() {}
    public void speak() {
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }
    public void interact() {}
    public boolean use(Entity entity) {return false;}
    public void checkDrop() {}
    public void dropItem(Entity droppedItem) {
        for(int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] == null) {
                int tempWorldX = worldX;
                int tempWorldY = worldY;
                while(gp.occupiedDropPlaces[worldX][worldY] != 0) {
                    int j = new Random().nextInt(100) + 1;
                    int k = new Random().nextInt(100) + 1;
                    int distance = 0;
                    if(k < 33) distance = 15;
                    else if(k < 66) distance = 20;
                    else distance = 25;

                    if(j < 12) worldX += distance;
                    else if(j < 24) worldX -= distance;
                    else if(j < 36) worldY += distance;
                    else if(j < 48) worldY -= distance;
                    else if(j < 60) {
                        worldX += distance;
                        worldY += distance;
                    }
                    else if(j < 72) {
                        worldX += distance;
                        worldY -= distance;
                    }
                    else if(j < 84) {
                        worldX -= distance;
                        worldY += distance;
                    }
                    else {
                        worldX -= distance;
                        worldY -= distance;
                    }

                }
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                gp.occupiedDropPlaces[worldX][worldY] = 1;

                worldY = tempWorldY;
                worldX = tempWorldX;
                break;
            }
        }
    }
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    public int getParticleSize() {
        int size = 0; // 6 pixels
        return size;
    }
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife,
                -2, -1, "", null);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife,
                2, -1, "", null);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife,
                -2, 1, "", null);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife,
                2, 1, "", null);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void generateDamageParticle(Entity generator, Entity target, int damage) {
        Color color = Color.red;
        int speed = 2;
        int maxLife = 20;
        String value = String.valueOf(damage);
        Particle p1 = new Particle(gp, target, color, 0, speed, maxLife,
                1, 0, value, null);
        p1.isDamageParticle = true;
        gp.particleList.add(p1);
    }
    public void generateImageParticle(Entity generator, Entity target, BufferedImage image) {
        int speed = 1;
        int maxLife = 20;
        Particle p1 = new Particle(gp, target, null, 0, speed, maxLife,
                -1, -1,"", image);
        Particle p2 = new Particle(gp, target, null, 0, speed, maxLife,
                1, -1,"", image);
        Particle p3 = new Particle(gp, target, null, 0, speed, maxLife,
                -1, 1,"", image);
        Particle p4 = new Particle(gp, target, null, 0, speed, maxLife,
                1, 1,"", image);
        p1.isImageParticle = true;
        p2.isImageParticle = true;
        p3.isImageParticle = true;
        p4.isImageParticle = true;
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void update() {
        if(knockBack == true) {
            checkCollision();
            if(collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else {
                switch(gp.player.attackDirection) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }

            knockBackCounter ++;
            if(knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else {
            setAction();
            checkCollision();

            // If collision is false, player can move
            if(collisionOn == false) {
                switch(direction) {
                    case "up": worldY -= speed;break;
                    case "down": worldY += speed;break;
                    case "left": worldX -= speed;break;
                    case "right": worldX += speed;break;
                }
            }
        }

        spriteCounter++;
        if(spriteCounter > 24) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(invincible == true) {
            invincibleCounter ++;
            int invCounter;
            if(this == gp.player)  invCounter = 40;
            else invCounter = 8;
            if(invincibleCounter > invCounter) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }
    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
    }
    public void damagePlayer(int attack) {
        if(gp.player.invincible == false) {
            //we can give dmg
            gp.playSE(6);

            int damage = attack - gp.player.defense;
            if(damage < 0) damage = 0;

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                switch(direction) {
                case "up":
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                    break;
            }
            // monster hp bar
            if(type == type_monster && hpBarOn == true) //is a monster
            {
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX - 1,screenY - 16,gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }



            if(invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if(dying == true) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY,null);
            changeAlpha(g2, 1F);
        }
    }
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) {changeAlpha(g2,0f);}
        if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2,1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2,0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2,1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2,0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2,1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2,0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2,1f);}
        if(dyingCounter > i*8) {
            alive = false;
        }

    }
    public void changeAlpha(Graphics2D g2, float alphaValue) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;

    }
    public void attack(int worldX, int worldY, String direction, boolean alive, Entity user) {}
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {}
    public void effect(Entity user) {}
    public void revertEffect(Entity user) {}
    public void loadImages() {
        fireball_up1 = setup("/projectile/fireball_up_1",gp.tileSize,gp.tileSize);
        fireball_up2 = setup("/projectile/fireball_up_2",gp.tileSize,gp.tileSize);
        fireball_down1 = setup("/projectile/fireball_down_1",gp.tileSize,gp.tileSize);
        fireball_down2 = setup("/projectile/fireball_down_2",gp.tileSize,gp.tileSize);
        fireball_left1 = setup("/projectile/fireball_left_1",gp.tileSize,gp.tileSize);
        fireball_left2 = setup("/projectile/fireball_left_2",gp.tileSize,gp.tileSize);
        fireball_right1 = setup("/projectile/fireball_right_1",gp.tileSize,gp.tileSize);
        fireball_right2 = setup("/projectile/fireball_right_2",gp.tileSize,gp.tileSize);

        divineshot_up1 = setup("/projectile/divineshot_up_1",gp.tileSize,gp.tileSize);
        divineshot_up2 = setup("/projectile/divineshot_up_2",gp.tileSize,gp.tileSize);
        divineshot_down1 = setup("/projectile/divineshot_down_1",gp.tileSize,gp.tileSize);
        divineshot_down2 = setup("/projectile/divineshot_down_2",gp.tileSize,gp.tileSize);
        divineshot_left1 = setup("/projectile/divineshot_left_1",gp.tileSize,gp.tileSize);
        divineshot_left2 = setup("/projectile/divineshot_left_2",gp.tileSize,gp.tileSize);
        divineshot_right1 = setup("/projectile/divineshot_right_1",gp.tileSize,gp.tileSize);
        divineshot_right2 = setup("/projectile/divineshot_right_2",gp.tileSize,gp.tileSize);

        iceball_up1 = setup("/projectile/iceball_up_1",gp.tileSize,gp.tileSize);
        iceball_up2 = setup("/projectile/iceball_up_1",gp.tileSize,gp.tileSize);
        iceball_down1 = setup("/projectile/iceball_down_1",gp.tileSize,gp.tileSize);
        iceball_down2 = setup("/projectile/iceball_down_1",gp.tileSize,gp.tileSize);
        iceball_left1 = setup("/projectile/iceball_left_1",gp.tileSize,gp.tileSize);
        iceball_left2 = setup("/projectile/iceball_left_1",gp.tileSize,gp.tileSize);
        iceball_right1 = setup("/projectile/iceball_right_1",gp.tileSize,gp.tileSize);
        iceball_right2 = setup("/projectile/iceball_right_1",gp.tileSize,gp.tileSize);

        rock_up1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_up2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_down1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_down2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_left1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_left2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_right1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        rock_right2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);

        bullet_down1 = setup("/projectile/bullet",gp.tileSize,gp.tileSize);

        spiderr = setup("/tiles/drain_albums/spiderr",gp.tileSize,gp.tileSize);
        crest = setup("/tiles/drain_albums/crest",gp.tileSize,gp.tileSize);
    }
    public void searchPath(int goalCol, int goalRow, boolean follow) {
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if(gp.pFinder.search() == true) {
            //next worldx and worldy
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            //entity solid area position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;


            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                //left or right
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn == true) direction = "left";
            }
            else if(enTopY > nextY && enLeftX < nextX) {
                //up or right
                direction = "up";
                checkCollision();
                if(collisionOn == true) direction = "right";
            }
            else if(enTopY < nextY && enLeftX > nextX) {
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn == true) direction = "left";
            }
            else if(enTopY < nextY && enLeftX < nextX) {
                //down or right
                direction = "down";
                checkCollision();
                if(collisionOn == true) direction = "right";
            }
        }
        //if reaches the goal
        if(follow == false && gp.pFinder.pathList.size() != 0) {
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
                speed = defaultSpeed;
                setDefaultBorders();
            }
        }
    }
    public int getDetected(Entity user, Entity[][] target, String targetName) {
        int index = 999;
        //check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        switch(user.direction) {
            case "up":
                nextWorldY = user.getTopY() - user.speed;
                break;    // change 1 to user.speed
            case "down":
                nextWorldY = user.getBottomY() + user.speed;
                break;    // change 1 to user.speed
            case "left":
                nextWorldX = user.getLeftX() - user.speed;
                break;    // change 1 to user.speed
            case "right":
                nextWorldX = user.getRightX() + user.speed;
                break;    // change 1 to user.speed
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++) {
            if(target[gp.currentMap][i] != null) {
                if(target[gp.currentMap][i].getCol() == col &&
                    target[gp.currentMap][i].getRow() == row &&
                    target[gp.currentMap][i].name.equals(targetName)) {

                    index = i;
                    break;
                }
            }
        }
        return index;
    }
    public void setDefaultBorders() {}
    public void setTempBorders() {}
}
