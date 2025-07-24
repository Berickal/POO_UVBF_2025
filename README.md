# POO 2025 : EShop Base
Un système de commerce électronique en ligne de commande basé sur Java qui simule la fonctionnalité d'achat en ligne avec gestion des utilisateurs, catalogue de produits, panier d'achat et traitement des commandes. 🚀 Fonctionnalités
Pour les utilisateurs

Gestion de compte : Inscription, connexion, gestion des profils
Navigation des produits : Visualisation des produits classés par catégories
Panier : Ajout de produits, gestion des quantités, affichage des totaux
Traitement des commandes : Passation de commandes avec génération automatique d'identifiants
Gestion des adresses : Ajout et mise à jour des adresses de livraison

Pour les administrateurs

Gestion des utilisateurs : Visualisation de tous les utilisateurs enregistrés et des statistiques
Gestion des produits : Création de catégories, ajout de produits, mise à jour des prix
Gestion des commandes : Visualisation de toutes les commandes, marquage comme livrées
Analyse système : Suivi des revenus, statistiques utilisateurs, catégories populaires

📁 Structure du projet
```
src/
├── account/
│ ├── Account.java # Classe de compte de base avec authentification
│ ├── User.java # Compte client avec gestion des adresses
│ ├── Admin.java # Administrateur avec privilèges élevés
│ └── Localisation.java # Gestion des adresses/emplacements
├── produit/
│ ├── Produit.java # Entité produit avec tarification
│ └── Category.java # Catégories et organisation des produits
├── commande/
│ ├── Panier.java # Gestion du panier et des commandes
│ └── QteProduit.java # Enveloppe de quantité de produits pour les articles du panier
└── Main.java # Point d'entrée et interface utilisateur de l'application
```
🔧 Configuration et installation
Prérequis

Kit de développement Java (JDK) 8 ou supérieur
Accès en ligne de commande/terminal

Étapes d'installation

Cloner ou télécharger les fichiers du projet
Compiler tous les fichiers Java :

```bash
javac -d bin src/**/*.java src/*.java
```

Exécutez l'application :
```bash
java -cp bin Main
```

🎮 Guide de démarrage rapide
Compte administrateur par défaut

E-mail : admin@eshop.com
Mot de passe : admin123

Exemples de données
Le système est préinstallé avec :

3 catégories : Électronique, Vêtements, Livres
5 produits : Ordinateur portable, Smartphone, T-shirt, Jeans, Roman
1 compte administrateur : Prêt à l'emploi

Flux d'utilisation de base

Démarrer l'application
Créer un nouveau compte utilisateur ou se connecter en tant qu'administrateur
Parcourir le catalogue de produits
Ajouter des produits au panier (utilisateurs uniquement)
Passer des commandes et gérer les livraisons

📖 Guide utilisateur
Options du menu principal
1. S'inscrire comme utilisateur # Enregistrer un nouvel utilisateur
2. Se connecter comme utilisateur # Connexion utilisateur
3. Se connecter comme administrateur # Connexion administrateur
4. Parcourir les produits (invité) # Parcourir en tant qu'invité
5. Quitter # Quitter application
Menu utilisateur (après connexion)
1. Parcourir le catalogue # Parcourir les produits
2. Voir mon panier # Voir le panier
3. Ajouter un produit au panier # Ajouter au panier
4. Valider ma commande # Passer la commande
5. Gérer mon adresse # Gérer l'adresse
6. Se déconnecter # Logout
Menu Administrateur (Après la connexion)
1. Créer une nouvelle catégorie # Créer une catégorie
2. Ajouter un produit # Ajouter un produit
3. Voir toutes les catégories # Afficher les catégories
4. Voir tous les produits # Voir les produits
5. Voir tous les utilisateurs # Afficher les utilisateurs
6. Voir toutes les commandes # Afficher les commandes
7. Livrer une commande # Livrer la commande
8. Statistiques système # Statistiques système
9. Se déconnecter # Logout
🛡️ Fonctionnalités de sécurité

Validation des e-mails : évite les comptes en double
Validation des entrées : gère les entrées non valides avec élégance
Protection par mot de passe : champs de mot de passe protégés dans les classes
Privilèges d'administrateur : fonctionnalités d'administration et niveaux d'accès séparés

📊 Architecture du système
Modèles de conception Utilisé

Héritage : Compte → Hiérarchie utilisateur/administrateur
Composition : Utilisateur contient Localisation, Panier contient QteProduit
Base de données statique : Stockage en mémoire utilisant des ArrayLists statiques
Modèle MVC : Séparation des données (entités), de la logique (méthodes) et de la présentation (Main)

Classes clés

Compte : Authentification de base et gestion des utilisateurs
Produit : Catalogue de produits avec catégories
Panier : Panier et traitement des commandes
Main : Interface console et flux applicatif

⚠️ Remarques importantes
Persistance des données

Stockage en mémoire : Toutes les données sont stockées dans des ArrayLists statiques
Session uniquement : Les données sont perdues à la fermeture de l'application
Sans base de données : Ceci est une version de démonstration

Limitations

Session mono-utilisateur : Un seul utilisateur à la fois
Interface console : Interaction textuelle uniquement
Sécurité de base : Mots de passe stockés en texte brut
Pas de validation des données : Nettoyage limité des entrées
