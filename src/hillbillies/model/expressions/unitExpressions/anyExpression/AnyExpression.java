package hillbillies.model.expressions.unitExpressions.anyExpression;

import java.util.Set;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import hillbillies.model.Unit;
import hillbillies.model.expressions.unitExpressions.UnitExpression;
import hillbillies.model.task.Task;

public class AnyExpression extends UnitExpression{

	public AnyExpression() {
	}
	
	@Override
	public Unit getValue(Task task) {
		Set<Unit> units = task.getExecutor().getWorld().getUnits();
		units.remove(task.getExecutor());
		return units.stream().findAny().orElse(null);
	}
	
}
