package skill_tree;

import main.GamePanel;
import main.UtilityTool;
import skill_tree.talents.TAL_Def;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SkillTree {
    public static BufferedImage defenseTalent;
    public int currentDepth = 0;
    public int currentIndexInRow = 0;
    public int numberOfDepths = 0;

    public GamePanel gp;
    public ArrayList<Integer> numOfTalentsInDepth = new ArrayList<>(Collections.nCopies(60, 0));
    public ArrayList<Talent>[] talentsInOrderInDepth = new ArrayList[20];
    public SkillTree(GamePanel gp) {
        this.gp = gp;
        for(int i = 0; i < talentsInOrderInDepth.length; i++) {
            talentsInOrderInDepth[i] = new ArrayList<Talent>();
        }

        loadImages();
        setTalents();
        calcNumberOfTalentsInDepths();
        calcNumberOfDepths();
    }

    public void draw(Graphics2D g2) {
        int x = gp.tileSize;
        int y = gp.tileSize/2;
        int width = gp.tileSize*18;
        int height = gp.tileSize*10;
        gp.ui.drawSubWindow(x, y, width, height);
        y = gp.tileSize;
        x = gp.tileSize*14;
        width = 7;
        height = gp.tileSize*9;
        g2.fillRoundRect(x,y,width,height,10,10);
        drawTalents(g2);
        drawCursor(g2);
        drawConnections(g2);
        drawDescription(g2);
    }
    public void setTalents() {
        addNewTalent(new TAL_Def(gp,0,0),0,0);
        addNewTalent(new TAL_Def(gp,0,1),0,0);
        addNewTalent(new TAL_Def(gp,0,2),0,0);
        addNewTalent(new TAL_Def(gp,1,0),0,0);
        addNewTalent(new TAL_Def(gp,1,1),0,0);
        addNewTalent(new TAL_Def(gp,1,2),0,1);
        addNewTalent(new TAL_Def(gp,1,3),0,1);
        addNewTalent(new TAL_Def(gp,1,4),0,2);
        addNewTalent(new TAL_Def(gp,1,5),0,2);
        addNewTalent(new TAL_Def(gp,2,0),1,0);
        addNewTalent(new TAL_Def(gp,2,2),1,3);
        addNewTalent(new TAL_Def(gp,2,5),1,5);
        addNewTalent(new TAL_Def(gp,3,0),2,0);
        addNewTalent(new TAL_Def(gp,3,1),2,0);
        addNewTalent(new TAL_Def(gp,3,2),2,2);
        addNewTalent(new TAL_Def(gp,3,3),2,2);
        addNewTalent(new TAL_Def(gp,3,4),2,5);
        addNewTalent(new TAL_Def(gp,3,5),2,5);
    }
    public void addNewTalent(Talent talent, int requiredTalentDepth, int requiredTalentIndexInRow) {
        gp.talents[talent.depth][talent.indexInRow] = talent;
        gp.talents[talent.depth][talent.indexInRow].requirement =
                gp.talents[requiredTalentDepth][requiredTalentIndexInRow];
        talentsInOrderInDepth[talent.depth].add(talent);
    }
    public void calcNumberOfTalentsInDepths() {
        for(int i = 0; i < gp.talents.length; i++) {
            for(int j = 0; j < gp.talents[0].length; j++) {
                if(gp.talents[i][j] != null) numOfTalentsInDepth.set(i,numOfTalentsInDepth.get(i)+1);
            }
        }
    }
    public void calcNumberOfDepths() {
        for(int i = 0; i < gp.talents.length; i++) {
            if(numOfTalentsInDepth.get(i) > 0) {
                numberOfDepths++;
            }
            else break;
        }
    }
    public void drawTalents(Graphics2D g2) {
        int x = 2*gp.tileSize;
        int y = gp.tileSize;
        //first depth
        for(int i = 0; i < Math.min(gp.talents.length,1); i++) {
            for(int j = 0; j < gp.talents[0].length; j++) {
                if(gp.talents[i][j] != null) {
                    if(gp.talents[i][j].obtained == true) {
                        g2.setColor(new Color(240,190,90));
                        g2.fillRoundRect(x,y,gp.tileSize,gp.tileSize,10,10);
                        g2.setColor(Color.white);
                    }
                    g2.setColor(Color.darkGray);
                    g2.drawRoundRect(x-2,y-2,gp.tileSize+4,gp.tileSize+4,10,10);
                    g2.setColor(Color.white);
                    g2.drawImage(gp.talents[i][j].image, x, y, null);
                    gp.talents[i][j].x = x;
                    gp.talents[i][j].y = y;
                }
                x += 5*gp.tileSize;
            }
            y += 2*gp.tileSize;
            x = 4*gp.tileSize;
        }
        x = 2*gp.tileSize;
        //next depths
        for(int i = 1; i < Math.max(gp.talents.length, 1); i++) {
            for(int j = 0; j < gp.talents[0].length; j++) {
                if(gp.talents[i][j] != null) {
                    if(gp.talents[i][j].obtained == true) {
                        g2.setColor(new Color(240,190,90));
                        g2.fillRoundRect(x,y,gp.tileSize,gp.tileSize,10,10);
                        g2.setColor(Color.white);
                    }
                    g2.setColor(Color.darkGray);
                    g2.drawRoundRect(x-2,y-2,gp.tileSize+4,gp.tileSize+4,10,10);
                    g2.setColor(Color.white);
                    g2.drawImage(gp.talents[i][j].image, x, y, null);
                    gp.talents[i][j].x = x;
                    gp.talents[i][j].y = y;
                }
                x += 2*gp.tileSize;
            }
            y += 2*gp.tileSize;
            x = 2*gp.tileSize;
        }
    }
    public void drawCursor(Graphics2D g2) {
        int x = 2*gp.tileSize;
        int y = 0;
        int width = gp.tileSize;
        int height = gp.tileSize;
        int addX = 0;
        if(currentDepth == 0) {
            addX = 5*gp.tileSize;
            y = gp.tileSize;
        }
        else {
            addX = 2*gp.tileSize;
            y = gp.tileSize*3 + 2*gp.tileSize*(currentDepth-1);
        }

        int index = 0;
        int additional = 0;
        if(currentIndexInRow > 0) {
            for (int i = 1; i < gp.talents[currentDepth].length; i++) {
                if (gp.talents[currentDepth][i] == null) {
                    additional++;
                }
                else {
                    index++;
                    x += addX;
                }
                if(index == currentIndexInRow) break;
            }
        }
        x += addX*additional;

        g2.drawRoundRect(x, y, width, height, 10, 10);
    }
    public void loadImages() {
        defenseTalent = setup("/talents/tal_def",gp.tileSize,gp.tileSize);
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
    public void obtainTalent() {
        if(gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].
                get(gp.skillTree.currentIndexInRow).obtained == false
                &&
                gp.player.availableSkillPoints > 0
                &&
                (gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].
                        get(gp.skillTree.currentIndexInRow).requirement.obtained == true
                        ||
                        gp.skillTree.currentDepth == 0)) {

            gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].get(gp.skillTree.currentIndexInRow).obtained = true;
            gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].get(gp.skillTree.currentIndexInRow).effect();
            gp.player.availableSkillPoints --;
            gp.player.allSkillPoints ++;
        }
    }
    public void drawConnections(Graphics2D g2) {
        for(int i = 1; i < gp.talents.length; i++) {
            for(int j = 0; j < gp.talents[0].length; j++) {
                if(gp.talents[i][j] != null) {
                    if(gp.talents[i][j].obtained == true) {
                        g2.setColor(Color.green);
                    }
                    g2.drawLine(gp.talents[i][j].x + gp.tileSize/2,
                            gp.talents[i][j].y,
                            gp.talents[i][j].requirement.x + gp.tileSize/2,
                            gp.talents[i][j].requirement.y + gp.tileSize);
                    g2.setColor(Color.white);
                }
            }
        }
    }
    public void drawDescription(Graphics2D g2) {
        int textX = gp.tileSize*15 - 10;
        int textY = gp.tileSize*2;
        g2.setColor(Color.white);
        g2.setFont(gp.ui.purisaB);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,28F));
        for (String line : gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].
                get(gp.skillTree.currentIndexInRow).title.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 32;
        }

        int y = gp.tileSize*2 + 20;
        int x = gp.tileSize*14 + 23;
        int width = gp.tileSize*4;
        int height = 7;
        g2.fillRoundRect(x,y,width,height,10,10);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        textX = gp.tileSize*14 + 23;
        textY = gp.tileSize*3 + 20;
        for (String line : gp.skillTree.talentsInOrderInDepth[gp.skillTree.currentDepth].
                get(gp.skillTree.currentIndexInRow).description.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 32;
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,14F));
        textX = gp.tileSize*14 + 23;
        textY = gp.tileSize*9;
        g2.drawString("Available skill points: " + gp.player.availableSkillPoints, textX, textY);
        textY = gp.tileSize*10;
        g2.drawString("All skill points: " + gp.player.allSkillPoints, textX, textY);
    }
    public int findNearestIndexInRow(int x) {
        int flag = 1;
        int index = 0;
        int length = 1;
        while(true) {
           if(returnTalentWithXInDepth(currentDepth, x) != null) {
               index = returnTalentWithXInDepth(currentDepth, x).indexInRow;
               for(int i = 0; i < talentsInOrderInDepth[currentDepth].size(); i++) {
                   if(talentsInOrderInDepth[currentDepth].get(i).indexInRow == index) {
                       index = i;
                       break;
                   }
               }
               break;
           }
           if(flag == 1) {
               x -= gp.tileSize*length;
               flag = 0;
               length++;
           }
           else if(flag == 0) {
               x += gp.tileSize*length;
               flag = 1;
               length++;
           }
        }
        return index;
    }
    public Talent returnTalentWithXInDepth(int depth, int x) {
        for(int i = 0; i < gp.talents[depth].length; i++) {
            if(gp.talents[depth][i] != null) {
                if(gp.talents[depth][i].x == x) {
                    return gp.talents[depth][i];
                }
            }
        }
        return null;
    }

}
