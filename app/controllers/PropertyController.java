package controllers;

import com.google.gson.*;
import models.Property;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


/**
 * @author Kang Kang Wang
 */
public class PropertyController extends Controller {
  private static long property_id = 1L;

  public PropertyController() {
  }

  public Result getById(int mlsId) {
    Property property = new Property();
    property.buildYear = 2010;
    property.id = property_id++;
    property.listingPrice = 500000;
    property.mlsId = Long.valueOf(mlsId);
    property.numOfBedrooms = 1;
    property.numOfFullBaths = 1;

    Gson gson = new GsonBuilder().setVersion(1.0)
        .setPrettyPrinting().serializeNulls().create();

    JsonElement json = gson.toJsonTree(property);
    String jsonResponse = gson.toJson(json);

    return ok(jsonResponse);
  }

  public Result createProperty() {
    try {
      session().clear();
      Form<Property> form = Form.form(Property.class).bindFromRequest();
      if (form.hasErrors()) {
        return badRequest("Request body data doesn't conform to expected format.");
      }
      Property property = form.get();

      //what should we check here? What if property with the same mlsId exists?

      property.publishedOn = System.currentTimeMillis();

      Gson gson = new GsonBuilder().create();
      session().put(String.valueOf(property.mlsId), gson.toJson(property));
    } catch (Exception e) {
      play.Logger.error(e.getMessage());
      return internalServerError();
    }
    return ok("You have successfully created a property.");
  }
}
