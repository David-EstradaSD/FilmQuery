package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Category;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Language;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		
		Film film = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.title, film.id, film.release_year, film.rating, film.description, language.name "
					+ "FROM film JOIN language ON language.id = film.language_id "
					+ "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();
				Language lang = new Language();
				film.setTitle(filmResult.getString("title"));
				film.setFilmId(filmResult.getInt("film.id"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setRating(filmResult.getString("rating"));
				film.setActors(findActorsByFilmId(filmId));
				// create the object (Table Name)
				// instantiate the object in the local result set (for Language, its on line 43)
				lang.setName(filmResult.getString("language.name"));
				film.setLanguage(lang);
				
			}
				filmResult.close();
				stmt.close();
				conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		
		List<Film> filmList = new ArrayList<Film>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, language.name "
					+ "FROM film JOIN language ON language.id = film.language_id "
					+ "WHERE film.title LIKE ? OR film.description LIKE ?"; // OR description LIKE '%" + keyword + "%'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,"%" + keyword + "%");
			// the 1 and 2 in setX method denotes which ? parameter we are referring to 
			// in this case, 1 is the first one and 2 is the second one
			stmt.setString(2,"%" + keyword + "%");
			ResultSet filmResult = stmt.executeQuery();
			while (filmResult.next()) { //next() is also checking 
				Film film = new Film();
				Language lang = new Language();
				film.setTitle(filmResult.getString("title"));
				film.setFilmId(filmResult.getInt("film.id"));
				film.setRating(filmResult.getString("rating"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				lang.setName(filmResult.getString("language.name"));
				film.setLanguage(lang);
				film.setActors(findActorsByFilmId(filmResult.getInt("film.id")));
				// we can use data from the database to pass as our arguments
				// we can use data that we are not going to display and is independent from the java classes
				filmList.add(film);
			}
			
				filmResult.close();
				stmt.close();
				conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filmList;
		
	}

	@Override
	public Actor findActorById(int actorId) {
		Film film = null;
		Actor actor = null;
		try {
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		  stmt.setInt(1, actorId);
		  ResultSet actorResult = stmt.executeQuery();
		  if (actorResult.next()) {
		    actor = new Actor(actorId); // Create the object
		    // Here is our mapping of query columns to our object fields:
		    actor.setId(actorResult.getInt("id"));
		    actor.setFirstName(actorResult.getString("first_name"));
		    actor.setLastName(actorResult.getString("last_name"));

		  }
		  
		  actorResult.close();
		  stmt.close();
		  conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  return actor;		
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<Actor>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM film "
					+ "JOIN film_actor ON film.id = film_actor.film_id "
					+ "JOIN actor ON film_actor.actor_id = actor.id "
					+ "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int actorId = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");
				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			return actors;
	}
	
	@Override
	  public Film findFilmByIdDisplayAllData(int filmId) {
		Film film = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT category.name, film.title, film.id, film.language_id, film.rental_duration, film.rental_rate, "
					+ "film.length, film.replacement_cost, film.special_features, film.release_year, film.rating, film.description, language.name "
					+ "FROM film LEFT JOIN language ON language.id = film.language_id "
					+ "LEFT JOIN film_category ON film_category.film_id = film.id "
					+ "LEFT JOIN category ON category.id = film_category.film_id "
					+ "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();
				Language lang = new Language();
				Category cat = new Category();
				film.setFilmId(filmResult.getInt("film.id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setRating(filmResult.getString("rating"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				//set actors
				film.setActors(findActorsByFilmId(filmId));
				//set language
				lang.setName(filmResult.getString("language.name"));
				film.setLanguage(lang);
				film.setLanguageId(filmResult.getInt("language_id"));
				//set category
				cat.setName(filmResult.getString("category.name"));
				film.setCategory(cat);
				
			}
				filmResult.close();
				stmt.close();
				conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
		
	}
	

}
