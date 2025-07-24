package commande;

import produit.Produit;

import java.util.Scanner;

public class QteProduit {

    private Produit produit;
    private int quantite;
    private float prixUnitaire;

    public QteProduit(Produit produit, int qte){
        this.produit = produit;
        this.prixUnitaire = produit.getPrix();
        this.quantite = qte;
    }

    // Fixed: Better error handling and validation
    public QteProduit(){
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Entrez l'ID du produit : ");
            int id = sc.nextInt();

            Produit produit1 = Produit.findProduitByIdFast(id);
            if(produit1 == null){
                System.out.println("Produit inexistant avec l'ID: " + id);
                this.produit = null;
                return;
            }

            this.produit = produit1;
            this.prixUnitaire = produit1.getPrix();

            System.out.print("Entrez la quantité : ");
            int qte = sc.nextInt();

            if(qte <= 0){
                System.out.println("La quantité doit être positive !");
                this.quantite = 1; // Default to 1
            } else {
                this.quantite = qte;
            }

            System.out.println("Produit ajouté: " + produit1.getNom() + " x" + this.quantite);

        } catch (Exception e) {
            System.out.println("Erreur lors de la saisie. Veuillez entrer des nombres valides.");
            this.produit = null;
        }
    }

    // Added: Constructor with validation
    public QteProduit(int productId, int quantity){
        Produit produit = Produit.findProduitByIdFast(productId);
        if(produit != null && quantity > 0){
            this.produit = produit;
            this.prixUnitaire = produit.getPrix();
            this.quantite = quantity;
        } else {
            throw new IllegalArgumentException("Produit inexistant ou quantité invalide");
        }
    }

    public Produit getProduit(){
        return this.produit;
    }

    public int getQuantite(){
        return this.quantite;
    }

    public float getPrixUnitaire(){
        return this.prixUnitaire;
    }

    public void setQuantite(int quantite){
        if(quantite > 0){
            this.quantite = quantite;
        } else {
            System.out.println("La quantité doit être positive !");
        }
    }

    // Added: Get total price for this item
    public float getTotalPrice(){
        return this.prixUnitaire * this.quantite;
    }

    // Added: Update price (in case product price changes)
    public void updatePrice(){
        if(this.produit != null){
            this.prixUnitaire = this.produit.getPrix();
        }
    }

    // Added: Validation method
    public boolean isValid(){
        return this.produit != null && this.quantite > 0;
    }

    @Override
    public String toString(){
        if(produit != null){
            return String.format("%s x%d @ %.2f€ = %.2f€",
                    produit.getNom(), quantite, prixUnitaire, getTotalPrice());
        }
        return "Produit invalide";
    }
}