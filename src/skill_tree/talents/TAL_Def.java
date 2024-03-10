package skill_tree.talents;

import main.GamePanel;
import skill_tree.SkillTree;
import skill_tree.Talent;

public class TAL_Def extends Talent {
    public GamePanel gp;
    int defenseValue = 1;
    public TAL_Def(GamePanel gp, int depth, int indexInRow) {
        super(gp,depth,indexInRow);
        this.gp = gp;
        this.depth = depth;
        this.indexInRow = indexInRow;
        image = SkillTree.defenseTalent;
        title = "Defensive";
        description = "Adds +1 DEF";
    }
    public void effect() {
        gp.player.defense += defenseValue;
    }
    public void revertEffect() {
        gp.player.defense -= defenseValue;
    }
}
