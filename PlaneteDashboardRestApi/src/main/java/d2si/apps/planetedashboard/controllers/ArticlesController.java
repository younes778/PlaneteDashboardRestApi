package d2si.apps.planetedashboard.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import d2si.apps.planetedashboard.data.AppData;
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
	public ArrayList<Article> get(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppData.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		articles = new ArrayList<>();

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
			String articles_request = "select art_code, art_lib, f.far_lib from articles a left join art_fam f on a.far_code = f.far_code "
					+ " where art_code in(" + " select art_code from lignes where doc_numero in( "
					+ " select doc_numero from documents " + " where doc_type in ('V','A') and doc_date between '"
					+ dateFrom + "' and '" + dateTo + "' ))";

			stmt = con.createStatement();
			rs = stmt.executeQuery(articles_request);
			while (rs.next()) {
				String fam = rs.getString(AppData.COLUMN_ART_FAM);
				if (fam == null)
					fam = "";
				articles.add(
						new Article(rs.getString(AppData.COLUMN_ART_CODE), rs.getString(AppData.COLUMN_ART_LIB), fam));
			}
			rs.close();

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
	public ArrayList<Article> update(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		articles = new ArrayList<>();

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
			String articles_request = "select art_code, art_lib, f.far_lib from articles a left join art_fam f on a.far_code = f.far_code "
					+ " where art_dtmaj > ' " + dateFrom + " ' ";

			stmt = con.createStatement();
			rs = stmt.executeQuery(articles_request);
			while (rs.next()) {
				String fam = rs.getString(AppData.COLUMN_ART_FAM);
				if (fam == null)
					fam = "";
				articles.add(
						new Article(rs.getString(AppData.COLUMN_ART_CODE), rs.getString(AppData.COLUMN_ART_LIB), fam));
			}
			rs.close();

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
		return articles;
	}

}
