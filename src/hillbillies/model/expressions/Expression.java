package hillbillies.model.expressions;

/**
 * Class for representing expressions
 * 
 * @author Jonas
 *
 */
public abstract class Expression {

	public Expression() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract Expression eval();

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