import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
public class TreeAddressTable {
	static String inputFileName="";
	static String outputFileName="";
	static File inputFile;
	static int numOfContacts;
	boolean isInTree = false;

	class BinaryTreeNode{
		String key; 
		String value;
		BinaryTreeNode left;
		BinaryTreeNode right;
		int size;
		int height;
		//constructor
		public BinaryTreeNode(String name,String address){
			key = name;
			value = address;
			left=right=null;
			size=0;
			height=0;
		}
		
		
	}
	BinaryTreeNode root;
	//check if the tree is empty
	private boolean treeIsEmpty() {
		return root==null;
	} 
//	private int size(BinaryTreeNode node) {
//		if(node==null) {
//			return 0;
//		}
//		else {
//			return 1+size(node.left)+size(node.right);
//		}
//	}
	//get height of the node
	private int height(BinaryTreeNode node) {
		//null node's height is -1
		if(node ==null) {
			return -1;
		}
		else {
			//the height of the node
			return 1+Math.max(height(node.left), height(node.right));
		}
	}
	//Inserts a new entry to the table
	public boolean insert(String key, String value) {
		//first check if the contact exists or not
		if(lookUp(key)==null) {
			//if the key is not found, insert the node
			insertNode(key,value,root);
			//count the number of the contact
			numOfContacts++;
			return true;
		}
		//if the key exists in the book
		else {
			return false;
		}	
	}
	//Looks up the entry with the given key and returns the associated value 
	public String lookUp(String key) {
		BinaryTreeNode node = find(key,root);
		//return its value if the node is found
		if(node!=null) {
			return node.value;
		}
		else {
			return null;
		}
	}
	//Deletes the entry with the given key.
	public boolean deleteContact(String key) {
		//first check if the contact exists or not
		if(lookUp(key)!=null) {
			remove(key,root);
			numOfContacts--;
			return true;
		}
		
		else {
			System.out.println("Cannot find " + key + " in the address book.");
			return false;
		}
	}
	//Replaces the old value associated with with the given key with the newValue string
	public boolean update(String key,String newValue) {
		BinaryTreeNode node = find(key,root);
		//first check if the contact exists or not
		if(node!=null) {
			node.value = newValue;
			return true;
		}
		else {
			return false;
		}
		
	}
	//Displays Name/Address for each table entry
	public int displayAll() {
		inorder(root);
		return root.size;
	}
	//reads the name of a text output file, and will write a list of the table entries to an the output file.
	public void save() throws FileNotFoundException {
		PrintStream ps = new PrintStream(new File(outputFileName));
		this.preorder(root,ps);
		System.out.println("Tree size = "+ root.size);
		System.out.println("Number of contacts in address book = "+ numOfContacts);
	}
	
	
	private BinaryTreeNode insertNode(String key, String value,BinaryTreeNode node) {
		if(node==null) {
			//create new node
			node = new BinaryTreeNode(key, value); 
			//if the tree is empty, assign node to root
			if(treeIsEmpty()) {
				root = node;
			}
		}
		//left subtree
		else if(key.compareTo(node.key)<0) {
			node.left = insertNode(key,value,node.left);
		}
		//right subtree
		else if (key.compareTo(node.key)>0) {
			node.right = insertNode(key,value,node.right);
		}
		node.size++;
		node.height=height(node);
		return node;
		
	}
	
