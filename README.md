# Bibliothèque Supermatrices Java

Cette bibliothèque Java est une traduction orientée objet du code C original pour les supermatrices. Elle conserve la logique et les fonctionnalités du code C tout en adoptant les bonnes pratiques Java.

## Structure du projet

```
src/main/java/supermat/
├── Supermat.java           # Classe principale représentant une supermatrice
├── SupermatUtils.java      # Classe utilitaire pour les conversions et opérations
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

## Principales différences avec le code C

### Avantages Java
1. **Gestion automatique de la mémoire** - Plus de `malloc`/`free`
2. **Messages d'erreur simples** avec System.err.println
3. **Encapsulation** avec getters/setters
4. **Surcharge de méthodes** pour plus de flexibilité
5. **Type safety** avec le système de types Java

### Adaptations nécessaires
1. **Sous-matrices** : En Java, elles sont indépendantes (copie) plutôt que des vues partagées
2. **Contiguïté** : Concept moins pertinent en Java (tableaux toujours contigus)
3. **Pointeurs** : Remplacés par des références d'objets

## Compilation et exécution

### Avec les scripts fournis (Windows)
```bash
# Compilation uniquement
compile.bat

# Tests principaux uniquement
test.bat

# Compilation + tests + démonstration
run.bat
```

### Avec javac
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

Supermat c = Supermat.produit(a, b);  // c sera 3x2
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

La bibliothèque utilise des messages d'erreur simples via `System.err.println()` pour signaler :
- Dimensions invalides
- Indices hors limites  
- Incompatibilités de dimensions pour les opérations
- Matrices null

En cas d'erreur, les méthodes retournent `null` ou des valeurs par défaut et affichent un message d'erreur.

```java
Supermat a = new Supermat(-1, 5);  // Affiche une erreur et crée une matrice 1x1
double val = a.get(10, 10);        // Affiche une erreur et retourne 0.0
```

## Tests

- `TestSupermat.main()` : Reproduit fidèlement les tests du code C original
- `DemoSupermat.main()` : Démonstration étendue avec exemples pratiques
- Méthode `TestSupermat.testerErreurs()` : Tests des cas d'erreur avec messages simples
