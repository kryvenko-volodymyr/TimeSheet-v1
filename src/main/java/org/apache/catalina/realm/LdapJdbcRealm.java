package org.apache.catalina.realm;


import org.apache.catalina.Realm;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;

import javax.naming.directory.DirContext;
import java.io.IOException;
import java.security.Principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * LdapJdbcRealm is a minimal implementation of a <b>Realm</b> to connect to LDAP
 * for authentication and a database for authorization.<br>
 * <br>
 * Example server.xml configuration fragment:<br>
 * <pre>
 * &lt;Realm className="org.apache.catalina.realm.LdapJdbcRealm"
 * connectionURL="ldap://ldaphost:389"
 * resourceName="LDAP Auth" driverName="oracle.jdbc.driver.OracleDriver"
 * userPattern="uid={0}, ou=Portal, dc=example, dc=com"
 * dbConnectionName="dbuser" dbConnectionPassword="dbpassword"
 * dbConnectionURL="jdbc:oracle:thin:@oracledb:1521:dbname"
 * userTable="users" userNameCol="user_id"
 * userRoleTable="user_role_xref" roleNameCol="role_id" /&gt;
 * </pre>
 *
 * @author Greg Chabala
 *         <p/>
 *         Created by IntelliJ IDEA.
 *         User: gchabala
 *         Date: Jul 14, 2009
 *         Time: 4:56:37 PM
 */
public class LdapJdbcRealm extends JNDIRealm implements Realm {
    /**
     * Encapsulated <b>JDBCRealm</b> to do role lookups
     */
    private JDBCRealm jdbcRealm = new JDBCRealm() {

        private boolean isRoleStoreDefined() {
            return userRoleTable != null || roleNameCol != null;
        }

        @Override
        protected ArrayList<String> getRoles(String username) {

            if (allRolesMode != AllRolesMode.STRICT_MODE && !isRoleStoreDefined()) {
                // Using an authentication only configuration and no role store has
                // been defined so don't spend cycles looking
                return null;
            }

            // Number of tries is the number of attempts to connect to the database
            // during this login attempt (if we need to open the database)
            // This needs rewritten wuth better pooling support, the existing code
            // needs signature changes since the Prepared statements needs cached
            // with the connections.
            // The code below will try twice if there is a SQLException so the
            // connection may try to be opened again. On normal conditions (including
            // invalid login - the above is only used once.
            int numberOfTries = 2;
            while (numberOfTries>0) {
                try {
                    // Ensure that we have an open database connection
                    open();

                    PreparedStatement stmt = roles(dbConnection, username);
                    try (ResultSet rs = stmt.executeQuery()) {
                        // Accumulate the user's roles
                        ArrayList<String> roleList = new ArrayList<>();

                        while (rs.next()) {
                            String role = rs.getString(1);
                            if (null!=role) {
                                roleList.add(role.trim());
                            }
                        }

                        return roleList;
                    } finally {
                        dbConnection.commit();
                    }
                } catch (SQLException e) {
                    // Log the problem for posterity
                    containerLog.error(sm.getString("jdbcRealm.exception"), e);

                    // Close the connection so that it gets reopened next time
                    if (dbConnection != null)
                        close(dbConnection);
                }

                numberOfTries--;
            }

            return null;
        }

        /*select title from role
        where id = (select role_id from  role_by_employee
        where employee_id = (select id from employee
        where account = ?));*/

        @Override
        protected synchronized PreparedStatement roles(Connection dbConnection,
                                                       String username)
                throws SQLException {

            if (preparedRoles == null) {
                StringBuilder sb = new StringBuilder("SELECT ");
                sb.append(roleNameCol);
                sb.append(" FROM ");
                sb.append(userRoleTable);
                sb.append(" where id = (select role_id from  role_by_employee " +
                        " where employee_id = (select id from employee " +
                        " where ");
                sb.append(userNameCol);
                sb.append(" = ?))");
                preparedRoles =
                        dbConnection.prepareStatement(sb.toString());
            }

            preparedRoles.setString(1, username);
            return (preparedRoles);

        }
    };

    /**
     * Descriptive information about this <b>Realm</b> implementation.
     */
    protected static final String info = "org.apache.catalina.realm.LdapJdbcRealm/1.0";

    /**
     * Descriptive information about this <b>Realm</b> implementation.
     */
    protected static final String name = "LdapJdbcRealm";

    /**
     * Set the all roles mode.
     *
     * @param allRolesMode authentication mode
     */
    public void setAllRolesMode(String allRolesMode) {
        super.setAllRolesMode(allRolesMode);
        jdbcRealm.setAllRolesMode(allRolesMode);
    }

    /**
     * Return the username to use to connect to the database.
     *
     * @return username
     * @see JDBCRealm#getConnectionName()
     */
    public String getDbConnectionName() {
        return jdbcRealm.getConnectionName();
    }

