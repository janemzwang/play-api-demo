package services;

import dao.BaseDao;
import models.Property;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kang Kang Wang
 */
public class TestablePropertyService {
  private BaseDao<Property> propertyDao;

  public TestablePropertyService(BaseDao<Property> propertyDao) {
    this.propertyDao = propertyDao;
  }

  public List<String> getMediaUrls(long propertyId) {
    Property property = propertyDao.findById(propertyId);
    if(property != null) {
      return property.media.stream().map(m -> m.url).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
