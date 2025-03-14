package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Particle extends Entity{
    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;
    BufferedImage image;
    boolean isDamageParticle = false;
    boolean isImageParticle = false;
    public Particle(GamePanel gp, Entity generator, Color color,
                    int size, int speed, int maxLife, int xd, int yd, String damageText, BufferedImage image) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
        this.damageText = damageText;
        this.image = image;

        life = maxLife;
        int offset = (gp.tileSize/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    public void update() {
        life--;
        if(life < maxLife/3) {
            yd ++;
        }
        worldX += xd*speed;
        worldY += yd*speed;
        if(life == 0) {
            alive = false;
        }
    }
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(isDamageParticle == true) {
            g2.setColor(Color.red);

         //   System.out.println(damageType);

            if(damageType == "fire") g2.setColor(Color.red);
            else if(damageType == "ice") g2.setColor(Color.blue);
            else if(damageType == "lightning") g2.setColor(Color.yellow);
            else if(damageType == "physical") g2.setColor(Color.white);
            else if(damageType == "chaos") g2.setColor(Color.MAGENTA);

            int damage = Integer.parseInt(damageText);
            int scale = 25 + damage;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, scale));
            g2.drawString(damageText,screenX,screenY);
        }
        else if(isImageParticle == true) {
            g2.drawImage(image, screenX- 20, screenY - 20, null);
        }
        else {
            g2.setColor(color);
            g2.fillRect(screenX, screenY, size, size);
        }
    }
}
