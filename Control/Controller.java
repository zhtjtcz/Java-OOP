package Control;

import Card.*;
import Plant.*;
import Zombie.*;
import Shovel.*;
// Import all element

import java.util.*;

public class Controller {
	private LaunchFrame frame;
	private static int cardHeight = 90, cardWidth = 45;
	private static int tileHeight = 100, tileWidth = 80;
	// Appearance
		
	private Plant[][] plants = new Plant[5][9];
	public Plant nowPlant = null;
	// Plants

		
	private static int numRow = 5, numCol = 9;	// Row and Col number
	private static int cardNum = 1;				// Card sum
	private Card[] Cards = new Card[10];		
	private Card card = null;					// Card on selected
	// Cards
		
	private Shovel shovel;
	// Shovel
		
	public List<ArrayList<Zombie>> Zombies = new LinkedList<>();
	// Zombies
}