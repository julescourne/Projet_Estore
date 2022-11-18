package edu.polytech.estore.dao;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import edu.polytech.estore.model.Converter;

@Stateless
public class ConversionDaoImpl implements ConversionDao {

	private static String URI = "https://api.apilayer.com/exchangerates_data";

	@Override
	public Double getPriceInCurrency(Double priceInEuro, String Currency) {

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(URI);


		target = target.path("convert");
		target = target.queryParam("from", "EUR");
		target = target.queryParam("to", Currency);
		target = target.queryParam("amount", priceInEuro);

		Converter convert = target.request(MediaType.APPLICATION_JSON)
				.header("apikey", "9ddH6YoK1su30UlttXuIiuyohEHjscv0").get(Converter.class);

		return convert.getResult();
	}
}
