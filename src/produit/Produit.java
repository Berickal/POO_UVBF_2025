package produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un produit dans le système e-commerce
 * Gère les informations de base d'un produit et sa recherche
 */
public class Produit {

    // Compteur statique pour générer des IDs uniques
    private static int nbrProduits = 0;

    // AJOUT: Base de données globale de tous les produits pour une recherche plus rapide
    private static List<Produit> db = new ArrayList<>();

    // Attributs du produit
    private int id;              // Identifiant unique du produit
    private String nom;          // Nom du produit
    private String description;  // Description détaillée
    private float prix;          // Prix en euros

    /**
     * Constructeur pour créer un nouveau produit
     * L'ID est généré automatiquement et le produit est ajouté à la base globale
     * @param nom Nom du produit
     * @param description Description du produit
     * @param prix Prix du produit en euros
     */
    public Produit(String nom, String description, float prix){
        this.id = nbrProduits;           // Attribution d'un ID unique
        this.nom = nom;
        this.description = description;
        this.prix = prix;

        nbrProduits += 1;                // Incrémentation du compteur global
        db.add(this);          // Ajout à la base de données globale
    }

    // Getters - méthodes d'accès aux attributs
    public int getId(){
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNom() {
        return this.nom;
    }

    public float getPrix() {
        return this.prix;
    }

    // Setters - méthodes de modification des attributs
    public void setDescription(String description) {
        this.description = description;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    /**
     * CORRECTION: Recherche d'un produit par ID dans la base de données
     * @param id Identifiant du produit recherché
     * @return Le produit trouvé ou null si non trouvé
     */
    public static Produit findProduitById(int id){
        for(Produit produit : db){
            if(produit.getId() == id){
                return produit;
            }
        }
        return null;
    }

    /**
     * Ajoute le produit à une catégorie spécifique avec vérification des doublons
     * @param categorieNom Nom de la catégorie de destination
     */
    public void saveToCategory(String categorieNom){
        Category category = Category.findByNom(categorieNom);
        if (category != null){
            ArrayList<Produit> produits = category.getProduits();

            // Vérification si le produit existe déjà dans cette catégorie
            boolean exists = false;
            for(Produit p : produits){
                if(p.getId() == this.getId()){
                    exists = true;
                    break;
                }
            }

            if(!exists){
                produits.add(this);
                category.setProduits(produits);
                System.out.println("Produit ajouté à la catégorie " + categorieNom);
            } else {
                System.out.println("Produit déjà dans cette catégorie");
            }
        } else {
            System.out.println("Catégorie non trouvée !");
        }
    }

    /**
     * Obtenir tous les produits du système
     * @return Copie de la liste de tous les produits
     */
    public static List<Produit> getAllProducts(){
        return new ArrayList<>(db);
    }

    /**
     * Supprimer le produit de toutes les catégories
     * Utile lors de la suppression définitive d'un produit
     */
    public void removeFromAllCategories(){
        List<Category> categories = Category.findTouteCategory();
        for(Category category : categories){
            // Suppression par condition sur l'ID
            category.getProduits().removeIf(p -> p.getId() == this.getId());
        }
    }

    /**
     * Représentation textuelle du produit pour l'affichage
     * @return Description formatée du produit
     */
    @Override
    public String toString(){
        return String.format("Produit{id=%d, nom='%s', prix=%.2f€}", id, nom, prix);
    }
}