package Editor;

import Editor.Editor.Blocks;
import Math.Vec3;

public class Block {

	private Blocks block;
    private Orientation orientation;
    private Vec3 vec3;

    public Block(Blocks block, Orientation orientation, Vec3 vec3) {
        this.block = block;
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
