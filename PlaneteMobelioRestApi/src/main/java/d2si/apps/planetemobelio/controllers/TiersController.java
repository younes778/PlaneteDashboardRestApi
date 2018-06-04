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
import d2si.apps.planetemobelio.data.Tiers;

/**
 * Class that represents the tiers controller
 *
 */

@RestController
public class TiersController {
	ArrayList<Tiers> tiers;

	/**
	 * Method that get tiers from the distant server between two dates
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @param dateTo
	 *            end date
	 * @return a list of tiers from distant database between the two dates
	 */
	@RequestMapping("/tiersGet")
	public ArrayList<Tiers> get(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppUtils.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		tiers = new ArrayList<>();

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
			logger.log(Level.INFO, "[TIERS][GET][REQUEST] : server - " + url + ", database - " + dbName + ", from - "
					+ dateFrom + ", to - " + dateTo);
			con = ds.getConnection();
			logger.log(Level.INFO, "[TIERS][GET][CONNECTION SUCESS] : server - " + url + ", database - " + dbName
					+ ", from - " + dateFrom + ", to - " + dateTo);
			String tiers_request = " select pcf_code, pcf_type, pcf_rs, pcf_rue, pcf_comp, pcf_cp, pcf_ville, pay_code, pcf_tel1, "
					+ " pcf_tel2, pcf_fax, pcf_email, pcf_url, f.fat_lib from tiers t left join tiers_fam f on t.fat_code = f.fat_code where pcf_code in( "
					+ " select pcf_code from documents " + " where doc_type in ('V','A') and doc_date between '"
					+ dateFrom + "' and '" + dateTo + "' )";

			stmt = con.createStatement();
			rs = stmt.executeQuery(tiers_request);
			while (rs.next()) {
				String fam = rs.getString(AppUtils.COLUMN_PCF_FAM);
				if (fam == null)
					fam = "";
				tiers.add(new Tiers(rs.getString(AppUtils.COLUMN_PCF_CODE), rs.getString(AppUtils.COLUMN_PCF_TYPE),
						rs.getString(AppUtils.COLUMN_PCF_RS), rs.getString(AppUtils.COLUMN_PCF_RUE),
						rs.getString(AppUtils.COLUMN_PCF_COMP), rs.getString(AppUtils.COLUMN_PCF_CP),
						rs.getString(AppUtils.COLUMN_PCF_VILLE), rs.getString(AppUtils.COLUMN_PAY_CODE),
						rs.getString(AppUtils.COLUMN_PCF_TEL1), rs.getString(AppUtils.COLUMN_PCF_TEL2),
						rs.getString(AppUtils.COLUMN_PCF_FAX), rs.getString(AppUtils.COLUMN_PCF_EMAIL),
						rs.getString(AppUtils.COLUMN_PCF_URL), fam));
			}
			rs.close();

			logger.log(Level.INFO, "[TIERS][GET][SUCESS] : server - " + url + ", database - " + dbName + ", from - "
					+ dateFrom + ", to - " + dateTo);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[TIERS][GET][ERROR] : server - " + url + ", database - " + dbName + ", from - "
					+ dateFrom + ", to - " + dateTo + ", error - " + e.getMessage());
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
		return tiers;
	}

	/**
	 * Method that update tiers from the distant server starting from a date
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @return a list of tiers from distant database starting from date
	 */

	@RequestMapping("/tiersUpdate")
	public ArrayList<Tiers> update(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		tiers = new ArrayList<>();

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
			logger.log(Level.INFO, "[TIERS][UPDATE][REQUEST] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			con = ds.getConnection();
			logger.log(Level.INFO, "[TIERS][UPDATE][CONNECTION SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			String tiers_request = " select pcf_code, pcf_type, pcf_rs, pcf_rue, pcf_comp, pcf_cp, pcf_ville, pay_code, pcf_tel1, "
					+ " pcf_tel2, pcf_fax, pcf_email, pcf_url, f.fat_lib from tiers t left join tiers_fam f on t.fat_code = f.fat_code where pcf_dtmaj > ' "
					+ dateFrom + " ' ";

			stmt = con.createStatement();
			rs = stmt.executeQuery(tiers_request);
			while (rs.next()) {
				String fam = rs.getString(AppUtils.COLUMN_PCF_FAM);
				if (fam == null)
					fam = "";
				tiers.add(new Tiers(rs.getString(AppUtils.COLUMN_PCF_CODE), rs.getString(AppUtils.COLUMN_PCF_TYPE),
						rs.getString(AppUtils.COLUMN_PCF_RS), rs.getString(AppUtils.COLUMN_PCF_RUE),
						rs.getString(AppUtils.COLUMN_PCF_COMP), rs.getString(AppUtils.COLUMN_PCF_CP),
						rs.getString(AppUtils.COLUMN_PCF_VILLE), rs.getString(AppUtils.COLUMN_PAY_CODE),
						rs.getString(AppUtils.COLUMN_PCF_TEL1), rs.getString(AppUtils.COLUMN_PCF_TEL2),
						rs.getString(AppUtils.COLUMN_PCF_FAX), rs.getString(AppUtils.COLUMN_PCF_EMAIL),
						rs.getString(AppUtils.COLUMN_PCF_URL), fam));
			}
			rs.close();

			logger.log(Level.INFO, "[TIERS][UPDATE][SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[TIERS][UPDATE][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
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
		return tiers;
	}
}
