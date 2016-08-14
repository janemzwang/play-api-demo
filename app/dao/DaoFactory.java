package dao;

import models.Property;

/**
 * @author Kang Kang Wang
 */
public class DaoFactory {
  public static BaseDao get(Class model) {
    return new BaseDao() {
      @Override
      protected Class getEntityClass() {
        return model;
      }
    };
  }

  private static BaseDao<Property> propertyBaseDAO = new BaseDao<Property>() {
    @Override
    protected Class getEntityClass() {
      return Property.class;
    }
  };

  public static BaseDao<Property> getPropertyDAO() {
    return propertyBaseDAO;
  }
}
