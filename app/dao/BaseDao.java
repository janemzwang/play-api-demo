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
}
