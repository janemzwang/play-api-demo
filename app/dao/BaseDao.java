package dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.TypedQuery;

import controllers.PaginationContext;
import models.BaseModel;
import org.slf4j.Logger;
import play.db.jpa.JPA;

/**
 * @author Kang Kang Wang
 */
public abstract class BaseDao<T extends BaseModel> {

  private final static Logger log = play.Logger.underlying();

  protected BaseDao() {
    log.debug("BaseDao constructor");
  }

  /**
   * @return should return the Class of the Entity for which this DAO provides
   *         data access (e.g. UserDao would return User.class).
   */
  protected abstract Class getEntityClass ();

  public T findById (long anId) {
    try {
      T entity = (T)JPA.em().find(getEntityClass(), anId);
      return entity;
    } catch (Exception e) {
      log.error("find failed", e);
      throw e;
    }
  }

  @SuppressWarnings("unchecked")
  public List<T> findAll () {
    final List<T> results = new LinkedList<>();
    results.addAll((List<T>)JPA.em().createQuery("from " + getEntityClass().getName()).getResultList());
    return results;
  }

  public List<T> findAllByQuery (String query) {
    final List<T> results = new ArrayList<>();
    TypedQuery<T> typedQuery = JPA.em().createQuery(query, getEntityClass());
    results.addAll(typedQuery.getResultList());
    return results;
  }

  public List<T> findPaginatedResultByQuery (String query, PaginationContext paginationContext) {
    final List<T> results = new ArrayList<>();
    TypedQuery<T> typedQuery = JPA.em().createQuery(query, getEntityClass());
    typedQuery.setFirstResult(paginationContext.getStart());
    typedQuery.setMaxResults(paginationContext._pageSize);
    results.addAll(typedQuery.getResultList());
    return results;
  }

  /**
   * utility method for returning a single object from the supplied list.  This delegates to the
   * multi-arg version of the same method
   *
   * @param aResultList the result list
   * @return the first object in the result set, or null if there are none.
   */
  public T getSingleObject (@SuppressWarnings("rawtypes") List aResultList) {
    return getSingleObject(aResultList, false, false);
  }


  /**
   * utility method for returning a single object from the supplied list.  This delegates to the
   * multi-arg version of the same method
   *
   * @param aResultList              the result list
   * @param aFailIfZeroObjects       if true, an IllegalArgumentException will be thrown if the aResultList  parameter
   *                                 contains zero objects.  Otherwise, a null will be returned.
   * @param aFailIfMoreThanOneObject if true, an IllegalArgumentException will be thrown if the ResultList parameter
   *                                 contains MORE than one object.  Otherwise, the first Object in the ResultList will be returned.
   * @return the first object in the result set, or null if there are none (see notes on fail params).
   * @throws IllegalArgumentException
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public T getSingleObject (List aResultList, boolean aFailIfZeroObjects, boolean aFailIfMoreThanOneObject) {
    if (aResultList.size() == 1) {
      return (T) aResultList.get(0);
    }

    if (aResultList.size() == 0) {
      if (aFailIfZeroObjects) {
        throw new IllegalArgumentException("expected one object, but there were 0 (and the aFailIfZeroObjects parameter was set to true");
      }
      else {
        return null;
      }
    }

    if (aResultList.size() > 1) {
      if (aFailIfMoreThanOneObject) {
        throw new IllegalArgumentException("expected one object, but there were [" + aResultList.size() + "] and the aFailIfMoreThanOneObject parameter was set to true");
      }
      else {
        log.debug("Note - there was more than one object in the ResultSet, returning the first");
        return (T) aResultList.get(0);
      }
    }

    return null;
  }
}
