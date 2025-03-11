package skill_tree.talents;

import main.GamePanel;
import skill_tree.SkillTree;
import skill_tree.Talent;

import java.lang.reflect.InvocationTargetException;

public class TAL_Dash extends Talent {
    public GamePanel gp;
    public TAL_Dash(GamePanel gp, int depth, int indexInRow) {
        super(gp,depth,indexInRow);
        this.gp = gp;
        this.depth = depth;
        this.indexInRow = indexInRow;
        image = SkillTree.dashTalent;
        title = "Dash";
        description = "Adds ability to dash";
    }
    public void effect() {
        if(gp.player.abilities.getOrDefault("dash", 0) == 0) {
            gp.player.abilities.put("dash", 1);
        }

    }
    public void revertEffect() {
        if(gp.player.abilities.get("dash") == 1) {
            gp.player.abilities.put("dash", 0);
        }


    }
}
