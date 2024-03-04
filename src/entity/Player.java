package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;
import object.accessory.OBJ_Shield_Wood;
import object.projectile.OBJ_Fireball;
import object.weapon.OBJ_Normal_Sword;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public int accessorySize = 4;
    public boolean haveMeleeAttacked = false;


    public String lastPressedDirection = "down";
    public int numberOfPressedDirections = 0;
    public ArrayList<String> pressedDirections = new ArrayList<>();


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 15;
        solidArea.y = 35;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 18;
        solidArea.height = 12;

        //attackArea.width = 36;
        //attackArea.height = 36;
        accessories = new Entity[accessorySize];


        setDefaultValues();
        String skinName = "bladee";
        getPlayerImage(skinName);
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

        defaultSpeed = 5;
        speed = defaultSpeed;
        direction = "down";

        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 6;
        mana = maxMana;
        ammo = 10;
        strength = 1; // more strength = more dmg deals
        dexterity = 1; // more dex = less dmg receives
        exp = 2;
        nextLevelExp = 5;
        coin = 300;
        currentWeapon = new OBJ_Normal_Sword(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense() + 20;
        projectile = new OBJ_Fireball(gp);
    }
    public void setDefaultPositions() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreLifeAndMana() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {

        return defense = dexterity * currentShield.defenseValue;
    }
    public void getPlayerImage(String skinName) {
        /*
        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
         */
        if(skinName == "thaiboy") {
            up1 = setup("/player/thaiboy/thaiboy1_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/thaiboy/thaiboy1_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/thaiboy/thaiboy1_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/thaiboy/thaiboy1_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/thaiboy/thaiboy1_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/thaiboy/thaiboy1_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/thaiboy/thaiboy1_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/thaiboy/thaiboy1_right_2", gp.tileSize, gp.tileSize);
        }
        else if(skinName == "ecco") {
            up1 = setup("/player/ecco/ecco1_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/ecco/ecco1_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/ecco/ecco1_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/ecco/ecco1_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/ecco/ecco1_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/ecco/ecco1_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/ecco/ecco1_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/ecco/ecco1_right_2", gp.tileSize, gp.tileSize);
        }
        else if(skinName == "bladee") {
            up1 = setup("/player/bladee/bladee1_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/bladee/bladee1_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("/player/bladee/bladee1_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/bladee/bladee1_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/bladee/bladee1_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/bladee/bladee1_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/bladee/bladee1_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/bladee/bladee1_right_2", gp.tileSize, gp.tileSize);
        }
        else if(skinName == "debug") {
            up1 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            up2 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            down1 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            down2 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            left1 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            left2 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            right1 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
            right2 = setup("/player/debug/debug_skin", gp.tileSize, gp.tileSize);
        }

    }
    public void getPlayerAttackImage() {
        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }
    public void update() {
        if(manaCounter > manaRecoverySpeed) {
            this.mana = this.mana + 2;
            manaCounter = 0;
        }
        if(mana < maxMana) manaCounter++;

        if(attacking == true) {
            attacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true ||
                keyH.upArrowPressed == true || keyH.downArrowPressed == true ||
                keyH.leftArrowPressed == true || keyH.rightArrowPressed == true ||
                keyH.enterPressed == true) {
            boolean meleeAttackingNow = false;
            if((keyH.upArrowPressed == true || keyH.downArrowPressed == true ||
                    keyH.leftArrowPressed == true || keyH.rightArrowPressed == true) &&
                    this.currentWeapon.canMeleeAttack == true) meleeAttackingNow = true;

            if(keyH.upPressed == true) {
                direction = "up";
                numberOfPressedDirections++;
                pressedDirections.add(direction);
            }
            if(keyH.downPressed == true) {
                direction = "down";
                numberOfPressedDirections++;
                pressedDirections.add(direction);
            }
            if(keyH.leftPressed == true) {
                direction = "left";
                numberOfPressedDirections++;
                pressedDirections.add(direction);
            }
            if(keyH.rightPressed == true) {
                direction = "right";
                numberOfPressedDirections++;
                pressedDirections.add(direction);
            }
            if(numberOfPressedDirections == 1) lastPressedDirection = direction;
            int idOfChoseDirection=0;
            if(numberOfPressedDirections > 1) {
                for(int i = 0; i < pressedDirections.size(); i++) {
                    if(pressedDirections.get(i) != lastPressedDirection) {
                        direction = pressedDirections.get(i);
                        idOfChoseDirection = i;
                        break;
                    }
                }
            }

            //diagonal move
            if(numberOfPressedDirections == 2) {
                collisionOn = false;
                checkCollision();
                //if first collision is false
                String previousDirection = null;
                if(collisionOn == false) {
                    if(idOfChoseDirection == 1) {
                        previousDirection = direction;
                        direction = pressedDirections.get(0);
                    }
                    if(idOfChoseDirection == 0) {
                        previousDirection = direction;
                        direction = pressedDirections.get(1);
                    }

                    collisionOn = false;
                    checkCollision();

                    //if first collision is false and second collision is false
                    if(collisionOn == false) {
                        //unfortunately its all i can do when speed is integer
                        int tempSpeed = speed;
                        speed = (int)(speed * 0.707);
                        while(speed < 0.707 * tempSpeed) speed ++;

                        String direction21 = pressedDirections.get(0);
                        String direction22 = pressedDirections.get(1);
                        switch (direction21) {
                            case "up": worldY -= speed;break;
                            case "down": worldY += speed;break;
                            case "left": worldX -= speed;break;
                            case "right": worldX += speed;break;
                        }
                        switch (direction22) {
                            case "up": worldY -= speed;break;
                            case "down": worldY += speed;break;
                            case "left": worldX -= speed;break;
                            case "right": worldX += speed;break;
                        }
                        speed = tempSpeed;
                    }
                    //if first collision is false and second collision is true
                    else {
                        switch (previousDirection) {
                            case "up": worldY -= speed;break;
                            case "down": worldY += speed;break;
                            case "left": worldX -= speed;break;
                            case "right": worldX += speed;break;
                        }
                    }
                }
                //if first collision is true
                else {
                    if(idOfChoseDirection == 1) {
                        previousDirection = direction;
                        direction = pressedDirections.get(0);
                    }
                    if(idOfChoseDirection == 0) {
                        previousDirection = direction;
                        direction = pressedDirections.get(1);
                    }

                    collisionOn = false;
                    checkCollision();

                    //if first collision is true and second collision is false
                    if(collisionOn == false) {
                        switch (direction) {
                            case "up": worldY -= speed;break;
                            case "down": worldY += speed;break;
                            case "left": worldX -= speed;break;
                            case "right": worldX += speed;break;
                        }
                    }
                    //if first collision is true and second collision is true
                    else {
                        //no movement
                    }
                }
            }
            else {
                collisionOn = false;
                checkCollision();
                if (collisionOn == false && meleeAttackingNow == false &&
                        (keyH.upPressed == true || keyH.downPressed == true ||
                                keyH.leftPressed == true || keyH.rightPressed == true)) {
                    switch (direction) {
                        case "up": worldY -= speed;break;
                        case "down": worldY += speed;break;
                        case "left": worldX -= speed;break;
                        case "right": worldX += speed;break;

                    }
                }
            }
            numberOfPressedDirections = 0;
            pressedDirections.clear();



            if(attackCanceled == false && meleeAttackingNow == true) {
                attacking = true;
                if(keyH.rightArrowPressed == true) attackDirection = "right";
                else if(keyH.leftArrowPressed == true) attackDirection = "left";
                else if(keyH.upArrowPressed == true) attackDirection = "up";
                else attackDirection = "down";
                spriteCounter = 0;
            }

            if(keyH.rightArrowPressed == true || keyH.leftArrowPressed == true ||
                    keyH.upArrowPressed == true ||
                    keyH.downArrowPressed == true) {
                if(keyH.rightArrowPressed == true) attackDirection = "right";
                else if(keyH.leftArrowPressed == true) attackDirection = "left";
                else if(keyH.upArrowPressed == true) attackDirection = "up";
                else attackDirection = "down";
            }

            if(shotAvailableCounter == this.currentWeapon.castSpeed && this.currentWeapon.useCost <= this.mana &&
                    (gp.keyH.rightArrowPressed == true || gp.keyH.leftArrowPressed == true ||
                            gp.keyH.upArrowPressed == true || gp.keyH.downArrowPressed == true)) {
                attackImageCounter = 0;
                String shotDirection;
                if(gp.keyH.rightArrowPressed == true) shotDirection = "right";
                else if(gp.keyH.leftArrowPressed == true) shotDirection = "left";
                else if(gp.keyH.upArrowPressed == true) shotDirection = "up";
                else shotDirection = "down";
                this.currentWeapon.attack(worldX,worldY,shotDirection,true,this);
                shotAvailableCounter = 0;
            }
            //melee weapon with projectile
            else if((gp.keyH.rightArrowPressed == true || gp.keyH.leftArrowPressed == true ||
                    gp.keyH.upArrowPressed == true || gp.keyH.downArrowPressed == true) &&
                    currentWeapon.canMeleeAttack == true && currentWeapon.haveProjectile == true)  {
                attackImageCounter = 0;
                String shotDirection;
                if(gp.keyH.rightArrowPressed == true) shotDirection = "right";
                else if(gp.keyH.leftArrowPressed == true) shotDirection = "left";
                else if(gp.keyH.upArrowPressed == true) shotDirection = "up";
                else shotDirection = "down";
                this.currentWeapon.attack(worldX,worldY,shotDirection,true,this);
                shotAvailableCounter = 0;
            }

            attackCanceled = false;
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
        else {
            standCounter++;
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }


        gp.keyH.enterPressed = false;
        if(invincible == true) {
            invincibleCounter ++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < this.currentWeapon.castSpeed) {
            shotAvailableCounter++;
        }
        else if(shotAvailableCounter > this.currentWeapon.castSpeed) {
            shotAvailableCounter = 0;
        }
        if(life > maxLife) life = maxLife;
        if(mana > maxMana) mana = maxMana;
        else if(mana < 0) mana = 0;

        if(life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.playSE(12);
            gp.player.invincible = false;
            gp.stopMusic();
            gp.ui.commandNum = -1;
        }
    }
    public void checkCollision() {
        gp.cChecker.checkTile(this);
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);
        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
        gp.eHandler.checkEvent();
    }
    public void attacking() {
        attackImageCounter = 0;
        spriteCounter++;
        if(spriteCounter <= 5) {
            spriteNum = 2;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            //save current worldx worldy solidarea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //adjust players worldx/y for the attackArea
            switch(attackDirection) {
                case "up": worldY -= 2*attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= 2*attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.width;

            //Check monster collision with the updated worldx worldy solidarea
            if(haveMeleeAttacked == false) {
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                damageInteractiveTile(iTileIndex);
                haveMeleeAttacked = true;
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 2;
            spriteCounter = 0;
            attacking = false;
            haveMeleeAttacked = false;
        }
    }
    public void pickUpObject(int i) {
        if(i != 999) {
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.occupiedDropPlaces[gp.obj[gp.currentMap][i].worldX][gp.obj[gp.currentMap][i].worldY] = 0;
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(keyH.enterPressed == true) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            else {
                gp.occupiedDropPlaces[gp.obj[gp.currentMap][i].worldX][gp.obj[gp.currentMap][i].worldY] = 0;
                String text;
                if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    public void interactNPC(int i) {
        if(gp.keyH.enterPressed == true) {
            if(i != 999) {
                    attackCanceled = true;
                    gp.gameState = gp.dialogueState;
                    gp.npc[gp.currentMap][i].speak();
            }
        }
    }
    public void contactMonster(int i) {
        if(i != 999) {
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0) damage = 0;

                life -= damage;
                if(life < 0) life = 0;
                invincible = true;
            }
        }
    }
    public void damageMonster(int i, int attack, int knockBackPower) {
        if(i != 999) {
            if(gp.monster[gp.currentMap][i].invincible == false) {
                gp.playSE(5);
                if(knockBackPower > 0) {
                    knockBack(gp.monster[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0) damage = 0;
                generateDamageParticle(this,gp.monster[gp.currentMap][i],damage);

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp +" + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
                currentWeapon.reactAfterDamagingMonster(gp.monster[gp.currentMap][i].worldX,
                        gp.monster[gp.currentMap][i].worldY, "", true, null, gp.monster[gp.currentMap][i]);
            }
        }
    }
    public void knockBack(Entity entity, int knockBackPower) {
        entity.direction = direction;
        entity.speed += knockBackPower;
        entity.knockBack = true;
    }
    public void damageInteractiveTile(int i) {
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true &&
                gp.iTile[gp.currentMap][i].isCorrectItem(this) == true &&
                gp.iTile[gp.currentMap][i].invincible == false) {
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life --;
            gp.iTile[gp.currentMap][i].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0) gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
        }
    }
    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            int ii = 0;
            while(exp >= nextLevelExp) {
                ii++;
                exp = exp - nextLevelExp;
                nextLevelExp = nextLevelExp * 2;
            }
            level += ii;
            maxLife += 2 * ii;
            strength += ii;
            dexterity += ii;

            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
        }
    }
    public void selectItem(int index) {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_wand) {
                currentWeapon = selectedItem;
                attack = getAttack();
            }
            if(selectedItem.type == type_accessory) {
                boolean doesContain = false;
                int containingIndex = 999;
                for(int i = 0; i < accessories.length; i++) {
                    if(accessories[i] == selectedItem) {
                        doesContain = true;
                        containingIndex = i;
                        break;
                    }
                }
                if(doesContain == true)  {
                    accessories[containingIndex].revertEffect(this);
                    accessories[containingIndex] = null;
                }
                if (accessories[index] != null) {
                    accessories[index].revertEffect(this);
                    accessories[index] = selectedItem;
                    accessories[index].effect(this);
                }
                else {
                    accessories[index] = selectedItem;
                    accessories[index].effect(this);
                }
            }
            if(selectedItem.type == type_consumable) {
                if(selectedItem.use(this) == true) {
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else inventory.remove(itemIndex);
                }
            }
        }
    }
    public int searchItemInventory(String itemName) {
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;
        if(item.stackable == true) {
            int index = searchItemInventory(item.name);
            if(index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {
                if(inventory.size() != maxInventorySize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        //not stackable
        else {
            if(inventory.size() != maxInventorySize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        BufferedImage image2 = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if(attacking == false) {
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
        }
        if(attacking == true) {
            switch(attackDirection) {
                case "up":
                    image = up1;
                    break;
                case "down":
                    image = down1;
                    break;
                case "left":
                    image = left1;
                    break;
                case "right":
                    image = right1;
                    break;
            }
        }

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }



        g2.drawImage(image, tempScreenX, tempScreenY, null);
        //reset alpha
        drawAttackImage(g2);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


    }
    public void drawAttackImage(Graphics2D g2) {
        attackImageCounter ++;
        int maxCounter;
        if(currentWeapon.canMeleeAttack == true) maxCounter = 2;
        else maxCounter = 60;
        if(attackImageCounter < maxCounter) {
            UtilityTool utool = new UtilityTool();
            image2 = this.currentWeapon.down1;
            int scale;
            if (currentWeapon.canMeleeAttack == true) scale = 48;
            else scale = 48;
            image2 = utool.scaleImage(this.currentWeapon.down1, scale, scale);
            if (attackDirection == "right") {
                image2 = utool.rotateImage(image2, 90);
                g2.drawImage(image2, screenX + gp.tileSize, screenY, null);
            }
            else if (attackDirection == "left") {
                image2 = utool.rotateImage(image2, 270);
                g2.drawImage(image2, screenX - gp.tileSize, screenY, null);
            }
            else if (attackDirection == "down") {
                image2 = utool.rotateImage(image2, 180);
                g2.drawImage(image2, screenX, screenY + gp.tileSize, null);
            }
            else if (attackDirection == "up") {
                g2.drawImage(image2, screenX, screenY - gp.tileSize, null);
            }
        }
    }

}


/*
 public void pickUpObject(int i) {
        if(i != 999) {
            String objectName = gp.obj[i].name;
            switch(objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    }
                    else gp.ui.showMessage("You need a key!");
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }
 */