	private BinaryTreeNode find(String key,BinaryTreeNode node) {
		while(node!=null) {
			//keep finding on the left subtree 
			if(key.compareTo(node.key)<0) {
				node =find(key,node.left);
			}
			//keep finding on the right subtree 
			else if (key.compareTo(node.key)>0) {
				node = find(key,node.right);
			}
			else {
				//match
				return node;
			}
		}
		//no match found
		return null;
	}
	private BinaryTreeNode findMin(BinaryTreeNode node) {
		if(node!=null) {
			//if there's a left node, means that there's a smaller node;
			//keep finding the smallest node until left is null
			while(node.left!=null) {
				node =node.left;
			}
			return node;
		}
		//the tree is empty
		else {
			System.out.println("No contact in the address book.");
			return null;
		}
	}
	private BinaryTreeNode removeMin(BinaryTreeNode node) {
		//the tree is empty
		if (node==null) {
			System.out.println("No contact in the address book.");
			return null;
		}
		//keep finding the minimum node
		else if(node.left!=null) {
			node.left = removeMin(node.left); //recursive
			node.size--;
			node.height=height(node);
			return node;
		}
		else {
			return node.right; //return the root of resulting subtree
		}
	}
	private BinaryTreeNode remove(String key,BinaryTreeNode node) {
		if(key.compareTo(node.key)<0) {
			node.left = remove(key,node.left);
		}
		else if(key.compareTo(node.key)>0) {
			node.right = remove(key,node.right);
		}
		//the node is found and it has two children
		else if(node.left!=null && node.right!=null) {
			//replace the node to the smallest node of the right subtree
			BinaryTreeNode min = findMin(node.right);
			node.key =min.key;
			node.value = min.value;
			//remove the smallest node
			node.right = removeMin(node.right);
		}
		//the node is found and it only has one child or doesn't have any child
		else{
			node = (node.left!=null)? node.left:node.right;
		}
		//when the node is leaf
		if (node!=null) {
			node.size--;
			node.height=height(node);
		}
			
		
		return node;
	}
	//print the contact by sorted
	private void inorder(BinaryTreeNode node) {
		if(node.left!=null) {
			inorder(node.left);
		}
		System.out.println(node.key);
		System.out.println(node.value);
		//System.out.println(node.size(node));
		System.out.println("     --- Node height = "+node.height);
		if (node.right!=null) {
			inorder(node.right);
		}
	}
	private void preorder(BinaryTreeNode node,PrintStream ps) throws FileNotFoundException {
		
		System.setOut(ps); 
		System.out.println(node.key);
		System.out.println(node.value);
		//System.out.println(node.size(node));
		System.out.println("     --- Node height = "+node.height);
		if(node.left!=null) {
			preorder(node.left,ps);
		}
		if (node.right!=null) {
			preorder(node.right,ps);
		}
		
	}
	public static void main(String args[]) throws FileNotFoundException {
		inputFileName=args[0];
		inputFile = new File(inputFileName);
		Scanner scFile = new Scanner(inputFile);
		Scanner scanner = new Scanner(System.in);
		String input ="";
		String name="";
		String address="";
		String output="";
		String tmpValue="";
		int rootSize;
		TreeAddressTable addressTable = new TreeAddressTable();
		while(scFile.hasNextLine()) {
			name = scFile.nextLine();
			address = scFile.nextLine();
			addressTable.insert(name, address);
		}
		
		do{
			System.out.println("\n1. Add a contact (a)");
			System.out.println("2. Look up a contact (l)");
			System.out.println("3. Update address (u)");
			System.out.println("4. Delete a contact (dc)");
			System.out.println("5. Display all contacts (ac)");
			System.out.println("6. Save and exit (q)");
			System.out.print("---> ");
			input = scanner.nextLine();
			switch(input) {
			case "a": case "1":
				System.out.print("Name: ");
				name = scanner.nextLine();
				//Check the input, if it's empty or whitespace(s), ask user to enter the name again
				/*trim: This method returns a copy of the string, with leading and 
				 trailing whitespace omitted.*/
				while (name.isEmpty() || name.trim().isEmpty() || name==null){
					System.out.println("A name cannot be empty!");
					System.out.print("Name: ");
					name = scanner.nextLine();
				}
				
				if(addressTable.lookUp(name)!=null) {
					System.out.println(name+" already exisited in the address book.");
				}
				else{
					System.out.print("Address: ");
					address = scanner.nextLine();
					//Check the input, if it's empty or whitespace(s), ask user to enter the address again
					/*trim: This method returns a copy of the string, with leading and 
					 trailing whitespace omitted.*/
					while(address.isEmpty() || address.trim().isEmpty() || address==null){
						System.out.println("An address cannot be empty!");
						System.out.print("Address: ");
						address = scanner.nextLine();
					}
					addressTable.insert(name, address);
				}
				break;
			case "l": case "2":
				System.out.print("Name: ");
				name = scanner.nextLine();
				//return value from lookup
				output = addressTable.lookUp(name);
				//if the name is not in the book
				if(output==null){
					System.out.println(name+" is not in the book.");
				}
				//Print the address (value)
				else{
					System.out.println("Address is "+ output);
				}
				break;
			case "u": case"3":
				System.out.print("Name: ");
				name = scanner.nextLine();
				//Get value from addressTable by calling lookup
				tmpValue = addressTable.lookUp(name);
				if(tmpValue==null){
					System.out.println(name+" is not in the book.");
				}
				else{
					System.out.println("Old address is "+ tmpValue);
					System.out.print("New address: ");
					String newAddress = scanner.nextLine();
					//replace tempValue to newAddress
					addressTable.update(name, newAddress);
				}
				break;
			//Delete contact
			case "dc": case "4":
				System.out.print("Name to delete: ");
				name = scanner.nextLine();
				addressTable.deleteContact(name);
				break;
			//Display address table
			case "ac": case "5":
				rootSize = addressTable.displayAll();
				System.out.println("Tree size = "+ rootSize);
				System.out.println("Number of contacts in address book = "+ numOfContacts);
				
				break;
			case "q": case "6":
				System.out.print("Output file name: ");
				outputFileName = scanner.nextLine();
				addressTable.save();
				
				break;
			default:
				System.out.println("Please enter correct command.");
				break; 
			}
	}while(!input.equals("q"));
		System.out.println("Quit the address table!");
	}

}
