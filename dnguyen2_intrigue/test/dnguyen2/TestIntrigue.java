package dnguyen2;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;

public class TestIntrigue extends TestCase {
	Intrigue intrigue;
	GameWindow gw;
	@Override
	protected void setUp() {
		intrigue = new Intrigue();
		gw = Main.generateWindow(intrigue, Deck.OrderBySuit);
	}
	
	@Override
	protected void tearDown() {
		gw.dispose();
		
	}
	
	public void testMoveCardTo5Rank(){
		Card dragCard = intrigue.columnQueen[0].get();
		MoveCardTo5RankPile m5_1 = new MoveCardTo5RankPile(intrigue.columnQueen[0],intrigue.pile5RankDown[7],dragCard);
		assertTrue(m5_1.valid(intrigue));
		MoveCardTo5RankPile m5_2 = new MoveCardTo5RankPile(intrigue.columnQueen[0],intrigue.pile5RankDown[6],dragCard);
		assertFalse(m5_2.valid(intrigue));
		
		m5_1.doMove(intrigue);
		assertFalse(m5_1.valid(intrigue));
		m5_1.undo(intrigue);
		assertTrue(m5_1.valid(intrigue));
	}
	
	public void testMoveCardTo6Rank(){
		Card dragCard = intrigue.columnQueen[1].get();
		MoveCardTo6RankPile m6_1 = new MoveCardTo6RankPile(intrigue.columnQueen[1],intrigue.pile6RankUp[7],dragCard);
		assertTrue(m6_1.valid(intrigue));
		MoveCardTo6RankPile m6_2 = new MoveCardTo6RankPile(intrigue.columnQueen[1],intrigue.pile6RankUp[6],dragCard);
		assertFalse(m6_2.valid(intrigue));
		
		m6_1.doMove(intrigue);
		assertFalse(m6_1.valid(intrigue));
		m6_1.undo(intrigue);
		assertTrue(m6_1.valid(intrigue));
	}
	
	public void testMoveCardToEmptyColumn(){
		Card dragCard = intrigue.columnQueen[0].get();
		MoveCardTo5RankPile m5_1 = new MoveCardTo5RankPile(intrigue.columnQueen[0],intrigue.pile5RankDown[7],dragCard);
		assertTrue(m5_1.valid(intrigue));
		MoveCardTo6RankPile m6_1 = new MoveCardTo6RankPile(intrigue.columnQueen[1],intrigue.pile6RankUp[6],dragCard);
		assertFalse(m6_1.valid(intrigue));
		
		m5_1.doMove(intrigue);
		
		Card dragCard2 = intrigue.columnQueen[6].get();
		MoveCardToEmptyColumn me_1 = new MoveCardToEmptyColumn(intrigue.columnQueen[6],intrigue.columnQueen[0],dragCard2);
		assertFalse(me_1.valid(intrigue));

		Card dragCard3 = intrigue.columnQueen[4].get();
		MoveCardToEmptyColumn me_2 = new MoveCardToEmptyColumn(intrigue.columnQueen[4],intrigue.columnQueen[0],dragCard3);
		assertTrue(me_2.valid(intrigue));
		me_2.doMove(intrigue);
		me_2.undo(intrigue);
		
		Card dragCard4 = intrigue.columnQueen[3].get();
		MoveCardToEmptyColumn me_3 = new MoveCardToEmptyColumn(intrigue.columnQueen[3],intrigue.columnQueen[2],dragCard4);
		assertFalse(me_3.valid(intrigue));
	}
	
	
}
