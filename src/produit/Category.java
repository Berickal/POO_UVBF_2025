package produit;

import account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant une catégorie de produits
 * Organise les produits par type et gère leur classification
 */
public class Category {

    // Base de données statique pour stocker toutes les catégories
    private static List<Category> db = new ArrayList<>();

    // Attributs de la catégorie
    private String nom;                              // Nom de la catégorie (unique)
    private String description;                      // Description de la catégorie
    private ArrayList<Produit> produits;            // Liste des produits dans cette catégorie

    /**
     * Constructeur par défaut - demande les informations à l'utilisateur
     * Utilise Scanner pour saisir le nom et la description depuis la console
     */
    public Category(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le nom de la categorie de produit : ");
        String nom = sc.nextLine();
        System.out.print("Entrez la description du categorie de produit : ");
        String description = sc.nextLine();

        // Initialisation des attributs
        this.nom = nom;
        this.description = description;
        this.produits = new ArrayList<>();

        this.save();  // Sauvegarde automatique
    }

    /**
     * Constructeur paramétré pour créer une catégorie avec des valeurs spécifiques
     * @param nom Nom de la catégorie (doit être unique)
     * @param description Description de la catégorie
     */
    public Category(String nom, String description){
        this.nom = nom;
        this.description = description;
        this.produits = new ArrayList<>();

        this.save();  // Sauvegarde automatique
    }

    // Getters - méthodes d'accès aux attributs
    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Produit> getProduits() {
        return produits;
    }

    // Setters - méthodes de modification des attributs avec validation

    /**
     * Modifier la description de la catégorie
     * Met automatiquement à jour la base de données
     * @param description Nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
        updateInDatabase(); // Mise à jour en base
    }

    /**
     * Modifier le nom de la catégorie avec vérification d'unicité
     * @param nom Nouveau nom (doit être unique)
     */
    public void setNom(String nom) {
        // Permettre le même nom pour les mises à jour, sinon vérifier l'unicité
        if(!isNomExist(nom) || nom.equals(this.nom)){
            this.nom = nom;
            updateInDatabase();
        } else {
            System.out.println("Ce nom de catégorie existe déjà !");
        }
    }

    /**
     * Définir la liste des produits de la catégorie
     * @param produits Nouvelle liste de produits
     */
    public void setProduits(ArrayList<Produit> produits) {
        this.produits = produits;
    }

    /**
     * Ajouter un seul produit à la catégorie
     * Vérifie que le produit n'existe pas déjà pour éviter les doublons
     * @param produit Produit à ajouter
     */
    public void addProduit(Produit produit){
        if(produit != null && !this.produits.contains(produit)){
            this.produits.add(produit);
            System.out.println("Produit ajouté à la catégorie " + this.nom);
        }
    }

    /**
     * Supprimer un produit de la catégorie
     * @param produit Produit à supprimer
     * @return true si la suppression réussit
     */
    public boolean removeProduit(Produit produit){
        return this.produits.remove(produit);
    }

    /**
     * Supprimer un produit par son ID
     * @param id ID du produit à supprimer
     * @return true si la suppression réussit
     */
    public boolean removeProduitById(int id){
        return this.produits.removeIf(p -> p.getId() == id);
    }

    /**
     * Vérifier si un nom de catégorie existe déjà
     * @param nom Nom à vérifier
     * @return true si le nom existe déjà
     */
    private boolean isNomExist(String nom){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getNom().equals(nom)) return true;
        }
        return false;
    }

    /**
     * Sauvegarder la catégorie dans la base de données
     * Vérifie l'unicité du nom avant la sauvegarde
     */
    public void save(){
        if(this.isNomExist(this.nom)){
            System.out.println("Categorie déjà existant");
        }
        else {
            db.add(this);
            System.out.println("Categorie enregistré avec succès");
        }
    }

    /**
     * Mettre à jour une catégorie existante dans la base de données
     * Méthode privée appelée lors des modifications
     */
    private void updateInDatabase(){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i) == this){
                db.set(i, this);
                break;
            }
        }
    }

    /**
     * Supprimer définitivement la catégorie
     * Ne peut être supprimée que si elle ne contient aucun produit
     * @return true si la suppression réussit
     */
    public boolean delete(){
        if(!this.produits.isEmpty()){
            System.out.println("Impossible de supprimer une catégorie contenant des produits !");
            return false;
        }
        return db.remove(this);
    }

    /**
     * Obtenir toutes les catégories du système
     * @return Copie de la liste des catégories (évite les modifications externes)
     */
    public static List<Category> findTouteCategory(){
        return new ArrayList<>(db);
    }

    /**
     * Rechercher une catégorie par son nom
     * @param nom Nom de la catégorie recherchée
     * @return La catégorie trouvée ou null si non trouvée
     */
    public static Category findByNom(String nom){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getNom().equals(nom)) return db.get(i);
        }
        return null;
    }

    /**
     * Trouver la catégorie contenant un produit spécifique
     * @param productId ID du produit recherché
     * @return La catégorie contenant le produit ou null
     */
    public static Category findCategoryByProductId(int productId){
        for(Category category : db){
            for(Produit produit : category.getProduits()){
                if(produit.getId() == productId){
                    return category;
                }
            }
        }
        return null;
    }

    // Méthodes statistiques ajoutées pour l'analyse des données

    /**
     * Obtenir le nombre de produits dans la catégorie
     * @return Nombre de produits
     */
    public int getProductCount(){
        return this.produits.size();
    }

    /**
     * Calculer le prix moyen des produits de la catégorie
     * @return Prix moyen en euros (0 si aucun produit)
     */
    public float getAveragePrice(){
        if(this.produits.isEmpty()) return 0;

        float total = 0;
        for(Produit produit : this.produits){
            total += produit.getPrix();
        }
        return total / this.produits.size();
    }

    /**
     * Trouver le produit le moins cher de la catégorie
     * @return Le produit le moins cher ou null si aucun produit
     */
    public Produit getCheapestProduct(){
        if(this.produits.isEmpty()) return null;

        Produit cheapest = this.produits.get(0);
        for(Produit produit : this.produits){
            if(produit.getPrix() < cheapest.getPrix()){
                cheapest = produit;
            }
        }
        return cheapest;
    }

    /**
     * Trouver le produit le plus cher de la catégorie
     * @return Le produit le plus cher ou null si aucun produit
     */
    public Produit getMostExpensiveProduct(){
        if(this.produits.isEmpty()) return null;

        Produit expensive = this.produits.get(0);
        for(Produit produit : this.produits){
            if(produit.getPrix() > expensive.getPrix()){
                expensive = produit;
            }
        }
        return expensive;
    }

    /**
     * Représentation textuelle de la catégorie pour l'affichage
     * @return Description formatée de la catégorie
     */
    @Override
    public String toString(){
        return String.format("Category{nom='%s', description='%s', products=%d}",
                nom, description, produits.size());
    }
}