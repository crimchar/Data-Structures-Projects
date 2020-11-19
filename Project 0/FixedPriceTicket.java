
public abstract class FixedPriceTicket extends Ticket{

	public int price;
	
	public FixedPriceTicket()
	{
		super();
	}
	
	public int getSN()
	{
		return super.getSN();
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public String toString()
	{
		return "SN: " + super.getSN() + ", $" + price + "\n";
	}
	
}
