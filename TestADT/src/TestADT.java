/**
 * This program contains an address table which store names as keys and address as values.
 * The table has several operations such as add contact, look up contact, update, display contacts 
 delete, send message, delete message and display messages.
 * All contacts information such as name, address, messages will be stored in a Node which contains key, value, next and message.
 * Messages that sent to each contacts are stored in dynamic array.
 * @author Hsin Ping (Julia) HSU on 2018.9/27
 */
import java.util.Scanner;
public class TestADT {
	
	class Node{
		private String key;
		private String value;
		Node next;
		//create a dynamic array to save message
		DynamicArray<String> message;
		//constructor of node
		public Node(String name, String address){
			key=name;
			value = address;
			next = null;
			message = new DynamicArray<>();;
		}
	}
	class Table{
		private Node head = null;
		private Node tail =null; 
		private Node mark;
		//check if the list is empty, if it's empty return true
		private boolean isEmpty(){
			return head==null;
		}
		//Inserts a new entry to the table
		public boolean insert(String key, String value){
			//if the list is empty
			if(isEmpty()){
				//create a new node with key and value, then set the new node as the head
				head = new Node(key,value);
				//because the list only has one element, the tail also equals the head
				tail = head;
				return true;
			}
			// if the list is not empty and the key isn't in the list
			else if (!isEmpty() && lookUp(key)==null ){
				//create a new node and make the tail's next point to the new node
				tail.next=new Node(key,value);
				//set the tail as the new node, which is tail.next
				tail=tail.next;
				return true;
			}
			//if the key is in the list, return false
			else{
				return false;
			}
			
		}
		//Looks up the entry with the given key and returns the associated value
		public String lookUp(String key){
			/*for loop: mark is a pointer start with head, keep searching for the key 
			value while mark is not null and the input name (key) is not the key of mark,
			as long as the key of mark equals to the input name, end of for loop.*/
			if(isEmpty()){
				return null;
			}
			for(markToStart(); mark!=null && !keyAtMark().equals(key); advanceMark());
			
				//return the address (value) of the name (key)
				if(mark!=null){
					return mark.value;
				}
				//if cannot find the name (key) in the list, return false
				else{
					return null;
				}
		}
		//Deletes the entry with the given name (key).
		public boolean deleteContact(String key){
			//check if the list is empty first, if so, return false.
			if(isEmpty()){
				return false;
			}
			//contact not found
			else if(lookUp(key)==null){
				System.out.println("Cannot find "+ key);
				return false;
			}
			/*check if the name (key) is also pointed by the head, if so, delete the head by
			setting its next value as the head*/
			
			else if (head.key.equals(key)){
				head = head.next;
				return true;
			}
			/*if the name is not a head, let the pointer mark points to a node and keep checking
			if the next node's key equals to the input name (key) until finding the key equals the
			input name.*/
			else{
				for(markToStart();mark!=null && !mark.next.key.equals(key);advanceMark());
				//if the key is the tail, meaning that mark.next is tail, then assign mark to tail.
				if(mark.next==tail){
					tail=mark;
					mark.next=null;
				}
				//if the deleted node is in the middle of the list.
				else{
					mark.next=mark.next.next;
				}
					
				return true;
			}
		}
		//Replaces the old value associated with with the given key with the newValue string.
		public boolean update(String key,String newValue){
			//if the input name (key) is not in the list, return false
			if(lookUp(key)==null){
				return false;
			}
			// keep moving the pointer until finding the key equals to the input name(key)
			else{
				for(markToStart();mark!=null && !keyAtMark().equals(key);advanceMark());
				//replace the address (value) with the new value.
				mark.value = newValue;
				return true;
			}
		}
		//Sets the mark to the first item in the table
		public boolean markToStart(){
			if (isEmpty()){
				return false;
			}
			else{
				mark = head;
				return true;
			}
			
		}
		//Moves the mark to the next item in the table.
		public boolean advanceMark(){
			if (isEmpty()){
				return false;
			}
			else if(mark.next==null){
				mark=mark.next=null;
				return false;
			}
			else{
				mark = mark.next;
				return true;
			}
		}
		//Returns the key stored in the item at the current mark
		public String keyAtMark(){
			return mark.key;
		}
		//Returns the value stored in the item at the current mark.
		public String valueAtMark(){
			return mark.value;
		}
		//Displays Name/Address for each table entry.
		public int displayAll(){
			//count for the total entry
			int count=0;
			//if the list is empty, return 0
			if(isEmpty()){
				System.out.println("No contact in the book!");
				return 0;
			}
			//print the table from the head to tail
			for(markToStart(); mark!=null; advanceMark()){
				System.out.println("Name: "+ keyAtMark());
				System.out.println("Address: "+ valueAtMark()+"\n");
				//counting the number of entry
				count++;
			}
			return count;
		}
		//adds a message into the dynamic array associated with this entry node
		public boolean sendMessage(String key,String msg){
			//if cannot find the name (key) in the list, return false.
			if(lookUp(key)==null){
				return false;
			}
			//else keep moving the pointer until finding the key equals to the input name.
			else{
				for(markToStart(); mark!=null && !keyAtMark().equals(key); advanceMark());
				//append the message to the dynamic array "message"
				mark.message.append(msg);
				return true;
			}
		}
		//Displays all text messages sent to a contact
		public int displayMessage(String key){
			//if the input name (key) is not in the list.
			if(lookUp(key)==null){
				System.out.println(key+" is not in the book, no messages deleted");
				return 0;
			}
			//else keep moving the pointer mark until finding the key equals to the input name
			else{
				for(markToStart(); mark!=null && !keyAtMark().equals(key); advanceMark());
				//if the person doesn't receive any message, which means the size of the message is 0.
				if(mark.message.getElementCount()==0){
					System.out.println(key+" doesn't receive any message.");
				}
				else{
					//else show all the messages that were sent to the person.
					System.out.print("Message sent: ");
					for(int i=0;i<mark.message.getElementCount();i++){
						//if it's the last message, don't show the ","
						if(i==mark.message.getElementCount()-1){
							System.out.println("-"+ mark.message.get(i));
						}
						else{
							System.out.print("-"+ mark.message.get(i)+", ");
						}
					}
				}
				//return the size of the message.
				return mark.message.getElementCount();
				
			}
			
		}
		//Deletes a message sent to the entry with the given key
		public boolean deleteMessages(String key){
			Scanner scanner = new Scanner(System.in);
			//the message number
			int msgNo=0;
			//if the key is not in the list.
			//keep moving the pointer mark until finding the key equals to the input name
			for(markToStart();mark!=null && !keyAtMark().equals(key);advanceMark());
			//if the person doesn't receive any message.
			if(mark.message.getElementCount()==0){
				System.out.println("No messages sent to "+ key);
				return false;
			}
			else{
				//allows user to input a number to choose message to be deleted
				System.out.println("Choose message to be deleted:");
				//show all the messages with a number
				for(int i=0;i<mark.message.getElementCount();i++){
					System.out.println((i+1) + ". " + mark.message.get(i));
				}
				//prompts user to enter a number.
				System.out.print("Message no: ");
				msgNo = scanner.nextInt();	
				//if the number is less than the size, then delete the message 
				if(msgNo<=mark.message.getElementCount()){
					System.out.println("Message -"+mark.message.get(msgNo-1)+"- deleted");
					mark.message.remove(msgNo-1);
				}
				//else if the user input a negative integer or enter a number which is bigger than the size of array
				else{
					System.out.println("Cannot find message no. "+ msgNo);
				}
					
				/*remove all messages of contact key, removing from the last element in 
				the dynamic array one by one by moving its index*/
				/*for(int i=msgSize-1;i>=0;i--){
					mark.message.remove(i);
				}*/
				return true;
			}
				
			
		}
	}
	
