package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dao.DaoFactory;
import models.Property;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * @author Kang Kang Wang
 */
public class PropertyController extends Controller {
  public PropertyController() {
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
