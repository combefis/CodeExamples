// VendingMachineSimulator.java

package be.ukonline.miniproject.vendingmachine;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A class representing a GUI for simulating a vending machine
 *
 * @author Sébastien Combéfis
 * @version January 3, 2009
 */
public class VendingMachineSimulator
{
	// Instance variables
	private final VendingMachine vdm;
	private final Coin[] coins;
	private final JLabel inserted;
	private final Product[] products;
	private final JLabel info;
	private final JLabel[] coinsqt;
	private final JLabel[] productsqt;
	
	/**
	 * Constructor
	 * 
	 * @pre vdm != null
	 * @post An instance of this is created
	 *       A frame is opened representing a simulator
	 *       for the specified VendingMachine 
	 */
	public VendingMachineSimulator (VendingMachine vdm)
	{
		assert vdm != null;
		
		this.vdm = vdm;
		
		JFrame frame = new JFrame ("Drinks Vending Machine Simulator 0.1");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		// Coins
		coins = vdm.acceptedCoins();
		Box coinsbox = Box.createVerticalBox();
		for (int i = 0; i < coins.length; i++)
		{
			JButton b = new JButton (coins[i].toString());
			b.addActionListener (new InsertCoinListener (i));
			coinsbox.add (b);
		}
		coinsbox.add (Box.createVerticalStrut (20));
		coinsbox.add (inserted = new JLabel ("�0"));
		coinsbox.setBorder (BorderFactory.createTitledBorder ("Coins"));
		
		// Products
		products = vdm.soldProducts();
		Box productsbox = Box.createVerticalBox();
		for (int i = 0; i < products.length; i++)
		{
			JButton b = new JButton (products[i].toString());
			b.addActionListener (new OrderProductListener (i));
			productsbox.add (b);
		}
		productsbox.add (Box.createVerticalStrut (40));
		JButton back = new JButton ("Give Back Money");
		back.addActionListener (new BackMoneyListener());
		productsbox.add (back);
		productsbox.add (Box.createVerticalStrut (20));
		productsbox.add (info = new JLabel ("Ins�rer l'argent"));
		productsbox.setBorder (BorderFactory.createTitledBorder ("Products"));
		
		// Admin
		Box admincoins = Box.createVerticalBox();
		coinsqt = new JLabel[coins.length];
		for (int i = 0; i < coins.length; i++)
		{
			admincoins.add (coinsqt[i] = new JLabel());
		}
		Box adminproducts = Box.createVerticalBox();
		productsqt = new JLabel[products.length];
		for (int i = 0; i < products.length; i++)
		{
			adminproducts.add (productsqt[i] = new JLabel());
		}
		Box hbox = Box.createHorizontalBox();
		hbox.add (adminproducts);
		hbox.add (Box.createHorizontalStrut (20));
		hbox.add (admincoins);
		hbox.setBorder (BorderFactory.createTitledBorder ("Administration"));
		updateAdmin();
		
		// Frame
		Container cp = frame.getContentPane();
		cp.add (productsbox, BorderLayout.CENTER);
		cp.add (coinsbox, BorderLayout.EAST);
		cp.add (hbox, BorderLayout.SOUTH);
		
		// Shows frame
		frame.setSize (new Dimension (600, 600));
		frame.setVisible (true);
	}
	
	private void updateAdmin()
	{
		for (int i = 0; i < coins.length; i++)
		{
			coinsqt[i].setText (coins[i] + "(" + vdm.getQuantity (coins[i]) + ")");
		}
		for (int i = 0; i < products.length; i++)
		{
			productsqt[i].setText (products[i].getName() + "(" + vdm.getQuantity (products[i]) + ")");
		}
	}
	
	/**
	 * Listener for the give back money button
	 */
	private class BackMoneyListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			Coin[] coins = vdm.giveBackMoney();
			info.setText ("Prenez votre monnaie : " + Arrays.toString (coins));
			inserted.setText ("�" + vdm.insertedMoney());
			
			updateAdmin();
		}
	}
	
	/**
	 * Listener for the insertion of a coin
	 */
	private class InsertCoinListener implements ActionListener
	{
		// Instance variables
		private final int coinid;
		
		/**
		 * Constructor
		 * 
		 * @pre 0 <= cid < coins.length
		 * @post An instance of this is created
		 *       Representing a listener for the insertion of the ith coin
		 */
		public InsertCoinListener (int cid)
		{
			coinid = cid;
		}
		
		/**
		 * Inserting the ith coin in the machine
		 */
		public void actionPerformed (ActionEvent event)
		{
			vdm.insertCoin (coins[coinid]);
			inserted.setText ("�" + vdm.insertedMoney());
			
			updateAdmin();
		}
	}
	
	/**
	 * Listener for the ordering of a product
	 */
	private class OrderProductListener implements ActionListener
	{
		// Instance variables
		private final int productid;
		
		/**
		 * Constructor
		 * 
		 * @pre 0 <= pid < products.length
		 * @post An instance of this is created
		 *       Representing a listener for the ordered of the ith product
		 */
		public OrderProductListener (int pid)
		{
			productid = pid;
		}
		
		/**
		 * Trying to buy the ith product on the machine
		 */
		public void actionPerformed (ActionEvent event)
		{
			try
			{
				Coin[] change = vdm.buyProduct (products[productid]);
				info.setText ("Prenez votre " + products[productid].getName() + " et votre monnaie : " + Arrays.toString (change));
				inserted.setText ("�" + vdm.insertedMoney());
				
				updateAdmin();
			}
			catch (ProductNotAvailableException e)
			{
				info.setText ("Le produit demand� n'est plus disponible");
			}
			catch (MissingMoneyException e)
			{
				info.setText ("Ins�rez encore �" + e.getMissingMoney());
			}
			catch (NotEnoughMoneyException e)
			{
				info.setText ("Pas assez d'argent pour rendre la monnaie, choisissez un autre produit");
			}
		}
	}
	
	/**
	 * Main method
	 */
	public static void main (String[] args)
	{
		new VendingMachineSimulator (new DrinksVendingMachine());
	}
}