    /**
     * Set the username to use to connect to the database.
     *
     * @param dbConnectionName username
     * @see JDBCRealm#setConnectionName(String)
     */
    public void setDbConnectionName(String dbConnectionName) {
        jdbcRealm.setConnectionName(dbConnectionName);
    }

    /**
     * Return the password to use to connect to the database.
     *
     * @return password
     * @see JDBCRealm#getConnectionPassword()
     */
    public String getDbConnectionPassword() {
        return jdbcRealm.getConnectionPassword();
    }

    /**
     * Set the password to use to connect to the database.
     *
     * @param dbConnectionPassword password
     * @see JDBCRealm#setConnectionPassword(String)
     */
    public void setDbConnectionPassword(String dbConnectionPassword) {
        jdbcRealm.setConnectionPassword(dbConnectionPassword);
    }

    /**
     * Return the URL to use to connect to the database.
     *
     * @return database connection URL
     * @see JDBCRealm#getConnectionURL()
     */
    public String getDbConnectionURL() {
        return jdbcRealm.getConnectionURL();
    }

    /**
     * Set the URL to use to connect to the database.
     *
     * @param dbConnectionURL The new connection URL
     * @see JDBCRealm#setConnectionURL(String)
     */
    public void setDbConnectionURL(String dbConnectionURL) {
        jdbcRealm.setConnectionURL(dbConnectionURL);
    }

    /**
     * Return the JDBC driver that will be used.
     *
     * @return driver classname
     * @see JDBCRealm#getDriverName()
     */
    public String getDriverName() {
        return jdbcRealm.getDriverName();
    }

    /**
     * Set the JDBC driver that will be used.
     *
     * @param driverName The driver name
     * @see JDBCRealm#setDriverName(String)
     */
    public void setDriverName(String driverName) {
        jdbcRealm.setDriverName(driverName);
    }

    /**
     * Return the table that holds user data..
     *
     * @return table name
     * @see JDBCRealm#getUserTable()
     */
    public String getUserTable() {
        return jdbcRealm.getUserTable();
    }

    /**
     * Set the table that holds user data.
     *
     * @param userTable The table name
     * @see JDBCRealm#setUserTable(String)
     */
    public void setUserTable(String userTable) {
        jdbcRealm.setUserTable(userTable);
    }

    /**
     * Return the column in the user table that holds the user's name.
     *
     * @return username database column name
     * @see JDBCRealm#getUserNameCol()
     */
    public String getUserNameCol() {
        return jdbcRealm.getUserNameCol();
    }

    /**
     * Set the column in the user table that holds the user's name.
     *
     * @param userNameCol The column name
     * @see JDBCRealm#setUserNameCol(String)
     */
    public void setUserNameCol(String userNameCol) {
        jdbcRealm.setUserNameCol(userNameCol);
    }

    /**
     * Return the table that holds the relation between user's and roles.
     *
     * @return user role database table name
     * @see JDBCRealm#getUserRoleTable()
     */
    public String getUserRoleTable() {
        return jdbcRealm.getUserRoleTable();
    }

    /**
     * Set the table that holds the relation between user's and roles.
     *
     * @param userRoleTable The table name
     * @see JDBCRealm#setUserRoleTable(String)
     */
    public void setUserRoleTable(String userRoleTable) {
        jdbcRealm.setUserRoleTable(userRoleTable);
    }

    /**
     * Return the column in the user role table that names a role.
     *
     * @return role column name
     * @see JDBCRealm#getRoleNameCol()
     */
    public String getRoleNameCol() {
        return jdbcRealm.getRoleNameCol();
    }

    /**
     * Set the column in the user role table that names a role.
     *
     * @param roleNameCol The column name
     * @see JDBCRealm#setRoleNameCol(String)
     */
    public void setRoleNameCol(String roleNameCol) {
        jdbcRealm.setRoleNameCol(roleNameCol);
    }

    @Override
    public SecurityConstraint[] findSecurityConstraints(Request request, Context context) {
        return jdbcRealm.findSecurityConstraints(request, context);
    }

    @Override
    public boolean hasUserDataPermission(Request request, Response response,
                                         SecurityConstraint[] constraints) throws IOException {
        return jdbcRealm.hasUserDataPermission(request, response, constraints);
    }

    @Override
    public boolean hasResourcePermission(Request request, Response response,
                                         SecurityConstraint[] constraints,
                                         Context context) throws IOException {
        return jdbcRealm.hasResourcePermission(request, response, constraints, context);
    }

    @Override
    public boolean hasRole(Wrapper wrapper, Principal principal, String role) {
        return jdbcRealm.hasRole(wrapper, principal, role);
    }

    /**
     * Return a List of roles associated with the given User. If no roles
     * are associated with this user, a zero-length List is returned.
     *
     * @param context unused. JDBC does not need this field.
     * @param user    The User to be checked
     * @return list of role names
     * @see JNDIRealm#getRoles(DirContext, User)
     * @see JDBCRealm#getRoles(String)
     */
    @Override
    protected List<String> getRoles(DirContext context, User user) {
        return jdbcRealm.getRoles(user.getUserName());
    }
}