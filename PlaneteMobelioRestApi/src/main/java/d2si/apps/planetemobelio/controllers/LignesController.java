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
import d2si.apps.planetemobelio.data.Ligne;

/**
 * Class that represents the lignes controller
 *
 */

@RestController
public class LignesController {
	ArrayList<Ligne> lignes;

	/**
	 * Method that get lignes from the distant server between two dates
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @param dateTo
	 *            end date
	 * @return a list of lignes from distant database between the two dates
	 */
	@RequestMapping("/lignesGet")
	public ArrayList<Ligne> get(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppUtils.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		lignes = new ArrayList<>();

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
			logger.log(Level.INFO, "[LIGNES][GET][REQUEST] : server - " + url + ", database - " + dbName + ", dbUser - "
					+ dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);
			con = ds.getConnection();
			logger.log(Level.INFO,
					"[LIGNES][GET][CONNECTION SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - "
							+ dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);
			String lignes_request = "select doc_numero, lig_numero, art_code, lig_qte, lig_p_net from lignes "
					+ " where art_code <> '' and doc_numero in(" + " select doc_numero from documents"
					+ " where doc_type in ('V','A') and doc_date between '" + dateFrom + "' and '" + dateTo + "' )";

			stmt = con.createStatement();
			rs = stmt.executeQuery(lignes_request);
			while (rs.next()) {
				lignes.add(
						new Ligne(
								rs.getString(AppUtils.COLUMN_LIG_NUMERO) + "_"
										+ rs.getString(AppUtils.COLUMN_DOC_NUMERO),
								rs.getInt(AppUtils.COLUMN_LIG_QTE), rs.getFloat(AppUtils.COLUMN_LIG_P_NET),
								rs.getString(AppUtils.COLUMN_ART_CODE)));
			}
			rs.close();

			logger.log(Level.INFO, "[LIGNES][GET][SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - "
					+ dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[LIGNES][GET][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
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
		return lignes;
	}

	/**
	 * Method that update lignes from the distant server starting from a date
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @return a list of lignes from distant database starting from date
	 */
	@RequestMapping("/lignesUpdate")
	public ArrayList<Ligne> update(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		lignes = new ArrayList<>();

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
			logger.log(Level.INFO, "[LIGNES][UPDATE][REQUEST] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			con = ds.getConnection();
			logger.log(Level.INFO, "[LIGNES][UPDATE][CONNECTION SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			String lignes_request = "select doc_numero, lig_numero, art_code, lig_qte, lig_p_net from lignes "
					+ " where art_code <> '' and doc_numero in(" + " select doc_numero from documents"
					+ " where doc_type in ('V','A') and doc_dtmaj > ' " + dateFrom + " ' )";

			stmt = con.createStatement();
			rs = stmt.executeQuery(lignes_request);
			while (rs.next()) {
				lignes.add(
						new Ligne(
								rs.getString(AppUtils.COLUMN_LIG_NUMERO) + "_"
										+ rs.getString(AppUtils.COLUMN_DOC_NUMERO),
								rs.getInt(AppUtils.COLUMN_LIG_QTE), rs.getFloat(AppUtils.COLUMN_LIG_P_NET),
								rs.getString(AppUtils.COLUMN_ART_CODE)));
			}
			rs.close();

			logger.log(Level.INFO, "[LIGNES][UPDATE][SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[LIGNES][UPDATE][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", error - " + e.getMessage());
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
		return lignes;
	}

}
