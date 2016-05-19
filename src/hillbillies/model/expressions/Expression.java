package hillbillies.model.expressions;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.statements.Statement;
import hillbillies.part3.programs.SourceLocation;


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
		return this.getStatement().getTask().getExecutor();
	}
	
	public World getWorld(){
		System.out.println(this.getStatement());
		System.out.println(this.getStatement().getTask());
		System.out.println(this.getStatement().getTask().getExecutor());
		System.out.println(this.getStatement().getTask().getExecutor().getWorld());
		return this.getStatement().getTask().getExecutor().getWorld();
	}	

	private SourceLocation sourceLocation;
	
	public SourceLocation getSourceLocation(){
		return this.sourceLocation;
	}
	
	public void setSourceLocation(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
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

