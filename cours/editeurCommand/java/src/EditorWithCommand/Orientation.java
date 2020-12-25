package EditorWithCommand;

public enum Orientation {
    NORTH("WEST","EAST"), 
    WEST("SOUTH","NORTH"),
    EAST("NORTH","SOUTH"), 
    SOUTH("EAST","WEST");

    private String left;
    private String right;

    Orientation(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public Orientation rotateLeft() {
        return Orientation.valueOf(this.left);
    }

    public Orientation rotateRight() {
        return Orientation.valueOf(this.right);
    }
}
