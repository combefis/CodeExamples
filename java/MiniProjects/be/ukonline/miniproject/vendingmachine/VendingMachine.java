// VendingMachine.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

/**
 * Interface representing a vending machine
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
public interface VendingMachine
{
	/**
	 * Buy a product from the machine
	 * 
	 * @pre p != null, p a product sold by the machine
	 * @post The product p is buyed from the machine
	 *       The returned value contains the coins sent back to the user
	 * @throws NotEnoughMoneyException if there is not enough money in the machine to give back change
	 * @throws ProductNotAvailableException if the required product is not available on the machine
	 * @throws MissingMoneyException if not enough money was inserted into the machine by the user
	 */
	public Coin[] buyProduct (Product p) throws NotEnoughMoneyException, ProductNotAvailableException, MissingMoneyException;
	
	/**
	 * Test whether it remains some product in the machine
	 * 
	 * @pre p != null, p a product sold by the machine
	 * @post The returned value contains true if it remains some product p
	 *       in the machine and false otherwise
	 */
	public boolean remains (Product p);
	
	/**
	 * Test whether it remains some coins in the machine
	 * 
	 * @pre c != null, c a coin accepted by the machine
	 * @post The returned value contains true if it remains some coin c
	 *       in the machine and false otherwise
	 */
	public boolean remains (Coin c);
	
	/**
	 * Get the quantity of a product in the machine
	 * 
	 * @pre p != null, p a product sold by the machine
	 * @post The returned value contains the number of the specified
	 *       product available in the machine
	 */
	public int getQuantity (Product p);
	
	/**
	 * Get the quantity of a coin in the machine
	 * 
	 * @pre c != null, c a coin accepted by the machine
	 * @post The returned value contains the number of the specified
	 *       coin available in the machine
	 */
	public int getQuantity (Coin c);
	
	/**
	 * Get the list of sold products
	 * 
	 * @pre -
	 * @post The returned value contains the list of products sold by the machine
	 */
	public Product[] soldProducts();
	
	/**
	 * Get the list of accepted coins
	 * 
	 * @pre -
	 * @post The returned value contains the list of coins accepted by the machine
	 */
	public Coin[] acceptedCoins();
	
	
	/**
	 * Insert a coin in the machine
	 * 
	 * @pre c != null, c a coin accepted by the machine
	 * @post The specified coin is inserted in the machine
	 */
	public void insertCoin (Coin c);
	
	/**
	 * Give back the money
	 * 
	 * @pre -
	 * @post The returned value contains the money inserted into the machine
	 */
	public Coin[] giveBackMoney();
	
	/**
	 * Get the value of all inserted money
	 * 
	 * @pre -
	 * @post The returned value contains the value of the money inserted into the machine
	 */
	public BigDecimal insertedMoney();
	
	/**
	 * Fill the machine with a product
	 * 
	 * @pre p != null, p a product sold by the machine
	 *      q > 0
	 * @post The machine is filled with q times the specified product
	 */
	public void fill (Product p, int q);
	
	/**
	 * Fill the machine with some coins
	 * 
	 * @pre c != null
	 *      q > 0
	 * @post The machine is filled with q times the specified coin
	 */
	public void fillCoin (Coin c, int q);
}