# Bibliothèque Supermatrices Java

Cette bibliothèque Java est une traduction orientée objet du code C original pour les supermatrices. Elle conserve la logique et les fonctionnalités du code C tout en adoptant les bonnes pratiques Java.

## Structure du projet

```
src/main/java/supermat/
├── Supermat.java           # Classe principale représentant une supermatrice
├── SupermatUtils.java      # Classe utilitaire pour les conversions et opérations
├── SupermatException.java  # Exception personnalisée
├── TestSupermat.java      # Tests reproduisant le main() du code C
└── DemoSupermat.java      # Démonstration complète des fonctionnalités
```

## Classes principales

### `Supermat`
Classe principale représentant une supermatrice avec :
- **Attributs** : `nl` (nombre de lignes), `nc` (nombre de colonnes), `ligne` (données)
- **Constructeurs** : Allocation de nouvelles matrices
- **Méthodes d'accès** : `get(i,j)`, `set(i,j,val)`
- **Opérations** : `produit()`, `permuterLignes()`, `sousMatrice()`
- **Affichage** : `afficher()`, `toString()`

### `SupermatUtils`
Classe utilitaire contenant :
- Conversions tableau ↔ supermatrice
- Création de matrices spéciales (identité, aléatoire)
- Opérations avancées (transposée)

### `SupermatException`
Exception personnalisée pour la gestion d'erreurs spécifiques aux supermatrices.

## Principales différences avec le code C

### Avantages Java
1. **Gestion automatique de la mémoire** - Plus de `malloc`/`free`
2. **Exceptions** pour la gestion d'erreurs robuste
3. **Encapsulation** avec getters/setters
4. **Surcharge de méthodes** pour plus de flexibilité
5. **Type safety** avec le système de types Java

### Adaptations nécessaires
1. **Sous-matrices** : En Java, elles sont indépendantes (copie) plutôt que des vues partagées
2. **Contiguïté** : Concept moins pertinent en Java (tableaux toujours contigus)
3. **Pointeurs** : Remplacés par des références d'objets

## Compilation et exécution

### Avec Maven
```bash
# Compilation
mvn compile

# Exécution des tests principaux
mvn exec:java

# Ou spécifier une classe particulière
mvn exec:java -Dexec.mainClass="supermat.DemoSupermat"
```

### Avec javac direct
```bash
# Compilation
javac -d bin src/main/java/supermat/*.java

# Exécution
java -cp bin supermat.TestSupermat
java -cp bin supermat.DemoSupermat
```

## Exemples d'utilisation

### Création et manipulation de base
```java
// Création d'une matrice 3x4
Supermat a = new Supermat(3, 4);

// Remplissage
for (int i = 0; i < a.getNombreLignes(); i++) {
    for (int j = 0; j < a.getNombreColonnes(); j++) {
        a.set(i, j, i * 10 + j);
    }
}

// Affichage
a.afficher("Ma matrice");
```

### Produit matriciel
```java
Supermat a = new Supermat(3, 4);
Supermat b = new Supermat(4, 2);
// ... remplissage ...

Supermat c = a.produit(b);  // c sera 3x2
c.afficher("Résultat");
```

### Sous-matrice
```java
Supermat sub = a.sousMatrice(0, 1, 1, 2);  // Lignes 0-1, colonnes 1-2
sub.afficher("Sous-matrice");
```

### Conversions
```java
// Depuis tableau 2D
double[][] data = {{1,2,3}, {4,5,6}};
Supermat sm = SupermatUtils.matSupermat(data);

// Vers tableau 2D
double[][] result = new double[2][3];
SupermatUtils.supermatMat(sm, result);
```

## Gestion d'erreurs

La bibliothèque utilise `SupermatException` pour signaler :
- Dimensions invalides
- Indices hors limites  
- Incompatibilités de dimensions pour les opérations
- Matrices null

```java
try {
    Supermat a = new Supermat(-1, 5);  // Dimensions invalides
} catch (SupermatException e) {
    System.err.println("Erreur: " + e.getMessage());
}
```

## Tests

- `TestSupermat.main()` : Reproduit fidèlement les tests du code C original
- `DemoSupermat.main()` : Démonstration étendue avec exemples pratiques
- Méthode `TestSupermat.testerErreurs()` : Tests des cas d'erreur

## Fidélité au code C original

Cette implémentation Java conserve :
- ✅ Toutes les fonctionnalités du code C
- ✅ La logique des algorithmes (produit matriciel, permutations, etc.)
- ✅ Les mêmes tests et résultats attendus
- ✅ La structure générale (allocation, opérations, libération → constructeur, méthodes, GC)

Adaptations Java idiomatiques :
- ✅ Exceptions au lieu de codes d'erreur
- ✅ Encapsulation avec méthodes d'accès
- ✅ Gestion automatique de la mémoire
- ✅ Documentation Javadoc
- ✅ Respect des conventions de nommage Java
