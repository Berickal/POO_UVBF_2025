package commande;

import account.User;
import produit.Produit;

import java.util.ArrayList;
import java.util.List;

public class Panier {

    private static ArrayList<Panier> db = new ArrayList<>();

    private int id;
    private User user;
    private ArrayList<QteProduit> produits = new ArrayList<>();
    private int status; // 0 = en cours, 1 = validé, 2 = livré

    public Panier(){
        this.id = db.size() + 1; // Fixed: Added ID assignment
        this.user = new User();
        this.produits = new ArrayList<>();
        this.status = 0;
    }

    public Panier(User user){
        this.id = db.size() + 1; // Fixed: Added ID assignment
        this.user = user;
        this.produits = new ArrayList<>();
        this.status = 0;
    }

    // Fixed: Complete addProduct method
    public void addProduct(){
        QteProduit produit = new QteProduit();
        if(produit.getProduit() != null) { // Check if product creation was successful
            addProduct(produit);
        }
    }

    // Added: Overloaded method for direct product addition
    public void addProduct(QteProduit produit){
        boolean found = false;
        for(int i = 0; i < this.produits.size(); i++){
            if(this.produits.get(i).getProduit().getId() == produit.getProduit().getId()){
                this.produits.get(i).setQuantite(this.produits.get(i).getQuantite() + produit.getQuantite());
                found = true;
                break;
            }
        }
        if(!found) { // Fixed: Add product if not already in cart
            this.produits.add(produit);
        }
    }

    // Added: Method to add product by ID and quantity
    public boolean addProduct(int productId, int quantity){
        Produit produit = Produit.findProduitByIdFast(productId);
        if(produit != null && quantity > 0){
            QteProduit qteProduit = new QteProduit(produit, quantity);
            addProduct(qteProduit);
            return true;
        }
        return false;
    }

    // Fixed: Improved removeProduct method
    public boolean removeProduct(int id){
        for(int i = 0; i < this.produits.size(); i++){
            if(this.produits.get(i).getProduit().getId() == id){
                this.produits.remove(i);
                return true;
            }
        }
        return false;
    }

    // Added: Update product quantity
    public boolean updateProductQuantity(int productId, int newQuantity){
        if(newQuantity <= 0){
            return removeProduct(productId);
        }

        for(QteProduit item : this.produits){
            if(item.getProduit().getId() == productId){
                item.setQuantite(newQuantity);
                return true;
            }
        }
        return false;
    }

    // Added: Clear cart
    public void clearCart(){
        this.produits.clear();
    }

    // Added: Calculate total price
    public float getTotalPrice(){
        float total = 0;
        for(QteProduit item : this.produits){
            total += item.getPrixUnitaire() * item.getQuantite();
        }
        return total;
    }

    // Added: Get item count
    public int getItemCount(){
        int count = 0;
        for(QteProduit item : this.produits){
            count += item.getQuantite();
        }
        return count;
    }

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

    public String getStatusString(){
        switch(this.status){
            case 0: return "En cours";
            case 1: return "Validé";
            case 2: return "Livré";
            default: return "Inconnu";
        }
    }

    public void validerPanier(){
        if(this.produits.isEmpty()){
            System.out.println("Impossible de valider un panier vide !");
            return;
        }
        this.status = 1;
        db.add(this);
        System.out.println("Panier validé avec l'ID: " + this.id);
    }

    public void livrerPanier(){
        if(this.status != 1){
            System.out.println("Le panier doit être validé avant d'être livré !");
            return;
        }
        this.status = 2;
        // Fixed: Better database update logic
        for (int i = 0; i < db.size(); i++){
            if(db.get(i).getId() == this.id){
                db.set(i, this);
                break;
            }
        }
        System.out.println("Panier livré avec succès !");
    }

    // Added: Get all orders
    public static List<Panier> getAllOrders(){
        return new ArrayList<>(db);
    }

    // Added: Find order by ID
    public static Panier findOrderById(int id){
        for(Panier panier : db){
            if(panier.getId() == id){
                return panier;
            }
        }
        return null;
    }

    // Added: Get orders by user
    public static List<Panier> getOrdersByUser(User user){
        List<Panier> userOrders = new ArrayList<>();
        for(Panier panier : db){
            if(panier.getUser().getEmail().equals(user.getEmail())){
                userOrders.add(panier);
            }
        }
        return userOrders;
    }

    @Override
    public String toString(){
        return String.format("Panier{id=%d, user=%s, items=%d, total=%.2f€, status=%s}",
                id, user.getEmail(), getItemCount(), getTotalPrice(), getStatusString());
    }
}