package tlisp;

import java.util.ArrayList;

import tlisp.ast.evaluatable.EvaluatableNode;

public class LispFunction implements Function {
	
	private EvaluatableNode node;
	
	public LispFunction(EvaluatableNode node) {
		this.node = node;
	}

	@Override
	public double call(EvaluateContext context, ArrayList<EvaluatableNode> parameters) {
		double[] parameterValues = new double[parameters.size()];
		for(int i = 0; i < parameters.size(); ++i){
			parameterValues[i] = parameters.get(i).eval(context); 
		}
		try{
			context.pushParameter(parameterValues);
			return this.node.eval(context);
		}finally{
			context.popParameter();
		}
	}

}
