*Pour de marrer le projet backend voila les etapes a suivre:*

[0] Ouvrez un terminal et deplacez-vous dans le projet  big-data-training-prod
    `cd src/backend/scala/big-data-training-prod`
[1] Faire une installation des dependances maven
    `mvn clean install`
[2] Generer le point jar
    `mvn package`
[3] Compiler le projet scala en utilisant le compilateur scala (scalac) et non fsc
    `mvn scala:cc -Donce=true -Dfsc=false`
[4] Executer le projet scala en utilisant le laucher par defaut
    `mvn scala:run`
