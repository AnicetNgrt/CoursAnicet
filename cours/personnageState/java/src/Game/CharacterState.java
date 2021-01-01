package Game;
import Engine.Input;

public interface CharacterState {
	public CharacterState onUpdate(float delta);
	public CharacterState onInput(Input input);
}
