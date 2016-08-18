package models;

import com.google.gson.annotations.Until;

import javax.persistence.*;

/**
 * @author Kang Kang Wang
 */
@Entity
@Table(name = "property_media")
public class PropertyMedia extends BaseModel {

//  @Until(1.0)
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  public Long id;

  @Until(1.0)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = false)
  @JoinColumn(name = "property_id", nullable = false)
  public Property property;

  @Column(name = "url")
  public String url;

  @Column(name = "note")
  public String note = "this is a nice picture.";
}
