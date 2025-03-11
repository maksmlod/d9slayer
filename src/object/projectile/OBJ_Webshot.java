package object.projectile;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Webshot extends Projectile {
    GamePanel gp;
    public OBJ_Webshot(GamePanel gp) {
        super(gp);
        this.gp = gp;

        damageType = "physical";
        name= "Divine";
        speed = 12;
        maxLife = 140;
        life = maxLife;
        attack = 5;
        alive = false;
        getImage();

        solidArea.x = 2;
        solidArea.y = 2;
        solidArea.width = 44;
        solidArea.height = 44;

        projectileTileArea.x = 22;
        projectileTileArea.y = 22;
        projectileTileArea.width = 4;
        projectileTileArea.height = 4;
    }
    public void getImage() {
        up1 = webshot_up1;
        up2 = webshot_up2;
        down1 = webshot_down1;
        down2 = webshot_down2;
        left1 = webshot_left1;
        left2 = webshot_left2;
        right1 = webshot_right1;
        right2 = webshot_right2;
    }

    public Color getParticleColor() {
        Color color = new Color(194,197,204);
        return color;
    }
    public int getParticleSize() {
        int size = 10;
        return size;
    }
    public int getParticleSpeed() {
        int speed = 2;
        return speed;
    }
    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
