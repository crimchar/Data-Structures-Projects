import java.security.SecureRandom;
import java.util.ArrayList;

public abstract class Ticket {

	private int sn; //private serial number variable
	
	private static ArrayList<Integer> serialNumbers = new ArrayList<Integer>(); //private array list of all serial numbers
	
	public Ticket() //will find unique serial number
	{
		SecureRandom rn = new SecureRandom();
		
		do
		{
		sn = rn.nextInt(10001);
		}
		while (serialNumbers.contains(sn)); //do while loop to find unique serial number
	
		serialNumbers.add(sn);
	}
	
	abstract public int getPrice(); //abstract method that will be used by child classes to define price of specific ticket types

	public int getSN() //will return serial number
	{	
		return sn;
	}
	
	abstract public String toString();

}
