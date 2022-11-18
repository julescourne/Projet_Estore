package edu.polytech.estore.business;

import java.util.List;

import javax.ejb.Local;

import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

@Local
public interface StoreBusinessLocal {

    /**
     * Pour le service n�1.
     */
    public List<Product> getProducts();

    /**
     * Pour le service n�2.
     * 
     * @param productId L'identifiant du produit.
     */
    public Product getProduct(Long productId);

    /**
     * Pour le service n�3.
     * 
     * @param category La cat�gorie sur laquelle filtrer.
     */
    public List<Product> getProductsOfCategory(String category);

    /**
     * Pour le service n�4.
     * 
     * @param asc Si <code>true</code>, le tri est ascendant sur le prix, si
     *            <code>false</code>, le tri est descendant.
     */
    public List<Product> getSortedProducts(Boolean asc);

    /**
     * Pour le service n�4.
     * 
     * @param category La cat�gorie sur laquelle filtrer.
     * @param asc      Si <code>true</code>, le tri est ascendant sur le prix, si
     *                 <code>false</code>, le tri est descendant.
     */
    public List<Product> getSortedProductsOfCategory(String category, Boolean asc);

    /**
     * Pour le service n�6.
     * 
     * @param productId L'identiant du produit � supprimer.
     */
    public void deleteProduct(Long productId);

    /**
     * Pour le serivce n�7.
     * 
     * @param product Le produit � cr�er.
     */
    public void createProduct(Product product);

    /**
     * Pour le serivce n�8.
     * 
     * @param product Le produit � modifier (modification totale).
     */
    public void updateProduct(Product product);

    /**
     * Pour le serivce n�9.
     * 
     * @param productId L'identifiant du produit � modifier (modification
     *                  partielle).
     * @param patch     Les modifications � apporter.
     */
    public void patchProduct(Long productId, Product patch);

    /**
     * Pour le serivce n�10.
     * 
     * @param productId L'identifiant du produit dont on souhaite r�cup�rer les
     *                  commentaires.
     */
    public List<Comment> getProductComments(Long productId);
    
    /**
     * Pour le serivce n�5
     * 
     * @param currency Nom de la devise choisi
     */
    public List<Product> getProductInCurrency(String currency);
    
    /**
     * Pour le serivce n�5
     * 
     * @param listProducts La liste des produits dont on veut le montant dans la devise choisi.
     * @param currency String repr�sentant la devise choisi.
     * @return La liste des produit avec le montant dans la devise choisi.
     */
    public List<Product> getProductInCurrency(List<Product> listProducts, String currency);
    
    /**
     * Pour le serivce n�5
     * 
     * @param product produit dont on veut le montant dans la devise choisi.
     * @param currency String repr�sentant la devise choisi.
     * @return Le produit avec le montant dans la devise choisi.
     */
    public Product getProductInCurrency(Product product, String currency);
}
