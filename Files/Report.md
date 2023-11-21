Séparation des préoccupations : MVC divise l'application en trois composantes principales, ce qui facilite la maintenance et l'évolution du code. Le modèle gère les données et la logique métier, la vue s'occupe de l'affichage des données, et le contrôleur fait le lien entre le modèle et la vue.

Facilité de maintenance : Cette séparation permet aux développeurs de travailler sur des composantes individuelles sans affecter les autres. Par exemple, le design de l'interface utilisateur peut être modifié sans toucher à la logique métier.

Développement parallèle : Comme les composantes sont déconnectées, différentes équipes peuvent travailler en parallèle sur le modèle, les vues, et les contrôleurs, ce qui peut accélérer le processus de développement.

Réutilisabilité du code : Les modèles peuvent souvent être réutilisés à travers différentes vues, et les vues peuvent être réutilisées avec différents contrôleurs.

Facilité de test : La séparation claire entre la logique métier et l'interface utilisateur facilite le test unitaire et le débogage. Les modèles, vues, et contrôleurs peuvent être testés séparément.

Flexibilité dans la présentation des données : Comme la vue est séparée du modèle, la même donnée peut être présentée de différentes manières. C'est particulièrement utile pour les applications qui nécessitent différentes interfaces utilisateur.

Adaptabilité et évolutivité : Le modèle MVC permet une plus grande flexibilité pour évoluer et adapter l'application aux besoins changeants, sans nécessiter une refonte complète.

Support des interactions complexes : MVC peut gérer efficacement les interactions complexes entre l'interface utilisateur et la logique métier, ce qui est crucial pour les applications web modernes.


Pour les test unitaires, nous allons utiliser JUnit, ainsi que les librairies Mockito et JFixture.
Les deux dernières librairies vont être très utiles pour mocker les services que nous ne souhaitons pas tester