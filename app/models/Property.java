package models;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kang Kang Wang
 */
public class Property {

  public Long id;

//  @Constraints.Required
  public Long mlsId;

  @Constraints.Pattern(value="off_market|for_sale|pending|auction|foreclosure|new_construction|coming_soon|sold|for_rent|foreclosed|lot/land|make_me_move", message = "error.property.market_status.invalid")
  public String marketStatus = "off_market";

  @Constraints.Pattern(value="condo|cooperative|townhouse|apartment|multi_family|single_family", message = "error.property.type.invalid")
  public String propertyType;

  @Constraints.Min(value = 0, message = "error.property.listingPrice.negative")
  public Integer listingPrice;

  @Constraints.Min(value = 0, message = "error.property.numOfBedrooms.negative")
  public Integer numOfBedrooms;

  @Constraints.Min(value = 0, message = "error.property.numOfBaths.negative")
  public Integer numOfFullBaths;

  @Constraints.Min(value = 0, message = "error.property.numOfBaths.negative")
  public Integer numOfHalfBaths;

  @Constraints.Min(value = 0, message = "error.property.totalNumOfRooms.negative")
  public Integer totalNumOfRooms;

  @Constraints.Min(value = 1500, message = "error.property.buildYear.min")
  @Constraints.Max(value = 3000, message = "error.property.buildYear.max")
  public Integer buildYear;

  public Long publishedOn;

  public List<ValidationError> validate() {
    List<ValidationError> errors = new ArrayList<ValidationError>();
    
    return errors.isEmpty() ? null : errors;
  }
}
