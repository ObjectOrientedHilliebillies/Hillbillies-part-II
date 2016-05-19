package hillbillies.model.expressions.booleanExpressions.isExpressions.unitIsExpression;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.part3.programs.SourceLocation;

public class IsCarryingItemExpression extends unitIsExpression{

	public IsCarryingItemExpression(UnitExpression unit, SourceLocation sourceLocation){
		super(unit);
		setSourceLocation(sourceLocation);
		this.unit = unit;
	}
	
	private UnitExpression unit;
	
	@Override
	public Boolean getValue(Task task){
		Unit unit = this.unit.getValue(task);
		return (unit.isCarryingBoulder() || unit.isCarryingLog());
	}
}
