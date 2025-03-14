package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    public boolean upArrowPressed, downArrowPressed, leftArrowPressed, rightArrowPressed;
    public boolean qPressed;
    boolean showDebugText = false;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState) {
            titleState(code);
        }
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
        else if(gp.gameState == gp.optionsState) {
            optionsState(code);
        }
        else if(gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        else if(gp.gameState == gp.skinsState) {
            skinsState(code);
        }
        else if(gp.gameState == gp.inventoryState) {
            inventoryState(code);
        }
        else if(gp.gameState == gp.tradeState) {
            tradeState(code);
        }
        else if(gp.gameState == gp.mapState) {
            mapState(code);
        }
        else if(gp.gameState == gp.skillTreeState) {
            skillTreeState(code);
        }
        else if(gp.gameState == gp.bossState) {
            bossState(code);
        }
    }
    public void titleState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.skinsState;
                //gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1) {

            }
            if(gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState(int code) {
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_K) {
            gp.gameState = gp.pauseState;
            gp.pauseMusic();
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
            gp.pauseMusic();
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightArrowPressed = true;
        }
        if(code == KeyEvent.VK_UP) {
            upArrowPressed = true;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftArrowPressed = true;
        }
        if(code == KeyEvent.VK_DOWN) {
            downArrowPressed = true;
        }
        if(code == KeyEvent.VK_E) {
            gp.gameState = gp.inventoryState;
        }
        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }
        if(code == KeyEvent.VK_X) {
            if(gp.map.miniMapOn == false) gp.map.miniMapOn = true;
            else gp.map.miniMapOn = false;
        }
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.skillTreeState;
        }

        //debug
        if(code == KeyEvent.VK_T) {
            if(showDebugText == false) {
                showDebugText = true;
                gp.player.getPlayerImage("debug");
                gp.tileM.drawPath = true;
            }
            else if(showDebugText = true) {
                showDebugText = false;
                gp.tileM.drawPath = false;
            }
        }
//        if(code == KeyEvent.VK_R) {
//            switch(gp.currentMap) {
//                case 0: gp.tileM.loadMap("/maps/world01.txt",0); break;
//                case 1: gp.tileM.loadMap("/maps/map1.txt",1); break;
//            }
//        }
    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_K) {
            gp.gameState = gp.playState;
            gp.resumeMusic();
        }
    }
    public void mapState(int code) {
        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.commandNum = 0;
            gp.gameState = gp.playState;
        }
    }
    public void optionsState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            gp.ui.commandNum = 0;
            gp.ui.subState = 0;
            gp.gameState = gp.playState;
            gp.resumeMusic();
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        int maxCommandNum = 5;
        if(code == KeyEvent.VK_W) {
            if(gp.ui.subState == 0) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = maxCommandNum;
                gp.playSE(9);
            }
            if(gp.ui.subState == 2) {
                maxCommandNum = 1;
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = maxCommandNum;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.subState == 0) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > maxCommandNum) gp.ui.commandNum = 0;
                gp.playSE(9);
            }
            if(gp.ui.subState == 2) {
                maxCommandNum = 1;
                gp.ui.commandNum++;
                if (gp.ui.commandNum > maxCommandNum) gp.ui.commandNum = 0;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_A) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.sound.volumeScale > 0) {
                    gp.sound.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.sound.volumeScale < 5) {
                    gp.sound.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum --;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum ++;
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if
        (code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.player.alive = true;
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1) {
                gp.player.alive = true;
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    public void skinsState(int code) {
        int maxCommandNum = 2;
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = maxCommandNum;
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > maxCommandNum) gp.ui.commandNum = 0;
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }
    public void inventoryState(int code) {
        if(code == KeyEvent.VK_E) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_Q) {
            qPressed = true;
        }
        if(gp.ui.subState == 0) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.commandNum = 0;
                gp.ui.subState = 0;
                gp.gameState = gp.playState;
            }
            if(code == KeyEvent.VK_W) {
                if(gp.ui.playerSlotRow != 0) {
                    gp.ui.playerSlotRow--;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_A) {
                if(gp.ui.playerSlotCol != 0) {
                    gp.ui.playerSlotCol--;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_S) {
                if(gp.ui.playerSlotRow != 3) {
                    gp.ui.playerSlotRow++;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_D) {
                if(gp.ui.playerSlotCol != 4) {
                    gp.ui.playerSlotCol++;
                    gp.playSE(9);
                }
            }
        }
        if(gp.ui.subState == 1) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 1) gp.ui.commandNum = 4;
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 4) gp.ui.commandNum = 1;
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_E) {
                gp.ui.commandNum = 0;
                gp.ui.subState = 0;
                gp.gameState = gp.playState;
            }
        }
    }
    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0) gp.ui.commandNum = 2;
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 2) gp.ui.commandNum = 0;
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.commandNum = 0;
                gp.gameState = gp.playState;
            }
        }
        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
                gp.ui.commandNum = 0;
                gp.gameState = gp.playState;
            }
        }
        if(gp.ui.subState == 2) {
            if(code == KeyEvent.VK_W) {
                if(gp.ui.playerSlotRow != 0) {
                    gp.ui.playerSlotRow--;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_A) {
                if(gp.ui.playerSlotCol != 0) {
                    gp.ui.playerSlotCol--;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_S) {
                if(gp.ui.playerSlotRow != 3) {
                    gp.ui.playerSlotRow++;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_D) {
                if(gp.ui.playerSlotCol != 4) {
                    gp.ui.playerSlotCol++;
                    gp.playSE(9);
                }
            }
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
                gp.ui.commandNum = 0;
                gp.gameState = gp.playState;
            }
        }
    }
    public void npcInventory(int code) {
        if(code == KeyEvent.VK_W) {
            if(gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_A) {
            if(gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D) {
            if(gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }
    public void skillTreeState(int code) {
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_D) {
            if(gp.skillTree.numOfTalentsInDepth.get(gp.skillTree.currentDepth)-1 > gp.skillTree.currentIndexInRow)
                gp.skillTree.currentIndexInRow++;
        }
        if(code == KeyEvent.VK_A) {
            if(gp.skillTree.currentIndexInRow > 0)
                gp.skillTree.currentIndexInRow--;
        }
        if(code == KeyEvent.VK_W) {
            if(gp.skillTree.currentDepth > 0) {
                int x = gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].
                        get(gp.skillTree.currentIndexInRow).x;
                gp.skillTree.currentDepth--;
                gp.skillTree.currentIndexInRow = gp.skillTree.findNearestIndexInRow(x);
            }
        }
        if(code == KeyEvent.VK_S) {
            if(gp.skillTree.currentDepth < gp.skillTree.numberOfDepths-1) {
                int x = gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].
                        get(gp.skillTree.currentIndexInRow).x;
                gp.skillTree.currentDepth++;
                gp.skillTree.currentIndexInRow = gp.skillTree.findNearestIndexInRow(x);
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.skillTree.obtainTalent();
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.skillTree.currentDepth = 0;
            gp.skillTree.currentIndexInRow = 0;
            gp.gameState = gp.playState;
        }
    }
    public void bossState(int code) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightArrowPressed = false;
        }
        if(code == KeyEvent.VK_UP) {
            upArrowPressed = false;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftArrowPressed = false;
        }
        if(code == KeyEvent.VK_DOWN) {
            downArrowPressed = false;
        }
    }
}
