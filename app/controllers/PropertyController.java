package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dao.DaoFactory;
import models.Property;
import play.db.Database;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Kang Kang Wang
 */
public class PropertyController extends Controller {
  private Database db;

  @Inject
  public PropertyController(Database db) {
    this.db = db;
  }

  private Property findProperty(int id) throws SQLException {
    try(Connection conn = db.getConnection();
      Statement st = conn.createStatement()) {
      ResultSet rs = st.executeQuery("select * from property where id =" + id);
      Property property = new Property();
      while(rs.next()) {
        property.id = Long.valueOf(id);
        property.mlsId = rs.getLong("mlsId");
        property.buildYear = rs.getInt("build_year");
        property.listingPrice = rs.getInt("listing_price");
        property.marketStatus = rs.getString("market_status");
        //and so on....
      }
      return property;
    }
  }

  @Transactional
  public Result getById(int id) {
    Property object = DaoFactory.getPropertyDAO().findById(id);

    if (object == null) {
      return notFound();
    }

    String jsonResponse = jsonifyResponse(object);

    return ok(jsonResponse);
  }

  @Transactional
  public Result getAll() {
    List<Property> properties = DaoFactory.getPropertyDAO().findAll();
    String jsonResponse = jsonifyResponse(properties);
    return ok(jsonResponse);
  }

  private String jsonifyResponse(Object obj) {
    Gson gson = new GsonBuilder().setVersion(1.0)
        .setPrettyPrinting().serializeNulls().create();
    JsonElement json = gson.toJsonTree(obj);
    return gson.toJson(json);
  }
}
