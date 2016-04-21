package dnguyen2;

import java.awt.Dimension;

import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.Pile;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

public class Intrigue extends Solitaire{
	
	Deck deck[] = new Deck[2];
	Pile pile5RankDown[] = new Pile[8];
	Pile pile6RankUp[] = new Pile[8];
	Column columnQueen[] = new Column[8];
	
	PileView pile5RankView[] = new PileView[8];
	PileView pile6RankView[] = new PileView[8];
	ColumnView columnQueenView[] = new ColumnView[8];
	IntegerView scoreView;
	
	public Intrigue() {
		super();
	}
	@Override
	public Dimension getPreferredSize() {
		  return new Dimension (900, 1000);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Duong Nguyen Intrigue";
	}

	@Override
	public boolean hasWon() {
		
		return getScore().getValue() == 80;
	}

	@Override
	public void initialize() {
		
		//2 decks of cards to be dealt
		// initialize model
		initializeModel(getSeed());
		initializeView();	
		initializeControllers();

		// prepare game by getting a random queen, then deal two deck of card on to
		// the queen until there is another queen put it to the next column and 
		// continue building on that column, until having a five or a six, put it on
		// pile, or any card that can build onto the 5 pile or 6 pile
		Card firstCard = new Card(12, 3);
		columnQueen[0].add(firstCard);
		int columnNum = 0;
		int pile5Num = 0;
		int pile6Num = 0;
		boolean isSecondQH = true;
		for(int i=0;i<=1;i++){
			for(int j=0;j<=51;j++){
				Card c = deck[i].get();
				//if the card get from the deck is not the queen of heart
				if (!((c.getRank()==firstCard.getRank())&&(c.getSuit()==firstCard.getSuit()))){
					//if it is a queen then put it to the next column
					if(c.getRank()==12){
						columnNum++;
						columnQueen[columnNum].add(c);
					} else if(c.getRank()==5){	//if it is a 5, add it to the pile 5
						pile5RankDown[pile5Num].add(c);
						pile5Num++;
					} else if(c.getRank()==6){	//if it is a 6, add it to the pile 6
						pile6RankUp[pile6Num].add(c);
						pile6Num++;
					} else {//if it is not 5,6 or queen
						if ((c.getRank()>6)&&(c.getRank()<12)){	//from 7 to J
							boolean isFinished = false;
							for (int k=0;k<=7;k++){
								if(!(isFinished)){
									if(!(pile6RankUp[k].empty())){
										Card topCard = pile6RankUp[k].peek();
										//if the card is one above the topcard
										if((c.getRank()-topCard.getRank())==1){
											pile6RankUp[k].add(c);
											this.updateScore(+1);
											isFinished = true;
										} 
									}
								}
							} 
							if (!(isFinished)){
								columnQueen[columnNum].add(c);
							}
							
						} else {	//everything else, ace to 4 and king
							boolean isFinished = false;
							for (int k=0;k<=7;k++){
								if (!(isFinished)){	
									if(!(pile5RankDown[k].empty())){
										Card topCard = pile5RankDown[k].peek();
										//if the card is one below the topcard or if it is the king and the topcard is ace
										if(((topCard.getRank()-c.getRank())==1)||((c.getRank()==13)&&(topCard.getRank()==1))){
											pile5RankDown[k].add(c);
											this.updateScore(+1);
											isFinished = true;
										}
									} 
								}
							}
							if (!(isFinished)){
								columnQueen[columnNum].add(c);
							}
						}
					}
				} else {	//if the card return is queen of heart, check if it is the second one
							//or not, if it is a third one then do not add to the next column
					
					if(isSecondQH){
						columnNum++;
						columnQueen[columnNum].add(c);
						isSecondQH = false;
					}
				}
			}
			
		}
	
	}

	private void initializeControllers() {
		// TODO Auto-generated method stub
		for (int i = 0; i <= 7; i++) {
			pile5RankView[i].setMouseAdapter (new Pile5RankController (this, pile5RankView[i]));
			pile5RankView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			pile5RankView[i].setUndoAdapter (new SolitaireUndoAdapter(this));
			
			pile6RankView[i].setMouseAdapter (new Pile6RankController (this, pile6RankView[i]));
			pile6RankView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			pile6RankView[i].setUndoAdapter (new SolitaireUndoAdapter(this));
			
			columnQueenView[i].setMouseAdapter (new ColumnQueenController (this, columnQueenView[i]));
			columnQueenView[i].setMouseMotionAdapter (new SolitaireMouseMotionAdapter (this));
			columnQueenView[i].setUndoAdapter (new SolitaireUndoAdapter(this));
		}
	}

	private void initializeView() {
		
		CardImages ci = getCardImages();
		for(int i = 0; i <=7;i++){
			pile5RankView[i] = new PileView(pile5RankDown[i]);
			pile5RankView[i].setBounds(100+(i-1)*(10+ci.getWidth()),40+ci.getHeight(),ci.getWidth(),ci.getHeight());
			container.addWidget(pile5RankView[i]);
			pile6RankView[i] = new PileView(pile6RankUp[i]);
			pile6RankView[i].setBounds(100+(i-1)*(10+ci.getWidth()),20,ci.getWidth(),ci.getHeight());
			container.addWidget(pile6RankView[i]);
			columnQueenView[i] = new ColumnView(columnQueen[i]);
			columnQueenView[i].setBounds(100+(i-1)*(10+ci.getWidth()),70+2*ci.getHeight(),ci.getWidth(),9999);
			container.addWidget(columnQueenView[i]);
		}
		
		scoreView = new IntegerView (getScore());
		scoreView.setFontSize(14);
		scoreView.setBounds (110+8*ci.getWidth(), 20, 100, 60);
		container.addWidget (scoreView);

	}

	private void initializeModel(int seed) {
		
		for(int i = 0;i<=1;i++){
			deck[i] = new Deck("Deck" + i);
			deck[i].create(seed);
			model.addElement(deck[i]);// add to our model (as defined within our superclass).
		}
		
		for(int i = 0; i<=7;i++){
			pile5RankDown[i] = new Pile("5RankPile" + i);
			model.addElement(pile5RankDown[i]);
			pile6RankUp[i] = new Pile("6RankPile" + i);
			model.addElement(pile6RankUp[i]);
			columnQueen[i] = new Column("QueenColumn" + i);
			model.addElement(columnQueen[i]);
			
		}
		
		
	}

	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		// Here the seed is to "order by suit."
		Main.generateWindow(new Intrigue(), Deck.OrderBySuit);
	}
}
