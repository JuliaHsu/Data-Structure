/**
 * This is the main program of Queues, stacks and hashtables project.
 * This project will use above data structures to implement the postfix machine for 
 * evaluating arithmetic expressions. 
 * This project is using linked list for queue
 * @author Hsin Ping (Julia) HSU on 2018.10/10
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class ExpressionEvaluator {
	static String fileName="";
	static File inputFile;
	static ExpressionEvaluator evaluator = new ExpressionEvaluator();
	static ExpressionEvaluator.ListQueue<String> inputQueue = 
			evaluator.new ListQueue<String>();
	static ProgramStack <String> operandStack = new ProgramStack <String>();
	static SymbolTable <String,Integer> sbTable = new SymbolTable<String,Integer>();
	static boolean isValid=true;
	//Linked List Node
	class Node<T>{
		private T element;
		private Node next;
		//constructor
		public Node(T newElement) {
			element = newElement;
			next = null;
		}
	}
	//Queue applied by Linked List 
	class ListQueue<T>{
		private Node<T> front=null;
		private Node<T> back=null;
		private Node<T> mark;
		//check if the queue is empty
		private boolean isEmpty() {
			return front==null;
		}
		//constructor
		public ListQueue(){
			front=back=null;
		}
		//insert the element from the back
		public void enqueue(T element) {
			if(isEmpty()) {
				front =back = new Node<T>(element);
			} 
			else {
				back.next=new Node<T>(element);
				back=back.next;
			}
		}
		//dequeue the element from the front
		public T dequeue() {
			if(isEmpty()) {
				return null;
			}
			T value = front.element;
			front = front.next;
			return value;		
		}
		//get the front element
		public T getFront() {
			if(isEmpty())
				return null;
			return front.element;
		}
		//clear the queue
		public void clear() {
			front = back=null;
		}
//		public void displayQueue() {
//			for(mark=front;mark!=null; mark=mark.next) {
//				System.out.println(mark.element);
//			}
//		}
		
	}
	//start evaluating
	private int evaluate() {
		isValid=true;
		int operand1 = 0;
		int operand2=0;
		String variable1="";
		String variable2="";
		int res = 0;
		String popValue1=null;
		String popValue2 = null;
		String queueVal=null;
		//scan and dequeue from the queue
		for(inputQueue.mark=inputQueue.front;inputQueue.mark!=null;
				inputQueue.mark=inputQueue.mark.next) {
			//get the front element from the queue
			queueVal = inputQueue.dequeue();
			//if the value is integer or variable, push into the stack
			//note: need to 
			if ((queueVal).matches("-?[1-9]\\d*|0|\\w+")) {
				operandStack.push(queueVal);
			}
			//assignment operators
			else if ((queueVal).equals("=")){
				if(operandStack.peek()!=null) {
					//pop the value from the stack and check if it's integer
					popValue1= operandStack.pop();
					if((popValue1).matches("-?[1-9]\\d*|0")) {
						operand1=Integer.parseInt(popValue1);
						//check before popping
						if(operandStack.peek()==null) {
							System.out.println("INVALID expression format");
							isValid=false;
							break;
						}
						//pop the value which is a variable
						popValue2= operandStack.pop();
						//check which value is it, and save it to print later
						variable1 = popValue2;
					}
					/*if the first value is a variable and it's in the hash table
					 * assign the value of the variable to the new variable*/
					else if(!(popValue1).matches("-?[1-9]\\d*|0") && 
							sbTable.get(popValue1)!=null) {
						operand1=sbTable.get(popValue1);
						popValue2= operandStack.pop();
						variable1 = popValue2;
						
					}
					//if the first value is not a invalid expression and break the loop
					else {
						System.out.println("INVALID expression format");
						isValid=false;
						break;
					}
					
					//store the variable at the hashtable with key and value
					sbTable.put(variable1, operand1);
					
				}
				else if(operandStack.peek()==null) {
					System.out.println("INVALID expression format");
					isValid=false;
				}
			}
			//assign operator
			else if(queueVal.matches("[+-/*/]=")) {

				if(operandStack.peek()!=null) {
					popValue1= operandStack.pop();
					//if the value is integer
					if((popValue1).matches("-?[1-9]\\d*|0")) {
						operand1=Integer.parseInt(popValue1);
					}
					else {
						variable1= popValue1;
					}
					
					
					//check if it's invalid expression
					if(operandStack.peek()==null) {
						System.out.println("INVALID expression format");
						isValid=false;
						break;
					}
					popValue2= operandStack.pop();
					if((popValue2).matches("-?[1-9]\\d*|0")) {
						operand1=Integer.parseInt(popValue2);
					}
					else {
						variable1=popValue2;
					}
					res=assignCal(queueVal,operand1,variable1);
					//operandStack.push(String.valueOf(res));
					sbTable.put(variable1, res);
				}
				else if(operandStack.peek()!=null) {
					System.out.println("INVALID expression format");
					isValid=false;
					break;
				}
			}
			//operators: +, -, * , /
			else if ((queueVal).matches("[+-/*/^]")) {
				//if it's an integer
				if(operandStack.peek()!=null && operandStack.peek().matches("-?[1-9]\\d*|0")) {
					popValue1= operandStack.pop();
					operand1=Integer.parseInt(popValue1);
					popValue2=operandStack.pop();
					if(popValue2==null) {
						System.out.println("INVALID expression format");
						isValid=false;
						break;
					}
					//if the second operand is also an integer
					else if((popValue2).matches("-?[1-9]\\d*|0")) {
						operand2=Integer.parseInt(popValue2);
						
					}
					//if the second value is variable, then search the value in hash table
					else {
						//check the hash table, if it's not in it, then break
						if(sbTable.get(popValue2)==null) {
							System.out.println("INVALID expression format");
							isValid=false;
							break;
						}
						//integer and variable
						operand2=sbTable.get(popValue2);
					}
					res=calculate(queueVal,operand1,operand2);
					//push the result back to the stack
					operandStack.push(String.valueOf(res));
					
				}
				else if(operandStack.peek()!=null && operandStack.peek().matches("\\w+")) {
					popValue1 = operandStack.pop();
					//if peek is variable
					if(!operandStack.peek().matches("-?[1-9]\\d*|0")) {
						popValue2 = operandStack.pop();
						res = calculateVar(queueVal,popValue1,popValue2);
						operandStack.push(String.valueOf(res));
					}
					else if(operandStack.peek().matches("-?[1-9]\\d*|0")){
						operand2 = Integer.parseInt(operandStack.pop());
						res=calculate(queueVal,sbTable.get(popValue1),operand2);
						operandStack.push(String.valueOf(res));
					}
					
						
				}
				
				else if(operandStack.peek()==null) {
					System.out.println("INVALID expression format");
					isValid=false;
					break;
				}

			}	
		}
		return res;
	}
	private int calculate(String operator,int op1,int op2) {
		switch (operator) {
		case "+":
			return op2+op1;
		case "-":
			return op2-op1;
		case "*":
			//System.out.println(op2+" * "+op1+" = "+(op2*op1));
			return op2*op1;
		case "/":
			//System.out.println(op2+" / "+op1+" = "+(op2/op1));
			return op2/op1;
		case "^":
			//System.out.println(op2+" ^ "+op1+" = "+(int)Math.pow(op2, op1));
			return (int) Math.pow(op2, op1);
		default:
			return -1;
		
		}
	}
	private int calculateVar(String operator,String var1,String var2) {
		switch (operator) {
		case "+":
			//System.out.println("x= "+ sbTable.get(var2)+" y="+ sbTable.get(var1));
			return sbTable.get(var2)+sbTable.get(var1);
		case "-":
			return sbTable.get(var2)-sbTable.get(var1);
		case "*":
			return sbTable.get(var2)*sbTable.get(var1);
		case "/":
			return sbTable.get(var2)/sbTable.get(var1);
		case "^":
			return (int) Math.pow(sbTable.get(var2), sbTable.get(var1));
		default:
			return -1;
		
		}
	}
	private int assignCal(String operator,int operand,String var) {
		switch(operator) {
		case "+=":
			return sbTable.get(var)+operand;
		case "-=":
			return sbTable.get(var)-operand;
		case "*=":
			return sbTable.get(var)*operand;
		case "/=":
			return sbTable.get(var)/operand;
		default:
			return -1;
		}
	}
	public static void main(String[] args) throws FileNotFoundException {
		fileName=args[0];
		inputFile = new File(fileName);
		Scanner sc = new Scanner (inputFile);
		String inputExp="";
		String[] expressionArr;
		String s=null;
		//ExpressionEvaluator evaluator = new ExpressionEvaluator();
		
		while(sc.hasNextLine()) {
			inputExp = sc.nextLine();
			if(!inputExp.trim().isEmpty())
				System.out.println("Input expression: "+inputExp);
			expressionArr =inputExp.split("\\s+");
			for(int i=0;i<expressionArr.length;i++) {
				
				inputQueue.enqueue(expressionArr[i]);
				//System.out.print(expressionArr[i]+" ");
			}
			//inputQueue.displayQueue();
			evaluator.evaluate();
			//get the result from the top of the stack
			s=operandStack.pop();
			if(isValid) {
				if(s!=null) {
					/*if the top of the stack is not an integer, 
					it's invalid expression format*/
					if(!s.matches("-?[1-9]\\d*|0")) {
						System.out.println("Value: "+ sbTable.get(s));;
					}
					//if the stack is not empty, it's invalid expression
					else if(operandStack.peek()!=null) {
						//System.out.print(operandStack.peek());
						System.out.println("INVALID expression format");
						isValid=false;
					}
					//otherwise, print the value (result)
					else {
						System.out.println("Value: "+ s);
					}
				}	
			}
			//print the values of variables
			if(isValid) {
				/*check all the elements in the expression array to see if it's in hash table
				 * if it's in the hash table, print the key and value, and then remove it from hashtable
				 */
				int count=0;
				for(int i=0;i<expressionArr.length;i++) {
					if(sbTable.get(expressionArr[i])!=null && sbTable.size()>1) {
						count++;
						if(count==1) {
							System.out.print("Symbol table entries: ");
						}
						//System.out.println("size= "+ sbTable.size());
						System.out.print(expressionArr[i]+"="+sbTable.get(expressionArr[i])+", ");
						sbTable.remove(expressionArr[i]);
						
					}
					else if(sbTable.get(expressionArr[i])!=null && sbTable.size()==1) {
						System.out.println(expressionArr[i]+"="+sbTable.get(expressionArr[i]));
						sbTable.remove(expressionArr[i]);
					}
				}
			}
			
			//clear queue, stack and hashtable
			inputQueue.clear();
			operandStack.clear();
			sbTable.clear();
			
		}
			
	
	}
	
	
}
