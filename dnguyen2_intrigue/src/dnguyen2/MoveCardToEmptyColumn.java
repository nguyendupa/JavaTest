package dnguyen2;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;

/**
 * Move card from the top of the column to top of the 6 rank pile
 * 
 * @author Duong D.T. Nguyen
 *
 */
public class MoveCardToEmptyColumn extends Move{
	Column columnSource;
	Column targetColumn;
	Card cardBeingDragged;
	public MoveCardToEmptyColumn(Column column,Column targetColumn,Card card){
		this.columnSource = column;
		this.targetColumn = targetColumn;
		this.cardBeingDragged = card;
	}
	@Override
	public boolean doMove(Solitaire game) {
		// TODO Auto-generated method stub
		if (!(valid(game))){return false;}
		this.targetColumn.add(this.cardBeingDragged);
		return true;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		this.columnSource.add(this.targetColumn.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub
		boolean isValid = true;
		//if the column is empty, then the move is not valid, cannot move the queen
		if (this.columnSource.count()==0){isValid = false;}
		// if the target column has more than one element, which is the queen
		//then return false
		if (this.targetColumn.count()>1){
			isValid = false;
		}
		
		return isValid;
	}

}
