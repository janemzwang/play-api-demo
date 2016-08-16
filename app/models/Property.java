package models;

import com.google.gson.annotations.Until;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Kang Kang Wang
 */
@Entity
@Table(name = "property")
public class Property {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id")
  public Long id;

  @Column(name = "mls_id")
  public Long mlsId;

  @Column(name = "market_status")
  @Constraints.Pattern(value="off_market|for_sale|pending|auction|foreclosure|new_construction|coming_soon|sold|for_rent|foreclosed|lot/land|make_me_move", message = "error.property.market_status.invalid")
  public String marketStatus = "off_market";

  @Column(name = "property_type")
  @Constraints.Pattern(value="condo|cooperative|townhouse|apartment|multi_family|single_family", message = "error.property.type.invalid")
  public String propertyType;

  @Column(name = "listing_price")
  @Min(value = 0, message = "error.property.listingPrice.negative")
  public Integer listingPrice;

  @Column(name = "num_bedroom")
  @Min(value = 0, message = "error.property.numOfBedrooms.negative")
  public Integer numOfBedrooms;

  @Column(name = "num_full_bath")
  @Min(value = 0, message = "error.property.numOfBaths.negative")
  public Integer numOfFullBaths;

  @Column(name = "num_half_bath")
  @Min(value = 0, message = "error.property.numOfBaths.negative")
  public Integer numOfHalfBaths;

  @Column(name = "total_room")
  @Min(value = 0, message = "error.property.totalNumOfRooms.negative")
  public Integer totalNumOfRooms;

  @Column(name = "build_year")
  @Min(value = 1500, message = "error.property.buildYear.min")
  @Max(value = 3000, message = "error.property.buildYear.max")
  public Integer buildYear;

  @Column(name = "published_on")
  public Long publishedOn;
}
