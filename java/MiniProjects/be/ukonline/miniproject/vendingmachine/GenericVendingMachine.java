// GenericVendingMachine.java

package be.ukonline.miniproject.vendingmachine;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A generic vending machine
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
public abstract class GenericVendingMachine implements VendingMachine
{
	// Instance variables
	private final Drink[] soldProducts;
	private final Coin[] acceptedCoins;
	
	private final int[] coinsQuantities;
	private BigDecimal totalMoney;
	private final List<Coin> insertedCoins;
	private BigDecimal insertedMoney;
	
	/**
	 * Constructor
	 * 
	 * @pre products != null
	 *      coins != null
	 * @post An instance of this is created
	 *       With ten times each product and
	 *       with ten times each coin
	 */
	protected GenericVendingMachine (Product[] products, Coin[] coins)
	{
		assert products != null && coins != null;
		
		soldProducts = new Drink[products.length];
		for (int i = 0; i < products.length; i++)
		{
			soldProducts[i] = new Drink (products[i], 10);
		}
		acceptedCoins = Arrays.copyOf (coins, coins.length);
		
		// Update quantities of coins
		coinsQuantities = new int[acceptedCoins.length];
		totalMoney = ZERO;
		for (int i = 0; i < acceptedCoins.length; i++)
		{
			coinsQuantities[i] = 10;
			totalMoney = totalMoney.add (BigDecimal.TEN.multiply (acceptedCoins[i].getValue()));
		}
		
		// Money counters
		insertedCoins = new LinkedList<Coin>();
		insertedMoney = ZERO;
	}
	
	/*
	 * @see VendingMachine#buyProduct(Product)
	 */
	public Coin[] buyProduct (Product p) throws NotEnoughMoneyException, ProductNotAvailableException, MissingMoneyException
	{
		assert p != null;
		
		Drink d = checkProduct (p);
		
		if (d.quantity == 0)
		{
			throw new ProductNotAvailableException();
		}
		
		BigDecimal diff = insertedMoney.subtract (p.getPrice());
		if (diff.compareTo (ZERO) < 0)
		{
			throw new MissingMoneyException (diff.abs());
		}
		
		if (diff.compareTo (totalMoney) > 0)
		{
			throw new NotEnoughMoneyException();
		}
		
		Coin[] change = computeChange (diff);
		
		d.quantity--;
		totalMoney = totalMoney.add (p.getPrice());
		insertedCoins.clear();
		insertedMoney = ZERO;
		
		return change;
	}
	
	/**
	 * Compute the coins to give back
	 * 
	 * @pre back > 0
	 * @post The returned value contains the list of coins to give back
	 *       The sum of values of coins in the returned value is equal to back
	 * @throws NotEnoughMoneyException if there is not enough money to give back the exact change
	 */
	private Coin[] computeChange (BigDecimal back) throws NotEnoughMoneyException
	{
		assert back.compareTo (ZERO) > 0;
		
		List<Coin> list = new ArrayList<Coin>();
		
		for (int i = acceptedCoins.length - 1; i >= 0; i--)
		{
			boolean hasMore = true;
			while (back.compareTo (acceptedCoins[i].getValue()) >= 0 && hasMore)
			{
				if (coinsQuantities[i] > 0)
				{
					coinsQuantities[i]--;
					list.add (acceptedCoins[i]);
					back = back.subtract (acceptedCoins[i].getValue());
				}
				else
				{
					hasMore = false;
				}
			}
		}
		
		if (back.compareTo (ZERO) > 0)
		{
			for (Coin c : list)
			{
				coinsQuantities[checkCoin (c)]++;
			}
			throw new NotEnoughMoneyException();
		}
		
		Coin[] coins = new Coin[list.size()];
		list.toArray (coins);
		return coins;
	}

	/*
	 * @see VendingMachine#fill(Product, int)
	 */
	public void fill (Product p, int q)
	{
		if (q <= 0)
		{
			throw new IllegalArgumentException ("quantity must be > 0");
		}
		
		Drink d = checkProduct (p);
		d.quantity += q;
	}

	/*
	 * @see VendingMachine#fillCoin(Coin, int)
	 */
	public void fillCoin (Coin c, int q)
	{
		if (q <= 0)
		{
			throw new IllegalArgumentException ("quantity must be > 0");
		}
		
		int i = checkCoin (c);
		coinsQuantities[i] += q;
		totalMoney = totalMoney.add (new BigDecimal (q).multiply (c.getValue()));
	}

