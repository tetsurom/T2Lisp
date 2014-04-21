package tlisp.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListNode extends Node {
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private boolean listLiteral;
	
	public ListNode(boolean isListLiteral) {
		this.listLiteral = isListLiteral;
	}
	
	public boolean isListLiteral(){
		return this.listLiteral;
	}
	
	public List<Node> getNodes(){
		return Collections.unmodifiableList(nodes);
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		boolean first = true;
		for(Node node : this.nodes){
			if(first){
				first = false;
			}else{
				builder.append(" ");
			}
			builder.append(node.toString());
		}
		builder.append(")");
		return builder.toString();
	}

	public void setLiteralNode(boolean b) {
		this.listLiteral = b;
	}
}
