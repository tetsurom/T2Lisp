package tlisp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import tlisp.ast.ListNode;
import tlisp.ast.Node;
import tlisp.ast.NumberNode;
import tlisp.ast.SymbolNode;
import tlisp.ast.evaluatable.AstNode;
import tlisp.ast.evaluatable.CallNode;
import tlisp.ast.evaluatable.ConstantNode;
import tlisp.ast.evaluatable.EvaluatableNode;
import tlisp.ast.evaluatable.ParameterNode;
import tlisp.ast.evaluatable.VariableNode;

public class EvaluateContext {
	
	Map<String, Function> functions = new HashMap<String, Function>();
	Map<String, Double> globalVariables = new HashMap<String, Double>();
	Stack<double[]> parameterStack = new Stack<>();
	List<Node> parameterNodes;
	
	public EvaluateContext(){
		this.functions.put("+", new NativeFunction(parameter -> parameter[0] + parameter[1]));
		this.functions.put("-", new NativeFunction(parameter -> parameter[0] - parameter[1]));
		this.functions.put("*", new NativeFunction(parameter -> parameter[0] * parameter[1]));
		this.functions.put("/", new NativeFunction(parameter -> parameter[0] / parameter[1]));
		this.functions.put("<", new NativeFunction(parameter -> parameter[0] < parameter[1] ? 1 : 0));
		this.functions.put(">", new NativeFunction(parameter -> parameter[0] > parameter[1] ? 1 : 0));
		this.functions.put("<=", new NativeFunction(parameter -> parameter[0] <= parameter[1] ? 1 : 0));
		this.functions.put(">=", new NativeFunction(parameter -> parameter[0] >= parameter[1] ? 1 : 0));
		this.functions.put("=", new NativeFunction(parameter -> parameter[0] == parameter[1] ? 1 : 0));
		this.functions.put("!=", new NativeFunction(parameter -> parameter[0] != parameter[1] ? 1 : 0));
		this.functions.put("print", new NativeFunction(new NativeFunctionWrapper() {
			@Override public double call(double[] parameter) {
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < parameter.length; ++i){
					if(i > 0){
						builder.append(" ");
					}
					if(Math.floor(parameter[i]) == parameter[i]){
						builder.append((long)parameter[i]);
					}else{
						builder.append(parameter[i]);
					}
				}
				System.out.println(builder.toString());
				return 0;
			}
		}));
		this.functions.put("if", new Function() {
			@Override public double call(EvaluateContext context, ArrayList<EvaluatableNode> parameters) {
				if(parameters.size() < 3){
					throw new RuntimeException("few arguments for if");
				}
				return (parameters.get(0).eval(context) != 0) ? 
						parameters.get(1).eval(context) : parameters.get(2).eval(context);
			}
		});
		this.functions.put("setq", new Function() {
			@Override public double call(EvaluateContext context, ArrayList<EvaluatableNode> parameters) {
				if(parameters.size() < 2){
					throw new RuntimeException("few arguments for setq");
				}
				String name = ((VariableNode)parameters.get(0)).getName();
				double value = parameters.get(1).eval(context);
				context.setVariableValue(name, value);
				return value;
			}
		});
		this.functions.put("defun", new Function() {
			@Override public double call(EvaluateContext context, ArrayList<EvaluatableNode> parameters) {
				if(parameters.size() < 3){
					throw new RuntimeException("few arguments for defun");
				}
				String name = ((VariableNode)parameters.get(0)).getName();
				AstNode args = (AstNode)parameters.get(1);
				AstNode body = (AstNode)parameters.get(2);
				context.parameterNodes = args.getAst().getNodes();
				ListNode bodyListNode = body.getAst();
				bodyListNode.setLiteralNode(false);
				EvaluatableNode eBody = context.createEvaluatableNode(bodyListNode);
				context.parameterNodes = null;
				context.functions.put(name, new LispFunction(eBody));
				return 1;
			}
		});
	}

	public Function getFunction(String name) {
		return functions.containsKey(name) ? functions.get(name) : null;
	}
	
	private int getParameterIndex(String name){
		if(parameterNodes != null){
			for(int i = 0; i < parameterNodes.size(); ++i){
				if(((SymbolNode)parameterNodes.get(i)).getSymbol().equals(name)){
					return i;
				}
			}
		}
		return -1;
	}
	
	private EvaluatableNode createEvaluatableNode(Node node){
		if(node instanceof NumberNode){
			return new ConstantNode(((NumberNode)node).getValue());
		}
		if(node instanceof ListNode){
			if(((ListNode)node).isListLiteral()){
				return new AstNode((ListNode)node);
			}
			List<Node> nodes = ((ListNode)node).getNodes();
			if(nodes.get(0) instanceof SymbolNode){
				String name = ((SymbolNode)nodes.get(0)).getSymbol();
				ArrayList<EvaluatableNode> parameters = new  ArrayList<>();
				for(int i = 1; i < nodes.size(); ++i){
					parameters.add(this.createEvaluatableNode(nodes.get(i)));
				}
				return new CallNode(name, parameters);
			}
		}
		if(node instanceof SymbolNode){
			String name = ((SymbolNode)node).getSymbol();
			int index = this.getParameterIndex(name);
			if(index != -1){
				return new ParameterNode(index);
			}
			return new VariableNode(name);
		}
		throw new RuntimeException("unknown ast: " + node);
	}
	
	public void evaluateNode(ListNode toplevelNode, boolean print){
		for(Node node : toplevelNode.getNodes()){
			EvaluatableNode eNode = this.createEvaluatableNode(node);
			double result = eNode.eval(this);
			if(print){
				System.out.println(result);
			}
		}
	}

	public double getVariableValue(String name) {
		if(this.globalVariables.containsKey(name)){
			return this.globalVariables.get(name);
		}
		throw new NoSuchFieldError(name);
	}
	
	public void setVariableValue(String name, double value) {
		this.globalVariables.put(name, value);
	}

	public double getParameter(int index) {
		if(this.parameterStack.size() > 0){
			return this.parameterStack.peek()[index];
		}
		throw new RuntimeException("not in the function");
	}
	
	public void pushParameter(double[] parameters){
		this.parameterStack.push(parameters);
	}
	
	public void popParameter(){
		this.parameterStack.pop();
	}
}
