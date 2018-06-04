package d2si.apps.planetemobelio.controllers;

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

import d2si.apps.planetemobelio.data.AppUtils;

/**
 * Class that represents the database server controller
 *
 */
@RestController
public class ServerDBController {

	/**
	 * Method that check if server and database are accessible
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @return true if database server is accessible, false else
	 */
	@RequestMapping("/server")
	public boolean check(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(dbUser);
		String hashPass=dbPassword;
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
			logger.log(Level.INFO, "[SERVER][CHECK][REQUEST] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword);
			con = ds.getConnection();
			logger.log(Level.INFO, "[SERVER][CHECK][SUCCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword);
			return true;

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[SERVER][CHECK][ERROR] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", error - " + e.getMessage());
			return false;
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
	}
}
