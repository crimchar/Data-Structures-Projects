
class Main {
    public static void main(String[] args) {
        QHT<Integer, Integer> qht1 = new QHT<Integer, Integer>(3);
        qht1.insert(0,55);
        qht1.insert(8,13);
        qht1.insert(16,19);
        qht1.insert(24,14);
        System.out.println(qht1.size()); //should be 3
        qht1.remove(0); //should be 55
        System.out.println(qht1.size()); //should be 3
        qht1.insert(8,64); //shouldn't be inserted
        System.out.println(qht1.size()); //should be 3
        System.out.println(qht1.capacity()); //should be 8
        System.out.println(qht1.loadFactor()); // should be 0.375

    }
}