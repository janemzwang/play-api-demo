package controllers;

import com.google.gson.*;
import models.Property;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Kang Kang Wang
 */
public class PropertyController extends Controller {
  private static long property_id = 1L;

  private static Map<String, String> store = new HashMap<>();

  public PropertyController() {
  }

  public Result getById(int mlsId) {
    Gson gson = new GsonBuilder().setVersion(1.0)
        .setPrettyPrinting().serializeNulls().create();

    String propertyJson = store.get(String.valueOf(mlsId));
    if(propertyJson != null) {
      Property property = gson.fromJson(propertyJson, Property.class);
      JsonElement json = gson.toJsonTree(property);
      String jsonResponse = gson.toJson(json);

      return ok(jsonResponse);
    } else {
      return notFound();
    }
  }

  public Result createProperty() {
    try {
      Form<Property> form = Form.form(Property.class).bindFromRequest();
      if (form.hasErrors()) {
        return badRequest("Request body data doesn't conform to expected format.");
      }
      Property property = form.get();
      property.id = (property_id++);
      //what should we check here? What if property with the same mlsId exists?

      property.publishedOn = System.currentTimeMillis();

      Gson gson = new GsonBuilder().create();
      store.put(String.valueOf(property.mlsId), gson.toJson(property));
    } catch (Exception e) {
      play.Logger.error(e.getMessage());
      return internalServerError();
    }
    return ok("You have successfully created a property.");
  }

  public Result updateProperty(int mlsId) {
    try {
      if(!store.containsKey(String.valueOf(mlsId))) {
        return notFound();
      }

      Form<Property> form = Form.form(Property.class).bindFromRequest();
      if (form.hasErrors()) {
        return badRequest("Request body data doesn't conform to expected format.");
      }
      Property property = form.get();
      property.mlsId = Long.valueOf(mlsId);

      Gson gson = new GsonBuilder().create();
      store.put(String.valueOf(property.mlsId), gson.toJson(property));
    } catch (Exception e) {
      play.Logger.error(e.getMessage());
      return internalServerError();
    }
    return ok("You have successfully updated the property.");
  }

  public Result deleteProperty(int mlsId) {
    try {
      if (!store.containsKey(String.valueOf(mlsId))) {
        return notFound();
      }
      store.remove(String.valueOf(mlsId));
    } catch (Exception e) {
      play.Logger.error(e.getMessage());
      return internalServerError();
    }
    return ok("You have successfully deleted the property.");
  }
}
