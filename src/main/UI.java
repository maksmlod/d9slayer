package main;

import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica;
    public Font purisaB;

    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_half, crystal_blank, coin, starImage;
    public boolean messageOn = false;
    public boolean gameFinished = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;
    public int commandNum2 = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
    Color common = new Color(255,255,255);
    Color uncommon = new Color(30,255,0);
    Color rare = new Color(0,112,221);
    Color epic = new Color(163,53,238);
    Color legendary = new Color(255,128,0);

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //       OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;

        //create hud object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_half = crystal.image2;
        crystal_blank = crystal.image3;
        Entity coinEntity = new OBJ_Coin(gp);
        coin = coinEntity.image;
    }
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2) throws IOException {
        this.g2 = g2;
        //g2.setFont(maruMonica);
        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if(gp.gameState == gp.skinsState) {
            drawSkins();
        }
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }
        if(gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if(gp.gameState == gp.transitionState) {
            drawTransition();
        }
        if(gp.gameState == gp.inventoryState) {
            drawInventory(gp.player, true, false);
        }
        if(gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
    }
    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //draw max life
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        //draw current life
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x+=gp.tileSize;
        }

        //draw mana
        x = (gp.tileSize/2)-3;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.maxMana/2) {
            g2.drawImage(crystal_blank,x,y,null);
            i++;
            x += 35;
        }
        x = (gp.tileSize/2)-3;
        y = (int)(gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.mana) {
            g2.drawImage(crystal_half,x,y,null);
            i++;
            if(i < gp.player.mana) {
                g2.drawImage(crystal_full, x, y, null);
            }
            i++;
            x += 35;
        }

        //coin
        x = (gp.tileSize/2)-3;
        y = (int)(gp.tileSize*3);
        g2.drawImage(coin, x, y, null);

        y += gp.tileSize/2 + 12;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.setColor(Color.black);
        String value = String.valueOf(gp.player.coin);
        x = getXforAlignToRightText(value, x);
        g2.drawString(value,x + 2*gp.tileSize + 2,y+2);
        g2.setColor(Color.white);
        g2.drawString(value,x + 2*gp.tileSize,y);

        //exp
        x = gp.screenWidth/2 - 2*gp.tileSize;
        y = gp.screenHeight - 2*gp.tileSize + gp.tileSize/3;
        int width = gp.tileSize*4;
        int height = 15;
        int width2 = (int)(gp.tileSize*4*((double)gp.player.exp / (double)gp.player.nextLevelExp));
        if(width2 > width) width2 = width;
        g2.setColor(Color.green);
        g2.fillRoundRect(x,y,width2,height,10,10);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12F));
        value = "Exp: " + gp.player.exp + "/" + gp.player.nextLevelExp;
        x = getXforCenteredText(value);
        y += 12;
        g2.drawString(value,x,y);

        //level
        y += 25;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
        value = "Level: " + gp.player.level;
        x = getXforCenteredText(value);
        g2.setColor(Color.black);
        g2.drawString(value,x + 2,y + 2);
        g2.setColor(Color.white);
        g2.drawString(value, x, y);

        //map info
        x = gp.tileSize;
        y = gp.screenHeight - gp.tileSize;
        String mapValue = null;
        if(gp.currentMap == 0) mapValue = "Lobby";
        if(gp.currentMap == 1) mapValue = "Spiderr";
        if(gp.currentMap == 2) mapValue = "Crest";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
        g2.setColor(Color.black);
        g2.drawString(mapValue,x + 2,y + 2);
        g2.setColor(Color.white);
        g2.drawString(mapValue,x,y);
    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 10F));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 25;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x ,y);
    }
    public void drawDialogueScreen() {
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize;
        y += gp.tileSize;


        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    public void drawCharacterScreen() {
        //create a frame
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*9;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize - 12;
        final int lineHeight = 34;

        // names
        g2.drawString("Level",textX,textY);
        textY += lineHeight*2;
        g2.drawString("Life",textX,textY);
        textY += lineHeight;
        g2.drawString("Mana",textX,textY);
        textY += lineHeight;
        g2.drawString("Strength",textX,textY);
        textY += lineHeight;
        g2.drawString("Dexterity",textX,textY);
        textY += lineHeight;
        g2.drawString("Attack",textX,textY);
        textY += lineHeight;
        g2.drawString("Defense",textX,textY);
        textY += lineHeight;
        g2.drawString("Exp",textX,textY);
        textY += lineHeight;
        g2.drawString("Next Level",textX,textY);
        textY += lineHeight;
        g2.drawString("Coin",textX,textY);
        textY += lineHeight + 30;

        //values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize - 12;
        String value;
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight*2;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;


    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);

        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0) {
            g2.drawString(">", x-40,y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += 65;
        g2.drawString(text,x,y);
        if(commandNum == 1) {
            g2.drawString(">", x-40,y);
        }
    }
    public void drawTransition() {
        counter ++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        if(counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }
    public void drawInventory(Entity entity, boolean cursor, boolean isTrading) {
        switch(subState) {
            case 0: show_items(entity, cursor, isTrading); break;
            case 1: wear_items(); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void show_items(Entity entity, boolean cursor, boolean isTrading) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player) {
            //int frameX = gp.tileSize*2;
            //int frameY = gp.tileSize - 24;
            //int frameWidth = gp.tileSize*6;
            //int frameHeight = gp.tileSize*5;
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        for(int i = 0; i < entity.inventory.size(); i++) {
            //equip cursor
            boolean isAccessory = false;

            for(int j = 0; j < gp.player.accessories.length; j++) {
                if(gp.player.accessories[j] == entity.inventory.get(i)) isAccessory = true;
            }
            if(entity.inventory.get(i) == entity.currentWeapon || isAccessory == true) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            //amount
            if(entity.inventory.get(i).amount > 1 && entity == gp.player) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,24F));
                int amountX;
                int amountY;
                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;


                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString(s, amountX-2, amountY-2);
            }

            if(entity == gp.player) {
                int eqNumber = 0;
                if (entity.inventory.get(i) == entity.currentWeapon) eqNumber = 1;
                else if (entity.inventory.get(i) == entity.accessories[0]) eqNumber = 2;
                else if (entity.inventory.get(i) == entity.accessories[1]) eqNumber = 3;
                else if (entity.inventory.get(i) == entity.accessories[2]) eqNumber = 4;
                else if (entity.inventory.get(i) == entity.accessories[3]) eqNumber = 5;
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
                g2.setColor(Color.black);
                if(eqNumber != 0) g2.drawString(Integer.toString(eqNumber),slotX+gp.tileSize-12 + 1,slotY+14 + 1);
                g2.setColor(Color.white);
                if(eqNumber != 0) g2.drawString(Integer.toString(eqNumber),slotX+gp.tileSize-12,slotY+14);

                //rarities
                Color color = null;
                if(entity.inventory.get(i).rarity == "common") color = common;
                else if(entity.inventory.get(i).rarity == "uncommon") color = uncommon;
                else if(entity.inventory.get(i).rarity == "rare") color = rare;
                else if(entity.inventory.get(i).rarity == "epic") color = epic;
                else if(entity.inventory.get(i).rarity == "legendary") color = legendary;
                if(color != null) {
                    Color tempColor = g2.getColor();
                    g2.setColor(color);
                    g2.fillRect(slotX,slotY + 45,gp.tileSize, 3);
                    g2.setColor(tempColor);
                }
            }


            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        //cursor
        if(cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //description
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 5;


            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                starImage = gp.player.setup("/objects/star", gp.tileSize/2, gp.tileSize/2);
                Color rarityColor = new Color(0,0,0);
                int rarityCount = 0;
                if(entity.inventory.get(itemIndex).rarity == "common") {rarityColor = common; rarityCount = 1;}
                else if(entity.inventory.get(itemIndex).rarity == "uncommon") {rarityColor = uncommon; rarityCount = 2;}
                else if(entity.inventory.get(itemIndex).rarity == "rare") {rarityColor = rare; rarityCount = 3;}
                else if(entity.inventory.get(itemIndex).rarity == "epic") {rarityColor = epic; rarityCount = 4;}
                else if(entity.inventory.get(itemIndex).rarity == "legendary") {rarityColor = legendary; rarityCount = 5;}

                g2.setColor(rarityColor);
                g2.drawString(entity.inventory.get(itemIndex).name,textX,textY);
                if(entity.inventory.get(itemIndex).albumOrigin != null) {
                    int width = g2.getFontMetrics().stringWidth(entity.inventory.get(itemIndex).name) + 10;
                    textX += width;
                    UtilityTool uTool = new UtilityTool();
                    BufferedImage image = entity.inventory.get(itemIndex).albumOrigin;
                    image = uTool.scaleImage(image,gp.tileSize/2,gp.tileSize/2);
                    g2.drawImage(image, textX, textY - gp.tileSize/2 + 4, null);
                    textX -= width;
                }
                textY += 5;
                textX = dFrameX + 20;
                for(int i = 0; i < rarityCount; i++) {
                    g2.drawImage(starImage,textX,textY,null);
                    textX += 20;
                }

                if(entity.inventory.get(itemIndex).armorSetOrigin != null) {
                    textX = dFrameX + 20;
                    textY += 40;
                    Font tempFont = g2.getFont();
                    g2.setColor(new Color(205,205,210));
                    g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 15F));
                    g2.drawString(entity.inventory.get(itemIndex).armorSetOrigin,textX,textY);
                    g2.setFont(tempFont);
                    textY -= 40;
                }

                textY += 75;
                textX = dFrameX + 20;
                g2.setColor(Color.white);
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }

                if(gp.keyH.enterPressed == true && entity.inventory.get(itemIndex).type == entity.type_accessory &&
                        isTrading == false) {
                    subState = 1;
                    commandNum = 1;
                }
                else if(gp.keyH.enterPressed == true && isTrading == false) {
                    gp.player.selectItem(0);
                }

                //dropping item
                if(gp.keyH.qPressed == true && entity == gp.player && entity.inventory.get(itemIndex) != entity.currentWeapon &&
                        entity.inventory.get(itemIndex) != entity.accessories[0] &&
                        entity.inventory.get(itemIndex) != entity.accessories[1] &&
                        entity.inventory.get(itemIndex) != entity.accessories[2] &&
                        entity.inventory.get(itemIndex) != entity.accessories[3] ) {
                    int xChange = 0;
                    int yChange = 0;
                    if(entity.direction == "up") {
                        entity.worldY -= (int)(1.5*gp.tileSize);
                        yChange = (int)(1.5*gp.tileSize);
                    }
                    else if(entity.direction == "down") {
                        entity.worldY += (int)(1.5*gp.tileSize);
                        yChange = -(int)(1.5*gp.tileSize);
                    }
                    else if(entity.direction == "left") {
                        entity.worldX -= (int)(1.5*gp.tileSize);
                        xChange = (int)(1.5*gp.tileSize);
                    }
                    else if(entity.direction == "right") {
                        entity.worldX += (int)(1.5*gp.tileSize);
                        xChange = -(int)(1.5*gp.tileSize);
                    }

                    entity.dropItem(entity.inventory.get(itemIndex));
                    entity.inventory.remove(itemIndex);
                    gp.keyH.qPressed = false;

                    entity.worldX += xChange;
                    entity.worldY += yChange;
                }
                else gp.keyH.qPressed = false;

            }
