package EditorNoCommand;

import Math.Vec3;

public class Editor {
    public enum Blocks {
        START,
        ROAD, 
        TURN, 
        LARGE_TURN,
        FINISH
    }

    private Block[][][] blocks;

    public Editor(Vec3 size) {
        blocks = new Block[size.x()][size.y()][size.z()];
    }

	public void addBlock(Blocks block, Orientation orientation, Vec3 vec3) {
        blocks[vec3.x()][vec3.y()][vec3.z()] = new Block(block, orientation, vec3);
	}

	public Block getBlockAt(Vec3 vec3) {
		return blocks[vec3.x()][vec3.y()][vec3.z()];
	}

	public void rotateBlock(Block block, Orientation orientation) {
        block.setOrientation(orientation);
	}

	public void deleteBlock(Block block) {
        Vec3 coords = block.getPosition();
        blocks[coords.x()][coords.y()][coords.z()] = null;
	}

	public void undo() {
	}

	public void undoAll() {
	}

	public void redo() {
	}

	public void redoAll() {
	}
}
