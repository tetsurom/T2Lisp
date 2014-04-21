package tlisp.ast;

public class NumberNode extends Node {
	double value = 0;
	
	public NumberNode(double value) {
		this.value = value;
	}
	
	public double getValue(){
		return this.value;
	}

	@Override
	public String toString() {
		return "<"+this.value + ">";
	}
	
}
