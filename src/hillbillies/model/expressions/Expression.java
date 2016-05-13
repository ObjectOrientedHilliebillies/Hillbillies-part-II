package hillbillies.model.expressions;
import hillbillies.model.Task;
import hillbillies.model.statements.Statement;


public abstract class Expression<T> {
	
	public void setValue(T object){
		this.object = object;
	}
	
	public T getValue(Task task){
		return this.object;
	}
	
	public Statement getStatement() {
		return this.statement;
	}

	private T object;
	private Statement statement;
	
}

//class evaluator 
//{
//	eval(IsAliveExpression test){
//		evaluator(test)
//	}
//	
//	eval(IsCarryingItemExpression test)
//	{
//		
//	}
//	eval(AndExpression and)
//	{
//		evaluator(and)
//		//eval(and1) && evaluator(and2)
//	}
//}

