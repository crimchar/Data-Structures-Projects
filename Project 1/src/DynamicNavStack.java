import javax.swing.*;

public class DynamicNavStack<E> {
    private E[] navStack;
    private int undoI;
    private int redoI;
    private int initialCapacity;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 2;

    DynamicNavStack()
    {

        this(0);    //calls other constructor and sets default capacity
    }

    DynamicNavStack(int c)
    {
        if (c <= 0) //if input is 0 or less
        {
            initialCapacity = DEFAULT_CAPACITY;
        }
        else    //set initial capacity to input
        {
            initialCapacity = c;
        }

        navStack = (E[])new Object[initialCapacity]; //creates the array with initial capacity

        undoI = 0;

        redoI = initialCapacity - 1;

        capacity = initialCapacity;

    }

    public int size()
    {

        int size = 0;

        for (int i = 0; i < capacity; i++)  //runs through whole array
        {
            if (navStack[i] != null)    //increments size if index of array is not empty
            {
                size++;
            }
        }

        return size;

    }

    public boolean canUndo()
    {

        if (navStack[0] != null)    //checks to see there is at least one thing in Undo stack
        {
            return true;
        }

        return false;

    }

    public boolean canRedo()
    {

        if (navStack[capacity - 1] != null) //checks to see there is at least one thing in Redo stack
        {
            return true;
        }

        return false;

    }

    public E undoTop()
    {

        if (this.canUndo())
        {
            if (navStack[undoI - 1] != null)    //makes sure undo not empty
            {
                return navStack[undoI - 1]; //return top of undo
            }
        }

        return null;

    }

    public E redoTop()
    {

        if (this.canRedo())
        {
            if (navStack[redoI + 1] != null)  //makes sure redo not empty
            {
                return navStack[redoI + 1]; //return top of redo
            }
        }

        return null;

    }

    public boolean isEmpty()
    {

        if (navStack[0] == null && navStack[capacity - 1] == null)   //checks to see if stacks empty based on indexes
        {
            return true;
        }

        return false;

    }

    public int capacity()
    {

        return capacity;    //this is some complex code, idk what this means

    }

    public void push(E e)
    {

        this.emptyRedo();   //empties redo stack

        if (undoI + 1 == capacity)  //resize if too many elements
        {
            this.resize(capacity * 2);
        }

        navStack[undoI] = e;

        undoI++;    //move undoI accordingly

        if (this.size() <= this.capacity() / 4 && capacity / 2 >= initialCapacity)  //if amount of elements is now 1/4 if array size, shrink by 1/2 unless the new size would be lower than the initial capacity
        {
            this.resize(capacity /2);
        }

    }

    public E undo()
    {

        E action;

        if (this.canUndo())     //makes you you can undo, then undo
        {
            action = navStack[undoI - 1];

            navStack[undoI - 1] = null; //"deletes" the action that was in undo stack

            redoI--;    //makes room for new action that will be put in redo stack

            undoI--;    //moves undoI accordingly

            navStack[redoI + 1] = action;   //puts action onto redo stack

            return action;
        }

        return null;

    }

    public E redo()
    {

        E action;

        if (this.canRedo())     //if redo is possible, redo
        {
            action = navStack[redoI + 1];

            navStack[redoI + 1] = null;     //"deletes" action in redo stack

            redoI++;    //move redoI to accommodate for deleted action

            undoI++;    //make room for new action in undo stack

            navStack[undoI - 1] = action;   //put action back into undo stack

            return action;
        }

        return null;

    }

    private void emptyRedo()
    {

        for (int i = redoI; i < capacity; i++)  //makes everything un redo null and puts redoI back at the beginning
        {
            navStack[i] = null;
        }

        redoI = capacity - 1;

    }

    private void resize(int newSize)
    {

        E[] tempStack = (E[])new Object[newSize];

        for (int i = 0; i <= undoI; i++)        //copies over all elements in undo stack, no need for redo since pushes delete in redo anyways
        {
            tempStack[i] = navStack[i];
        }

        navStack = tempStack;

        capacity = newSize;

        redoI = capacity - 1;

    }

    public String toString() {
        String ret = "Array Looks Like this: [";
        for (int i=0; i<capacity; i++)
            if (navStack[i] != null)
                ret += navStack[i].toString() + " ";
            else
                ret += "null ";
        ret += "]\n";
        ret += "undo stack: [";
        for (int i=0; i<undoI; i++)
            ret += navStack[i].toString() + " ";
        ret += "]\n";
        ret += "redo stack: [";
        for (int i=capacity-1; i>redoI; i--)
            ret += navStack[i].toString() + " ";
        ret += "]";
        return ret;
    }
}
