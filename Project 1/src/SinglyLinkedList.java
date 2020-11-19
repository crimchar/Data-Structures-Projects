public class SinglyLinkedList<E> {

    private E[] e;  //the generic array

    private SinglyLinkedList<E> n;  //the next node

    private arrayNode<E> an = new arrayNode<>(); //object for node

    SinglyLinkedList(SinglyLinkedList<E> inn) {
        e = (E[]) an.a();    //set generic array of size 8 to generic array e

        n = inn;    //set next node to inn
    }

    void setElement(E it, int i) {
        an.set(it, i); //sets element inside array node
    }

    E readElement(int i) {
        return an.read(i);
    }

    E pushElement(int i) {
        return an.push(i);
    }

    SinglyLinkedList next() {
        return n;
    }

    SinglyLinkedList setNext(SinglyLinkedList inn) {
        return n = inn;
    }

}

class arrayNode<E>  //class for node that has a generic array of 8
{
    private E[] array_8;

    arrayNode()
    {
        array_8 = (E[])new Object[8];   //constructor that created generic array of 8

    }

    public E[] a()  //gives you the array
    {
        return array_8;
    }

    public void set (E it, int i)   //sets element in array to given value
    {
        array_8[i] = it;
    }

    public E read (int i)
    {
        return array_8[i];
    }

    public E push (int i)
    {
        E val = array_8[i];

        array_8[i] = null;

        return val;
    }

}