/*
            //inventory right (player's current items)
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize - 24;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);

            int lineHeight = 30;
            textX = frameX + 20;
            textY = frameY + gp.tileSize;
            g2.drawString("Weapon", textX, textY);
            textX += 2 * gp.tileSize;
            textY -= 30;
            g2.drawImage(entity.currentWeapon.down1, textX, textY, null);
            textY += 30;
            textX -= 2 * gp.tileSize;
            textY += lineHeight + lineHeight;

            g2.drawString("Shield", textX, textY);
            textX += 2 * gp.tileSize;
            textY -= 30;
            g2.drawImage(entity.currentShield.down1, textX, textY, null);
            textY += 30;
 */

            // current items:
            if(isTrading == false && entity == gp.player) {
                frameX = gp.tileSize * 2;
                frameY = gp.tileSize;
                frameWidth = (int)(gp.tileSize * 6);
                frameHeight = gp.tileSize * 10;
                drawSubWindow(frameX, frameY, frameWidth, frameHeight);
                frameX += gp.tileSize/2;
                frameY += gp.tileSize/2 + 20;
                int addY = (int)(gp.tileSize*1.85);

                g2.drawString("Weapon:",frameX,frameY);
                frameY += addY;
                g2.drawString("Accessory 1:",frameX,frameY);
                frameY += addY;
                g2.drawString("Accessory 2:",frameX,frameY);
                frameY += addY;
                g2.drawString("Accessory 3:",frameX,frameY);
                frameY += addY;
                g2.drawString("Accessory 4:",frameX,frameY);
                frameY = 2*gp.tileSize + 5;

                int defaultX = frameX;
                g2.drawImage(gp.player.currentWeapon.down1,frameX,frameY,null);
                frameX += gp.tileSize + 15;
                frameY += gp.tileSize/2;
                Color rarityColor = new Color(0,0,0);
                int rarityCount = 0;
                if(gp.player.currentWeapon.rarity == "common") {rarityColor = common; rarityCount = 1;}
                else if(gp.player.currentWeapon.rarity == "uncommon") {rarityColor = uncommon; rarityCount = 2;}
                else if(gp.player.currentWeapon.rarity == "rare") {rarityColor = rare; rarityCount = 3;}
                else if(gp.player.currentWeapon.rarity == "epic") {rarityColor = epic; rarityCount = 4;}
                else if(gp.player.currentWeapon.rarity == "legendary") {rarityColor = legendary; rarityCount = 5;}
                g2.setColor(rarityColor);
                g2.drawString(gp.player.currentWeapon.name, frameX, frameY);
                if(gp.player.currentWeapon.albumOrigin != null) {
                    int width = g2.getFontMetrics().stringWidth(gp.player.currentWeapon.name) + 10;
                    frameX += width;
                    UtilityTool uTool = new UtilityTool();
                    BufferedImage image = entity.inventory.get(itemIndex).albumOrigin;
                    image = uTool.scaleImage(image,gp.tileSize/2,gp.tileSize/2);
                    g2.drawImage(image, textX, textY - gp.tileSize/2 + 4, null);
                    frameX -= width;
                }
                frameY += 5;
                for(int i = 0; i < rarityCount; i++) {
                    g2.drawImage(starImage,frameX,frameY,null);
                    frameX += 20;
                }

                for(int i = 0; i < gp.player.accessorySize; i++) {
                    if(gp.player.accessories[i] != null) {
                        frameX = defaultX;
                        frameY += (int)(gp.tileSize*1.85) - gp.tileSize/2 - 5;
                        g2.drawImage(gp.player.accessories[i].down1,frameX,frameY,null);
                        frameX += gp.tileSize + 15;
                        frameY += gp.tileSize/2;
                        rarityColor = new Color(0,0,0);
                        rarityCount = 0;
                        if(gp.player.accessories[i].rarity == "common") {rarityColor = common; rarityCount = 1;}
                        else if(gp.player.accessories[i].rarity == "uncommon") {rarityColor = uncommon; rarityCount = 2;}
                        else if(gp.player.accessories[i].rarity == "rare") {rarityColor = rare; rarityCount = 3;}
                        else if(gp.player.accessories[i].rarity == "epic") {rarityColor = epic; rarityCount = 4;}
                        else if(gp.player.accessories[i].rarity == "legendary") {rarityColor = legendary; rarityCount = 5;}
                        g2.setColor(rarityColor);
                        g2.drawString(gp.player.accessories[i].name, frameX, frameY);
                        frameY += 5;
                        for(int j = 0; j < rarityCount; j++) {
                            g2.drawImage(starImage,frameX,frameY,null);
                            frameX += 20;
                        }
                    }
                    else {
                        frameY += (int)(gp.tileSize*1.85);
                    }
                }

            }

        }

    }
    public void wear_items() {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        frameX = gp.tileSize*12;
        frameY = gp.tileSize;
        frameWidth = gp.tileSize*6;
        frameHeight = gp.tileSize*5;
        slotCol = playerSlotCol;
        slotRow = playerSlotRow;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        for(int i = 0; i < gp.player.inventory.size(); i++) {
            //equip cursor
            boolean isAccessory = false;
            for(int j = 0; j < gp.player.accessories.length; j++) {
                if(gp.player.accessories[j] == gp.player.inventory.get(i)) isAccessory = true;
            }
            if(gp.player.inventory.get(i) == gp.player.currentWeapon || isAccessory == true) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            //amount
            if(gp.player.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,24F));
                int amountX;
                int amountY;
                String s = "" + gp.player.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;


                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString(s, amountX-2, amountY-2);
            }

                int eqNumber = 0;
                if (gp.player.inventory.get(i) == gp.player.currentWeapon) eqNumber = 1;
                else if (gp.player.inventory.get(i) == gp.player.accessories[0]) eqNumber = 2;
                else if (gp.player.inventory.get(i) == gp.player.accessories[1]) eqNumber = 3;
                else if (gp.player.inventory.get(i) == gp.player.accessories[2]) eqNumber = 4;
                else if (gp.player.inventory.get(i) == gp.player.accessories[3]) eqNumber = 5;
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
                g2.setColor(Color.black);
                if(eqNumber != 0) g2.drawString(Integer.toString(eqNumber),slotX+gp.tileSize-12 + 1,slotY+14 + 1);
                g2.setColor(Color.white);
                if(eqNumber != 0) g2.drawString(Integer.toString(eqNumber),slotX+gp.tileSize-12,slotY+14);

            //rarities
            Color color = null;
            if(gp.player.inventory.get(i).rarity == "common") color = common;
            else if(gp.player.inventory.get(i).rarity == "uncommon") color = uncommon;
            else if(gp.player.inventory.get(i).rarity == "rare") color = rare;
            else if(gp.player.inventory.get(i).rarity == "epic") color = epic;
            else if(gp.player.inventory.get(i).rarity == "legendary") color = legendary;
            if(color != null) {
                Color tempColor = g2.getColor();
                g2.setColor(color);
                g2.fillRect(slotX,slotY + 45,gp.tileSize, 3);
                g2.setColor(tempColor);
            }

            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //description
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 5;

        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));
        int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
        if (itemIndex < gp.player.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                starImage = gp.player.setup("/objects/star", gp.tileSize/2, gp.tileSize/2);
                Color rarityColor = new Color(0,0,0);
                int rarityCount = 0;
                if(gp.player.inventory.get(itemIndex).rarity == "common") {rarityColor = common; rarityCount = 1;}
                else if(gp.player.inventory.get(itemIndex).rarity == "uncommon") {rarityColor = uncommon; rarityCount = 2;}
                else if(gp.player.inventory.get(itemIndex).rarity == "rare") {rarityColor = rare; rarityCount = 3;}
                else if(gp.player.inventory.get(itemIndex).rarity == "epic") {rarityColor = epic; rarityCount = 4;}
                else if(gp.player.inventory.get(itemIndex).rarity == "legendary") {rarityColor = legendary; rarityCount = 5;}

                g2.setColor(rarityColor);
                g2.drawString(gp.player.inventory.get(itemIndex).name,textX,textY);
                textY += 5;
                textX = dFrameX + 20;
                for(int i = 0; i < rarityCount; i++) {
                    g2.drawImage(starImage,textX,textY,null);
                    textX += 20;
                }

                textY += 75;
                textX = dFrameX + 20;
                g2.setColor(Color.white);
                for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }

        frameX = gp.tileSize * 2;
        frameY = gp.tileSize;
        frameWidth = (int)(gp.tileSize * 6);
        frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        frameX += gp.tileSize/2;
        frameY += gp.tileSize/2 + 20;
        int addY = (int)(gp.tileSize*1.85);

        g2.drawString("Weapon:",frameX,frameY);
        frameY += addY;
        g2.drawString("Accessory 1:",frameX,frameY);
        frameY += addY;
        g2.drawString("Accessory 2:",frameX,frameY);
        frameY += addY;
        g2.drawString("Accessory 3:",frameX,frameY);
        frameY += addY;
        g2.drawString("Accessory 4:",frameX,frameY);
        frameY = 2*gp.tileSize + 5;

        int defaultX = frameX;
        g2.drawImage(gp.player.currentWeapon.down1,frameX,frameY,null);
        frameX += gp.tileSize + 15;
        frameY += gp.tileSize/2;
        Color rarityColor = new Color(0,0,0);
        int rarityCount = 0;
        if(gp.player.currentWeapon.rarity == "common") {rarityColor = common; rarityCount = 1;}
        else if(gp.player.currentWeapon.rarity == "uncommon") {rarityColor = uncommon; rarityCount = 2;}
        else if(gp.player.currentWeapon.rarity == "rare") {rarityColor = rare; rarityCount = 3;}
        else if(gp.player.currentWeapon.rarity == "epic") {rarityColor = epic; rarityCount = 4;}
        else if(gp.player.currentWeapon.rarity == "legendary") {rarityColor = legendary; rarityCount = 5;}
        g2.setColor(rarityColor);
        g2.drawString(gp.player.currentWeapon.name, frameX, frameY);
        if(gp.player.currentWeapon.albumOrigin != null) {
            int width = g2.getFontMetrics().stringWidth(gp.player.currentWeapon.name) + 10;
            frameX += width;
            g2.drawImage(gp.player.currentWeapon.albumOrigin, frameX, frameY - gp.tileSize/2 + 4, null);
            frameX -= width;
        }
        frameY += 5;
        for(int i = 0; i < rarityCount; i++) {
                    g2.drawImage(starImage,frameX,frameY,null);
                    frameX += 20;
                }
        for(int i = 0; i < gp.player.accessorySize; i++) {
                    if(gp.player.accessories[i] != null) {
                        frameX = defaultX;
                        frameY += (int)(gp.tileSize*1.85) - gp.tileSize/2 - 5;
                        g2.drawImage(gp.player.accessories[i].down1,frameX,frameY,null);
                        frameX += gp.tileSize + 15;
                        frameY += gp.tileSize/2;
                        rarityColor = new Color(0,0,0);
                        rarityCount = 0;
                        if(gp.player.accessories[i].rarity == "common") {rarityColor = common; rarityCount = 1;}
                        else if(gp.player.accessories[i].rarity == "uncommon") {rarityColor = uncommon; rarityCount = 2;}
                        else if(gp.player.accessories[i].rarity == "rare") {rarityColor = rare; rarityCount = 3;}
                        else if(gp.player.accessories[i].rarity == "epic") {rarityColor = epic; rarityCount = 4;}
                        else if(gp.player.accessories[i].rarity == "legendary") {rarityColor = legendary; rarityCount = 5;}
                        g2.setColor(rarityColor);
                        g2.drawString(gp.player.accessories[i].name, frameX, frameY);
                        frameY += 5;
                        for(int j = 0; j < rarityCount; j++) {
                            g2.drawImage(starImage,frameX,frameY,null);
                            frameX += 20;
                        }
                    }
                    else {
                        frameY += (int)(gp.tileSize*1.85);
                    }
                }


        cursorX = gp.tileSize * 2 + 20;
        cursorY = gp.tileSize + 18;
        cursorWidth = (int)(gp.tileSize * 5) + 5;
        cursorHeight = gp.tileSize * 2;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        if(commandNum == 1) {
            cursorY += addY;
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        }
        else if(commandNum == 2) {
            cursorY += 2*addY;
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        }
        else if(commandNum == 3) {
            cursorY += 3*addY;
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        }
        else if(commandNum == 4) {
            cursorY += 4*addY;
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        }
        if(gp.keyH.enterPressed == true) {
            gp.player.selectItem(gp.ui.commandNum-1);
            subState = 0;
            commandNum = 0;
        }
    }
    public void drawOptionsScreen() throws IOException {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_control(frameX, frameY); break;
            case 2: options_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY) throws IOException {
        int textX;
        int textY;
        //title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        //full screen
        textX = frameX + gp.tileSize;
        textY = gp.tileSize * 4;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                if(gp.fullScreanOn == false) {
                    gp.fullScreanOn = true;
                    gp.setFullScreen();
                }
                else {
                    gp.fullScreanOn = false;
                    gp.setSmallScreen();
                }
            }
        }
        if(commandNum == 5 && gp.keyH.enterPressed == true) {
            gp.gameState = gp.playState;
            commandNum = 0;
            subState = 0;
        }
        //music
        textY += gp.tileSize;
        g2.drawString("Music",textX,textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
        }
        //se
        textY += gp.tileSize;
        g2.drawString("SE",textX,textY);
        if(commandNum == 2) {
            g2.drawString(">", textX-25, textY);
        }
        //control
        textY += gp.tileSize;
        g2.drawString("Control",textX,textY);
        if(commandNum == 3) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 1;
                commandNum = 0;
            }
        }
        //end game
        textY += gp.tileSize;
        g2.drawString("End Game",textX,textY);
        if(commandNum == 4) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }
        //back
        textY += gp.tileSize*2;
        g2.drawString("Back",textX,textY);
        if(commandNum == 5) {
            g2.drawString(">", textX-25, textY);
        }

        //full screen check box
        textX = frameX + (int)(gp.tileSize*6);
        textY = frameY + (int)(gp.tileSize*2.5);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24,24);
        if(gp.fullScreanOn == true) g2.fillRect(textX,textY,24,24);

        //music volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 80, 24);
        int volumeWidth = 16 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        //se volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 80, 24);
        volumeWidth = 16 * gp.sound.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();
    }
    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize/2;
        textY += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));
        g2.drawString("Move", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Inventory", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Character Screen", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("ARROWS", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("E", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("C", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("P", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("ESC", textX, textY);

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*2;

        currentDialogue = "Quit the game\nand return to\nthe title screen?";
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                gp.stopMusic();
                gp.gameState = gp.titleState;
            }
        }
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 230);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public void drawTitleScreen() {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //wallpaper
        BufferedImage wallpaper;
        Entity entity = new Entity(gp);
        wallpaper = entity.setup("/objects/wallp",1080/2,607/2);
        g2.drawImage(wallpaper, 0, 24, gp.screenWidth,gp.screenHeight - 3*gp.tileSize, null);

        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "D9 Slayer";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*2;
        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        //main color
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        //character image
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y +=gp.tileSize*2;

        //menu
        y+=gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        text = "New game";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Load game";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    public void drawSkins() {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,35F));
        g2.setColor(Color.WHITE);
        String text = "Choose your skin";
        int x = getXforCenteredText(text);
        int y = (int)(gp.tileSize*1.5);
        g2.drawString(text, x ,y);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
        y+=gp.tileSize*2;
        x = gp.tileSize *2;

        //skins
        BufferedImage skin;
        Entity entity = new Entity(gp);
        //ecco
        g2.drawString("Ecco2k",x,y);
        x += gp.tileSize*5;
        y -= gp.tileSize+10;
        skin = entity.setup("/player/ecco/ecco1_down_1",gp.tileSize*2,gp.tileSize*2);
        g2.drawImage(skin, x,y,null);

        y += gp.tileSize+10;
        x+= gp.tileSize*3;
        g2.drawString("\"World Tour\" variant",x,y);
        x-= gp.tileSize*3;
        y -= gp.tileSize+10;

        y += gp.tileSize *4;
        //thaiboy
        x -= gp.tileSize*5;
        g2.drawString("Thaiboy Digital",x,y);
        x += gp.tileSize*5;
        y -= gp.tileSize+10;
        skin = entity.setup("/player/thaiboy/thaiboy1_down_1",gp.tileSize*2,gp.tileSize*2);
        g2.drawImage(skin, x,y,null);

        y += gp.tileSize+10;
        x+= gp.tileSize*3;
        g2.drawString("\"Odwolane\" variant",x,y);
        x-= gp.tileSize*3;
        y -= gp.tileSize+10;

        y += gp.tileSize *4;
        //bladee
        x -= gp.tileSize*5;
        g2.drawString("Bladee",x,y);
        x += gp.tileSize*5;
        y -= gp.tileSize+10;
        skin = entity.setup("/player/bladee/bladee1_down_1",gp.tileSize*2,gp.tileSize*2);
        g2.drawImage(skin, x,y,null);

        y += gp.tileSize+10;
        x+= gp.tileSize*3;
        g2.drawString("\"Into Dust\" variant",x,y);
        x-= gp.tileSize*3;
        y -= gp.tileSize+10;

        //cursor
        x -= gp.tileSize*5;
        y -= gp.tileSize*4 + 18;
        if(commandNum == 0) {
            g2.drawString(">",x-45,y);
            if(gp.keyH.enterPressed == true) {
                gp.player.getPlayerImage("ecco");
                gp.gameState = gp.playState;
                gp.playMusic(0);
                gp.keyH.enterPressed = false;
            }
        }
        if(commandNum == 1) {
            g2.drawString(">",x-45,y + 3*gp.tileSize - 10);
            if(gp.keyH.enterPressed == true) {
                gp.player.getPlayerImage("thaiboy");
                gp.gameState = gp.playState;
                gp.playMusic(0);
                gp.keyH.enterPressed = false;
            }
        }
        if(commandNum == 2) {
            g2.drawString(">",x-45,y + 6*gp.tileSize - 20);
            if(gp.keyH.enterPressed == true) {
                gp.player.getPlayerImage("bladee");
                gp.gameState = gp.playState;
                gp.playMusic(0);
                gp.keyH.enterPressed = false;
            }
        }

    }
    public void drawTradeScreen() {
        switch(subState) {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select() {
        drawDialogueScreen();
        int x = gp.tileSize*15;
        int y = gp.tileSize*4;
        int width = gp.tileSize*4;
        int height = (int)(gp.tileSize*3.5);
        drawSubWindow(x,y,width,height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy",x,y);
        if(commandNum == 0) {
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true) {
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell",x,y);
        if(commandNum == 1) {
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true) {
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave",x,y);
        if(commandNum == 2) {
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true) {
                commandNum = 0;
                gp.gameState = gp.playState;
            }
        }
    }
    public void trade_buy() {
        show_items(gp.player, false, true);
        show_items(npc, true, true);


        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;

        //coin window
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Money: " + gp.player.coin, x+24, y+36);
        g2.drawString("[ESC] Back", x+24, y+75);

        //price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()) {
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            if(npc.inventory.get(itemIndex).needItemToBuy == false)
                g2.drawImage(coin,x+10,y+8,32,32,null);
            else
                g2.drawImage(npc.inventory.get(itemIndex).neededItemToBuy.down1,x+10,y+8,32,32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*8-20);
            g2.drawString(text, x, y+31);

            if(gp.keyH.enterPressed == true) {
                if(npc.inventory.get(itemIndex).needItemToBuy == false) {
                    if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                        subState = 0;
                        gp.gameState = gp.dialogueState;
                        currentDialogue = "You need more coin!";
                        drawDialogueScreen();
                    } else {
                        if (npc.inventory.get(itemIndex).stackable == true) {
                            if (gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
                                gp.player.coin -= npc.inventory.get(itemIndex).price;
                            } else {
                                subState = 0;
                                gp.gameState = gp.dialogueState;
                                currentDialogue = "Your inventory is full!";
                                drawDialogueScreen();
                            }
                        } else {
                            if (gp.player.inventory.size() == gp.player.maxInventorySize) {
                                subState = 0;
                                gp.gameState = gp.dialogueState;
                                currentDialogue = "Your inventory is full!";
                                drawDialogueScreen();
                            } else {
                                gp.player.coin -= npc.inventory.get(itemIndex).price;
                                gp.player.inventory.add((Entity) getItemFromNpc(itemIndex));
                            }
                        }
                    }
                }
                else {
                    boolean playerHasItem = false;
                    int neededItemIndex = 0;
                    for(int i = 0; i < gp.player.inventory.size(); i++) {
                        if(gp.player.inventory.get(i).getClass().equals(npc.inventory.get(itemIndex).neededItemToBuy.getClass())) {
                            playerHasItem = true;
                            neededItemIndex = i;
                            break;
                        }
                    }
                    if(playerHasItem == true) {
                        if(gp.player.inventory.get(neededItemIndex).amount >= npc.inventory.get(itemIndex).neededItemAmount) {
                            gp.player.inventory.get(neededItemIndex).amount -= npc.inventory.get(itemIndex).neededItemAmount;
                            if(gp.player.inventory.get(neededItemIndex).amount == 0)
                                gp.player.inventory.remove(neededItemIndex);
                            gp.player.inventory.add((Entity) getItemFromNpc(itemIndex));
                        }
                        else {
                            subState = 0;
                            gp.gameState = gp.dialogueState;
                            currentDialogue = "You need more: " + gp.player.inventory.get(neededItemIndex).name;
                            drawDialogueScreen();
                        }
                    }



                }
            }
        }
    }
    public Object getItemFromNpc(int itemIndex) {
        Class<?> newItemClass = npc.inventory.get(itemIndex).getClass();
        try {
            Object newItem = newItemClass.getDeclaredConstructor(GamePanel.class).newInstance(gp);
            return newItem;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void trade_sell() {
        show_items(gp.player, true, true);
        int x;
        int y;
        int width;
        int height;

        //hint window
        x = gp.tileSize*2;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //coin window
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Money: " + gp.player.coin, x+24, y+60);

        //price window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()) {
            x = (int)(gp.tileSize * 15.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin,x+10,y+8,32,32,null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*18-20);
            g2.drawString(text, x, y+34);

            if(gp.keyH.enterPressed == true) {
                boolean isAccessory = false;
                for(int j = 0; j < gp.player.accessories.length; j++) {
                    if(gp.player.accessories[j] == gp.player.inventory.get(itemIndex)) isAccessory = true;
                }
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                    isAccessory == true) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot sell equipped item!";
                }
                else {
                    if(gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount --;
                    }
                    else gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                }
            }
        }

    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    public int getXforAlignToLeftText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX + length;
        return x;
    }



}


/*
if(gameFinished == true) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength, x, y;

            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenWidth / 2 - (gp.tileSize * 5);
            g2.drawString(text, x, y);

            text = "Your time is: " + dFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenWidth / 2 + (gp.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenWidth / 2 + (gp.tileSize * 1);
            g2.drawString(text, x, y);

        }
        else {

            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("Key = " + gp.player.hasKey, 74, 65);


            // Time
            playTime += (double)1/60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);




            if(messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
 */
