package tlisp.ast;

public class SymbolNode extends Node {
	
	private String symbol;
	
	public SymbolNode(String symbol){
		this.symbol = symbol;
	}
	
	public String getSymbol(){
		return this.symbol;
	}

	@Override
	public String toString() {
		return "'"+this.symbol+"'";
	}

}
