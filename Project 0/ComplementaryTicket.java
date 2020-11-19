
public class ComplementaryTicket extends FixedPriceTicket{

	public ComplementaryTicket()
	{
		super();
	}
	
	public int getPrice()
	{
		
		return 0;
	}
	
	public String toString()
	{
		return "SN: " + super.getSN() + ", $" + this.getPrice() + "\n";
	}
		
}
