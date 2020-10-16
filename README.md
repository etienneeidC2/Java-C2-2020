# Java-C2-2020
Projet pour Java C2 2020

L’objectifprincipal est de créer une plate-forme qui aidera tous les autres groupes àfaire de bonnes documentations de leurs apis développées.


Pour lancer les serveurs backend:

1 - JDK 11 doit être installé

2 - Le serveur MySql doit être installé et en cours d'exécution

3 - Démarrez le projet `apidoc-gateway-server-ws`

4 - Démarrez le projet `apidoc-auth-server-ws`

5 - Démarrez le projet `apidoc-action-server-ws`

Ces services utiliseront respectivement les ports suivants, 8080, 8081, 8082


La base de données se compose de 2 tables:

1- la table `users`:

| Colonnes      | Type          | Description  |
| ------------- |:-------------:|:------------:|
| id     | int        |ID de utilisateur       |
| userId     | varchar        | ID généré aléatoirement de l'utilisateur       |
| firstName     | varchar        |Prénom de l'utilisateur       |
| lastName     | varchar        |Nom de famille de l'utilisateur       |
| email     | varchar        |email de l'utilisateur       |

2 - la table `apis`:

| Colonnes      | Type          | Description  |
| ------------- |:-------------:|:------------:|
| id     | int        |ID de l'api       |
| userId     | varchar        | ID généré aléatoirement du propriétaire de l'API       |
| name     | varchar        |nom de l'API       |
| method     | varchar        |méthode de l'API       |
| route     | varchar        |route de l'API       |
| description     | varchar        |description de l'API       |


Pour lancer l'application frontend:

1 - Les dernières versions de `NodeJS` et `npm` doivent être installées

2 - Ouvrez le répertoire `apidoc-frontend` dans un terminal et exécutez `npm install`

3 - Pour pouvoir vous authentifier avec google, vous devez exporter une variable d'environnement à l'aide de la commande suivante `export GOOGLE_CLIENT_ID = <your google app client id>`

4 - Exécutez `npm run start`

L'application fonctionnera sur le port 3000
