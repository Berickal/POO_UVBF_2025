import account.Account;
import account.Admin;
import account.User;
import commande.QteProduit;
import produit.Category;
import produit.Produit;
import commande.Panier;

import java.util.Scanner;
import java.util.List;

/**
 * Classe principale du système e-commerce
 * Gère l'interface utilisateur en ligne de commande et les interactions
 */
public class Main {
    // Scanner global pour la saisie utilisateur
    private static Scanner scanner = new Scanner(System.in);

    // Variables de session pour l'utilisateur connecté
    private static User currentUser = null;           // Utilisateur actuellement connecté
    private static Admin currentAdmin = null;         // Administrateur actuellement connecté
    private static Panier currentPanier = null;      // Panier de l'utilisateur connecté

    /**
     * Point d'entrée principal du programme
     * Initialise les données d'exemple et lance la boucle principale
     */
    public static void main(String[] args) {
        System.out.println("=== Bienvenue dans notre E-Shop ===");

        // Initialisation des données d'exemple pour tester le système
        initializeSampleData();

        // Boucle principale du programme
        while (true) {
            if (currentUser == null && currentAdmin == null) {
                showMainMenu();           // Menu principal si personne n'est connecté
            } else if (currentUser != null) {
                showUserMenu();           // Menu utilisateur si un utilisateur est connecté
            } else if (currentAdmin != null) {
                showAdminMenu();          // Menu administrateur si un admin est connecté
            }
        }
    }

    /**
     * Initialise des données d'exemple pour démontrer le système
     * Crée des catégories, produits et un compte administrateur
     */
    private static void initializeSampleData() {
        // Création des catégories d'exemple
        Category electronics = new Category("Électronique", "Appareils électroniques et gadgets");
        Category clothing = new Category("Vêtements", "Vêtements pour hommes et femmes");
        Category books = new Category("Livres", "Livres et magazines");

        // Création des produits d'exemple
        Produit laptop = new Produit("Laptop HP", "Ordinateur portable HP 15 pouces", 799.99f);
        Produit phone = new Produit("Smartphone Samsung", "Galaxy S23 128GB", 599.99f);
        Produit tshirt = new Produit("T-Shirt", "T-Shirt en coton 100%", 19.99f);
        Produit jeans = new Produit("Jeans", "Jeans bleu délavé", 49.99f);
        Produit novel = new Produit("Roman", "Best-seller français", 12.99f);

        // Ajout des produits aux catégories correspondantes
        electronics.getProduits().add(laptop);
        electronics.getProduits().add(phone);
        clothing.getProduits().add(tshirt);
        clothing.getProduits().add(jeans);
        books.getProduits().add(novel);

        // Création d'un compte administrateur par défaut
        Admin admin = new Admin("Admin", "Super", "admin@eshop.com", "admin123");

        System.out.println("Données d'exemple chargées !");
    }

