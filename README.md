# Vendure CLI

CLI Java pour interagir avec l'API GraphQL Shop de Vendure.

## Contexte :

Ce projet a été réalisé dans le cadre du cours de Génie logiciel (SP26) à l'Université de Fribourg.

## Fonctionnalités :

- Récupérer la liste des produits depuis un serveur Vendure.
- Afficher les produits en table ou en JSON.
- Définir des requêtes GraphQL sous forme de classes Java typées.
- Tester la génération des requêtes et le parsing JSON sans lancer Vendure.

## Prérequis :

- Java 17 ou plus récent.
- Maven pour lancer les tests.
- Node.js et npm pour lancer un serveur Vendure local.
- Un serveur Vendure disponible, par exemple sur `http://localhost:3000/shop-api`.

## Correspondance avec les consignes :

- a) Conception : `GraphQlQuery<T>` définit le contrat des requêtes typées, et `GraphQlClient` exécute les requêtes HTTP.
- b) Implémentation : `ProductsQuery` récupère la liste des produits, `ProductDetailQuery` récupère le détail d'un produit, et `ProductService` expose ces opérations.
- c) Tests : `ProductsQueryTest` et `ProductDetailQueryTest` vérifient la génération GraphQL et le parsing JSON. `GraphQlClientTest` teste l'appel HTTP sans serveur Vendure réel.
- d) Intégration CLI : `ListCommand` utilise `ProductService` pour afficher les vrais produits depuis l'endpoint Vendure configuré avec `--url`.

## Conception :

Le projet contient une petite abstraction pour définir des requêtes GraphQL typées :

```java
public interface GraphQlQuery<T> {
  String toGraphQl();

  T parse(JsonNode data);
}
```

Chaque requête GraphQL implémente cette interface :

- `ProductsQuery` retourne une `List<Product>`.
- `ProductDetailQuery` retourne un `Product`.

Le `GraphQlClient` est responsable de l'appel HTTP vers Vendure. Il envoie une requête `POST` avec un payload JSON contenant le champ `"query"`, puis délègue la conversion de la réponse JSON à la requête Java.

Cette séparation permet d'ajouter une nouvelle requête GraphQL en créant une nouvelle classe Java, sans modifier le client HTTP.

## Structure du projet :

```text
src/main/java/cli
|-- Main.java
|-- ListCommand.java
|-- Product.java
|-- ProductService.java
|-- graphql
|   |-- GraphQlClient.java
|   |-- GraphQlException.java
|   `-- GraphQlQuery.java
`-- vendure
    |-- ProductDetailQuery.java
    `-- ProductsQuery.java
```

Les tests se trouvent dans `src/test/java` et suivent la même organisation de packages.

## Lancer les tests :

```bash
mvn test
```

Les tests unitaires ne dépendent pas d'un serveur Vendure réel.

## Lancer Vendure :

Le serveur Vendure doit être lancé séparément du projet Java. Le projet Vendure n'est pas inclus dans ce repository.

Exemple avec un projet Vendure créé dans le dossier voisin `vendure-server` :

```bash
cd ../vendure-server
npm run dev:server -w server
```

L'API Shop est ensuite disponible sur :

```text
http://localhost:3000/shop-api
```

## Lancer la CLI :

Depuis IntelliJ, lancer la classe `cli.Main` avec les arguments suivants :

```text
--url http://localhost:3000/shop-api list --format json
```

Pour l'affichage table :

```text
--url http://localhost:3000/shop-api list --format table
```

Le format table est aussi le format par défaut :

```text
--url http://localhost:3000/shop-api list
```

## Scénario de test utilisateur :

1. Lancer le serveur Vendure.
2. Lancer la CLI avec l'URL de l'API Shop.
3. Vérifier que la commande `list` affiche les produits réels de Vendure.

Exemple :

```text
--url http://localhost:3000/shop-api list --format json
```

## Commandes disponibles :

La CLI expose actuellement la sous-commande suivante :

```text
list
```

Options utiles :

- `--url` : URL de l'API Shop Vendure, par exemple `http://localhost:3000/shop-api`.
- `--format` : format d'affichage, soit `table`, soit `json`.

## Exemple de sortie :

```json
[
  {"name": "Laptop", "price": 129900},
  {"name": "Tablet", "price": 32900}
]
```

## URL Vendure :

L'URL peut être donnée avec l'option `--url` :

```text
--url http://localhost:3000/shop-api
```

Elle peut aussi être fournie avec la variable d'environnement `URL`.

Si aucune URL n'est fournie, la commande affiche une erreur explicite.

## Choix d'implémentation :

- Le client n'est pas un client GraphQL généraliste complet.
- Chaque requête GraphQL est représentée par une classe Java dédiée.
- Seuls les champs nécessaires au devoir sont récupérés : le nom du produit et le prix de la première variante.
- Les tests de transformation utilisent du JSON local et ne dépendent pas d'un serveur Vendure.
- Le serveur Vendure est lancé séparément, ce qui garde le projet Java concentré sur la CLI.

## Intégration continue :

Le repository contient une configuration GitHub Actions qui lance :

- les tests Maven avec `mvn test`
- la vérification du formatage avec `google-java-format`
