package supermat;

/**
 * Démonstration complète de la bibliothèque Supermatrice.
 * Montre tous les aspects de l'utilisation des supermatrices en Java.
 */
public class DemoSupermat {
    
    public static void main(String[] args) {
        System.out.println("=== Démonstration de la bibliothèque Supermatrice ===\n");
        
        demonstrationComplete();
        TestSupermat.testerErreurs();
    }
    
    private static void demonstrationComplete() {
        // Exemple pratique : résolution d'un petit système matriciel
        System.out.println("=== Exemple pratique : calculs matriciels ===");
        
        // Création de matrices pour un exemple concret
        System.out.println("1. Création des matrices A et B");
        
        // Matrice A (3x3) - exemple de transformation
        double[][] dataA = {
            {2.0, 1.0, 0.0},
            {1.0, 3.0, 1.0},
            {0.0, 1.0, 2.0}
        };
        Supermat A = SupermatUtils.matSupermat(dataA);
        A.afficher("Matrice A");
        
        // Matrice B (3x2) - exemple de données
        double[][] dataB = {
            {5.0, 2.0},
            {3.0, 4.0},
            {1.0, 6.0}
        };
        Supermat B = SupermatUtils.matSupermat(dataB);
        B.afficher("Matrice B");
        
        // Calcul du produit A × B
        System.out.println("2. Calcul du produit A × B");
        Supermat C = Supermat.produit(A, B);
        C.afficher("Résultat C = A × B");
        
        // Démonstration des opérations avancées
        System.out.println("3. Opérations avancées");
        
        // Transposée
        Supermat At = SupermatUtils.transpose(A);
        At.afficher("Transposée de A");
        
        // Sous-matrice
        Supermat subA = A.sousMatrice(0, 1, 0, 1);
        subA.afficher("Sous-matrice A[0..1][0..1]");
        
        // Modification de la sous-matrice
        System.out.println("4. Test de modification de sous-matrice");
        System.out.println("Avant modification:");
        A.afficher("A originale");
        subA.afficher("Sous-matrice");
        
        subA.set(0, 0, 999.0);
        System.out.println("Après modification de sub[0,0] = 999:");
        A.afficher("A (inchangée car vue indépendante en Java)");
        subA.afficher("Sous-matrice modifiée");
        
        // Démonstration avec matrices plus grandes
        System.out.println("5. Matrices plus grandes");
        Supermat grandeMatrice = new Supermat(5, 5);
        SupermatUtils.remplirAleatoire(grandeMatrice, 1.0, 100.0);
        grandeMatrice.afficher("Matrice 5x5 aléatoire");
        
        // Test de performance avec une matrice identité
        Supermat identite5 = SupermatUtils.identite(5);
        Supermat produitIdentite = Supermat.produit(grandeMatrice, identite5);
        
        System.out.println("6. Vérification: Matrice × Identité = Matrice");
        System.out.println("Les matrices suivantes devraient être identiques:");
        grandeMatrice.afficher("Matrice originale");
        produitIdentite.afficher("Matrice × Identité");
        
        // Test des permutations
        System.out.println("7. Test des permutations de lignes");
        Supermat testPerm = new Supermat(4, 3);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                testPerm.set(i, j, i * 10 + j);
            }
        }
        
        testPerm.afficher("Avant permutations");
        testPerm.permuterLignes(0, 3);
        testPerm.afficher("Après permutation lignes 0 et 3");
        testPerm.permuterLignes(1, 2);
        testPerm.afficher("Après permutation lignes 1 et 2");
        
        // Conversion tableau ↔ supermatrice
        System.out.println("8. Test de conversion complète");
        double[] tableau1D = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Supermat fromArray = SupermatUtils.matSupermat(tableau1D, 3, 4, 3, 4);
        fromArray.afficher("Matrice créée depuis tableau 1D (3x4)");
        
        double[] retour1D = new double[12];
        SupermatUtils.supermatMat(fromArray, retour1D, 3, 4);
        
        System.out.println("Tableau 1D après conversion retour:");
        for (int i = 0; i < retour1D.length; i++) {
            System.out.printf("%.1f ", retour1D[i]);
            if ((i + 1) % 4 == 0) System.out.println();
        }
        System.out.println();
        
        System.out.println("=== Fin de la démonstration ===\n");
    }
}
