package App;

import Editor.Block;
import Editor.Editor;
import Editor.Orientation;
import Math.Vec3;

public class Main {

	public static void main(String[] args) {
		Editor editor = new Editor(new Vec3(10, 10, 10));
		editor.addBlock(Editor.Blocks.ROAD, Orientation.NORTH, new Vec3(1, 2, 9));
		Block block = editor.getBlockAt(new Vec3(1, 2, 9));
		editor.rotateBlock(block, Orientation.EAST);
		editor.rotateBlock(block, block.getOrientation().rotateLeft());
		editor.deleteBlock(block);
		editor.undo();
		editor.undoAll();
		editor.redo();
		editor.redoAll();
	}

}
