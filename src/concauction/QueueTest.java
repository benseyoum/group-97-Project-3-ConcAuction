package concauction;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  test the queue class
 *  @author krish
 *  @version Nov 11, 2026
 */
public class QueueTest extends TestCase
{
    //~ Fields ................................................................

    //~ Constructors ..........................................................

    //~Public  Methods ........................................................
    /**
     * tests adding elements to a queue
     */
    public void testEnqueue()
    {
        Queue<String> queue = new Queue<String>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
    }


    /**
     * tests adding more elements than the initial capacity
     */
    public void testEnqueueFull()
    {
        Queue<Integer> queue = new Queue<Integer>();

        for (int i = 0; i < 11; i++)
        {
            queue.enqueue(i);
        }

        for (int i = 0; i < 11; i++)
        {
            assertEquals(i, queue.dequeue().intValue());
        }

        assertTrue(queue.isEmpty());
    }


    /**
     * tests removing the front element
     */
    public void testDequeue()
    {
        Queue<String> queue = new Queue<String>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.peek());
    }


    /**
     * tests removing from an empty queue
     */
    public void testDequeueEmpty()
    {
        Queue<String> queue = new Queue<String>();

        assertNull(queue.dequeue());
    }


    /**
     * tests doubling the queue capacity
     */
    public void testDoubleSize()
    {
        Queue<Integer> queue = new Queue<Integer>();

        for (int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
        }

        queue.doubleSize();

        for (int i = 10; i < 20; i++)
        {
            queue.enqueue(i);
        }

        for (int i = 0; i < 20; i++)
        {
            assertEquals(i, queue.dequeue().intValue());
        }

        assertTrue(queue.isEmpty());
    }


    /**
     * tests whether a new queue is empty
     */
    public void testIsEmpty()
    {
        Queue<String> queue = new Queue<String>();

        assertTrue(queue.isEmpty());
    }


    /**
     * tests wrapping around the circular array
     */
    public void testEnqueueWrap()
    {
        Queue<Integer> queue = new Queue<Integer>();

        for (int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
        }

        for (int i = 0; i < 3; i++)
        {
            queue.dequeue();
        }

        for (int i = 10; i < 13; i++)
        {
            queue.enqueue(i);
        }

        for (int i = 3; i < 13; i++)
        {
            assertEquals(i, queue.dequeue().intValue());
        }

        assertTrue(queue.isEmpty());
    }


    /**
     * tests viewing the front element
     */
    public void testPeek()
    {
        Queue<String> queue = new Queue<String>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.peek());
        assertEquals("A", queue.dequeue());
    }


    /**
     * tests peeking at an empty queue
     */
    public void testPeekEmpty()
    {
        Queue<String> queue = new Queue<String>();

        assertNull(queue.peek());
    }


    /**
     * tests isempty on a nonempty queue
     */
    public void testIsEmptyNonEmpty()
    {
        Queue<String> queue = new Queue<String>();

        queue.enqueue("A");

        assertFalse(queue.isEmpty());
    }


    /**
     * tests whether the queue is empty after removing
     * its only element
     */
    public void testIsEmptyAfterDequeue()
    {
        Queue<String> queue = new Queue<String>();

        queue.enqueue("A");
        queue.dequeue();

        assertTrue(queue.isEmpty());
    }


    /**
     * tests doubling an empty queue
     */
    public void testDoubleSizeEmpty()
    {
        Queue<String> queue = new Queue<String>();

        queue.doubleSize();

        assertTrue(queue.isEmpty());

        queue.enqueue("A");

        assertEquals("A", queue.dequeue());
    }


    /**
     * tests doubling a queue after wrapping around
     */
    public void testDoubleSizeWrap()
    {
        Queue<Integer> queue = new Queue<Integer>();

        for (int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
        }

        for (int i = 0; i < 3; i++)
        {
            queue.dequeue();
        }

        for (int i = 10; i < 13; i++)
        {
            queue.enqueue(i);
        }

        queue.doubleSize();

        for (int i = 3; i < 13; i++)
        {
            assertEquals(i, queue.dequeue().intValue());
        }

        assertTrue(queue.isEmpty());
    }
}   
