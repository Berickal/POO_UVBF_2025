package produit;

import java.util.ArrayList;
import java.util.List;

public class Produit {

    private static int nbrProduits = 0;
    private static List<Produit> allProducts = new ArrayList<>(); // Added: Global product database

    private int id;
    private String nom;
    private String description;
    private float prix;

    public Produit(String nom, String description, float prix){
        this.id = nbrProduits;
        this.nom = nom;
        this.description = description;
        this.prix = prix;

        nbrProduits += 1;
        allProducts.add(this); // Add to global product list
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    // Fixed: Array bounds exception (changed <= to <)
    public static Produit findProduitById(int id){
        List<Category> categories = Category.findTouteCategory();
        for (int i = 0; i < categories.size(); i++){
            for (int j = 0; j < categories.get(i).getProduits().size(); j++){ // Fixed: < instead of <=
                if (categories.get(i).getProduits().get(j).getId() == id){
                    return categories.get(i).getProduits().get(j);
                }
            }
        }
        return null;
    }

    // Alternative faster search using global product list
    public static Produit findProduitByIdFast(int id){
        for(Produit produit : allProducts){
            if(produit.getId() == id){
                return produit;
            }
        }
        return null;
    }

    // Fixed: Made save method public
    public void saveToCategory(String categorieNom){
        Category category = Category.findByNom(categorieNom);
        if (category != null){
            ArrayList<Produit> produits = category.getProduits();
            // Check if product already exists in category
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

    // Added: Get all products
    public static List<Produit> getAllProducts(){
        return new ArrayList<>(allProducts);
    }

    // Added: Remove product from all categories
    public void removeFromAllCategories(){
        List<Category> categories = Category.findTouteCategory();
        for(Category category : categories){
            category.getProduits().removeIf(p -> p.getId() == this.getId());
        }
    }

    @Override
    public String toString(){
        return String.format("Produit{id=%d, nom='%s', prix=%.2f€}", id, nom, prix);
    }
}