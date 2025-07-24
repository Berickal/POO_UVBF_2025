package account;

import commande.Panier;
import produit.Category;
import produit.Produit;

import java.util.List;

/**
 * Classe représentant un administrateur du système e-commerce
 * Hérite de Account et ajoute des fonctionnalités de gestion et d'administration
 */
public class Admin extends Account{

    /**
     * Affiche le mot de passe en clair
     */
    public void afficherMotDePasse(){
        System.out.println("Mon mot de passe est : " + this.password);
    }

    /**
     * Constructeur pour créer un compte administrateur
     * @param nom Nom de famille de l'administrateur
     * @param prenom Prénom de l'administrateur
     * @param email Email de connexion (doit être unique)
     * @param password Mot de passe sécurisé
     */
    public Admin(String nom, String prenom, String email, String password){
        super(nom, prenom, email, password);  // Appel du constructeur parent
    }

    /**
     * Constructeur par défaut pour créer un compte administrateur.
     * Appelle le constructeur de la class Account qui demandera la saisie des informations necessaire.
     */
    public Admin(){
        super();
    }

    /**
     * Afficher tous les utilisateurs du système
     * Montre les informations de base et le statut des adresses
     */
    public void displayAllUsers(){
        System.out.println("\n=== TOUS LES UTILISATEURS ===");
        List<Account> accounts = Account.getAllAccounts();

        if(accounts.isEmpty()){
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }

        // Parcours et affichage de tous les comptes utilisateurs
        for(Account account : accounts){
            if(account instanceof User){  // Filtrer les utilisateurs (exclure les admins)
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
     * Afficher toutes les commandes du système
     * Vue d'ensemble de toutes les commandes avec leurs statuts
     */
    public void displayAllOrders(){
        System.out.println("\n=== TOUTES LES COMMANDES ===");
        List<Panier> orders = Panier.getAllOrders();

        if(orders.isEmpty()){
            System.out.println("Aucune commande trouvée.");
            return;
        }

        // Affichage détaillé de chaque commande
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
     * Afficher les statistiques complètes du système
     * Tableau de bord avec métriques importantes pour la gestion
     */
    public void displayStatistics(){
        System.out.println("\n=== STATISTIQUES SYSTÈME ===");

        // Statistiques des utilisateurs
        List<Account> accounts = Account.getAllAccounts();
        int userCount = accounts.size();
        int usersWithAddress = 0;

        // Comptage des utilisateurs avec adresse
        for(Account account : accounts){
            if(account instanceof User && ((User)account).hasAddress()){
                usersWithAddress++;
            }
        }

        // Statistiques des produits et catégories
        List<Category> categories = Category.findTouteCategory();
        List<Produit> products = Produit.getAllProducts();

        // Statistiques des commandes et chiffre d'affaires
        List<Panier> orders = Panier.getAllOrders();
        int validatedOrders = 0;   // Commandes confirmées
        int deliveredOrders = 0;   // Commandes livrées
        float totalRevenue = 0;    // Chiffre d'affaires total

        // Calcul des métriques de commandes
        for(Panier order : orders){
            if(order.getStatus() == 1) validatedOrders++;    // Statut validé
            if(order.getStatus() == 2) deliveredOrders++;    // Statut livré
            if(order.getStatus() >= 1) totalRevenue += order.getTotalPrice();  // Revenus des commandes payées
        }

        // Affichage des statistiques organisées par sections
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
     * Livrer une commande spécifique
     * Change le statut d'une commande validée vers "livrée"
     * @param orderId Identifiant de la commande à livrer
     */
    public void deliverOrder(int orderId){
        Panier order = Panier.findOrderById(orderId);
        if(order != null){
            order.livrerPanier();  // Appel de la méthode de livraison
        } else {
            System.out.println("Commande non trouvée avec l'ID: " + orderId);
        }
    }

    /**
     * Modifier le prix d'un produit
     * Permet à l'administrateur d'ajuster les prix
     * @param productId ID du produit à modifier
     * @param newPrice Nouveau prix en euros
     */
    public void updateProductPrice(int productId, float newPrice){
        Produit product = Produit.findProduitById(productId);
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
     * Supprimer un produit du système
     * Retire le produit de toutes les catégories
     * @param productId ID du produit à supprimer
     */
    public void removeProduct(int productId){
        Produit product = Produit.findProduitById(productId);
        if(product != null){
            product.removeFromAllCategories();  // Suppression de toutes les catégories
            System.out.println("Produit '" + product.getNom() + "' supprimé du système.");
        } else {
            System.out.println("Produit non trouvé avec l'ID: " + productId);
        }
    }

    /**
     * Représentation textuelle de l'administrateur pour l'affichage
     * @return Description formatée de l'administrateur
     */
    @Override
    public String toString(){
        return String.format("Admin{nom='%s', prenom='%s', email='%s'}",
                getNom(), getPrenom(), getEmail());
    }
}