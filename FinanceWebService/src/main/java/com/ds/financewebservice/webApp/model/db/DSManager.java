package com.ds.financewebservice.webApp.model.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.DriverManager;

/**
 * DS manager. Works with MongoDB.
 * Only the required DAO methods are defined!
 *
 */
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class DSManager {
    private static final Logger log = LoggerFactory.getLogger(DSManager.class);


    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DSManager instance;

    public static synchronized DSManager getInstance() {
        if (instance == null)
            instance = new DSManager();
        return instance;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");

            // mongodb/MyMongoClient - the name of data source
            DataSource ds = (DataSource)envContext.lookup("jdbc/mongodb");
            con = ds.getConnection();
        } catch (NamingException ex) {
            log.error("Cannot obtain a connection from the pool", ex);
        }
        return con;
    }

    private DSManager() {

    }


    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Commits and close the given connection.
     *
     * @param con
     *            Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con
     *            Connection to be rollback and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
