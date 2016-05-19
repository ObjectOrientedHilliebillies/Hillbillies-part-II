package hillbillies.model.expressions;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.statements.Statement;


public abstract class Expression<T> {
	
	public void setValue(T object){
		this.object = object;
	}
	
	public abstract T getValue();
	
	private T object;
	
	public Statement getStatement() {
		return this.statement;
	}
	
	public void setStatement(Statement statement) {
			this.statement = statement;	
	}
	
	private Statement statement;
	
	public Unit getExecutor(){
		System.out.println(this.getStatement());
		System.out.println(this.getStatement().getTask());
		return this.getStatement().getTask().getExecutor();
	}
	
	public World getWorld(){
		return this.getStatement().getTask().getExecutor().getWorld();
	}	
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

