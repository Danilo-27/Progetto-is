package database;

import java.sql.*;

/**
 * Classe responsabile della gestione delle connessioni al database.
 * Fornisce metodi utili per la creazione, chiusura delle connessioni
 * e l'esecuzione di query SQL sul database.
 */
public class DBConnectionManager {
    /**
     * URL di base per la connessione al database MySQL.
     * Specifica il protocollo, l'host e la porta utilizzati per accedere
     * al database.
     */
    public static final String URL = "jdbc:mysql://localhost:3306/";
    /**
     * Nome del database al quale la classe si connette.
     * Utilizzato per costruire l'URL della connessione al database.
     */
    public static final String DB_NAME = "ticketwo";
    /**
     * Costante che specifica il nome completo della classe driver JDBC da utilizzare
     * per la connessione a un database MySQL.
     */
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * Rappresenta il nome utente predefinito utilizzato per stabilire la connessione al database.
     */
    public static final String USER_NAME = "root";
    /**
     * Costante che rappresenta la password utilizzata per l'autenticazione
     * nella connessione al database.
     */
    public static final String PASSWORD = "admin";


    /**
     * Metodo privato costruttore per la classe DBConnectionManager.
     * Impedisce l'instanziazione diretta della classe poiché tutte le sue funzionalità
     * sono fornite attraverso metodi statici.
     */
    private DBConnectionManager(){}

    /**
     * Crea e restituisce un'istanza della connessione al database utilizzando i parametri
     * predefiniti per URL, nome utente, password e driver JDBC.
     *
     * @return Un'istanza di {@link Connection} che rappresenta la connessione attiva al database.
     * @throws ClassNotFoundException Se il driver JDBC specificato non viene trovato.
     * @throws SQLException Se si verifica un errore durante la connessione al database.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL + DB_NAME, USER_NAME, PASSWORD);
        return conn;
    }
    /**
     * Chiude la connessione al database specificata, liberando le risorse associate.
     * Questo metodo deve essere chiamato per garantire il rilascio corretto della connessione.
     *
     * @param c la connessione al database che deve essere chiusa. Non deve essere null.
     * @throws SQLException se si verifica un errore durante la chiusura della connessione.
     */
    //prova
    public static void closeConnection(Connection c) throws SQLException {
        c.close();
    }

    /**
     * Esegue una query SQL di tipo SELECT sul database e restituisce il risultato sotto forma di ResultSet.
     *
     * @param query Il comando SQL di tipo SELECT da eseguire sul database.
     * @return Un oggetto ResultSet contenente i risultati della query eseguita.
     * @throws ClassNotFoundException Se il driver JDBC non viene trovato.
     * @throws SQLException Se si verifica un errore nell'esecuzione della query o nella connessione al database.
     */
    public static ResultSet selectQuery(String query) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        Statement statment = conn.createStatement();
        return statment.executeQuery(query);
    }

    /**
     * Esegue un'operazione di aggiornamento (INSERT, UPDATE, DELETE) sul database
     * utilizzando la query SQL specificata. Stabilisce una connessione al database,
     * esegue la query e chiude la connessione.
     *
     * @param query la stringa SQL da eseguire come operazione di aggiornamento
     * @throws ClassNotFoundException se la classe del driver JDBC non viene trovata
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la gestione della connessione
     */
    public static void updateQuery(String query) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
        conn.close();
    }

    /**
     * Esegue un'operazione di aggiornamento su un database utilizzando una query SQL
     * e restituisce la chiave generata automaticamente dall'operazione.
     *
     * @param query La stringa SQL dell'operazione da eseguire. Deve essere un'istruzione
     *              che supporta la generazione di chiavi, ad esempio un'operazione INSERT.
     * @return La chiave generata automaticamente (generalmente un ID incrementale)
     *         come valore Integer, oppure null se non è stata generata alcuna chiave.
     * @throws ClassNotFoundException Se la classe del driver JDBC specificato non è trovata.
     * @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    public static Integer updateQueryReturnGeneratedKey(String query) throws ClassNotFoundException, SQLException {
        Integer ret = null;
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate(query, 1);
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            ret = rs.getInt(1);
        }

        conn.close();
        return ret;
    }
}