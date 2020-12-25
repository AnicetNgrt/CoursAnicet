package EditorWithCommand;

import EditorWithCommand.Editor.Blocks;
import Math.Vec3;

public class Block {

	private Blocks blockType;
    private Orientation orientation;
    private Vec3 vec3;

    public Block(Blocks blockType, Orientation orientation, Vec3 vec3) {
        this.blockType = blockType;
        this.orientation = orientation;
        this.vec3 = vec3;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
	}

	public Vec3 getPosition() {
		return vec3;
	}

}
