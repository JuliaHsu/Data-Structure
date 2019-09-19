# Linked Lists and Dynamic Arrays
INFS 519: Project 1 
Create a class to implement the Table abstract data type and then use this class to implement a simple address book.

## Requirements:
Table is an ADT which stores pairs of values: a key and a value. The key must be unique and is used to identify the pair. There are some operations:
1. String lookUp(String key): Looks up the entry with the given key and returns the associated value. If no entry is found null is returned.
2. boolean insert(String key, String value): Inserts a new entry to the table. If an entry already exists with the given key value make no insertion but return false.
3. boolean deleteContact(String key): Deletes the entry with the given key. If no entry is found returns false.
4. boolean update(String key, String newValue): Replaces the old value associated with with the given key with the newValue string.
5. boolean SendMessage(String key, String msg): adds a message into the dynamic array associated with this entry node. If no entry is found returns false and displays appropriate message.
6. boolean deleteMessages(String key) : Deletes all messages sent to the entry with the given key. If no entry is found returns false.
7. int displayMessages() : Displays all text messages sent to a contact. Returns total message count.
8. Dynamic Array:
DynamicArray<T> (DynamicArray.java):
This class represents a generic collection of objects in an array whose capacity can grow and shrink as needed. It supports the usual operations of adding and removing objects including add() and remove(). The capacity of a DynamicArray must change as items are added or removed and the following rules must be followed in your implementation:
	1. The default initial capacity is fixed to be 2.
	2. When you need to add items and there is not enough space, grow the array to double its capacity.
	3. When you delete an item and the size falls below 1/3 of the capacity, shrink the array to half its capacit

## Implementations:
1. A class Table stores entries which are key and value.  
2. A Node class that have a key String (used to identify the entry), a value String (used to store some data associated with the key), a dynamic array msgs (with an initial size of two) for storing text messages to a contact, and a next pointer used for linking. 
4. Table will keep a linked list of Nodes. Each contact entry will be stored in an instance of Node stored in this linked list.


