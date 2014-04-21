package tlisp.ast.evaluatable;

import java.util.ArrayList;

import tlisp.EvaluateContext;

public class BlockNode extends EvaluatableNode {
	
	private ArrayList<EvaluatableNode> nodes = new ArrayList<EvaluatableNode>();
	
	public void addNode(EvaluatableNode node){
		this.nodes.add(node);
	}

	@Override public double eval(EvaluateContext context) {
		double result = 0;
		for(EvaluatableNode node : this.nodes){
			result = node.eval(context);
		}
		return result;
	}

}
