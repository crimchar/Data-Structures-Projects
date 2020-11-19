
public class StudentAdvanceTicket extends AdvanceTicket{

	
	public StudentAdvanceTicket(int days)
	{
		super(days);
	}

	public int getSN()
	{
		return super.getSN();
	}
	
	public int getPrice()
	{
		int price = super.getPrice() / 2;
		
		return price;
	}
	
	public String toString()
	{
		return "SN: " + super.getSN() + ", $" + this.getPrice() +"(student)" + "\n";
	}
}
