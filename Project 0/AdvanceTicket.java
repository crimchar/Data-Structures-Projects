
public class AdvanceTicket extends Ticket{
	
	public int days;
	
	public AdvanceTicket (int days)
	{
		super();
		this.days = days;
	}
	
	public int getSN()
	{
		return super.getSN();
	}
	
	public int getPrice()
	{
		if (days >= 10)
		{
			return 30;
		}
		else if (days < 10)
		{
			return 40;
		}
		else
		{
			System.out.println("Not a valid Number");
		}
		
		return -1;
	}
	
	public String toString()
	{
		return "SN: " + super.getSN() + ", $" + this.getPrice() + "\n";
	}

}
