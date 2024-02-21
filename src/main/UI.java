package main;

import entity.Entity;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;

    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_half, crystal_blank, coin;
    public boolean messageOn = false;
    public boolean gameFinished = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;
    int counter = 0;

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
            drawPlayerLife();
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
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
        x = gp.screenWidth - 2*gp.tileSize;
        y = gp.tileSize/2;
        g2.drawImage(coin, x, y, null);

        y += gp.tileSize/2 + 12;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.setColor(Color.black);
        String value = String.valueOf(gp.player.coin);
        x -= value.length() * 15;
        g2.drawString(value,x+2,y+2);
        g2.setColor(Color.white);
        g2.drawString(value,x,y);

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
        g2.drawString(value,x,y);
    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));

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
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
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
        final int frameY = gp.tileSize - 24;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize - 12;
        final int lineHeight = 34;

        // names
        g2.drawString("Level",textX,textY);
        textY += lineHeight;
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
        g2.drawString("Weapon",textX,textY);
        textY += lineHeight + 15;
        g2.drawString("Shield",textX,textY);
        textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize - 12;
        String value;
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
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

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);


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
    public void drawInventory() {
        int frameX = gp.tileSize*12;
        int frameY = gp.tileSize - 24;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        for(int i = 0; i < gp.player.inventory.size(); i++) {
            //equip cursor
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        //cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight, 10, 10);
        //description
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*5;


        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));
        int itemIndex = getItemIndexOnSlot();
        if(itemIndex < gp.player.inventory.size()) {
            drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line,textX,textY);
                textY += 32;
            }
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
        g2.drawString("Shoot/Cast", textX, textY);
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
        g2.drawString("ENTER", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("F", textX, textY);
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
    public int getItemIndexOnSlot() {
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
        wallpaper = entity.setup("/objects/wallp",1080,607);
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
