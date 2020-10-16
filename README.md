# Java-C2-2020
Projet pour Java C2 2020

L’objectifprincipal est de créer une plate-forme qui aidera tous les autres groupes àfaire de bonnes documentations de leurs apis développées.


To launch the backend servers:

1 - JDK 11 must be installed

2 - Start the `apidoc-gateway-server-ws` project

3 - Start the `apidoc-auth-server-ws` project

4 - Start the `apidoc-action-server-ws` project

These services will be using the following ports respectively, 8080, 8081, 8082

To launch the frontend application:

1 - The latest NodeJS ad npm must be installed

2 - Open the `apidoc-frontend` directory in a terminal and run `npm install`

3 - To be able to authenticate with google you must export an environment variable using the following command `export GOOGLE_CLIENT_ID=< your google app client id>`

4 - Run `npm run start`

The application will be running on port 3000
