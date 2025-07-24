package commande;

import produit.Produit;

import java.util.Scanner;

/**
 * Classe représentant un produit avec sa quantité dans un panier
 * Associe un produit à une quantité et conserve le prix unitaire au moment de l'ajout
 */
public class QteProduit {

    private Produit produit;        // Référence vers le produit
    private int quantite;           // Quantité commandée
    private float prixUnitaire;     // Prix unitaire fixé au moment de l'ajout

    /**
     * Constructeur avec produit et quantité spécifiés
     * @param produit Le produit à ajouter
     * @param qte Quantité désirée
     */
    public QteProduit(Produit produit, int qte){
        this.produit = produit;
        this.prixUnitaire = produit.getPrix();  // Fixation du prix au moment de l'ajout
        this.quantite = qte;
    }

    /**
     * Constructeur par défaut avec meilleure gestion d'erreur
     * Demande à l'utilisateur de saisir l'ID du produit et la quantité
     * Inclut une validation et une gestion d'erreur robuste
     */
    public QteProduit(){
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Entrez l'ID du produit : ");
            int id = sc.nextInt();

            // Recherche du produit par ID (utilisation de la méthode rapide)
            Produit produit1 = Produit.findProduitById(id);
            if(produit1 == null){
                System.out.println("Produit inexistant avec l'ID: " + id);
                this.produit = null;  // Marquer comme invalide
                return;
            }

            this.produit = produit1;
            this.prixUnitaire = produit1.getPrix();

            System.out.print("Entrez la quantité : ");
            int qte = sc.nextInt();

            // Validation de la quantité
            if(qte <= 0){
                System.out.println("La quantité doit être positive !");
                this.quantite = 1; // Valeur par défaut
            } else {
                this.quantite = qte;
            }

            System.out.println("Produit ajouté: " + produit1.getNom() + " x" + this.quantite);

        } catch (Exception e) {
            System.out.println("Erreur lors de la saisie. Veuillez entrer des nombres valides.");
            this.produit = null;  // Marquer comme invalide en cas d'erreur
        }
    }

    /**
     * Crée un QteProduit à partir d'un ID de produit et d'une quantité
     * @param productId ID du produit
     * @param quantity Quantité désirée
     * @throws IllegalArgumentException si le produit n'existe pas ou la quantité est invalide
     */
    public QteProduit(int productId, int quantity){
        Produit produit = Produit.findProduitById(productId);
        if(produit != null && quantity > 0){
            this.produit = produit;
            this.prixUnitaire = produit.getPrix();
            this.quantite = quantity;
        } else {
            throw new IllegalArgumentException("Produit inexistant ou quantité invalide");
        }
    }

    // Getters - méthodes d'accès aux attributs
    public Produit getProduit(){
        return this.produit;
    }

    public int getQuantite(){
        return this.quantite;
    }

    public float getPrixUnitaire(){
        return this.prixUnitaire;
    }

    /**
     * Setter avec validation pour la quantité
     * @param quantite Nouvelle quantité (doit être positive)
     */
    public void setQuantite(int quantite){
        if(quantite > 0){
            this.quantite = quantite;
        } else {
            System.out.println("La quantité doit être positive !");
        }
    }

    /**
     * Calculer le prix total pour cette ligne de produit
     * @return Prix total (prix unitaire × quantité)
     */
    public float getTotalPrice(){
        return this.prixUnitaire * this.quantite;
    }

    /**
     * Mettre à jour le prix unitaire
     * Utile si le prix du produit change après l'ajout au panier
     */
    public void updatePrice(){
        if(this.produit != null){
            this.prixUnitaire = this.produit.getPrix();
        }
    }

    /**
     * Vérifier si l'objet QteProduit est valide
     * @return true si le produit existe et la quantité est positive
     */
    public boolean isValid(){
        return this.produit != null && this.quantite > 0;
    }

    /**
     * Représentation textuelle de la ligne de produit
     * Affiche le nom, la quantité, le prix unitaire et le total
     * @return Description formatée de la ligne
     */
    @Override
    public String toString(){
        if(produit != null){
            return String.format("%s x%d @ %.2f€ = %.2f€",
                    produit.getNom(), quantite, prixUnitaire, getTotalPrice());
        }
        return "Produit invalide";
    }
}