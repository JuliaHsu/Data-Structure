/**
 * Generic dynamic array class
 * collect of objects in an array whose capacity can grow and shrink.
 * @author Hsin Ping (Julia) HSU on 2018.9/27
 */
class DynamicArray<T> {
	private int elementCount;
	private Object[] elementData;
	private Object[] newElementData;
	//constructor for dynamic array
	//Initial dynamic array with size 2
	public DynamicArray(){
		this.elementData=new Object[2];
	}
	
	T elementData(int index){
		return (T) elementData[index];
	}
	//Check the range of the array, if the index is out of bound, throw exception
	private void rangeCheck(int index){
		if(index>=elementCount){
			String outOfBoundsMsg="Index: "+index + ",number of element: "+elementCount;
			throw new IndexOutOfBoundsException(outOfBoundsMsg);
		}
	}

	public T set(int index, T element){
		rangeCheck(index);
		//get original value of index
		T oldValue = elementData(index);
		//set new element to the index
		elementData[index]=element;
		return oldValue;
	}
	public T get(int index){
		rangeCheck(index);
		return elementData(index);
	}

	private void ensureCapacityInternal(int minCapacity){
		//overflow
		//While adding one element, the minimum capacity is current size +1 
		//when there's no space for new item, call grow(minCapacity) method to double its capacity 
		if(minCapacity-elementData.length>0){
			grow();
		}
	}
	private void grow(){
		//double the capacity of array by creating a new array called newElementData
		newElementData= new Object[elementData.length * 2];
		//copy all the elements to the new array
		for(int i=0;i<elementData.length;i++){
			newElementData[i]=elementData[i];
		}
		//re address the elementData to the new array to do further work
		elementData = newElementData;
		System.out.println("** Doubling message array");
	}
	//shrink the array to its actual size
	private void shrink(int currentCapacity){
		int newSize=0;//new size for newElementData array
		//newSize=currentCapacity;
		//when the size falls below 1/3 of the capacity, shrink the array to half its capacity
		if (elementCount<((currentCapacity)/3)){
			//set the new size to half of its capacity
			newSize=currentCapacity/2;
			newElementData = new Object[newSize];
			//Move all elements from elementData to newElementData
			for(int i=0;i<elementCount;i++){
					newElementData[i]=elementData[i];
				
			}
			//re address the elementData to the new array to do further work
			elementData=newElementData;
			System.out.println("** Shrinking message array");
		}
	}
	//Copy Array method
	/*src: source array
	 srcStartPos: starting position in the source array
	 dest: destination array
	 destStartPos: starting position in the destination array
	 numOfElements: the number of array elements to be copied*/
	private void copyArray(Object[] src, int srcStartPos, Object[] dest, int destStartPos, int numOfElements){
		while(numOfElements>0){
			dest[destStartPos]=src[srcStartPos];
			srcStartPos++;
			destStartPos++;
			numOfElements--;
			
		}
	}
	//Insert element from the end
	public boolean append(T element){
		//While adding one element, the minimum capacity is current size +1 
		//Check if the array needs to grow or not.
		ensureCapacityInternal(elementCount+1);
		//assign the element to next index
		elementData[elementCount++]=element;
		return true;
	}
	//Insert element to specific index
	public void insert(int index, T element){
		//check if it's out of bound
		rangeCheck(index);
		//check the array needs to grow or not.
		ensureCapacityInternal(elementCount+1);
		//copy the array in order to insert an element inside the array
		copyArray(elementData,index,elementData,index+1,elementCount-index);
		elementData[index]=element;
		elementCount++;
	}

	//remove the index th element 
	public T remove(int index){
		rangeCheck(index);
		T oldValue = elementData(index);
		//the number of array elements to be copied after removing 1 element
		int numMoved=elementCount-index-1;
		if (numMoved>0){
			//copy the array in order to delete an element inside the array
			copyArray(elementData, index+1, elementData, index, numMoved);
		}
		//because the size of elements is decreased, assign the last index to null
		elementData[--elementCount]=null;
		//check if the array need to be shrinked or not
		shrink(elementData.length);
		return oldValue;
	}
	//get the index of element
	public int indexOf(Object object){
		if (object==null){
			for(int i=0; i<elementCount;i++){
				if(elementData[i]==null)
					return i;
			}
		}
		else{
			for(int i=0;i<elementCount;i++){
				if(elementData[i]==object)
					return i;
			}
		}
		return -1;
	}
	//Get the size of elements
	public int getElementCount(){
		return elementCount;
	}
	
	
}
