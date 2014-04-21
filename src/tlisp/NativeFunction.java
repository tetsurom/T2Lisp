package tlisp;

import java.util.ArrayList;

import tlisp.ast.evaluatable.EvaluatableNode;

public class NativeFunction implements Function {
	
	private NativeFunctionWrapper wrapper;
	
	public NativeFunction(NativeFunctionWrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public double call(EvaluateContext context, ArrayList<EvaluatableNode> parameters) {
		double[] parameterValues = new double[parameters.size()];
		for(int i = 0; i < parameters.size(); ++i){
			parameterValues[i]= parameters.get(i).eval(context); 
		}
		return wrapper.call(parameterValues);
	}

}
