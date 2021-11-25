import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;
import java.lang.*;
/**
 * JUnit Tests for Chapter 14
 */
@SuppressWarnings("deprecation")
public class Chapter14Test {
	/**
	 * Base Stacks and Queues
	 */
	static Queue<Integer> emptyQueue, singleQueue, palindrome, notPalindrome;
	static Stack<Integer> emptyStack, singleStack, sortedStack, notSortedStack, 
						equalStack, minStack, minRemovedStack, minRemovedStack2;
	/**
	 * Reset the base data structures just in case
	 */
	@BeforeEach 
	void reset(){
		emptyQueue = new LinkedList<Integer>();
		singleQueue = new LinkedList<Integer>(List.of(1));
		palindrome = new LinkedList<Integer>(List.of(3, 8, 17, 9, 17, 8, 3));
		notPalindrome = new LinkedList<Integer>(List.of(3, 17, 9, 4, 17, 3));
		
		emptyStack = new Stack<Integer>();
		singleStack = new Stack<Integer>();singleStack.push(1);
		sortedStack = new Stack<Integer>();
		equalStack = new Stack<Integer>();
		notSortedStack = new Stack<Integer>();notSortedStack.push(1);
		List<Integer> sortedList = List.of(20, 20, 17, 11, 8, 8, 3, 2);
		for(int i: sortedList){
			sortedStack.push(i);notSortedStack.push(i);equalStack.push(i);
		}
		
		List<Integer> minList = List.of(2, 8, 3, 19, 2, 3, 2, 7, 12, -8, 4);
		minStack = new Stack<Integer>();
		minRemovedStack = new Stack<Integer>();
		minRemovedStack2 = new Stack<Integer>();
		for(int i: minList){
			minStack.push(i);minRemovedStack.push(i);minRemovedStack2.push(i);
			if(i == -8){
				minRemovedStack.pop();
				minRemovedStack2.pop();
			} else if(i == 2){
				minRemovedStack2.pop();
			}
		}		
		return;
	}
	
	/**
	 * Tests 14.5 equals
	 */
	@Test 
	public void testEquals(){
		assertTrue(equals(sortedStack, equalStack));
		assertFalse(equals(sortedStack, minStack));
		assertEquals(sortedStack, equalStack);//ensure sorted is "unchanged"
		assertFalse(equals(emptyStack, singleStack));
		assertFalse(equals(null, emptyStack));
		assertTrue(equals(null,null));
	}
	
	/**
	 * Tests 14.8 isPalindrome
	 */
	@Test 
	public void testIsPalindrome(){
		assertTrue(isPalindrome(emptyQueue));
		assertTrue(isPalindrome(palindrome));
		assertTrue(isPalindrome(palindrome));//ensure palindrome is "unchanged"
		assertNotEquals(emptyQueue, palindrome);
		assertTrue(isPalindrome(singleQueue));
		assertFalse(isPalindrome(notPalindrome));
		assertThrows(IllegalArgumentException.class, ()->{isPalindrome(null);});
	}
	
	/**
	 * Tests 14.15 isSorted
	 */
	@Test 
	public void testIsSorted(){
		assertTrue(isSorted(sortedStack));
		assertEquals(sortedStack, equalStack);//ensure sortedStack is "unchanged"
		assertFalse(isSorted(notSortedStack));
		assertTrue(isSorted(emptyStack));
		assertTrue(isSorted(singleStack));
		assertThrows(IllegalArgumentException.class, ()->{isSorted(null);});
	}
	
	/**
	 * Tests 14.19 removeMin
	 */
	@Test 
	public void testRemoveMin(){
		assertEquals(-8, removeMin(minStack));
		assertEquals(minStack, minRemovedStack);
		assertEquals(2, removeMin(minStack));
		assertEquals(minStack, minRemovedStack2);
		assertThrows(IllegalArgumentException.class, ()->{removeMin(null);});
		assertThrows(IllegalArgumentException.class, ()->{removeMin(emptyStack);});
	}

