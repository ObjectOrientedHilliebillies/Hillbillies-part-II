package hillbillies.model.expressions;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import hillbillies.model.statements.Statement;

>>>>>>> refs/remotes/origin/master
public abstract class Expression<T> {
	
	public void setValue(T object){
		this.object = object;
	}
	
	public T getValue(){
		return this.object;
	}
	
	private Statement getStatement() {
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
=======
import hillbillies.model.expressions.isExpressions.IsAliveExpression;
import hillbillies.model.expressions.isExpressions.IsCarryingItemExpression;
import hillbillies.model.expressions.logicalExpressions.AndExpression;

/**
 * Class for representing expressions
 * 
 * @author Jonas
 *
 */
public abstract class Expression {

	public Expression() {
	}
	
	public abstract Expression eval();

}
>>>>>>> refs/remotes/origin/Jonas
