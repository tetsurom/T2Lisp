package tlisp.ast.evaluatable;

import javax.xml.soap.Node;

import tlisp.EvaluateContext;
import tlisp.ast.ListNode;

public class AstNode extends EvaluatableNode {

	private ListNode listNode;
	
	public ListNode getAst(){
		return this.listNode;
	}

	public AstNode(ListNode node){
		this.listNode = node;
	}
	
	@Override
	public double eval(EvaluateContext context) {
		return 0;
	}

}
