package d2si.apps.planetedashboard.controllers;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import d2si.apps.planetedashboard.data.AppUtils;

/**
 * Class that represents the user controller
 *
 */
@RestController
public class UserController {

	/**
	 * Method that check if user with password exist in database dbName in the
	 * distant server url
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param user
	 *            start date
	 * @param password
	 *            end date
	 * @return true if user and password are correct, false else
	 */
	@RequestMapping("/user")
	public boolean check(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_USER, defaultValue = "") String user,
			@RequestParam(value = AppUtils.FIELD_PASSWORD, defaultValue = "") String password) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(dbUser);
		String hashPass = dbPassword;
		try {
			hashPass = AppUtils.decrypt(URLDecoder.decode(dbPassword, "UTF-8").replace("\n", ""));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ds.setPassword(hashPass);
		ds.setServerName(url);
		ds.setPortNumber(AppUtils.DB_SERVER_PORT);
		ds.setDatabaseName(dbName);

		Logger logger = Logger.getLogger(AppUtils.APP_LOGGER);

		try {
			logger.log(Level.INFO, "[USER][CHECK][REQUEST] : server - " + url + ", database - " + dbName + ", dbUser - "
					+ dbUser + ", dbPassword - " + dbPassword + ", user - " + user + ", password - " + password);
			con = ds.getConnection();
			logger.log(Level.INFO,
					"[USER][CHECK][CONNECTION SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - "
							+ dbUser + ", dbPassword - " + dbPassword + ", user - " + user + ", password - "
							+ password);
			String user_request = "select *" + " from users" + " where usr_login= '" + user + "' and usr_passwd='"
					+ URLDecoder.decode(password, "UTF-8").replace("\n", "") + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(user_request);
			logger.log(Level.INFO, "[USER][CHECK][SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - "
					+ dbUser + ", dbPassword - " + dbPassword + ", user - " + user + ", password - " + password);
			return rs.next();

		} catch (Exception e) {
			logger.log(Level.INFO,
					"[USER][CHECK][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", user - " + user + ", password - " + password
							+ ", error - " + e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return false;
	}
}
