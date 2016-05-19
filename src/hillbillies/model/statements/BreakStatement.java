package hillbillies.model.statements;

import hillbillies.part3.programs.SourceLocation;

public class BreakStatement extends Statement{

	public BreakStatement(SourceLocation sourceLocation) {
		setSourceLocation(sourceLocation);
	}

	@Override
	public double execute() {
		
	}

}
