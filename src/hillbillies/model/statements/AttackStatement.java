package hillbillies.model.statements;

import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expressions.Expression;

public class AttackStatement extends ActivityStatement{

	public AttackStatement(Expression<Unit> unit) {
		setUnit(unit);
	}
	
	private void setUnit(Expression<Unit> unit) {
		this.unit = unit.eval();
	}
	
	private Unit getUnit() {
		return this.unit;
	}
	
	private Unit unit;
	
	@Override
	public void execute(Task task) {
		task.getExecutor().attack(this.getUnit());
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
