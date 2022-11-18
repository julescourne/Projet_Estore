package edu.polytech.estore.dao;

public interface ConversionDao {

	public Double getPriceInCurrency(Double priceInEuro, String Currency);
}
