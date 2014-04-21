package tlisp.ast.evaluatable;

import tlisp.EvaluateContext;

public class VariableNode extends EvaluatableNode {
	
	private String name;

	public VariableNode(String name) {
		this.name = name;
	}

	@Override
	public double eval(EvaluateContext context) {
		return context.getVariableValue(this.name);
	}
	
	public String getName(){
		return this.name;
	}

}