	/*
	 * @see VendingMachine#giveBackMoney()
	 */
	public Coin[] giveBackMoney()
	{
		for (Coin c : insertedCoins)
		{
			coinsQuantities[checkCoin (c)]--;
		}
		
		Coin[] coins = new Coin[insertedCoins.size()];
		insertedCoins.toArray (coins);
		insertedCoins.clear();
		insertedMoney = ZERO;
		return coins;
	}
	
	/*
	 * @see VendingMachine#insertedMoney()
	 */
	public BigDecimal insertedMoney()
	{
		return insertedMoney;
	}

	/*
	 * @see VendingMachine#insertCoin(Coin)
	 */
	public void insertCoin (Coin c)
	{
		assert c != null;
		
		int i = checkCoin (c);
		
		insertedCoins.add (c);
		insertedMoney = insertedMoney.add (c.getValue());
		coinsQuantities[i]++;
	}
	
	/*
	 * @see VendingMachine#getQuantity(Product)
	 */
	public int getQuantity (Product p)
	{
		assert p != null;
		
		Drink d = checkProduct (p);
		return d.quantity;
	}
	
	/*
	 * @see VendingMachine#getQuantity(Coin)
	 */
	public int getQuantity (Coin c)
	{
		assert c != null;
		
		int i = checkCoin (c);
		return coinsQuantities[i];
	}

	/*
	 * @see VendingMachine#remains(Product)
	 */
	public boolean remains (Product p)
	{
		assert p != null;
		
		return getQuantity (p) > 0;
	}
	
	/*
	 * @see VendingMachine#remains(Coin)
	 */
	public boolean remains (Coin c)
	{
		assert c != null;
		
		return getQuantity (c) > 0;
	}

	/*
	 * @see VendingMachine#soldProducts()
	 */
	public Product[] soldProducts()
	{
		return Arrays.copyOf (soldProducts, soldProducts.length);
	}
	
	/*
	 * @see VendingMachine#acceptedCoins()
	 */
	public Coin[] acceptedCoins()
	{
		return Arrays.copyOf (acceptedCoins, acceptedCoins.length);
	}
	
	/**
	 * Check if a product is sold by this machine
	 * 
	 * @pre -
	 * @post The returned value contains the product p cast to Drinks
	 * @throws IllegalArgumentException if p = null or is not sold by the machine
	 */
	private Drink checkProduct (Product p) throws IllegalArgumentException
	{
		if (! (p instanceof Drink))
		{
			throw new IllegalArgumentException ("Product not sold by the machine");
		}
		
		return (Drink) p;
	}
	
	/**
	 * Check if a coin is accepted by this machine
	 * 
	 * @pre -
	 * @post The returned value contains the index of the coin c
	 * @throws IllegalArgumentException if c = null or is not accepted by the machine
	 */
	private int checkCoin (Coin c) throws IllegalArgumentException
	{
		for (int i = 0; i < acceptedCoins.length; i++)
		{
			if (acceptedCoins[i] == c)
			{
				return i;
			}
		}
		throw new IllegalArgumentException ("Coin not accepted by the machine");
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder ("* * * * *\n");
		builder.append ("Products :\n");
		for (Drink p : soldProducts)
		{
			builder.append ("\t").append (p).append (" : ").append (p.quantity).append ("\n");
		}
		builder.append ("Coins :\n");
		for (int i = 0; i < acceptedCoins.length; i++)
		{
			builder.append ("\t").append (acceptedCoins[i]).append (" : ").append (coinsQuantities[i]).append ("\n");
		}
		builder.append ("Total Money : �").append (totalMoney);
		builder.append ("\nInserted coins : ").append (insertedCoins).append (" (total : �").append (insertedMoney).append (")");
		return builder.append ("\n* * * * *").toString();
	}
	
	/**
	 * Class representing a drink
	 */
	private static class Drink extends Product
	{
		// Instance variables
		public int quantity;
		
		/**
		 * Constructor
		 * 
		 * @pre name != null, name != ""
		 *      price != null, price > 0
		 *      quantity >= 0
		 * @post An instance of this is created
		 */
		public Drink (String name, BigDecimal price, int quantity)
		{
			super (name, price);
			this.quantity = quantity;
		}
		
		/**
		 * Constructor
		 * 
		 * @pre p != null
		 *      quantity >= 0
		 * @post An instance of this is created
		 */
		public Drink (Product p, int quantity)
		{
			this (p.getName(), p.getPrice(), quantity);
		}
	}
}