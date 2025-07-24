package commande;

import account.User;
import produit.Produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un panier d'achat
 * Gère les produits sélectionnés par un utilisateur et le processus de commande
 */
public class Panier {

    // Base de données statique pour stocker toutes les commandes
    private static ArrayList<Panier> db = new ArrayList<>();

    // Attributs du panier
    private int id;                                    // Identifiant unique du panier
    private User user;                                // Utilisateur propriétaire du panier
    private ArrayList<QteProduit> produits = new ArrayList<>();  // Liste des produits avec quantités
    private int status;                               // Statut: 0=en cours, 1=validé, 2=livré

    /**
     * Constructeur par défaut - crée un panier avec un utilisateur par défaut
     */
    public Panier(){
        this.id = db.size() + 1;
        this.user = new User();
        this.produits = new ArrayList<>();
        this.status = 0;
    }

    /**
     * Constructeur avec utilisateur spécifique
     * @param user Utilisateur propriétaire du panier
     */
    public Panier(User user){
        this.id = db.size() + 1;
        this.user = user;
        this.produits = new ArrayList<>();
        this.status = 0;
    }

    /**
     * Ajoute un produit au panier en demandant les informations à l'utilisateur
     * Gère l'augmentation de quantité si le produit existe déjà
     */
    public void addProduct(){
        QteProduit produit = new QteProduit();  // Création avec saisie utilisateur
        if(produit.getProduit() != null) {      // Vérification de la création réussie
            addProduct(produit);
        }
    }

    /**
     * Méthode surchargée pour ajouter directement un QteProduit
     * @param produit Produit avec quantité à ajouter
     */
    public void addProduct(QteProduit produit){
        boolean found = false;

        // Recherche si le produit existe déjà dans le panier
        for(int i = 0; i < this.produits.size(); i++){
            if(this.produits.get(i).getProduit().getId() == produit.getProduit().getId()){
                // Si trouvé, augmenter la quantité
                this.produits.get(i).setQuantite(this.produits.get(i).getQuantite() + produit.getQuantite());
                found = true;
                break;
            }
        }

        // Ajouter le produit s'il n'était pas déjà présent
        if(!found) {
            this.produits.add(produit);
        }
    }

    /**
     * Supprime un produit du panier par son ID
     * @param id ID du produit à supprimer
     * @return true si la suppression réussit, false si le produit n'existe pas
     */
    public boolean removeProduct(int id){
        for(int i = 0; i < this.produits.size(); i++){
            if(this.produits.get(i).getProduit().getId() == id){
                this.produits.remove(i);
                return true;
            }
        }
        return false; // Produit non trouvé
    }

    /**
     * Calculer le prix total du panier
     * @return Prix total en euros
     */
    public float getTotalPrice(){
        float total = 0;
        for(QteProduit item : this.produits){
            total += item.getPrixUnitaire() * item.getQuantite();
        }
        return total;
    }

    /**
     * Compter le nombre total d'articles dans le panier
     * @return Nombre total d'articles (somme des quantités)
     */
    public int getItemCount(){
        int count = 0;
        for(QteProduit item : this.produits){
            count += item.getQuantite();
        }
        return count;
    }

    // Getters
    public List<QteProduit> getProduits(){
        return this.produits;
    }

    public User getUser(){
        return this.user;
    }

    public int getId(){
        return this.id;
    }

    public int getStatus(){
        return this.status;
    }

    /**
     * Obtenir le statut sous forme de texte
     * @return Description textuelle du statut
     */
    public String getStatusString(){
        switch(this.status){
            case 0: return "En cours";
            case 1: return "Validé";
            case 2: return "Livré";
            default: return "Inconnu";
        }
    }

    /**
     * Valider le panier (passer la commande)
     * Change le statut à "validé" et ajoute à la base de données des commandes
     */
    public void validerPanier(){
        if(this.produits.isEmpty()){
            System.out.println("Impossible de valider un panier vide !");
            return;
        }
        this.status = 1;
        db.add(this);
        System.out.println("Panier validé avec l'ID: " + this.id);
    }

    /**
     * Livrer le panier (marquer comme livré)
     * Ne peut être appelé que sur un panier validé
     */
    public void livrerPanier(){
        if(this.status != 1){
            System.out.println("Le panier doit être validé avant d'être livré !");
            return;
        }
        this.status = 2;

        // CORRECTION: Logique de mise à jour améliorée
        for (int i = 0; i < db.size(); i++){
            if(db.get(i).getId() == this.id){
                db.set(i, this);
                break;
            }
        }
        System.out.println("Panier livré avec succès !");
    }

    /**
     *Obtenir toutes les commandes du système
     * @return Copie de la liste de toutes les commandes
     */
    public static List<Panier> getAllOrders(){
        return new ArrayList<>(db);
    }

    /**
     * Rechercher une commande par ID
     * @param id Identifiant de la commande
     * @return La commande trouvée ou null
     */
    public static Panier findOrderById(int id){
        for(Panier panier : db){
            if(panier.getId() == id){
                return panier;
            }
        }
        return null;
    }

    /**
     * Représentation textuelle du panier pour l'affichage
     * @return Description formatée du panier
     */
    @Override
    public String toString(){
        return String.format("Panier{id=%d, user=%s, items=%d, total=%.2f€, status=%s}",
                id, user.getEmail(), getItemCount(), getTotalPrice(), getStatusString());
    }
}