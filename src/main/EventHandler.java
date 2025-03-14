package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;
    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow) {
                    row = 0;
                    map ++;
                }
            }
        }


    }

    public void checkEvent() {
        //check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if(canTouchEvent == true) {
            if (hit(0,26, 16, "right") == true) {
                //event happens
            }
            // lobby to spiderr
            else if(hit(0,20,26,"any") == true) {
                teleport(1,13,24);
                gp.stopMusic();
                gp.playMusic(15);
            }
            // spider to lobby
            else if(hit(1,13,24,"any") == true) {
                teleport(0,20,26);
                gp.stopMusic();
                gp.playMusic(0);
            }
            // lobby to crest
            else if(hit(0,22,26,"any") == true) {
                teleport(2,13,24);
                gp.stopMusic();
                gp.playMusic(16);
            }
            // crest to lobby
            else if(hit(2,13,24,"any") == true) {
                teleport(0,22,26);
                gp.stopMusic();
                gp.playMusic(0);
            }
            // reset button
            else if(hit(0,31,19,"any") == true) {
                gp.aSetter.setMonster(0,false);
                canTouchEvent = false;
            }
            else if(hit(1,27,20,"any") == true) {
                gp.aSetter.setMonster(1,false);
                canTouchEvent = false;
            }
            else if(hit(2,27,20,"any") == true) {
                gp.aSetter.setMonster(2,false);
                canTouchEvent = false;
            }
        }

    }
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX - 10 + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY  + gp.player.solidArea.y ;
            int tempWidth = gp.player.solidArea.width;
            int tempHeight = gp.player.solidArea.height;
            gp.player.solidArea.width += 20;
            gp.player.solidArea.height += 20;

            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x - 5;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y + 5;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            gp.player.solidArea.width = tempWidth;
            gp.player.solidArea.height = tempHeight;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
   //     eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingPool(int gameState) {
        if(gp.keyH.enterPressed == true) {
                gp.gameState = gameState;
                gp.player.attackCanceled = true;
                gp.ui.currentDialogue = "You drink the water.";
                gp.player.life = gp.player.maxLife;
                gp.player.mana = gp.player.maxMana;
        }
    }
    public void teleport(int map, int col, int row) {
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        gp.deleteProjectilesAndParticles();
        canTouchEvent = false;
        gp.playSE(13);
    }
    public void teleportWithoutTransition(int map, int col, int row) {
        tempMap = map;
        tempCol = col;
        tempRow = row;
        gp.deleteProjectilesAndParticles();

        gp.gameState = gp.playState;
        gp.currentMap = tempMap;
        gp.player.worldX = gp.tileSize * tempCol;
        gp.player.worldY = gp.tileSize * tempRow;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;

        canTouchEvent = false;
    }

}
