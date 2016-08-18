package services;

import dao.BaseDao;
import dao.DaoFactory;
import models.Property;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kang Kang Wang
 */
public class PropertyService {

  public PropertyService() {}

  public List<String> getMediaUrls(long propertyId) {
    BaseDao<Property> propertyDao = DaoFactory.getPropertyDAO();
    Property property = propertyDao.findById(propertyId);
    if(property != null) {
      return property.media.stream().map(m -> m.url).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
