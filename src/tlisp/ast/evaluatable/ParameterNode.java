package tlisp.ast.evaluatable;

import tlisp.EvaluateContext;

public class ParameterNode extends EvaluatableNode {

	private int index;

	public ParameterNode(int index) {
		this.index = index;
	}

	@Override
	public double eval(EvaluateContext context) {
		return context.getParameter(this.index);
	}
}
