/*This is a generic class used to implement the program stack which
 *is implemented by dynamic array
 * @author Hsin Ping (Julia) HSU on 2018.10/10
 */
class ProgramStack <T> {
	private Object[] operandArr;
	private Object[] tmpArr;
	
	private int top;
	private static final int CAPACITY=10;
	
	public ProgramStack() {
		operandArr=new Object[CAPACITY];
		top=-1;
	}
	//double the array is the array is full
	private void doubleArray() {
		tmpArr=new Object[operandArr.length*2];
		for(int i=0;i<operandArr.length;i++) {
			tmpArr[i]=operandArr[i];
		}
		operandArr=tmpArr;
	}
	//pushes an item on the stack
	public void push(T item) {
		//when the length of array == top+1, means that the array is full.
		if(top+1==operandArr.length) {
			doubleArray();
		}
		top++;
		operandArr[top]=item;
	}
	//pops an item off the stack
	public T pop() {
		if(top==-1) {
			return null;
		}
		T value = (T) operandArr[top];
		top--;
		return value;
	}
	//returns the item at top of the stack
	public T peek() {
		if(top==-1) {
			return null;
		}
		else {
			return (T) operandArr[top];
		}
	}
	//removes everything from the stack
	public void clear() {
		top=-1;
	}
//	public void displayStack() {
//		for(int i=0;i<operandArr.length;i++) {
//			System.out.println(operandArr[i]);
//		}
//	}
}
