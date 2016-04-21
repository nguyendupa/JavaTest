package dnguyen2;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestIntrigueController extends KSTestCase {
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
	
	public void testMoveTo5Pile(){
		//create mouse press at (10,50) within the first column view
		
		MouseEvent press = this.createPressed(intrigue, intrigue.columnQueenView[0], 10,50);
		intrigue.columnQueenView[0].getMouseManager().handleMouseEvent(press);
		
		assertEquals(12,intrigue.columnQueen[0].peek().getRank());
		
		MouseEvent release = this.createReleased(intrigue, intrigue.pile5RankView[7], 0, 0);
		intrigue.pile5RankView[7].getMouseManager().handleMouseEvent(release);
		
		assertEquals(13,intrigue.pile5RankDown[7].peek().getRank());
		
		MouseEvent press2 = this.createPressed(intrigue, intrigue.columnQueenView[1], 10,120);
		intrigue.columnQueenView[1].getMouseManager().handleMouseEvent(press2);
		
		MouseEvent release2 = this.createReleased(intrigue, intrigue.columnQueenView[0], 0,0);
		intrigue.columnQueenView[0].getMouseManager().handleMouseEvent(release2);
		
		assertEquals(7,intrigue.columnQueen[0].peek().getRank());
	}
	public void testMoveTo6Pile(){
		//create mouse press at (10,0) within the first column view
		
		MouseEvent press = this.createPressed(intrigue, intrigue.columnQueenView[1], 10,120);
		intrigue.columnQueenView[1].getMouseManager().handleMouseEvent(press);
		
		assertEquals(8,intrigue.columnQueen[1].peek().getRank());
		
		MouseEvent release = this.createReleased(intrigue, intrigue.pile6RankView[7], 0, 0);
		intrigue.pile6RankView[7].getMouseManager().handleMouseEvent(release);
		
		assertEquals(7,intrigue.pile6RankUp[7].peek().getRank());
		
	}
}
