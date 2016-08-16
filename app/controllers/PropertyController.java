package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.typesafe.config.ConfigFactory;
import dao.BaseDao;
import dao.DaoFactory;
import models.BaseModel;
import models.Property;
import org.apache.commons.lang3.StringUtils;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  /**
   * supported calls:
   * 1. /api/property?status=open
   * 2. /api/property?cityId=1
   * 3. /api/property?boroughId=1&boroughId=2&boroughId=3&status=open
   * 4. /api/property?created_on=DATETIME TODO
   * @return
   */
  @Transactional
  public Result get() {
    BaseDao dao = DaoFactory.getPropertyDAO();
    StringBuffer hql = new StringBuffer(String.format("FROM Property WHERE 1 = 1 "));

    try {
      hql.append(constructGetQueryHelper(Property.class, request().queryString()));
      PaginationContext paginationContext = extractPaginationContext();
      List<BaseModel> objects = dao.findPaginatedResultByQuery(
          hql.toString(), paginationContext);

      String jsonResponse = jsonifyResponse(objects);

      return ok(jsonResponse);
    } catch (Exception e) {
      play.Logger.error("get failed", e);
      return internalServerError(e.getMessage());
    }
  }

  protected PaginationContext extractPaginationContext() {
    Map<String, String[]> params = request().queryString();
    return new PaginationContext(params.keySet().contains("psize") ? Integer.parseInt(params.get("psize")[0]) : ConfigFactory.load().getInt("default_page_size"),
        (params.keySet().contains("pid")) ? Integer.parseInt(params.get("pid")[0]) : 0);
  }

  protected String constructGetQueryHelper(Class modelClass, Map<String, String[]> params) {
    Map<String, Field> queryableFields = Arrays.stream(modelClass.getFields())
        .collect(Collectors.toMap(f -> f.getName(), Function.identity()));

    StringBuilder whereBuilder = new StringBuilder(params.keySet().stream().filter(k -> !"pid".equalsIgnoreCase(k) && !"psize".equalsIgnoreCase(k))
        .map(k -> {
          Field f = queryableFields.get(k);
          String dbField = f.getName();
          Class type = f.getType();
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

    return whereBuilder.toString();
  }

  private <R> String valueComposer(String[] values, String key, String formatter, String inFormatter, String inSplitter, Function<String, R> parser) {
    if (values.length == 1) {
      return String.format(" AND %s = "+formatter, key, parser.apply(values[0]));
    }
    return String.format(" AND %s IN (" + inFormatter + ")", key,
        StringUtils.join(values, inSplitter));
  }

  private String jsonifyResponse(Object obj) {
    Gson gson = new GsonBuilder().setVersion(1.0)
        .setPrettyPrinting().serializeNulls().create();
    JsonElement json = gson.toJsonTree(obj);
    return gson.toJson(json);
  }
}
