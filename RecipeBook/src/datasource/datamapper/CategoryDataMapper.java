package datasource.datamapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.database.DBConnection;
import model.Category;

public class CategoryDataMapper {

	public CategoryDataMapper() {

	}

	public List<Category> readAll() {

		List<Category> allCategories = new ArrayList<Category>();

		// if identity map can not return an object, connect to db and get data
		DBConnection DB = new DBConnection();
		String sql = "SELECT * FROM categories";

		try {
			PreparedStatement preparedStatement = DB.prepare(sql);
			ResultSet rset = preparedStatement.executeQuery();
			DB.getDbConnection().commit();
		
			int categoryId = -1;
			String categoryName = "";

			while (rset.next()) {
				categoryId = rset.getInt("category_id");
				categoryName = rset.getString("category_name");
				Category category = new Category(categoryId, categoryName);
				allCategories.add(category);
			}

			// Add the retrieved recipe into identity map for the next requests.
			return allCategories;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return allCategories;
		}
	}
}
