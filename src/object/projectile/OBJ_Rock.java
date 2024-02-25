package object.projectile;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name= "Rock";
        speed = 8;
        maxLife = 150;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage() {
        up1 = rock_up1;
        up2 = rock_up2;
        down1 = rock_down1;
        down2 = rock_down2;
        left1 = rock_left1;
        left2 = rock_left2;
        right1 = rock_right1;
        right2 = rock_right2;
    }
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    public void substractResource(Entity user) {
        user.ammo -= useCost;
    }
    public Color getParticleColor() {
        Color color = new Color(40,50,1);
        return color;
    }
    public int getParticleSize() {
        int size = 10; // 6 pixels
        return size;
    }
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
