package tlisp.ast.evaluatable;

import tlisp.EvaluateContext;

public class ConstantNode extends EvaluatableNode {

	private double value;
	
	public ConstantNode(double value){
		this.value = value;
	}

	@Override
	public double eval(EvaluateContext context) {
		return this.value;
	}

}
