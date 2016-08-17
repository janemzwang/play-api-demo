package models;

import play.data.validation.Constraints;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Kang Kang Wang
 */
public class Property {
  public Long id;

  public Long mlsId;

  @Constraints.Pattern(value="off_market|for_sale|pending|auction|foreclosure|new_construction|coming_soon|sold|for_rent|foreclosed|lot/land|make_me_move", message = "error.property.market_status.invalid")
  public String marketStatus = "off_market";

  @Constraints.Pattern(value="condo|cooperative|townhouse|apartment|multi_family|single_family", message = "error.property.type.invalid")
  public String propertyType;

  @Min(value = 0, message = "error.property.listingPrice.negative")
  public Integer listingPrice;

  @Min(value = 0, message = "error.property.numOfBedrooms.negative")
  public Integer numOfBedrooms;

  @Min(value = 0, message = "error.property.numOfBaths.negative")
  public Integer numOfFullBaths;

  @Min(value = 0, message = "error.property.numOfBaths.negative")
  public Integer numOfHalfBaths;

  @Min(value = 0, message = "error.property.totalNumOfRooms.negative")
  public Integer totalNumOfRooms;

  @Min(value = 1500, message = "error.property.buildYear.min")
  @Max(value = 3000, message = "error.property.buildYear.max")
  public Integer buildYear;

  public Long publishedOn;
}
