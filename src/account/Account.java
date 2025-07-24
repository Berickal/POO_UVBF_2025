package account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe de base pour tous les comptes utilisateurs
 * Gère l'authentification et les informations de base des comptes
 */
public class Account {

    // Base de données statique pour stocker tous les comptes
    private static List<Account> db = new ArrayList<>();

    // Informations personnelles du compte
    private String nom;
    private String prenom;
    private String email;
    protected String password; // Protégé pour permettre l'accès aux classes filles

    /**
     * Constructeur par défaut - demande les informations à l'utilisateur
     * Utilise Scanner pour saisir les données depuis la console
     */
    public Account(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez votre nom : ");
        String nom = sc.nextLine();
        System.out.print("Entrez votre prenom : ");
        String prenom = sc.nextLine();
        System.out.print("Entrez votre email : ");
        String email = sc.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String password = sc.nextLine();

        // Initialisation des attributs
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;

        // CORRECTION: Logique inversée - sauvegarder si l'email n'existe pas
        if(!isEmailExist(this.getEmail())){
            saveAccount();
        } else {
            System.out.println("Email déjà utilisé !");
        }
    }

    /**
     * Constructeur paramétré pour créer un compte avec des valeurs spécifiques
     * @param nom Nom de famille
     * @param prenom Prénom
     * @param email Adresse email (doit être unique)
     * @param password Mot de passe
     */
    public Account(String nom, String prenom, String email, String password){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;

        // Sauvegarde automatique si l'email n'existe pas déjà
        if(!isEmailExist(this.getEmail())){
            saveAccount();
        }
    }

    // Getters - méthodes d'accès aux attributs privés
    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public String getEmail(){
        return this.email;
    }

    // Setters - méthodes de modification des attributs
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Vérifie les identifiants et met à jour l'objet actuel avec les données du compte trouvé
     * @param email Email de connexion
     * @param password Mot de passe
     * @return true si la connexion réussit, false sinon
     */
    public boolean connecter(String email, String password){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getEmail().equals(email) && db.get(i).password.equals(password)){
                // Mise à jour des informations de l'objet actuel
                this.setNom(db.get(i).nom);
                this.setPrenom(db.get(i).prenom);
                this.setEmail(db.get(i).getEmail());
                this.setPassword(db.get(i).password);
                return true; // Connexion réussie
            }
        }
        return false; // Identifiants incorrects
    }

    /**
     * Vérifie si un email existe déjà dans la base de données
     * @param email Email à vérifier
     * @return true si l'email existe, false sinon
     */
    public static boolean isEmailExist(String email){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getEmail().equals(email)) return true;
        }
        return false;
    }

    /**
     * Sauvegarde le compte dans la base de données
     * Méthode privée appelée lors de la création d'un compte
     */
    private void saveAccount(){
        db.add(this);
        System.out.println("Compte enregistré avec succès !");
        System.out.println("Nombre de compte créés : " + db.size());
    }

    /**
     * Méthode pour obtenir tous les comptes (utile pour l'administration)
     * Retourne une copie de la liste pour éviter les modifications externes
     * @return Liste de tous les comptes enregistrés
     */
    public static List<Account> getAllAccounts(){
        return new ArrayList<>(db);
    }
}