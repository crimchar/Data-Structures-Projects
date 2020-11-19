public class LinkedArrayQueue<E> {

    private int front = 0;

    private int back = 0;

    private int size = 0;

    private int arrayNumber = 1;

    private int traversed = 0;

    private SinglyLinkedList<E> top;

    private SinglyLinkedList<E> bottom;

    private SinglyLinkedList<E> current;

    private SinglyLinkedList<E> firstTraverse;


    public LinkedArrayQueue()
    {

        bottom = new SinglyLinkedList<E>(null);   //makes an object of type SinglyLinkedList

        top = bottom;

        top.setNext(bottom);

        current = top;

    }

    public int size()   //returns the size
    {
        return size;
    }

    //return the number of arrays currently storing elements
    public int numArrays()
    {
        return arrayNumber;

    }

    public boolean isEmpty()
    {
        if (this.size() == 0)   //if there are no elements, is empty
        {
            return true;
        }

        return false;
    }

    public E first()
    {

        if (this.isEmpty())
        {
            return null;
        }

        if (front == 8)
        {
            return top.readElement(7);
        }

        return top.readElement(front); //reads element labeled with front index in top array
    }

    public E last()
    {

        if (this.isEmpty())
        {
            return null;
        }

        return bottom.readElement(back - 1);    //returns element with back index in bottom node

    }

    public void enqueue(E e)
    {
        if (back == 8)
        {

            current.next();

            bottom.setNext(new SinglyLinkedList<E>(null)); //make a new node and set bottom to it

            bottom = bottom.next();

            if (traversed == 0)
            {
                traversed = 1;

                firstTraverse = bottom;
            }

            current.setNext(bottom);

            back = 0;   //reset back to 0 for index

            bottom.setElement(e, back);

            back++;

            size++;

            arrayNumber++;

            return;

        }

            bottom.setElement(e, back);

            back++;

            size++;


    }

    //pop and return the element at the front of the queue. return null if queue is empty
    public E dequeue()
    {

        E val;

        if (size == 0)
        {
            return null;
        }

        else
        {
            val = top.pushElement(front);

            front++;

            if (front == 8)
            {

                if (traversed == 1)
                {
                    top.setNext(firstTraverse);

                    traversed++;
                }

                front = 0;

                top = top.next();

                arrayNumber--;
            }

            size--;

            return val;
        }

    }

    private int arrayChecker(int counter)
    {

        if (current.next() != bottom)
        {
            current = current.next();

            return this.arrayChecker(counter + 1);

        }

        return counter;

    }

}