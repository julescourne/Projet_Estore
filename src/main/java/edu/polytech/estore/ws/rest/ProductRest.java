package edu.polytech.estore.ws.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.polytech.estore.business.StoreBusinessLocal;
import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

@Stateless
@Path("v1")
public class ProductRest {

	@EJB
	private StoreBusinessLocal storebusinessLocal;

	/**
	 * Web service pour les services 1, 3, 4 et 5.
	 * 
	 * @param category String représentant la catégorie à laquelle doivent appartenir les produits.
	 * @param asc Boolean indiquant si le résultat est trié par ordre croissant de l'id.
	 * @param currency String représentant la devise dans laquelle le montant est indiqué.
	 * @return une liste de produit
	 */
	@GET
	@Path("/products")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Product> getProducts(@QueryParam("category") String category, @QueryParam("asc") Boolean asc,
			@QueryParam("currency") String currency) {

		if (category != null && asc != null && currency == null) {
			return storebusinessLocal.getSortedProductsOfCategory(category, asc);
		} else if (category == null && asc != null && currency == null) {
			return storebusinessLocal.getSortedProducts(asc);
		} else if (category != null && asc == null && currency == null) {
			return storebusinessLocal.getProductsOfCategory(category);
		} else if (category != null && asc != null && currency != null) {
			return storebusinessLocal
					.getProductInCurrency(storebusinessLocal.getSortedProductsOfCategory(category, asc), currency);
		} else if (category == null && asc != null && currency != null) {
			return storebusinessLocal.getProductInCurrency(storebusinessLocal.getSortedProducts(asc), currency);
		} else if (category != null && asc == null && currency != null) {
			return storebusinessLocal.getProductInCurrency(storebusinessLocal.getProductsOfCategory(category),
					currency);
		} else if (category == null && asc == null && currency != null) {
			return storebusinessLocal.getProductInCurrency(currency);
		}

		return storebusinessLocal.getProducts();

	}

	/**
	 * Web service pour les services 2 et 5.
	 * 
	 * @param id Long représentant l'id du produit.
	 * @param currency String représentant la devise dans laquelle le montant est indiqué.
	 * @return le produit ayant pour identifiant id.
	 */
	@GET
	@Path("/products/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Product getProduct(@PathParam("id") Long id, @QueryParam("currency") String currency) {

		if (currency != null) {
			return this.storebusinessLocal.getProductInCurrency(this.storebusinessLocal.getProduct(id), currency);
		}
		return this.storebusinessLocal.getProduct(id);
	}

	
	/**
	 * 
	 * @param authorization 
	 * @return le status de la requête/
	 */
	private Status checkAuthorization(String authorization) {
		if (null == authorization) {
			return Status.UNAUTHORIZED;
		}
		if (!"42".equals(authorization)) {
			return Status.FORBIDDEN;
		}
		return null;
	}

	/**
	 * Web service pour le service 6.
	 * 
	 * @param idProd Long représentant l'id du produit à supprimer.
	 * @param authorization
	 * @return 1 si la suppression est effectué, 0 sinon.
	 */
	@DELETE
	@Path("/products/{id}")
	public Response deleteLocation(@PathParam("id") Long idProd,
			@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {

		Status status = checkAuthorization(authorization);
		if (null != status) {
			return Response.status(status).build();
		}
		storebusinessLocal.deleteProduct(idProd);
		return Response.ok().build();
	}


	/**
	 * Web service pour le service 7.
	 * 
	 * @param authorization
	 * @param prod Object de type Produit représentant le produit à ajouter.
	 * @return 1 si l'ajout est effectué, 0 sinon.
	 */
	@POST
	@Path("/products")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createProduct(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, Product prod) {

		Status status = checkAuthorization(authorization);
		if (null != status) {
			return Response.status(status).build();
		}
		System.out.println(prod.getPriceInEuro() + prod.getLabel());
		storebusinessLocal.createProduct(prod);

		return Response.ok().build();
	}

	
	/**
	 * Web service pour le service 8.
	 * 
	 * @param authorization
	 * @param idProd Long représentant l'id du produit à modifier.
	 * @param prod Object de type Produit représentant le produit à modifier.
	 * @return 1 si la modification est effectué, 0 sinon.
	 */
	@PUT
	@Path("/products/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateProduct(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
			@PathParam("id") Long idProd, Product prod) {

		Status status = checkAuthorization(authorization);
		if (null != status) {
			return Response.status(status).build();
		}

		Product oldProd = storebusinessLocal.getProduct(idProd);
		if (null == oldProd) {
			return Response.status(Status.NOT_FOUND).build();
		}

		prod.setProductId(idProd);

		storebusinessLocal.updateProduct(prod);

		return Response.ok().build();
	}

	/**
	 * Web service pour le service 9.
	 * 
	 * @param authorization
	 * @param idProd Long représentant l'id du produit à modifier.
	 * @param prod Object de type Produit représentant le produit à modifier.
	 * @return 1 si la modification est effectué, 0 sinon.
	 */
	@PATCH
	@Path("/products/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response updateLocationPatch(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
			@PathParam("id") Long idProd, Product prod) {

		Status status = checkAuthorization(authorization);
		if (null != status) {
			return Response.status(status).build();
		}

		Product oldProd = storebusinessLocal.getProduct(idProd);
		if (null == oldProd) {
			return Response.status(Status.NOT_FOUND).build();
		}

		storebusinessLocal.patchProduct(idProd, prod);

		return Response.ok().build();
	}

	/**
	 * Web service pour le service 10.
	 * 
	 * @param idProd Long représentant l'id du produit dont on veut les commentaires.
	 * @return la liste des commentaires d'un produit donnée.
	 */
	@GET
	@Path("/comments/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Comment> getComments(@PathParam("id") Long idProd) {
		return storebusinessLocal.getProductComments(idProd);
	}
}
