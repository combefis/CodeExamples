// DrinksVendingMachineTest.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

import junit.framework.TestCase;

/**
 * Test class for the GenericVendingMachine
 *
 * @author Sébastien Combéfis
 * @version January 3, 2009
 */
public class DrinksVendingMachineTest extends TestCase
{
	// Instance variables
	private GenericVendingMachine vdm;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		vdm = new DrinksVendingMachine();
	}

	public void testBuyProduct()
	{
		Product[] p = vdm.soldProducts();
		
		try
		{
			vdm.buyProduct (p[0]);
			fail ("buyProduct has not thrown NotEnoughMoneyException");
		}
		catch (MissingMoneyException exception)
		{
			// Test OK
		}
		catch (Exception exception)
		{
			fail ("Should not happen");
		}
		
		try
		{
			vdm.insertCoin (EuroCoin.TWOEUROS);
			Coin[] back = vdm.buyProduct (p[0]);
			
			BigDecimal sum = BigDecimal.ZERO;
			for (Coin c : back)
			{
				sum = sum.add (c.getValue());
			}
			sum = sum.add (p[0].getPrice());
			assertEquals (0, EuroCoin.TWOEUROS.getValue().compareTo (sum));
		}
		catch (Exception exception)
		{
			fail ("Should not append");
		}
		
		for (int i = 0; i < 9; i++)
		{
			vdm.insertCoin (EuroCoin.FIFTYCENTS);
			vdm.insertCoin (EuroCoin.TENCENTS);
			try
			{
				Coin[] back = vdm.buyProduct (p[0]);
				assertEquals (0, back.length);
			}
			catch (Exception exception)
			{
				fail ("Should not happen");
			}
		}
		
		try
		{
			vdm.insertCoin (EuroCoin.TWOEUROS);
			vdm.buyProduct (p[0]);
			fail ("buyProduct has not thrown ProductNotAvailableException");
		}
		catch (ProductNotAvailableException exception)
		{
			// Test OK
		}
		catch (Exception exception)
		{
			fail ("Should not happen");
		}
		
		try
		{
			vdm.insertCoin (EuroCoin.TWOEUROS);
			vdm.buyProduct (new Product ("Coca-Cola", new BigDecimal ("0.6")));
			fail ("buyProduct has not thrown ProductNotAvailableException");
		}
		catch (IllegalArgumentException exception)
		{
			// Test OK
		}
		catch (Exception exception)
		{
			fail ("Should not happen");
		}
		
		vdm.fill (p[1], 10);
		for (int i = 0; i < 10; i++)
		{
			vdm.insertCoin (EuroCoin.ONEEURO);
			try
			{
				Coin[] back = vdm.buyProduct (p[1]);
				assertTrue (back.length > 0);
			}
			catch (Exception exception)
			{
				fail ("Should not happen");
			}
		}
		
		try
		{
			vdm.insertCoin (EuroCoin.ONEEURO);
			vdm.buyProduct (p[1]);
			fail ("buyProduct has not thrown MissingMoneyException");
		}
		catch (NotEnoughMoneyException exception)
		{
			// Test OK
		}
		catch (Exception exception)
		{
			exception.printStackTrace ();
			fail ("Should not happen");
		}
	}

	public void testFill()
	{
		try
		{
			vdm.fill (new Product ("dummy product", new BigDecimal ("0.1")), 10);
			fail ("fill has not thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException exception)
		{
			// Test ok
		}
		
		Product[] p = vdm.soldProducts();
		int mid = p.length / 2;
		
		int q = vdm.getQuantity (p[mid]);
		vdm.fill (p[mid], 50);
		assertEquals (q + 50, vdm.getQuantity (p[mid]));
		
		q = vdm.getQuantity (p[0]);
		vdm.fill (p[0], 12);
		assertEquals (q + 12, vdm.getQuantity (p[0]));
		
		q = vdm.getQuantity (p[p.length - 1]);
		vdm.fill (p[p.length - 1], 1);
		assertEquals (q + 1, vdm.getQuantity (p[p.length - 1]));
		
		try
		{
			vdm.fill (new Product (p[0].getName(), p[0].getPrice()), 10);
			fail ("fill has not thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException exception)
		{
			// Test ok
		}
	}

	public void testFillCoin()
	{
		try
		{
			vdm.fillCoin (new Coin()
			{
				public BigDecimal getValue()
				{
					return BigDecimal.ONE;
				}
			}, 10);
			fail ("fillCoin has not thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException exception)
		{
			// Test ok
		}
		
		Coin[] c = vdm.acceptedCoins();
		int mid = c.length / 2;
		
		int q = vdm.getQuantity (c[mid]);
		vdm.fillCoin (c[mid], 50);
		assertEquals (q + 50, vdm.getQuantity (c[mid]));
		
		q = vdm.getQuantity (c[0]);
		vdm.fillCoin (c[0], 12);
		assertEquals (q + 12, vdm.getQuantity (c[0]));
		
		q = vdm.getQuantity (c[c.length - 1]);
		vdm.fillCoin (c[c.length - 1], 1);
		assertEquals (q + 1, vdm.getQuantity (c[c.length - 1]));
	}

	public void testGiveBackMoney()
	{
		Coin[] coins = vdm.giveBackMoney();
		assertEquals (0, coins.length);
		
		vdm.insertCoin (EuroCoin.TWOCENTS);
		coins = vdm.giveBackMoney();
		assertEquals (1, coins.length);
		assertEquals (EuroCoin.TWOCENTS, coins[0]);
		assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
		
		vdm.insertCoin (EuroCoin.FIFTYCENTS);
		vdm.insertCoin (EuroCoin.TWOEUROS);
		
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			coins = vdm.giveBackMoney();
			assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
			assertEquals (0, coins.length);
		}
		catch (Exception e)
		{
			// should not happed
			e.printStackTrace();
			fail ("Should not happen");
		}
		
		vdm.insertCoin (EuroCoin.TWOCENTS);
		vdm.insertCoin (EuroCoin.TWENTYCENTS);
		coins = vdm.giveBackMoney();
		assertEquals (2, coins.length);
		assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
		assertTrue (contains (coins, EuroCoin.TWOCENTS));
		assertTrue (contains (coins, EuroCoin.TWENTYCENTS));
		
		vdm.insertCoin (EuroCoin.ONEEURO);
		vdm.insertCoin (EuroCoin.TWOCENTS);
		vdm.insertCoin (EuroCoin.ONEEURO);
		vdm.insertCoin (EuroCoin.TWOCENTS);
		vdm.insertCoin (EuroCoin.TWOCENTS);
		coins = vdm.giveBackMoney();
		assertEquals (5, coins.length);
		assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
		assertEquals (2, count (coins, EuroCoin.ONEEURO));
		assertEquals (3, count (coins, EuroCoin.TWOCENTS));
	}
	
	private static boolean contains (Coin[] coins, EuroCoin coin)
	{
		for (Coin c : coins)
		{
			if (c.equals (coin))
			{
				return true;
			}
		}
		return false;
	}

	private static int count (Coin[] coins, EuroCoin coin)
	{
		int count = 0;
		for (Coin c : coins)
		{
			if (c.equals (coin))
			{
				count++;
			}
		}
		return count;
	}
	public void testInsertedMoney()
	{
		assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
		
		// Insert one euro
		vdm.insertCoin (EuroCoin.ONEEURO);
		assertEquals (BigDecimal.ONE, vdm.insertedMoney());
		
		// Insert fifty cents
		vdm.insertCoin (EuroCoin.FIFTYCENTS);
		assertEquals (new BigDecimal ("1.5"), vdm.insertedMoney());
		
		// Takes back the money
		vdm.giveBackMoney();
		assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
		
		// Insert one cent
		vdm.insertCoin (EuroCoin.ONECENT);
		assertEquals (new BigDecimal ("0.01"), vdm.insertedMoney());
		
		// Insert two euros
		vdm.insertCoin (EuroCoin.TWOEUROS);
		assertEquals (new BigDecimal ("2.01"), vdm.insertedMoney());
		
		// Buy a product
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			assertEquals (BigDecimal.ZERO, vdm.insertedMoney());
		}
		catch (Exception e)
		{
			// should not happed
			e.printStackTrace();
			fail ("Should not happen");
		}
	}

	public void testInsertCoin()
	{
		vdm.insertCoin (EuroCoin.FIFTYCENTS);
		assertEquals (new BigDecimal ("0.5"), vdm.insertedMoney());
		
		vdm.insertCoin (EuroCoin.TWOEUROS);
		assertEquals (new BigDecimal ("2.5"), vdm.insertedMoney());
		
		try
		{
			vdm.insertCoin (new Coin()
			{
				public BigDecimal getValue()
				{
					return BigDecimal.ONE;
				}
			});
			fail ("insertCoin has not thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException exception)
		{
			// Test OK
		}
		
		vdm.giveBackMoney();
		vdm.insertCoin (EuroCoin.TWOEUROS);
		assertEquals (new BigDecimal ("2"), vdm.insertedMoney());
		
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			vdm.insertCoin (EuroCoin.ONECENT);
			assertEquals (new BigDecimal ("0.01"), vdm.insertedMoney());
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
	}

	public void testGetQuantityProduct()
	{
		assertEquals (10, vdm.getQuantity (vdm.soldProducts()[0]));
		
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("Not enough money");
		}
		catch (MissingMoneyException exception)
		{
			// Test OK
			assertEquals (10, vdm.getQuantity (vdm.soldProducts()[0]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		try
		{
			for (int i = 9; i >= 0; i--)
			{
				vdm.insertCoin (EuroCoin.FIFTYCENTS);
				vdm.insertCoin (EuroCoin.TWENTYCENTS);
				vdm.buyProduct (vdm.soldProducts()[0]);
				assertEquals (i, vdm.getQuantity (vdm.soldProducts()[0]));
			}
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		vdm.insertCoin (EuroCoin.ONEEURO);
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("getQuantity has not thrown ProductNotAvailableException");
		}
		catch (ProductNotAvailableException e)
		{
			assertEquals (0, vdm.getQuantity (vdm.soldProducts()[0]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
	}

	public void testGetQuantityCoin()
	{
		assertEquals (10, vdm.getQuantity (vdm.acceptedCoins()[4]));
		
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("Not enough money");
		}
		catch (MissingMoneyException exception)
		{
			// Test OK
			assertEquals (10, vdm.getQuantity (vdm.acceptedCoins()[4]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		try
		{
			for (int i = 1; i <= 5; i++)
			{
				vdm.insertCoin (EuroCoin.ONEEURO);
				vdm.buyProduct (vdm.soldProducts()[0]);
				assertEquals (10 - i * 2, vdm.getQuantity (vdm.acceptedCoins()[4]));
			}
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		vdm.insertCoin (EuroCoin.ONEEURO);
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			assertEquals (0, vdm.getQuantity (vdm.acceptedCoins()[4]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
	}

	public void testRemainsProduct()
	{
		assertTrue (vdm.remains (vdm.soldProducts()[0]));
		
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("Not enough money");
		}
		catch (MissingMoneyException exception)
		{
			// Test OK
			assertTrue (vdm.remains (vdm.soldProducts()[0]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		try
		{
			for (int i = 9; i >= 0; i--)
			{
				assertTrue (vdm.remains (vdm.soldProducts()[0]));
				vdm.insertCoin (EuroCoin.FIFTYCENTS);
				vdm.insertCoin (EuroCoin.TWENTYCENTS);
				vdm.buyProduct (vdm.soldProducts()[0]);
			}
			assertFalse (vdm.remains (vdm.soldProducts()[0]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		vdm.insertCoin (EuroCoin.ONEEURO);
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("getQuantity has not thrown ProductNotAvailableException");
		}
		catch (ProductNotAvailableException e)
		{
			assertFalse (vdm.remains (vdm.soldProducts()[0]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
	}

	public void testRemainsCoin()
	{
		assertTrue (vdm.remains (vdm.acceptedCoins()[4]));
		
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("Not enough money");
		}
		catch (MissingMoneyException exception)
		{
			// Test OK
			assertTrue (vdm.remains (vdm.acceptedCoins()[4]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		try
		{
			for (int i = 1; i <= 5; i++)
			{
				assertTrue (vdm.remains (vdm.acceptedCoins()[4]));
				vdm.insertCoin (EuroCoin.ONEEURO);
				vdm.buyProduct (vdm.soldProducts()[0]);
			}
			assertFalse (vdm.remains (vdm.acceptedCoins()[4]));
		}
		catch (Exception exception)
		{
			// Should not happen
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		vdm.insertCoin (EuroCoin.ONEEURO);
		try
		{
			vdm.buyProduct (vdm.soldProducts()[0]);
			assertFalse (vdm.remains (vdm.acceptedCoins()[4]));
		}
		catch (Exception exception)
		{
			// Should not happen
			fail ("Should not happen");
		}
	}
	
	public void testSoldProducts()
	{
		Product[] p = vdm.soldProducts();
		for (int i = 0; i < p.length; i++)
		{
			p[i] = null;
		}
		
		Product[] p2 = vdm.soldProducts();
		try
		{
			vdm.insertCoin (EuroCoin.TWOEUROS);
			vdm.buyProduct (p2[0]);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		try
		{
			p2[0] = new Product ("Sprite", new BigDecimal ("2"));
			vdm.insertCoin (EuroCoin.TWOEUROS);
			vdm.buyProduct (p2[0]);
			fail ("Should not happen");
		}
		catch (IllegalArgumentException exception)
		{
			// Test OK
		}
		catch (ArrayStoreException exception)
		{
			// Test OK
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			fail ("Should not happen");
		}
	}
	
	public void testAcceptedCoins()
	{
		Coin[] c = vdm.acceptedCoins();
		for (int i = 0; i < c.length; i++)
		{
			c[i] = null;
		}
		
		Coin[] c2 = vdm.acceptedCoins();
		try
		{
			vdm.insertCoin (EuroCoin.TWOEUROS);
			vdm.buyProduct (vdm.soldProducts()[0]);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			fail ("Should not happen");
		}
		
		try
		{
			c2[0] = new Coin()
			{
				public BigDecimal getValue()
				{
					return BigDecimal.ZERO;
				}
			};
			vdm.insertCoin (c2[0]);
			vdm.buyProduct (vdm.soldProducts()[0]);
			fail ("Should not happen");
		}
		catch (IllegalArgumentException exception)
		{
			// Test OK
		}
		catch (ArrayStoreException exception)
		{
			// Test OK
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			fail ("Should not happen");
		}
	}
}