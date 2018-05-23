package d2si.apps.planetedashboard.controllers;

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

import d2si.apps.planetedashboard.data.AppData;
import d2si.apps.planetedashboard.data.Document;

/**
 * Class that represents the sales controller
 *
 */
@RestController
public class SalesController {
	ArrayList<Document> sales;

	/**
	 * Method that get sales from the distant server between two dates
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @param dateTo
	 *            end date
	 * @return a list of sales from distant database between the two dates
	 */
	@RequestMapping("/salesGet")
	public ArrayList<Document> get(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppData.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		sales = new ArrayList<>();

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(AppData.DB_USER);
		ds.setPassword(AppData.DB_PASSWORD);
		ds.setServerName(url);
		ds.setPortNumber(AppData.DB_SERVER_PORT);
		ds.setDatabaseName(dbName);

		Logger logger = Logger.getLogger(AppData.APP_LOGGER);

		try {
			logger.log(Level.INFO,"[DOCUMENT][GET][REQUEST] : server - "+url+", database - "+dbName+", from - "+dateFrom+", to - "+dateTo);
			con = ds.getConnection();
			logger.log(Level.INFO,"[DOCUMENT][GET][CONNECTION SUCESS] : server - "+url+", database - "+dbName+", from - "+dateFrom+", to - "+dateTo);
			String sales_positive_request = "select doc_numero, doc_date, pcf_code,rep_code"
					+ " from documents where doc_type = 'V' and ( (doc_stype = 'B' and doc_etat <> 'S') or doc_stype= 'F')   and doc_date between '"
					+ dateFrom + "' and '" + dateTo + "'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sales_positive_request);
			while (rs.next()) {
				sales.add(
						new Document(rs.getString(AppData.COLUMN_DOC_NUMERO), "VP", rs.getDate(AppData.COLUMN_DOC_DATE),
								rs.getString(AppData.COLUMN_PCF_CODE), rs.getString(AppData.COLUMN_REP_CODE)));
			}
			rs.close();

			String sales_negative_request = "select doc_numero, doc_date, pcf_code,rep_code"
					+ " from documents where doc_type = 'V' and ( (doc_stype = 'R' and doc_etat <> 'S') or doc_stype= 'A')   and doc_date between '"
					+ dateFrom + "' and '" + dateTo + "'";
			rs = stmt.executeQuery(sales_negative_request);
			while (rs.next()) {
				sales.add(
						new Document(rs.getString(AppData.COLUMN_DOC_NUMERO), "VN", rs.getDate(AppData.COLUMN_DOC_DATE),
								rs.getString(AppData.COLUMN_PCF_CODE), rs.getString(AppData.COLUMN_REP_CODE)));
			}

			logger.log(Level.INFO,"[DOCUMENT][GET][SUCESS] : server - "+url+", database - "+dbName+", from - "+dateFrom+", to - "+dateTo);

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DOCUMENT][GET][ERROR] : server - "+url+", database - "+dbName+", from - "+dateFrom+", to - "+dateTo+", error - "+e.getMessage());
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
		return sales;
	}

	/**
	 * Method that update sales from the distant server starting from a date
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @return a list of sales from distant database starting from date
	 */
	@RequestMapping("/salesUpdate")
	public ArrayList<Document> update(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		sales = new ArrayList<>();

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(AppData.DB_USER);
		ds.setPassword(AppData.DB_PASSWORD);
		ds.setServerName(url);
		ds.setPortNumber(AppData.DB_SERVER_PORT);
		ds.setDatabaseName(dbName);

		Logger logger = Logger.getLogger(AppData.APP_LOGGER);

		try {
			logger.log(Level.INFO,"[DOCUMENT][UPDATE][REQUEST] : server - "+url+", database - "+dbName+", from - "+dateFrom);
			con = ds.getConnection();
			logger.log(Level.INFO,"[DOCUMENT][UPDATE][CONNECTION SUCESS] : server - "+url+", database - "+dbName+", from - "+dateFrom);
			String sales_positive_request = "select doc_numero, doc_date, pcf_code,rep_code"
					+ " from documents where doc_type = 'V' and ( (doc_stype = 'B' and doc_etat <> 'S') or doc_stype= 'F')   and doc_dtmaj > '"
					+ dateFrom + " ' ";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sales_positive_request);
			while (rs.next()) {
				sales.add(
						new Document(rs.getString(AppData.COLUMN_DOC_NUMERO), "VP", rs.getDate(AppData.COLUMN_DOC_DATE),
								rs.getString(AppData.COLUMN_PCF_CODE), rs.getString(AppData.COLUMN_REP_CODE)));
			}
			rs.close();

			String sales_negative_request = "select doc_numero, doc_date, pcf_code,rep_code"
					+ " from documents where doc_type = 'V' and ( (doc_stype = 'R' and doc_etat <> 'S') or doc_stype= 'A')   and doc_dtmaj > '"
					+ dateFrom + " ' ";
			rs = stmt.executeQuery(sales_negative_request);
			while (rs.next()) {
				sales.add(
						new Document(rs.getString(AppData.COLUMN_DOC_NUMERO), "VN", rs.getDate(AppData.COLUMN_DOC_DATE),
								rs.getString(AppData.COLUMN_PCF_CODE), rs.getString(AppData.COLUMN_REP_CODE)));
			}
			
			logger.log(Level.INFO,"[DOCUMENT][UPDATE][SUCESS] : server - "+url+", database - "+dbName+", from - "+dateFrom);

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[DOCUMENT][UPDATE][ERROR] : server - "+url+", database - "+dbName+", from - "+dateFrom+", error - "+e.getMessage());
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
		return sales;
	}

}
