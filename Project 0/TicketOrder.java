import java.util.ArrayList;


public class TicketOrder {
	
	public static ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
	
	public boolean add(Ticket ticket)
	{
		if (ticket.getPrice() < 0)
		{
			return false;
		}
		else
		{
			ticketList.add(ticket);
			return true;
		}
	}
	
	public String toString()
	{
		
		String tickets = "";
		for (Ticket ticket : ticketList)
		{
			tickets += ticket.toString();
		}
		
		return tickets;
	}
	
	public int totalPrice()
	{
		int sum = 0;
		
		for (int i = 0; i < ticketList.size(); i ++)
		{
			sum += ticketList.get(i).getPrice();
		}
		
		return sum;
	}

}