	public static void  main(String[] args){
		String input ="";
		String name="";
		String address="";
		String text="";
		String output="";
		String tmpValue="";
		Scanner scanner = new Scanner(System.in);
		TestADT adt = new TestADT();
		TestADT.Table addressTable = adt.new Table();
		
		do{
			System.out.println("\nAdd a contact (a)");
			System.out.println("Send a text message (m)");
			System.out.println("Look up a contact (l)");
			System.out.println("Update address (u)");
			System.out.println("Delete a contact (dc)");
			System.out.println("Delete messages of a contact (dm)");
			System.out.println("Display all contacts (ac)");
			System.out.println("Dsplay all messages to a contact (am)");
			System.out.println("Quit (q)");
			System.out.print("---> ");
			input = scanner.nextLine();
			switch(input){
			//Call insert
			case "a":
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
				//use look up to check if the name is in the book or not.
				
				if(addressTable.lookUp(name)!=null){
					System.out.println(name+" alrady exist in the book.");
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
			//Call sendMessage
			case "m":
				System.out.print("Name: ");
				name = scanner.nextLine();
				//use look up to check if the name is in the book or not.
				if(addressTable.lookUp(name)==null){
					System.out.println(name+" is not in the book, message not sent.");
				}
				else{
					System.out.print("Message: ");
					text = scanner.nextLine();
					addressTable.sendMessage(name, text);
					System.out.println("Message -"+text+"- sent to "+name);
				}
				break;
			//Look up
			case "l":
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
			//Update
			case "u":
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
			case "dc":
				System.out.print("Name to delete: ");
				name = scanner.nextLine();
				addressTable.deleteContact(name);
				break;
			//Display all contact
			case "ac":
				addressTable.displayAll();
				break;
			//Delete message
			case "dm":
				System.out.print("Name: ");
				name = scanner.nextLine();
				//use look up to check if the name is in the book or not.
				if(addressTable.lookUp(name)==null){
					System.out.println(name + " is not in the book, no messages deleted");
				}
				else{
					addressTable.deleteMessages(name);
				}
				break;
			// Display all messages
			case "am":
				System.out.print("Name: ");
				name = scanner.nextLine();
				addressTable.displayMessage(name);
				break;
			//Quit
			case"q":
				break;
			//ENter wrong command.
			default:
				System.out.println("Please enter correct command.");
				break;
			
			}
		}while(!input.equals("q"));
		System.out.println("Quit the address table!");
	}
}

