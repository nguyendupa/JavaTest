package dnguyen2;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
/**
 * Move card from the top of the column to top of the 6 rank pile
 * 
 * @author Duong D.T. Nguyen
 *
 */
public class MoveCardTo6RankPile extends Move{
	Column columnSource;
	Pile targetPile;
	Card cardBeingDragged;
	public MoveCardTo6RankPile(Column column,Pile pile,Card card){
		this.columnSource = column;
		this.targetPile = pile;
		this.cardBeingDragged = card;
	}
	@Override
	public boolean doMove(Solitaire game) {
		// TODO Auto-generated method stub
		if (!(valid(game))){return false;}
		this.targetPile.add(this.cardBeingDragged);
		game.updateScore(+1);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		this.columnSource.add(this.targetPile.get());
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub
		boolean isValid = true;
		//if the column is empty, then the move is not valid, cannot move the queen
		if (this.columnSource.count()==0){isValid = false;}
		if (!((this.cardBeingDragged.getRank()-this.targetPile.peek().getRank())==1)){
			isValid = false;
		}
		
		return isValid;
	}

}
