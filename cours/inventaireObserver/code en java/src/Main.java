import Game.Game;
import Game.Item;
import Game.Player;

public class Main {
	public static void main(String[] args) {
		System.out.println("Here");

		Player player = new Player();
		Item[] items = new Item[] {
			new Item("Sword", 5),
			new Item("Bow", 10),
			new Item("Chestplate", 35),
			new Item("Apple", 2)
		};
		
		Game game = new Game();
		
		game.addObject(player);
		for(Item i:items) {
			game.addObject(i);
			player.giveItem(i);
		}
		
		while(!game.isFinished()) {
			game.nextTurn();
		}
	}
}
