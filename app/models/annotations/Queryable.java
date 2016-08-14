package models.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kang Kang Wang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Queryable {
  /**
   * This is the column name of the field.
   * @return Database column name represented by the member field
   */
  String dbField();

  /**
   * This is the name of the query param in the GET request, e.g., city.id
   * @return
   */
  String mappedName();

  /**
   * Type of the underlying database column: Integer, Long, Double, Float, String, or something else.
   * @return
   */
  Class type();
}
