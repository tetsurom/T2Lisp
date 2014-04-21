package tlisp;

import java.util.ArrayList;

import tlisp.ast.evaluatable.EvaluatableNode;

public interface Function {
	public abstract double call(EvaluateContext context, ArrayList<EvaluatableNode> parameters);

}
