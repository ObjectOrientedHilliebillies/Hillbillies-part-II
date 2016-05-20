package hillbillies.model.expressions.unitExpressions.anyExpression;

import java.util.Set;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;
import hillbillies.part3.programs.SourceLocation;

public class AnyExpression extends UnitExpression{

	public AnyExpression() {
	}
	
	@Override
	public Unit getValue(Task task) {
		Set<Unit> units = task.getExecutor().getWorld().getUnits();
		System.out.println(units);
		units.remove(task.getExecutor());
		if (units.size() == 0) {
			return null;
		}
		return units.stream().findAny().orElse(null);
	}
	
}
