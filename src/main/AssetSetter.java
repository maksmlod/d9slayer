package main;

import animals.GuineaPig;
import entity.Entity;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import entity.NPC_Spider_Merchant;
import monster.*;
import object.*;
import object.weapon.*;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_Reset;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadLocalRandom;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {
        int mapNum = 0;
        int i = 0;
/*
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 40 * gp.tileSize;
        i++;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = 37 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
        i++;

        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].worldX = 10 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 7 * gp.tileSize;
        i++;

        gp.obj[mapNum][i] = new OBJ_Boots(gp);
        gp.obj[mapNum][i].worldX = 37 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
        i++;
       */
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 18 * gp.tileSize;
        i++;

        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 18 * gp.tileSize;
        i++;




    }

    public void setNPC() {

        int mapNum = 0;
        int i = 0;
        /*
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*27;
        gp.npc[mapNum][i].worldY = gp.tileSize*17;
        i++;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*25;
        gp.npc[mapNum][i].worldY = gp.tileSize*22;
        i++;
         */

        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*17;
        gp.npc[mapNum][i].worldY = gp.tileSize*22;
        i++;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*19;
        gp.npc[mapNum][i].worldY = gp.tileSize*19;
        i++;

        gp.npc[mapNum][i] = new GuineaPig(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*26;
        gp.npc[mapNum][i].worldY = gp.tileSize*17;
        gp.npc[mapNum][i].borderXLeft = gp.tileSize*25;
        gp.npc[mapNum][i].borderXRight = gp.tileSize*27;
        gp.npc[mapNum][i].borderYUp = gp.tileSize*16;
        gp.npc[mapNum][i].borderYDown = gp.tileSize*18;
        i++;

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Spider_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*11;
        gp.npc[mapNum][i].worldY = gp.tileSize*23;
        i++;


    }

    public void setMonster(int selectedMap, boolean allMap) {
        int mapNum = 0;
        /*
        if(selectedMap == mapNum || allMap == true) {
            int i = 0;
            gp.monster[mapNum][i] = new MON_GreenSlime(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 38;
            gp.monster[mapNum][i].worldY = gp.tileSize * 20;
            i++;

            gp.monster[mapNum][i] = new MON_GreenSlime(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 38;
            gp.monster[mapNum][i].worldY = gp.tileSize * 22;
            i++;

            gp.monster[mapNum][i] = new MON_GreenSlime(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 38;
            gp.monster[mapNum][i].worldY = gp.tileSize * 24;
            i++;

            gp.monster[mapNum][i] = new MON_GreySlime(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 40;
            gp.monster[mapNum][i].worldY = gp.tileSize * 24;
            i++;

            gp.monster[mapNum][i] = new MON_WhiteSlime(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 39;
            gp.monster[mapNum][i].worldY = gp.tileSize * 33;
            i++;
            //debug monsters
            gp.monster[mapNum][i] = new MON_GreenSlime(gp);
            gp.monster[mapNum][i].speed = 0;
            gp.monster[mapNum][i].dropAmount = 10;
            gp.monster[mapNum][i].canAttack = false;
            gp.monster[mapNum][i].worldX = gp.tileSize * 24;
            gp.monster[mapNum][i].worldY = gp.tileSize * 16;
            i++;

            gp.monster[mapNum][i] = new MON_GreenSlime(gp);
            gp.monster[mapNum][i].speed = 0;
            gp.monster[mapNum][i].dropAmount = 10;
            gp.monster[mapNum][i].canAttack = false;
            gp.monster[mapNum][i].worldX = gp.tileSize * 24;
            gp.monster[mapNum][i].worldY = gp.tileSize * 19;
            i++;
        }
        */
        mapNum = 1;
        if(selectedMap == mapNum || allMap == true) {
            int i = 0;
            setPackOfMonsters(new MON_OrangeSpider(gp), 17, 19, 3, 6,
                    3,i,mapNum);
            i = 3;
            setPackOfMonsters(new MON_OrangeSpider(gp), 32, 36, 6, 9,
                    4,i,mapNum);
            i = 7;
            setPackOfMonsters(new MON_OrangeSpider(gp), 20, 24, 30, 34,
                    4,i,mapNum);
            i = 11;
            setPackOfMonsters(new MON_OrangeSpider(gp), 4, 9, 36, 42,
                    7,i,mapNum);
            i = 18;
            setPackOfMonsters(new MON_OrangeSpider(gp), 41, 46, 21, 24,
                    5,i,mapNum);
            i = 23;


            gp.monster[mapNum][i] = new MON_BigOrangeSpider(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 44;
            gp.monster[mapNum][i].worldY = gp.tileSize * 42;
            i++;
        }
        mapNum = 2;
        if(selectedMap == mapNum || allMap == true) {
            int i = 0;
            gp.monster[mapNum][i] = new MON_Priest(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 34;
            gp.monster[mapNum][i].worldY = gp.tileSize * 20;
            i++;
        }
    }


    public void setInteractiveTile() {

        int mapNum = 0;
        int i = 0;
        /*
        gp.iTile[mapNum][i] = new IT_DryTree(gp,32,22);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,32,21);
        i++;
        */
        gp.iTile[mapNum][i] = new IT_DryTree(gp,25,23);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,25,24);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,25,25);
        i++;
        gp.iTile[mapNum][i] = new IT_Reset(gp,31,19);
        i++;
        mapNum = 1;
        gp.iTile[mapNum][i] = new IT_Reset(gp,27,20);
        i++;
        mapNum = 2;
        gp.iTile[mapNum][i] = new IT_Reset(gp,27,20);
        i++;



    }


    public void setPackOfMonsters(Entity monster, int borderXLeft, int borderXRight,
                                  int borderYUp, int borderYDown, int quantity, int i, int mapNum) {
        Class<?> monsterClass = monster.getClass();
        int[][] spawns = new int[51][51];
        try {
            for(int j = 0; j < quantity; j++) {
                gp.monster[mapNum][i] = (Entity)monsterClass.getDeclaredConstructor(GamePanel.class).newInstance(gp);
                boolean flag = true;
                int counter = 0;
                int randomX = 0;
                int randomY = 0;
                while(flag) {
                    randomX = ThreadLocalRandom.current().nextInt(borderXLeft, borderXRight + 1);
                    randomY = ThreadLocalRandom.current().nextInt(borderYUp, borderYDown + 1);
                    if(spawns[randomX][randomY] == 0) {
                        flag = false;
                        spawns[randomX][randomY] = 1;
                    }
                    counter ++;
                    if(counter > 50) break;
                }
                gp.monster[mapNum][i].spawnCol = randomX;
                gp.monster[mapNum][i].spawnRow = randomY;

                gp.monster[mapNum][i].borderXRight = borderXRight*gp.tileSize;
                gp.monster[mapNum][i].borderXLeft = borderXLeft*gp.tileSize;
                gp.monster[mapNum][i].borderYDown = borderYDown*gp.tileSize;
                gp.monster[mapNum][i].borderYUp = borderYUp*gp.tileSize;
                gp.monster[mapNum][i].setTempBorders();

                gp.monster[mapNum][i].worldX = gp.tileSize * gp.monster[mapNum][i].spawnCol;
                gp.monster[mapNum][i].worldY = gp.tileSize * gp.monster[mapNum][i].spawnRow;
                i++;
            }
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


}

/*
  gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 37 * gp.tileSize;
        gp.obj[2].worldY = 7 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;

        gp.obj[7] = new OBJ_Boots(gp);
        gp.obj[7].worldX = 37 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;
 */
