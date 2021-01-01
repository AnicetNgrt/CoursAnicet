package Game;
import Engine.Entity;
import Engine.Input;

public class Character implements Entity {
	
	private CharacterState state;
	
	@Override
	public void update(float delta) {
		CharacterState nextState = state.onUpdate(delta);
		changeState(nextState);
	}

	@Override
	public void input(Input input) {
		CharacterState nextState = state.onInput(input);
		changeState(nextState);
	}
	
	private void changeState(CharacterState nextState) {
		if(nextState != state) {
			state = nextState;
			System.out.println("State changed to "+state.getClass().toString()+"!");
		}
	}
}
