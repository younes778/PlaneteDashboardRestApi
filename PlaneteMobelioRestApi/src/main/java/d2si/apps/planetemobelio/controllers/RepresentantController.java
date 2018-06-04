package d2si.apps.planetemobelio.controllers;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import d2si.apps.planetemobelio.data.AppUtils;
import d2si.apps.planetemobelio.data.Representant;

/**
 * Class that represents the representants controller
 *
 */
@RestController
public class RepresentantController {
	ArrayList<Representant> representants;

	/**
	 * Method that get representatns from the distant server between two dates
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @param dateTo
	 *            end date
	 * @return a list of representants from distant database between the two dates
	 */
	@RequestMapping("/representantsGet")
	public ArrayList<Representant> get(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppUtils.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		representants = new ArrayList<>();

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
			logger.log(Level.INFO,
					"[REPRESENTANT][GET][REQUEST] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);
			con = ds.getConnection();
			logger.log(Level.INFO,
					"[REPRESENTANT][GET][CONNECTION SUCESS] : server - " + url + ", database - " + dbName
							+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom
							+ ", to - " + dateTo);
			String personnels_request = " select rep_code, rep_nom, rep_prenom from representants where rep_code in( "
					+ " select distinct rep_code from documents  "
					+ " where doc_type in ('V','A') and doc_date between '" + dateFrom + "' and '" + dateTo + "' )";

			stmt = con.createStatement();
			rs = stmt.executeQuery(personnels_request);
			while (rs.next()) {
				representants.add(new Representant(rs.getString(AppUtils.COLUMN_REP_CODE),
						rs.getString(AppUtils.COLUMN_REP_NOM), rs.getString(AppUtils.COLUMN_REP_PRENOM)));
			}
			rs.close();

			logger.log(Level.INFO,
					"[REPRESENTANT][GET][SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[REPRESENTANT][GET][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo
							+ ", error - " + e.getMessage());
			return null;
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
		return representants;
	}

	/**
	 * Method that update representants from the distant server starting from a date
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @return a list of representants from distant database starting from date
	 */
	@RequestMapping("/representantsUpdate")
	public ArrayList<Representant> update(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		representants = new ArrayList<>();

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
			logger.log(Level.INFO, "[REPRESENTANT][UPDATE][REQUEST] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			con = ds.getConnection();
			logger.log(Level.INFO, "[REPRESENTANT][UPDATE][CONNECTION SUCESS] : server - " + url + ", database - "
					+ dbName + ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			String personnels_request = " select rep_code, rep_nom, rep_prenom from representants where rep_dtmaj > ' "
					+ dateFrom + " ' ";

			stmt = con.createStatement();
			rs = stmt.executeQuery(personnels_request);
			while (rs.next()) {
				representants.add(new Representant(rs.getString(AppUtils.COLUMN_REP_CODE),
						rs.getString(AppUtils.COLUMN_REP_NOM), rs.getString(AppUtils.COLUMN_REP_PRENOM)));
			}
			rs.close();

			logger.log(Level.INFO, "[REPRESENTANT][UPDATE][SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[REPRESENTANT][UPDATE][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - "
							+ dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", error - "
							+ e.getMessage());
			return null;
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
		return representants;
	}
}
