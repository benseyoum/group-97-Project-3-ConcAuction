package concauction;
// -------------------------------------------------------------------------
/**
 *  Generic queue that stores elements in FIFO using a circular array
 * 
 *  @author krish
 *  @version Nov 11, 2026
 */
public class Queue<E>
{
    //~ Fields ................................................................
    private E[] queueArray;
    private int front;
    private int back;
    private int size;
    private int capacity;
   
    
    
    
    
    // ----------------------------------------------------------
    /**
     * Create a new Queue object.
     */
    public Queue()
    {
        capacity = 10;
        queueArray = (E[])new Object[capacity];
        front = 0;
        back = 0;
        size = 0;
    }
    
    
    
    
    
   
    // ----------------------------------------------------------
    /**
     * Doubles capacity queue. Keeps it in FIFO.
     */
    public void doubleSize()
    {
        int newCapacity = capacity * 2;
        E[] newArray = (E[])new Object[newCapacity];

        for (int i = 0; i < size; i++)
        {
            newArray[i] = queueArray[(front + i) % capacity];
        }

        queueArray = newArray;
        capacity = newCapacity;
        front = 0;
        back = size;
    }
    
    
    
    
    /**
     * Checks whether the queue is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    
    
    
    // ----------------------------------------------------------
    /**
     * Removes and returns the first element
     * @return the removed element or null
     */
    public E dequeue()
    {
        if (isEmpty())
        {
            return null;
        }

        E element = queueArray[front];
        queueArray[front] = null;
        front = (front + 1) % capacity;
        size--;

        return element;
    }
    
    
    // ----------------------------------------------------------
    /**
     * Returns the first element without removing
     * @return the first element
     */
    public E peek()
    {
        if (isEmpty())
        {
            return null;
        }

        return queueArray[front];
    }
    
    /**
     * Adds an element to the back of the queue
     *
     * @param element the element to add
     *            
     */
    public void enqueue(E element)
    {
        if (size == capacity)
        {
            doubleSize();
        }

        queueArray[back] = element;
        back = (back + 1) % capacity;
        size++;
    }
}
