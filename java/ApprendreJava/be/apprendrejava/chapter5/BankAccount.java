// BankAccount.java

package be.apprendrejava.chapter5;

public class BankAccount
{
	private final String accountNumber;
	
	public BankAccount (String nb)
	{
		balance = 0;
		accountNumber = nb;
	}
	
	private int balance;
	
	public void withdraw (int amount)
	{
		balance -= amount;
	}
	
	public BankAccount (String nb, int amount)
	{
		balance = amount;
		accountNumber = nb;
	}
}