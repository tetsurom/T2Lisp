package tlisp.ast.evaluatable;

import java.util.ArrayList;

import tlisp.EvaluateContext;
import tlisp.Function;

public class CallNode extends EvaluatableNode {

	private ArrayList<EvaluatableNode> parameters;
	private String name;
	private Function function;
	
	public CallNode(String name, ArrayList<EvaluatableNode> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	@Override
	public double eval(EvaluateContext context) {
		if(this.function == null){
			this.function = context.getFunction(this.name);
			if(this.function == null){
				try{
					double result = context.getVariableValue(this.name);
					for(int i = 0; i < parameters.size(); ++i){
						result = parameters.get(i).eval(context); 
					}
					return result;
				}catch(NoSuchFieldError e){
					throw new NoSuchMethodError(this.name);
				}
			}
		}
		return function.call(context, this.parameters);
	}
}
