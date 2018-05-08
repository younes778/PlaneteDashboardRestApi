package d2si.apps.planetedashboard.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import d2si.apps.planetedashboard.data.AppData;

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
	public boolean user(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DB_USER, defaultValue = "") String user,
			@RequestParam(value = AppData.FIELD_DB_PASSWORD, defaultValue = "") String password) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(AppData.DB_USER);
		ds.setPassword(AppData.DB_PASSWORD);
		ds.setServerName(url);
		ds.setPortNumber(AppData.DB_SERVER_PORT);
		ds.setDatabaseName(dbName);

		try {
			System.out.println("Connecting to SQL Server ... ");
			con = ds.getConnection();
			System.out.println("Connected");
			String user_request = "select *" + " from users" + " where usr_login= '" + user + "' and usr_passwd='"
					+ password + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(user_request);
			return rs.next();

		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
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
