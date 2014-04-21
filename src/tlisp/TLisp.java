package tlisp;

import tlisp.ast.ListNode;

public class TLisp {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ListNode node = (ListNode)new Parser().parse("(defun fibo '(n) '(if (< n 3) 1 (+ (fibo (- n 1)) (fibo (- n 2)))) ) (print (fibo 36))");
		new EvaluateContext().evaluateNode(node);
		long diff = System.currentTimeMillis() - start;
		System.out.println("time: " + diff + "ms");
	}

}
