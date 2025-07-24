package account;

import commande.Panier;
import produit.Category;
import produit.Produit;

import java.util.List;

/**
 * Represents an administrator account with elevated privileges in the e-shop system.
 * Administrators can manage users, products, categories, orders, and view system statistics.
 * Extends the base Account class with administrative functionality.
 *
 * @author E-Shop Development Team
 * @version 1.0
 * @since 1.0
 * @see Account
 */
public class Admin extends Account{

    /**
     * Creates an administrator account with the specified credentials.
     *
     * @param nom Administrator's last name
     * @param prenom Administrator's first name
     * @param email Administrator's email address (must be unique)
     * @param password Administrator's password
     */
    public Admin(String nom, String prenom, String email, String password){
        super(nom, prenom, email, password);
    }

    /**
     * Displays the administrator's password to the console.
     * <p><strong>Security Warning:</strong> This method should be removed in production environments
     * as it exposes sensitive information.</p>
     *
     * @deprecated This method poses a security risk and should not be used in production
     */
    @Deprecated
    public void afficherMotDePasse(){
        System.out.println("Mon mot de passe est : " + this.password);
    }

    /**
     * Displays all registered users in the system.
     * Shows user email, name, and whether they have an address configured.
     */
    public void displayAllUsers(){
        System.out.println("\n=== TOUS LES UTILISATEURS ===");
        List<Account> accounts = Account.getAllAccounts();

        if(accounts.isEmpty()){
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }

        for(Account account : accounts){
            if(account instanceof User){
                User user = (User) account;
                System.out.printf("Email: %s | Nom: %s %s | Adresse: %s%n",
                        user.getEmail(),
                        user.getPrenom(),
                        user.getNom(),
                        user.hasAddress() ? "Oui" : "Non");
            }
        }
        System.out.println("Total utilisateurs: " + accounts.size());
    }

    /**
     * Displays all orders in the system with their current status.
     * Shows order ID, user email, item count, total value, and delivery status.
     */
    public void displayAllOrders(){
        System.out.println("\n=== TOUTES LES COMMANDES ===");
        List<Panier> orders = Panier.getAllOrders();

        if(orders.isEmpty()){
            System.out.println("Aucune commande trouvée.");
            return;
        }

        for(Panier order : orders){
            System.out.printf("ID: %d | User: %s | Items: %d | Total: %.2f€ | Status: %s%n",
                    order.getId(),
                    order.getUser().getEmail(),
                    order.getItemCount(),
                    order.getTotalPrice(),
                    order.getStatusString());
        }
        System.out.println("Total commandes: " + orders.size());
    }

    /**
     * Displays comprehensive system statistics including user, product, and order metrics.
     * Provides insights into system usage, revenue, and business performance.
     */
    public void displayStatistics(){
        System.out.println("\n=== STATISTIQUES SYSTÈME ===");

        // User statistics
        List<Account> accounts = Account.getAllAccounts();
        int userCount = accounts.size();
        int usersWithAddress = 0;
        for(Account account : accounts){
            if(account instanceof User && ((User)account).hasAddress()){
                usersWithAddress++;
            }
        }

        // Product statistics
        List<Category> categories = Category.findTouteCategory();
        List<Produit> products = Produit.getAllProducts();

        // Order statistics
        List<Panier> orders = Panier.getAllOrders();
        int validatedOrders = 0;
        int deliveredOrders = 0;
        float totalRevenue = 0;

        for(Panier order : orders){
            if(order.getStatus() == 1) validatedOrders++;
            if(order.getStatus() == 2) deliveredOrders++;
            if(order.getStatus() >= 1) totalRevenue += order.getTotalPrice();
        }

        System.out.println("UTILISATEURS:");
        System.out.println("  Total: " + userCount);
        System.out.println("  Avec adresse: " + usersWithAddress);

        System.out.println("\nPRODUITS:");
        System.out.println("  Catégories: " + categories.size());
        System.out.println("  Produits: " + products.size());

        System.out.println("\nCOMMANDES:");
        System.out.println("  Total: " + orders.size());
        System.out.println("  Validées: " + validatedOrders);
        System.out.println("  Livrées: " + deliveredOrders);
        System.out.printf("  Chiffre d'affaires: %.2f€%n", totalRevenue);
    }

    /**
     * Marks an order as delivered by its ID.
     *
     * @param orderId The unique identifier of the order to deliver
     */
    public void deliverOrder(int orderId){
        Panier order = Panier.findOrderById(orderId);
        if(order != null){
            order.livrerPanier();
        } else {
            System.out.println("Commande non trouvée avec l'ID: " + orderId);
        }
    }

    /**
     * Finds and displays all orders for a specific user.
     *
     * @param email The email address of the user whose orders to find
     */
    public void findOrdersByUser(String email){
        System.out.println("\n=== COMMANDES POUR: " + email + " ===");
        List<Panier> orders = Panier.getAllOrders();
        boolean found = false;

        for(Panier order : orders){
            if(order.getUser().getEmail().equals(email)){
                System.out.printf("ID: %d | Items: %d | Total: %.2f€ | Status: %s%n",
                        order.getId(),
                        order.getItemCount(),
                        order.getTotalPrice(),
                        order.getStatusString());
                found = true;
            }
        }

        if(!found){
            System.out.println("Aucune commande trouvée pour cet utilisateur.");
        }
    }

    /**
     * Updates the price of a product.
     *
     * @param productId The unique identifier of the product
     * @param newPrice The new price to set for the product
     */
    public void updateProductPrice(int productId, float newPrice){
        Produit product = Produit.findProduitByIdFast(productId);
        if(product != null){
            float oldPrice = product.getPrix();
            product.setPrix(newPrice);
            System.out.printf("Prix du produit '%s' modifié: %.2f€ → %.2f€%n",
                    product.getNom(), oldPrice, newPrice);
        } else {
            System.out.println("Produit non trouvé avec l'ID: " + productId);
        }
    }

    /**
     * Removes a product from the entire system.
     * The product will be removed from all categories.
     *
     * @param productId The unique identifier of the product to remove
     */
    public void removeProduct(int productId){
        Produit product = Produit.findProduitByIdFast(productId);
        if(product != null){
            product.removeFromAllCategories();
            System.out.println("Produit '" + product.getNom() + "' supprimé du système.");
        } else {
            System.out.println("Produit non trouvé avec l'ID: " + productId);
        }
    }

    /**
     * Identifies and displays the category with the most products.
     * Useful for understanding which product categories are most popular.
     */
    public void getMostPopularCategory(){
        List<Category> categories = Category.findTouteCategory();
        if(categories.isEmpty()){
            System.out.println("Aucune catégorie trouvée.");
            return;
        }

        Category mostPopular = categories.get(0);
        for(Category category : categories){
            if(category.getProductCount() > mostPopular.getProductCount()){
                mostPopular = category;
            }
        }

        System.out.printf("Catégorie la plus populaire: %s (%d produits)%n",
                mostPopular.getNom(), mostPopular.getProductCount());
    }

    /**
     * Returns a string representation of the admin object.
     *
     * @return String containing admin's basic information
     */
    @Override
    public String toString(){
        return String.format("Admin{nom='%s', prenom='%s', email='%s'}",
                getNom(), getPrenom(), getEmail());
    }
}