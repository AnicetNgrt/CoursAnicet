package Engine;

public class Input {
	private final String name;
	private final int keyID;
	
	public Input(String name, int keyID) {
		this.name = name;
		this.keyID = keyID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getKeyID() {
		return keyID;
	}
}