	/**
	 * @param s1 The first stack
	 * @param s2 The second stack
	 * @return true if the stacks have the same sequence
	 * First we check to see if the two stacks are empty, 
	 * then we check to see if only the first is null. 
	 * Then a new stack, and boolean is created. Boolean is set to true.
	 * Two ints are created and set to the stack's sizes.
	 * Then if two sizes do not equal each other we return false.
	 * Then when the first stack is not empty. We create two new ints and set it to 
	 * remove for the stacks then when the two are not equal to each other, we set our bool equal to false.
	 * then while our new stack is not empty we add the removal to each list. Then we return our boolean. 
	 *  
	 */
	public static boolean equals(Stack<Integer> s1, Stack<Integer> s2){
		if (s1 == null && s2 == null)
			return true;
		if (s1 == null)
			return false;
	Stack<Integer> stack = new Stack<>();
    boolean eq;
    eq = true;
    
    
	int s1size = s1.size();
	int s2size = s2.size();
            
    if(s1size != s2size) {
        return false;
	}                                                   
    while(!s1.isEmpty()) {
        int fpop = s1.pop();
        int spop = s2.pop();
		if(fpop != spop) {
            eq = false;
        }
        stack.push(fpop);
        stack.push(spop);
    }
    while(!stack.isEmpty()) {
        s2.push(stack.pop());
        s1.push(stack.pop());
    }
    return eq;
}
	
	
	/**
	 * Checks if a queue of Integers has the same sequence forwards and backwards.
	 * 
	 * Use one stack as auxilary storage. The queue needs to look as 
	 * it did prior to this function call after it returns.
	 * 
	 * @param q The candidate palindrome
	 * @return true if the queue is a palindrome
	 * @throws IllegalArgumentException if the queue is null
	 * First we check to if the queue is null, if it is we throw an exception.
	 * Then a new stack, an int that represents the queue's size, and a boolean is created. Boolean is set to true.
	 * Then we have a for loop and we loop through the queue and set a new int equal to remove items from the queue
	 * Then we add the removes to the queue and the stack.
	 * Then we have another for loop and we loop through the queue and set a new int equal to remove items from the queue and a new int 
	 * to remove items from the stack.
	 * If they do not equal each other we set our bool to false. Then we add our remoes to the queue and return the bool/
	 * 
	 */
	public static boolean isPalindrome(Queue<Integer> q){
	if(q == null){
			throw new IllegalArgumentException();
		}
	Stack<Integer> stack = new Stack<>();
    int size = q.size();
    boolean pal;
    pal = true;
                
    for(int i = 0; i < size; i++) {
        int rem = q.remove();
        q.add(rem);
        stack.push(rem);
    }
                                                    
    for(int i = 0; i < size; i++) {
        int newrem = q.remove();
        int newpop = stack.pop();
        
        if(newrem != newpop){
            pal = false;
		}
            
        q.add(newrem);
    }
    
    return pal;
	}
	/**
	 * Checks if a stack of Integers is sorted
	 * 
	 * Use one queue or stack, but not both, as auxilary storage. 
	 * The stack needs to look as it did prior to this function call after it returns.
	 * 
	 * @param s The candidate sorted stack
	 * @return true if the stack is sorted
	 * @throws IllegalArgumentException if the stack is null
	 * First we check to if the queue is null, if it is we throw an exception.
	 * then we check to see if the size of the stack is less than 2, if it is then we return true.
	 * Then a new stack, an int that removes from the stack, and a boolean is created. Boolean is set to true. We also push the removal to the stack
	 * Then when the stack is not empty a new int removes is ccreated and if the new removes is greater than the old removes,
	 * then the bool is equal to false and the old removes is equal to the new removes and the remove is pushed to the stack. 
	 * Then while our new stack is not empty we push the removal to the top of the stack.
	 * Then we return our boolean. 
	 */
	public static boolean isSorted(Stack<Integer> s){
	if(s == null){
		throw new IllegalArgumentException();
	}
	if(s.size() < 2){
        return true;
	}
    Stack<Integer> stack = new Stack<>();
    boolean sort = true;
    int sortpop = s.pop();
    stack.push(sortpop);
    
    while(!s.isEmpty()) {
        int newsortpop = s.pop();
        
        if(newsortpop < sortpop)
            sort = false;
            
        sortpop = newsortpop;
        stack.push(sortpop);
    }
    
    while(!stack.isEmpty()) {
        s.push(stack.pop());
	}
        
    return sort;
	}
	
	/**
	 * Removes the minimum value from a stack.
	 * 
	 * Use one queue as auxilary storage. 
	 * The stack needs to maintain relative ordering sans min(s).
	 * 
	 * @param s The stack to have elements removed
	 * @return true if the stack is sorted
	 * @throws IllegalArgumentException if the stack is null or empty
	 * First we throw an exception when the stack is equal to null or is empty.
	 * Then we create a new queue and an int that removes the value at the top of the stack without removing it (this is our min).
	 * Then while our stack is not empty, we create a new int equal to remove in the stack then we add the removal to the queue. 
	 * If our min is greater than the removal then min becomes the removal. 
	 * Then while the queue is empty we create a new int that is equal to the remove in the queue, then if the removal is not equal to the removal
	 * the removal is added to the stack.
	 * Then while the stack is not empty we add the removal from the stack to the queue and 
	 * while our queue is not empty we add the removal from the queue to the stack
	 * then finally we return our minimum.
	 */
	public static int removeMin(Stack<Integer> s){
	if(s == null || s.isEmpty()){
		throw new IllegalArgumentException();
	}
	Queue<Integer> queue = new LinkedList<>();
    int min = s.peek();
    
    while (!s.isEmpty()) {
        int spop = s.pop();
        queue.add(spop);
        
        if (min > spop) {
            min = spop;
        }
    }
    while (!queue.isEmpty()) {
        int qrem = queue.remove();
        
        if (qrem != min) {
            s.push(qrem);
        }
    }
    while (!s.isEmpty()) {
        queue.add(s.pop());
    }
    while (!queue.isEmpty()) {
        s.push(queue.remove());
    }
    
    return min;
}
}