    /**
     * Affiche le menu principal pour les utilisateurs non connectés
     * Propose l'inscription, la connexion, ou la navigation en tant qu'invité
     */
    private static void showMainMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. S'inscrire comme utilisateur");
        System.out.println("2. Se connecter comme utilisateur");
        System.out.println("3. Se connecter comme administrateur");
        System.out.println("4. Parcourir les produits (invité)");
        System.out.println("5. Quitter");
        System.out.print("Votre choix : ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne

        // Traitement du choix de l'utilisateur
        switch (choice) {
            case 1:
                registerUser();    // Inscription d'un nouvel utilisateur
                break;
            case 2:
                loginUser();       // Connexion utilisateur
                break;
            case 3:
                loginAdmin();      // Connexion administrateur
                break;
            case 4:
                browseCatalog();   // Navigation invité
                break;
            case 5:
                System.out.println("Merci de votre visite !");
                System.exit(0);    // Fermeture du programme
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    /**
     * Affiche le menu utilisateur pour les clients connectés
     * Propose les fonctionnalités de shopping et de gestion de compte
     */
    private static void showUserMenu() {
        System.out.println("\n=== MENU UTILISATEUR ===");
        System.out.println("Connecté en tant que: " + currentUser.getPrenom() + " " + currentUser.getNom());
        System.out.println("1. Parcourir le catalogue");
        System.out.println("2. Voir mon panier");
        System.out.println("3. Ajouter un produit au panier");
        System.out.println("4. Valider ma commande");
        System.out.println("5. Gérer mon adresse");
        System.out.println("6. Se déconnecter");
        System.out.print("Votre choix : ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consommer le caractère de nouvelle ligne

        // Traitement des actions utilisateur
        switch (choice) {
            case 1:
                browseCatalog();      // Parcourir les produits
                break;
            case 2:
                viewCart();           // Afficher le panier
                break;
            case 3:
                addToCart();          // Ajouter au panier
                break;
            case 4:
                validateOrder();      // Passer commande
                break;
            case 5:
                manageAddress();      // Gérer l'adresse de livraison
                break;
            case 6:
                logout();             // Déconnexion
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== MENU ADMINISTRATEUR ===");
        System.out.println("1. Créer une nouvelle catégorie");
        System.out.println("2. Ajouter un produit");
        System.out.println("3. Voir toutes les catégories");
        System.out.println("4. Voir tous les produits");
        System.out.println("5. Voir tous les utilisateurs");
        System.out.println("6. Voir toutes les commandes");
        System.out.println("7. Livrer une commande");
        System.out.println("8. Statistiques système");
        System.out.println("9. Se déconnecter");
        System.out.print("Votre choix : ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                createCategory();
                break;
            case 2:
                addProduct();
                break;
            case 3:
                viewAllCategories();
                break;
            case 4:
                viewAllProducts();
                break;
            case 5:
                currentAdmin.displayAllUsers();
                break;
            case 6:
                currentAdmin.displayAllOrders();
                break;
            case 7:
                deliverOrder();
                break;
            case 8:
                currentAdmin.displayStatistics();
                break;
            case 9:
                logout();
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    /**
     * Processus d'inscription d'un nouvel utilisateur
     * Utilise le constructeur par défaut de User qui demande les informations
     */
    private static void registerUser() {
        System.out.println("\n=== INSCRIPTION UTILISATEUR ===");
        User newUser = new User();  // Le constructeur gère la saisie et la validation
        System.out.println("Inscription réussie !");
    }

    /**
     * Processus de connexion utilisateur amélioré
     * Vérifie l'existence de l'email et valide le mot de passe
     */
    private static void loginUser() {
        System.out.println("\n=== CONNEXION UTILISATEUR ===");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        // Vérification de l'existence de l'email
        if (Account.isEmailExist(email)) {
            currentUser = new User();
            // CORRECTION: Utilisation du booléen de retour pour valider la connexion
            if(currentUser.connecter(email, password)){
                currentPanier = new Panier(currentUser);  // Création du panier
                System.out.println("Connexion réussie !");
            } else {
                currentUser = null;  // Réinitialisation en cas d'échec
                System.out.println("Mot de passe incorrect !");
            }
        } else {
            System.out.println("Email non trouvé !");
        }
    }

    /**
     * Processus de connexion administrateur
     * Utilise des identifiants fixes pour la démonstration
     */
    private static void loginAdmin() {
        System.out.println("\n=== CONNEXION ADMINISTRATEUR ===");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        // Vérification simple des identifiants admin (à sécuriser en production)
        if (email.equals("admin@eshop.com") && password.equals("admin123")) {
            currentAdmin = new Admin("Admin", "Super", email, password);
            System.out.println("Connexion administrateur réussie !");
        } else {
            System.out.println("Identifiants administrateur incorrects !");
        }
    }

    private static void browseCatalog() {
        System.out.println("\n=== CATALOGUE PRODUITS ===");
        List<Category> categories = Category.findTouteCategory();

        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie disponible.");
            return;
        }

        for (Category category : categories) {
            System.out.println("\n--- " + category.getNom() + " ---");
            System.out.println("Description: " + category.getDescription());

            if (category.getProduits().isEmpty()) {
                System.out.println("Aucun produit dans cette catégorie.");
            } else {
                for (Produit produit : category.getProduits()) {
                    System.out.printf("ID: %d | %s - %.2f€%n",
                            produit.getId(), produit.getNom(), produit.getPrix());
                    System.out.println("  Description: " + produit.getDescription());
                }
            }
        }
    }

    private static void viewCart() {
        if (currentPanier == null || currentPanier.getProduits().isEmpty()) {
            System.out.println("\nVotre panier est vide.");
            return;
        }

        System.out.println("\n=== VOTRE PANIER ===");
        float total = 0;

        for (QteProduit item : currentPanier.getProduits()) {
            float itemTotal = item.getPrixUnitaire() * item.getQuantite();
            System.out.printf("%s x%d - %.2f€ chacun = %.2f€%n",
                    item.getProduit().getNom(),
                    item.getQuantite(),
                    item.getPrixUnitaire(),
                    itemTotal);
            total += itemTotal;
        }

        System.out.printf("TOTAL: %.2f€%n", total);
    }

    private static void addToCart() {
        if (currentPanier == null) {
            System.out.println("Erreur: Panier non initialisé.");
            return;
        }

        System.out.println("\n=== AJOUTER AU PANIER ===");
        browseCatalog();

        try {
            currentPanier.addProduct(); // This will prompt for product ID and quantity
            System.out.println("Produit ajouté au panier !");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout au panier.");
        }
    }

    private static void validateOrder() {
        if (currentPanier == null || currentPanier.getProduits().isEmpty()) {
            System.out.println("Votre panier est vide !");
            return;
        }

        System.out.println("\n=== VALIDATION COMMANDE ===");
        viewCart();

        System.out.print("Confirmer la commande ? (o/n) : ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("o") || confirm.equalsIgnoreCase("oui")) {
            currentPanier.validerPanier();
            System.out.println("Commande validée avec succès !");
            System.out.println("Numéro de commande: " + currentPanier.getId());

            // Create new cart for future orders
            currentPanier = new Panier(currentUser);
        } else {
            System.out.println("Commande annulée.");
        }
    }

    private static void manageAddress() {
        System.out.println("\n=== GESTION ADRESSE ===");

        if (currentUser.getAddress() != null) {
            System.out.println("Adresse actuelle:");
            System.out.println("Ville: " + currentUser.getAddress().getVille());
            System.out.println("Secteur: " + currentUser.getAddress().getSecteur());
            System.out.println("Description: " + currentUser.getAddress().getDescription());

            System.out.print("Modifier l'adresse ? (o/n) : ");
            String modify = scanner.nextLine();

            if (modify.equalsIgnoreCase("o") || modify.equalsIgnoreCase("oui")) {
                currentUser.addAddress();
                System.out.println("Adresse mise à jour !");
            }
        } else {
            System.out.println("Aucune adresse enregistrée. Ajout d'une nouvelle adresse:");
            currentUser.addAddress();
            System.out.println("Adresse ajoutée !");
        }
    }

    private static void createCategory() {
        System.out.println("\n=== CRÉER CATÉGORIE ===");
        Category newCategory = new Category(); // This will prompt for input
    }

    private static void addProduct() {
        System.out.println("\n=== AJOUTER PRODUIT ===");
        System.out.print("Nom du produit : ");
        String nom = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Prix : ");
        float prix = scanner.nextFloat();
        scanner.nextLine(); // consume newline

        Produit newProduct = new Produit(nom, description, prix);

        System.out.println("Catégories disponibles:");
        List<Category> categories = Category.findTouteCategory();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getNom());
        }

        System.out.print("Choisir une catégorie (numéro) : ");
        int categoryChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (categoryChoice >= 0 && categoryChoice < categories.size()) {
            categories.get(categoryChoice).addProduit(newProduct);
            System.out.println("Produit ajouté avec succès !");
        } else {
            System.out.println("Catégorie invalide !");
        }
    }

    private static void deliverOrder(){
        System.out.println("\n=== LIVRER COMMANDE ===");
        currentAdmin.displayAllOrders();

        System.out.print("Entrez l'ID de la commande à livrer : ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        currentAdmin.deliverOrder(orderId);
    }

    private static void viewAllCategories() {
        System.out.println("\n=== TOUTES LES CATÉGORIES ===");
        List<Category> categories = Category.findTouteCategory();

        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie trouvée.");
        } else {
            for (Category category : categories) {
                System.out.println("- " + category.getNom() + ": " + category.getDescription());
                System.out.println("  Nombre de produits: " + category.getProduits().size());
            }
        }
    }

    private static void viewAllProducts() {
        System.out.println("\n=== TOUS LES PRODUITS ===");
        List<Category> categories = Category.findTouteCategory();
        int totalProducts = 0;

        for (Category category : categories) {
            if (!category.getProduits().isEmpty()) {
                System.out.println("\n--- " + category.getNom() + " ---");
                for (Produit produit : category.getProduits()) {
                    System.out.printf("ID: %d | %s - %.2f€%n",
                            produit.getId(), produit.getNom(), produit.getPrix());
                    totalProducts++;
                }
            }
        }

        System.out.println("\nTotal des produits: " + totalProducts);
    }

    private static void logout() {
        currentUser = null;
        currentAdmin = null;
        currentPanier = null;
        System.out.println("Déconnexion réussie !");
    }
}