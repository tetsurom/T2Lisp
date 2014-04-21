package tlisp;
import java.util.regex.Pattern;

import tlisp.ast.ListNode;
import tlisp.ast.Node;
import tlisp.ast.NumberNode;
import tlisp.ast.SymbolNode;


public class Parser {
	
	private int tokenIndex;
	
	public Parser(){
		
	}
	
	private static String[] tokenize(String text){
		return text.replaceAll("\\)", " )").replaceAll("\\(", "( ").replaceAll("'", "' ").split("\\s+");
	}
	
	public Node parse(String text){
		text = text.trim();
		if(!text.startsWith("(")){
			text = "(" + text + ")";
		}
		text = "(" + text + ")";
		String[] tokens = Parser.tokenize(text);
		this.tokenIndex = 0;
		return this.parseNode(tokens);
	}
	
	private Pattern floatPattern = Pattern.compile("([+|-]?)(\\d+)|(([+|-]?)(\\d*)\\.(\\d+)|([+|-]?)(\\d+)\\.)(e([+|-]?)\\d+)?|(([+|-]?)\\d+(e([+|-]?)\\d+))");
	private Node parseNode(String[] tokens){
		if(tokens.length == 0){
			return new ListNode(false);
		}
		String token = null;
		
		do{
			token = tokens[this.tokenIndex];
			this.tokenIndex += 1;
		}while(token.length() == 0);
		
		if(this.floatPattern.matcher(token).matches()){
			return new NumberNode(Double.parseDouble(token));
		}
		boolean listLiteral = false;
		if(token.equals("'")){
			listLiteral = true;
			token = tokens[this.tokenIndex];
			this.tokenIndex += 1;
		}
		
		if(token.equals("(")){
			ListNode list = new ListNode(listLiteral);
			while(this.tokenIndex < tokens.length){
				token = tokens[this.tokenIndex];
				if(token.equals(")")){
					this.tokenIndex += 1;
					return list;
				}else{
					list.addNode(parseNode(tokens));
				}
			}
		}
		return new SymbolNode(token);
	}
}
