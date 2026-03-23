flowchart TD
    %% Acteurs et API
    Client(["Client / CURL"])
    API_W["API POST/PUT/PATCH\n(Api/W)"]
    API_R["API GET\n(Api/R)"]

    %% Bases de données
    subgraph Databases
        DB_WRITE[("DB État / Validation\nPG SQL")]
        DB_READ[("DB Lecture\nVue dé-normalisée")]
    end

    %% Cluster Kafka
    subgraph Kafka_Cluster ["Kafka Cluster"]
        direction TB
        Broker1["Broker 1"]
        Broker2["Broker 2"]
        Broker3["Broker 3"]
        Topic["Topic: Commandes/Events"]
        
        %% Lien purement visuel pour montrer que les brokers hébergent le topic
        Broker1 ~~~ Topic
        Broker2 ~~~ Topic
        Broker3 ~~~ Topic
    end

    %% Composant de synchronisation
    SyncWorker[["Composant de\nSynchronisation"]]

    %% Flux (Connexions = -->, Messages = ==>)
    Client -->|"1. Connexion HTTP"| API_W
    API_W ==>|"2. Sens du message (Producer)"| Topic
    Topic ==>|"3. Sens du message (Consumer)"| SyncWorker
    
    SyncWorker -->|"4a. Connexion (SQL Update)"| DB_WRITE
    SyncWorker -->|"4b. Connexion (SQL Update)"| DB_READ

    Client -->|"Connexion HTTP"| API_R
    API_R -->|"Connexion (SQL Select)"| DB_READ