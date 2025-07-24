package produit;

import account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category {

    private static List<Category> db = new ArrayList<>();

    private String nom;
    private String description;
    private ArrayList<Produit> produits;

    public Category(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le nom de la categorie de produit : ");
        String nom = sc.nextLine();
        System.out.print("Entrez la description du categorie de produit : ");
        String description = sc.nextLine();
        this.nom = nom;
        this.description = description;
        this.produits = new ArrayList<>();

        this.save();
    }

    public Category(String nom, String description){
        this.nom = nom;
        this.description = description;
        this.produits = new ArrayList<>();

        this.save();
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Produit> getProduits() {
        return produits;
    }

    public void setDescription(String description) {
        this.description = description;
        updateInDatabase(); // Update in database when changed
    }

    public void setNom(String nom) {
        if(!isNomExist(nom) || nom.equals(this.nom)){ // Allow same name for updates
            this.nom = nom;
            updateInDatabase();
        } else {
            System.out.println("Ce nom de catégorie existe déjà !");
        }
    }

    public void setProduits(ArrayList<Produit> produits) {
        this.produits = produits;
    }

    // Added: Method to add a single product
    public void addProduit(Produit produit){
        if(produit != null && !this.produits.contains(produit)){
            this.produits.add(produit);
            System.out.println("Produit ajouté à la catégorie " + this.nom);
        }
    }

    // Added: Method to remove a product
    public boolean removeProduit(Produit produit){
        return this.produits.remove(produit);
    }

    // Added: Method to remove product by ID
    public boolean removeProduitById(int id){
        return this.produits.removeIf(p -> p.getId() == id);
    }

    private boolean isNomExist(String nom){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getNom().equals(nom)) return true;
        }
        return false;
    }

    public void save(){
        if(this.isNomExist(this.nom)){
            System.out.println("Categorie déjà existant");
        }
        else {
            db.add(this);
            System.out.println("Categorie enregistré avec succès");
        }
    }

    // Added: Update existing category in database
    private void updateInDatabase(){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i) == this){
                db.set(i, this);
                break;
            }
        }
    }

    // Added: Delete category
    public boolean delete(){
        if(!this.produits.isEmpty()){
            System.out.println("Impossible de supprimer une catégorie contenant des produits !");
            return false;
        }
        return db.remove(this);
    }

    public static List<Category> findTouteCategory(){
        return new ArrayList<>(db); // Return copy to prevent external modification
    }

    public static Category findByNom(String nom){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getNom().equals(nom)) return db.get(i);
        }
        return null;
    }

    // Added: Find category by product ID
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

    // Added: Get category statistics
    public int getProductCount(){
        return this.produits.size();
    }

    public float getAveragePrice(){
        if(this.produits.isEmpty()) return 0;

        float total = 0;
        for(Produit produit : this.produits){
            total += produit.getPrix();
        }
        return total / this.produits.size();
    }

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

    @Override
    public String toString(){
        return String.format("Category{nom='%s', description='%s', products=%d}",
                nom, description, produits.size());
    }
}