package tlisp.ast.evaluatable;

import java.util.ArrayList;

import tlisp.EvaluateContext;
import tlisp.Function;

public class CallNode extends EvaluatableNode {

	private ArrayList<EvaluatableNode> parameters;
	private String name;
	
	public CallNode(String name, ArrayList<EvaluatableNode> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	@Override
	public double eval(EvaluateContext context) {
		Function function = context.getFunction(this.name);
		if(function != null){
			return function.call(context, this.parameters);
		}
		throw new NoSuchMethodError(this.name);
	}

}
