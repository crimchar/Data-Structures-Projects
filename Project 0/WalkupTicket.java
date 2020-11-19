
public class WalkupTicket extends FixedPriceTicket{
	
	public WalkupTicket()
	{
		super();
	}

	public int getSN()
	{
		return super.getSN();
	}
	
	public int getPrice()
	{
		
		return 50;
	}
	
	public String toString()
	{
		return "SN: " + super.getSN() + ", $" + this.getPrice() + "\n";
	}
}
