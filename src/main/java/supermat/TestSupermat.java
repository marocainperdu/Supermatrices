package supermat;

/**
 * Classe de test pour la bibliothèque Supermatrice.
 * Équivalent de la fonction main() du code C.
 */
public class TestSupermat {
      public static void main(String[] args) {
        System.out.println("=== Test de la bibliothèque Supermatrice Java ===\n");
        
        // 1) Allocation et remplissage de la matrice a (3x4)
        System.out.println("1. Allocation de a (3x4)");
        Supermat a = new Supermat(3, 4);
        
        // Remplissage
        for (int i = 0; i < a.getNombreLignes(); i++) {
            for (int j = 0; j < a.getNombreColonnes(); j++) {
                a.set(i, j, i * 10 + j);
            }
        }
        a.afficher("a");
        
        // 2) Produit matriciel a × b (4x2)
        System.out.println("2. Produit a × b (4x2)");
        Supermat b = new Supermat(4, 2);
        for (int i = 0; i < b.getNombreLignes(); i++) {
            for (int j = 0; j < b.getNombreColonnes(); j++) {
                b.set(i, j, i + j * 5);
            }
        }
        b.afficher("b");
        
        Supermat c = Supermat.produit(a, b);
        if (c != null) {
            c.afficher("a × b");
        }
        
        // 3) Permutation de lignes
        System.out.println("3. Permutation des lignes 0 et 2 de a");
        a.afficher("avant permutation");
        a.permuterLignes(0, 2);
        a.afficher("après permutation");
        
        // 4) Sous-matrice
        System.out.println("4. Extraction de sous-matrice a[0..1][1..2]");
        Supermat sub = a.sousMatrice(0, 1, 1, 2);
        if (sub != null) {
            sub.afficher("sub");
            
            sub.set(0, 0, 777.0);
            System.out.println("Après modification de sub :");
            sub.afficher("sub modifiée");
            a.afficher("a (note: en Java, les vues sont indépendantes)");
        }
        
        // 5) Conversion tableau → supermatrice → tableau
        System.out.println("5. Conversion tableau → supermatrice → tableau");
        double[][] mat = {
            {1.1, 2.2, 3.3, 4.4, 5.5},
            {6.6, 7.7, 8.8, 9.9, 10.0},
            {11.1, 12.2, 13.3, 14.4, 15.5}
        };
        
        Supermat sm = SupermatUtils.matSupermat(mat);
        if (sm != null) {
            sm.afficher("sm");
            
            // Extraction d'une partie (3x4) dans un tableau plus grand (4x6)
            double[][] retour = new double[4][6];
            SupermatUtils.supermatMat(sm, retour);
            
            System.out.println("Tableau retour :");
            for (int i = 0; i < sm.getNombreLignes(); i++) {
                for (int j = 0; j < sm.getNombreColonnes(); j++) {
                    System.out.printf("%7.2f ", retour[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
        
        // 6) Test de contiguïté
        System.out.println("6. Contiguïté de a = " + a.contiguite());
        if (sub != null) {
            System.out.println("   Contiguïté de sub = " + sub.contiguite());
        }
        
        // 7) Tests supplémentaires spécifiques à Java
        System.out.println("7. Tests supplémentaires Java");
        
        // Test matrice identité
        Supermat identite = SupermatUtils.identite(3);
        if (identite != null) {
            identite.afficher("Matrice identité 3x3");
        }
        
        // Test remplissage aléatoire
        Supermat aleatoire = new Supermat(2, 3);
        SupermatUtils.remplirAleatoire(aleatoire, 0.0, 10.0);
        aleatoire.afficher("Matrice aléatoire");
        
        // Test transposée
        if (b != null) {
            Supermat transpose = SupermatUtils.transpose(b);
            if (transpose != null) {
                transpose.afficher("Transposée de b");
            }
        }
        
        // Test produit avec identité
        if (identite != null) {
            Supermat produitIdentite = Supermat.produit(a, SupermatUtils.identite(a.getNombreColonnes()));
            if (produitIdentite != null) {
                produitIdentite.afficher("a × I (doit être égal à a)");
            }
        }
        
        System.out.println("8. Informations sur les matrices");
        System.out.println("a: " + a.toString());
        if (b != null) System.out.println("b: " + b.toString());
        if (c != null) System.out.println("c: " + c.toString());
        if (sub != null) System.out.println("sub: " + sub.toString());
        
        System.out.println("\nFin des tests.");
    }
    
    /**
     * Méthode utilitaire pour tester les cas d'erreur.
     */
    public static void testerErreurs() {
        System.out.println("=== Test des cas d'erreur ===");
        
        // Test dimensions invalides
        System.out.println("Test: création avec dimensions invalides...");
        new Supermat(-1, 5);
        
        // Test accès hors limites
        System.out.println("Test: accès hors limites...");
        Supermat test = new Supermat(2, 2);
        test.get(3, 1);
        
        // Test produit incompatible
        System.out.println("Test: produit matriciel incompatible...");
        Supermat test1 = new Supermat(2, 3);
        Supermat test2 = new Supermat(4, 2);
        Supermat.produit(test1, test2);
        
        System.out.println("=== Fin des tests d'erreur ===\n");
    }
}
