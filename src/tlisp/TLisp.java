package tlisp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tlisp.ast.ListNode;

public class TLisp {

	public static void execFile(String filename){
		InputStream stream = null;
		String file;
		try {
			stream = new FileInputStream(filename);
			byte[] b = new byte[stream.available()];
			stream.read(b);
			file = new String(b);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}

		long start = System.currentTimeMillis();
		ListNode node = (ListNode)new Parser().parse(file);
		new EvaluateContext().evaluateNode(node, false);
		long diff = System.currentTimeMillis() - start;
		System.out.println("time: " + diff + "ms");
	}
	
	public static void main(String[] args) {
		if(args.length > 0){
			execFile(args[0]);
		}else{
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Parser parser = new Parser();
			EvaluateContext context = new EvaluateContext();
			while(true){
				System.out.print("> ");
				String line = null;
				try {
					line = reader.readLine();
				} catch (IOException e) {
					return;
				}
				ListNode node = null;
				try{
					node = (ListNode)parser.parse(line);
				} catch (Exception e) {
					System.err.println("Parse error!");
					e.printStackTrace();
					continue;
				}
				try{
					context.evaluateNode(node, true);
				} catch (Exception e) {
					System.err.println("Runtime error!");
					e.printStackTrace();
					continue;
				}
			}
		}
	}

}
