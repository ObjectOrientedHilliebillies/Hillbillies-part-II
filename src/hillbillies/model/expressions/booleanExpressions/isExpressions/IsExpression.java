package hillbillies.model.expressions.booleanExpressions.isExpressions;

import hillbillies.model.expressions.booleanExpressions.BooleanExpression;

public abstract class IsExpression<T> extends BooleanExpression{
	
	public void setExpression(T e){
		this.expression = e;
	}
	
	public T getExpression(){
		return this.expression;
	}
	
	private T expression;
}
