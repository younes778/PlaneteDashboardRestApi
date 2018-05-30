package d2si.apps.planetedashboard.controllers;

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

import d2si.apps.planetedashboard.data.AppUtils;
import d2si.apps.planetedashboard.data.Article;

/**
 * Class that represents the articles controller
 *
 */

@RestController
public class ArticlesController {
	ArrayList<Article> articles;

	/**
	 * Method that get articles from the distant server between two dates
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @param dateTo
	 *            end date
	 * @return a list of articles from distant database between the two dates
	 */
	@RequestMapping("/articlesGet")
	public ArrayList<Article> get(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppUtils.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		articles = new ArrayList<>();

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
					"[ARTICLES][GET][REQUEST] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);
			con = ds.getConnection();
			logger.log(Level.INFO,
					"[ARTICLES][GET][CONNECTION SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - "
							+ dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);
			String articles_request = "select art_code, art_lib, f.far_lib from articles a left join art_fam f on a.far_code = f.far_code "
					+ " where art_code in(" + " select art_code from lignes where doc_numero in( "
					+ " select doc_numero from documents " + " where doc_type in ('V','A') and doc_date between '"
					+ dateFrom + "' and '" + dateTo + "' ))";

			stmt = con.createStatement();
			rs = stmt.executeQuery(articles_request);
			while (rs.next()) {
				String fam = rs.getString(AppUtils.COLUMN_ART_FAM);
				if (fam == null)
					fam = "";
				articles.add(new Article(rs.getString(AppUtils.COLUMN_ART_CODE), rs.getString(AppUtils.COLUMN_ART_LIB),
						fam));
			}
			rs.close();
			logger.log(Level.INFO,
					"[ARTICLES][GET][SUCESS] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
							+ ", dbPassword - " + dbPassword + ", from - " + dateFrom + ", to - " + dateTo);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[ARTICLES][GET][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
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
		return articles;
	}

	/**
	 * Method that update articles from the distant server starting from a date
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @return a list of articles from distant database starting from date
	 */
	@RequestMapping("/articlesUpdate")
	public ArrayList<Article> update(@RequestParam(value = AppUtils.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppUtils.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppUtils.FIELD_DB_USER, defaultValue = "") String dbUser,
			@RequestParam(value = AppUtils.FIELD_DB_PASSWORD, defaultValue = "") String dbPassword,
			@RequestParam(value = AppUtils.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		articles = new ArrayList<>();

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
			logger.log(Level.INFO, "[ARTICLES][UPDATE][REQUEST] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			con = ds.getConnection();
			logger.log(Level.INFO, "[ARTICLES][UPDATE][CONNECTION SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);
			String articles_request = "select art_code, art_lib, f.far_lib from articles a left join art_fam f on a.far_code = f.far_code "
					+ " where art_dtmaj > ' " + dateFrom + " ' ";

			stmt = con.createStatement();
			rs = stmt.executeQuery(articles_request);
			while (rs.next()) {
				String fam = rs.getString(AppUtils.COLUMN_ART_FAM);
				if (fam == null)
					fam = "";
				articles.add(new Article(rs.getString(AppUtils.COLUMN_ART_CODE), rs.getString(AppUtils.COLUMN_ART_LIB),
						fam));
			}
			rs.close();
			logger.log(Level.INFO, "[ARTICLES][UPDATE][SUCESS] : server - " + url + ", database - " + dbName
					+ ", dbUser - " + dbUser + ", dbPassword - " + dbPassword + ", from - " + dateFrom);

		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"[ARTICLES][UPDATE][ERROR] : server - " + url + ", database - " + dbName + ", dbUser - " + dbUser
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
		return articles;
	}

}
