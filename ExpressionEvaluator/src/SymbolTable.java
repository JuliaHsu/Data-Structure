/*A generic class to implement a simple probing hash table to store 
 * variables along with their values. The hash table is implemented 
 * as a dynamic array (with initial size of 2)
 * @author Hsin Ping (Julia) HSU on 2018.10/10
 * */
class SymbolTable <K,T>{
	private final int DEFAULT_TABLE_SIZE=2;
	private TableEntry<String,T>[] hashtable;
	private TableEntry<String,T>[] tmpHashTable;
	private TableEntry<String,T> tmp;
	private int numOfElements=0;
	
	public SymbolTable() {
		hashtable=new TableEntry[DEFAULT_TABLE_SIZE];
		for(int i=0;i<hashtable.length;i++) {
			hashtable[i]=null;
		}
	}
	//returns the length of the hashtable
	public int getCapacity() {
		return hashtable.length;
	}
	//returns the number of elements in the hash table
	public int size() {
		return numOfElements;
	}
	
	//places value v at the location of key k.
	public void put(String k,T v) {
		tmp = new TableEntry<String,T>(k,v);
		int hashcode = (k.hashCode()&0x7fffffff)%getCapacity();
		//check if the key is already in hash table, if so, replace the value
		if(get(k)!=null) {
			tmp = new TableEntry<String,T>(k,v);
			while(!hashtable[hashcode].getKey().equals(k)) {
				hashcode=(hashcode)+1%getCapacity();
			}
			hashtable[hashcode]=tmp;
		}
		
		// else if the place is not occupied or the location is marked deleted
		else if(hashtable[hashcode]==null || hashtable[hashcode].tombstone) {
			
			tmp = new TableEntry<String,T>(k,v);
			//the table is 80% full, double the array and rehash
			if(size()+1>=getCapacity()*0.8) {	
				rehash(getCapacity());
			}
			hashtable[hashcode]=tmp;
			numOfElements++;		
		}
		else {
			tmp = new TableEntry<String,T>(k,v);
				//check if elements can fit in the hashtable
				if(size()+1>=getCapacity()*0.8) {
					//System.out.println("size: "+ size()+" Capacity: "+ getCapacity()+k+" rehashing");
					rehash(getCapacity());
				}
				//linear probing
				while (hashtable[hashcode]!=null && !hashtable[hashcode].tombstone) {
					hashcode=(hashcode+1)%getCapacity();
				}
				hashtable[hashcode]= tmp;
				numOfElements++;
			
			
		}
//		for(int i=0;i<hashtable.length;i++) {
//			System.out.println(hashtable[i]);
//		}
	}
	/*Removes the given key (and associated value) from the table
	and returns the value removed or null if value is not found.*/
	public T remove(String k) {
		//count the searching to avoid infinite search loop
		int searchCount=0;
		int hashcode = (k.hashCode()&0x7fffffff)%getCapacity();
		tmp=hashtable[hashcode];
		//start searching
		while(tmp==null||!tmp.getKey().equals(k)) {
			hashcode=(hashcode+1)%getCapacity();
			tmp=hashtable[hashcode];
			searchCount++;
			//if all the index has been searched, leave the loop and return null
			if(searchCount==hashtable.length) {
				return null;
			}
		}
		//mark the index with tombstone
		tmp.tombstone=true;
		numOfElements--;
		//System.out.println(numOfElements);
		return tmp.getValue();		
	}
	
	//Given a key, returns the value from the table, returns null if not found
	public T get(String k) {
		//count the searching to avoid infinite search loop
		int searchCount=0;
		int hashcode = (k.hashCode()&0x7fffffff)%getCapacity();
		//start searching
		tmp=hashtable[hashcode];
		/*if there's sth in the index, and the key is the same as k, 
		and it's not marked deleted*/
		if(tmp!=null && tmp.getKey().equals(k) && !tmp.tombstone) {
			//System.out.println(tmp.tombstone);
			return tmp.getValue();
		}
		// if the key is same as k, but it's marked deleted, return null
		else if(tmp!=null && tmp.getKey().equals(k) &&tmp.tombstone) {
			return null;
		}
		// start linear probing searching 
		else {
			while((tmp==null||!tmp.getKey().equals(k))) {
				hashcode=(hashcode+1)%getCapacity();
				tmp=hashtable[hashcode];
				searchCount++;
				//when search Count == hashtable.length means that cannot find the key in the array.
				if(searchCount==hashtable.length) {
					return null;
				}
		}
		
	}
		return tmp.getValue() ;
	}
	//removes everything from the hash table
	public void clear() {
		hashtable=new TableEntry[DEFAULT_TABLE_SIZE];
		numOfElements=0;
	}
	//increases the size of the hash table, rehashing all values.
	public boolean rehash(int size) {
		int newHashCode=0;
		TableEntry<String,T> t;
		if(size*2>size+1) {
			//double the size of table
			tmpHashTable = new TableEntry[size*2];
			for(int i=0;i<getCapacity();i++) {
				//find non empty index
				if(hashtable[i]!=null) {
					t=hashtable[i];
					//get new hashcode
					newHashCode= (t.getKey().hashCode()& 0x7fffffff)%tmpHashTable.length;
					while (tmpHashTable[newHashCode]!=null) {
						newHashCode=(newHashCode+1)%tmpHashTable.length;
					}
					tmpHashTable[newHashCode]= t;
				}
				//System.out.println(tmpHashTable[i]);
				
			}
			hashtable=tmpHashTable;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	class TableEntry<K,V> {
		private K key;
		private V value;
		private boolean tombstone;
		public TableEntry(K key, V value){
			this.key = key; 
			this.value = value; 
			this.tombstone=false;
		}
		public K getKey(){ 
			return key; 
		}
		public V getValue(){ 
			return value; 
		}
		public String toString(){
			return key.toString()+":"+value.toString(); 
		}
	}

}
