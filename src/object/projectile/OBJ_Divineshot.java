package object.projectile;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Divineshot extends Projectile {
    GamePanel gp;
    public OBJ_Divineshot(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name= "Divine";
        speed = 5;
        maxLife = 120;
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
        up1 = divineshot_up1;
        up2 = divineshot_up2;
        down1 = divineshot_down1;
        down2 = divineshot_down2;
        left1 = divineshot_left1;
        left2 = divineshot_left2;
        right1 = divineshot_right1;
        right2 = divineshot_right2;
    }

    public Color getParticleColor() {
        Color color = new Color(230,230,0);
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
