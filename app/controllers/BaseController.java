package controllers;

import com.google.gson.*;
import com.google.inject.Injector;
import dao.DaoFactory;
import exceptions.DemoException;
import models.BaseModel;
import models.annotations.Orderable;
import models.annotations.Queryable;
import models.responses.SingularResponse;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.db.jpa.Transactional;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Kang Kang Wang
 */
public class BaseController extends Controller {
  protected Injector injector;

  protected Class getInjectedModelClass() {
    BaseModel model = injector.getInstance(BaseModel.class);
    return model.getClass();
  }

  @Transactional
  public F.Promise<Result> getById(int id) {
    long startTime = new DateTime().getMillis();

    play.Logger.info(getInjectedModelClass().getName());
    BaseModel object = DaoFactory.get(getInjectedModelClass()).findById(id);

    if (object == null) {
      return F.Promise.pure(notFound());
    }
    long duration = new DateTime().getMillis() - startTime;
    SingularResponse response = new SingularResponse(startTime, duration).setResult(object);
    String jsonResponse = jsonifyResponse(response);

    return F.Promise.pure(ok(jsonResponse));
  }

  /**
   * This function constructs query using request query param.
   * e.g. property?cityId=1
   * property?pid=1&psize=40
   * @param ownershipParam defaults to ""
   * @return
   */
  private String constructGetQuery(Optional<String> ownershipParam) throws DemoException {
    Map<String, String[]> params = request().queryString();
    StringBuilder s = new StringBuilder();
    s.append(constructGetQueryHelper(getInjectedModelClass(), params));
    return ownershipParam.map(param -> s.append(" AND ").append(param).toString()).orElse(s.toString());
  }

  protected String constructGetQueryHelper(Class modelClass, Map<String, String[]> params) {
    Map<String, Field> queryableFields = Arrays.stream(modelClass.getFields())
        .filter(f -> f.isAnnotationPresent(Queryable.class))
        .collect(Collectors.toMap(f -> f.getAnnotation(Queryable.class)
            .mappedName(), Function.identity()));
    Map<String, Field> orderableFields = getOrderableFields(modelClass);
    StringBuilder whereBuilder = new StringBuilder(params.keySet().stream().filter(k -> !"pid".equalsIgnoreCase(k) && !"psize".equalsIgnoreCase(k))
        .map(k -> {
          Field f = queryableFields.get(k);
          String dbField = f.getAnnotation(Queryable.class).dbField();
          Class type = f.getAnnotation(Queryable.class).type();
          if ("id".equalsIgnoreCase(dbField) && params.get(k)[0].contains(",")) {
            // support get by comma-delimited ids
            return valueComposer(params.get(k)[0].split(","), dbField, "%f", "%s", ",", Integer::parseInt);
          } else if (Long.class == type || Integer.class == type) {
            return valueComposer(params.get(k), dbField, "%d", "%s", ",", Long::parseLong);
          } else if(Double.class == type || Float.class == type) {
            return valueComposer(params.get(k), dbField, "%f", "%s", ",", Double::parseDouble);
          }
          //For now, we treat all other types the same way as String
          return valueComposer(params.get(k), dbField, "'%s'", "'%s'", "','", String::toString);
        }).collect(Collectors.joining()));


    String orderFields = constructOrderBy(params, orderableFields);
    if(orderFields != null && !orderFields.isEmpty()) {
      whereBuilder.append(" ORDER BY ").append(orderFields);
    }
    return whereBuilder.toString();
  }

  private <R> String valueComposer(String[] values, String key, String formatter, String inFormatter, String inSplitter, Function<String, R> parser) {
    if (values.length == 1) {
      return String.format(" AND %s = "+formatter, key, parser.apply(values[0]));
    }
    return String.format(" AND %s IN (" + inFormatter + ")", key,
        StringUtils.join(values, inSplitter));
  }

  protected Map<String, Field> getOrderableFields(Class modelClass) {
    return Arrays.stream(modelClass.getFields()).filter(f -> f.isAnnotationPresent(Orderable.class))
        .collect(Collectors.toMap(f -> f.isAnnotationPresent(Queryable.class)?f.getAnnotation(Queryable.class).mappedName():f.getName(), Function.identity()));

  }
  protected String constructOrderBy(Map<String, String[]> params, Map<String, Field> orderableFields) {
    String[] orderQueryParams = params.get("orderBy");
    String orderFields = null;
    if(orderQueryParams != null) {
      //orderBy=field1,asc;field2,desc
      String orderBy = orderQueryParams[0];
      orderFields = Arrays.stream(orderBy.split(";")).map(o -> {
        String[] tokens = o.split(",");
        if (orderableFields.containsKey(tokens[0])) {
          if (tokens.length == 2 && (tokens[1].equalsIgnoreCase("asc") || tokens[1].equalsIgnoreCase("desc"))) {
            return tokens[0] + " " + tokens[1];
          }
        }
        return null;
      }).filter(Objects::nonNull).collect(Collectors.joining(","));
    }
    return orderFields;
  }

  /**
   * Moved this function from ResponseHelper to BaseController
   * because we need each child controller to transform its response object
   * for i18n and potentially other use cases.
   * @param src
   * @return
   */
  protected String jsonifyResponse(Object src) {
    try {
      Gson gson = new GsonBuilder().setVersion(1.0)
          .setPrettyPrinting().serializeNulls().create();
      JsonElement json = gson.toJsonTree(src);
      if (json.getAsJsonObject().has("result")) {
        JsonObject object = json.getAsJsonObject().getAsJsonObject("result");
        transformResponse(object);
      } else if (json.getAsJsonObject().has("results")) {
        JsonArray objects = json.getAsJsonObject().getAsJsonArray("results");
        Iterator<JsonElement> iterator = objects.iterator();
        while (iterator.hasNext()) {
          JsonObject object = iterator.next().getAsJsonObject();
          transformResponse(object);
        }
      } else {
        // do nothing. It's probably an ErrorResponse.
      }
      return gson.toJson(json);
    } catch(Exception e) {
      e.printStackTrace();
      return e.getLocalizedMessage();
    }
  }

  protected void transformResponse(JsonObject object) {}
}
