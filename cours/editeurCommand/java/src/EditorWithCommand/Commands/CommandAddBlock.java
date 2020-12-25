package EditorWithCommand.Commands;

import CommandPattern.Command;
import EditorWithCommand.Editor.Blocks;
import EditorWithCommand.Block;
import EditorWithCommand.Editor;
import EditorWithCommand.Orientation;
import Math.Vec3;

public class CommandAddBlock extends Command {

    private Blocks blockType;
    private Orientation orientation;
    private Vec3 position;

    public CommandAddBlock(Blocks blockType, Orientation orientation, Vec3 position) {
        this.blockType = blockType;
        this.orientation = orientation;
        this.position = position;
    }

    @Override
    public void execute() {
        if(target instanceof Editor) {
            Editor editor = (Editor) this.target;
            editor.addBlock(new Block(blockType, orientation, position), position);
            executed = true;
        }
    }

    @Override
    public void undo() {
        if(executed) {
            Editor editor = (Editor) this.target;
            editor.addBlock(null, position);
            executed = false;
        }
    }
    
}
