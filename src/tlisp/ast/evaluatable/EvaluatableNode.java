package tlisp.ast.evaluatable;

import tlisp.EvaluateContext;

public abstract class EvaluatableNode {
	public abstract double eval(EvaluateContext context);
}
