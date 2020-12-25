package AppWithCommand;

import EditorWithCommand.Editor;
import EditorWithCommand.Orientation;
import EditorWithCommand.Commands.CommandAddBlock;
import EditorWithCommand.Editor.Blocks;
import Math.Vec3;

public class Main {

	public static void main(String[] args) {
		Editor editor = new Editor(new Vec3(10, 10, 10));
		editor.executeCommand(new CommandAddBlock(
			Blocks.ROAD, 
			Orientation.NORTH, 
			new Vec3(1, 2, 9)
		));
		editor.undo();
	}

}
