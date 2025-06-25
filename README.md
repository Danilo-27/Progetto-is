Gestione Eventi

Progetto di Ingegneria del Software – a.a. 2024/2025
Corso di Laurea in Ingegneria Informatica
Docenti: Anna Rita Fasolino, Marco De Luca
Autori: Francesco Ardolino, Paolo Altucci, Danilo Cioffi

📌 Descrizione

Gestione Eventi è un’applicazione web progettata per la gestione completa di eventi pubblici, dalla creazione alla partecipazione, con funzionalità dedicate sia ad utenti finali (clienti) sia ad amministratori di sistema.

Il sistema gestisce:
	•	La registrazione e l’autenticazione degli utenti
	•	La creazione e pubblicazione di eventi
	•	La consultazione e ricerca di eventi
	•	L’acquisto e la validazione di biglietti elettronici
	•	Il controllo accessi il giorno dell’evento
	•	Il monitoraggio in tempo reale della partecipazione

⚙️ Funzionalità principali

Per gli utenti
	•	Registrazione con nome, cognome, email, password
	•	Login sicuro
	•	Consultazione del catalogo eventi con filtri per data e luogo
	•	Acquisto biglietto con generazione codice univoco
	•	Download e visualizzazione dei biglietti
	•	Storico eventi e partecipazioni

Per gli amministratori
	•	Pubblicazione eventi con dettagli (titolo, descrizione, luogo, capienza, costo)
	•	Visualizzazione partecipanti per eventi attivi
	•	Statistiche su eventi passati (numero di partecipanti)

Architettura

Package principali
	•	boundary/ – Interfacce utente (Swing)
	•	database/ – DAO e gestione connessioni
	•	entity/ – Entità di dominio (Utente, Evento, Biglietto, etc.)
	•	exceptions/ – Gestione errori e validazioni
	•	DTO/ – Data Transfer Object
	•	external/ – Interfaccia e stub per pagamento esterno

Tecnologie utilizzate
	•	Java (JDK 21.0.7)
	•	Swing (UI)
	•	MySQL 8.0.33
	•	MySQL Connector: mysql-connector-j-8.0.33.jar
	•	SwingX: swingx-1.6.1

📁 Documentazione
	•	Diagrammi UML (Casi d’uso, Classi, Sequenza)
	•	Piano di test funzionale completo
	•	Javadoc disponibile nella cartella /javadoc

▶️ Avvio del progetto

Prerequisiti
	•	Java 21+
	•	MySQL Server 8.0+
	•	Import delle dipendenze (mysql-connector, swingx)
	•	Database inizializzato secondo lo schema fornito in database/

Esecuzione
	1.	Compilare il progetto con un IDE Java (es. IntelliJ o Eclipse)
	2.	Configurare correttamente il file di connessione al DB (DBConnectionManager)
	3.	Avviare la classe principale dell’interfaccia (HomePage)
