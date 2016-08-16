package models;

import play.data.validation.Constraints;

/**
 * @author Kang Kang Wang
 */
public class Property {

  private Long id;

  private Long mlsId;

  @Constraints.Pattern(value="off_market|for_sale|pending|auction|foreclosure|new_construction|coming_soon|sold|for_rent|foreclosed|lot/land|make_me_move", message = "error.property.market_status.invalid")
  private String marketStatus = "off_market";

  @Constraints.Pattern(value="condo|cooperative|townhouse|apartment|multi_family|single_family", message = "error.property.type.invalid")
  private String propertyType;

  @Constraints.Min(value = 0, message = "error.property.listingPrice.negative")
  private Integer listingPrice;

  @Constraints.Min(value = 0, message = "error.property.numOfBedrooms.negative")
  private Integer numOfBedrooms;

  @Constraints.Min(value = 0, message = "error.property.numOfBaths.negative")
  private Integer numOfFullBaths;

  @Constraints.Min(value = 0, message = "error.property.numOfBaths.negative")
  private Integer numOfHalfBaths;

  @Constraints.Min(value = 0, message = "error.property.totalNumOfRooms.negative")
  private Integer totalNumOfRooms;

  @Constraints.Min(value = 1500, message = "error.property.buildYear.min")
  @Constraints.Max(value = 3000, message = "error.property.buildYear.max")
  private Integer buildYear;

  private Long publishedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMlsId() {
    return mlsId;
  }

  public void setMlsId(Long mlsId) {
    this.mlsId = mlsId;
  }

  public String getMarketStatus() {
    return marketStatus;
  }

  public void setMarketStatus(String marketStatus) {
    this.marketStatus = marketStatus;
  }

  public String getPropertyType() {
    return propertyType;
  }

  public void setPropertyType(String propertyType) {
    this.propertyType = propertyType;
  }

  public Integer getListingPrice() {
    return listingPrice;
  }

  public void setListingPrice(Integer listingPrice) {
    this.listingPrice = listingPrice;
  }

  public Integer getNumOfBedrooms() {
    return numOfBedrooms;
  }

  public void setNumOfBedrooms(Integer numOfBedrooms) {
    this.numOfBedrooms = numOfBedrooms;
  }

  public Integer getNumOfFullBaths() {
    return numOfFullBaths;
  }

  public void setNumOfFullBaths(Integer numOfFullBaths) {
    this.numOfFullBaths = numOfFullBaths;
  }

  public Integer getNumOfHalfBaths() {
    return numOfHalfBaths;
  }

  public void setNumOfHalfBaths(Integer numOfHalfBaths) {
    this.numOfHalfBaths = numOfHalfBaths;
  }

  public Integer getTotalNumOfRooms() {
    return totalNumOfRooms;
  }

  public void setTotalNumOfRooms(Integer totalNumOfRooms) {
    this.totalNumOfRooms = totalNumOfRooms;
  }

  public Integer getBuildYear() {
    return buildYear;
  }

  public void setBuildYear(Integer buildYear) {
    this.buildYear = buildYear;
  }

  public Long getPublishedOn() {
    return publishedOn;
  }

  public void setPublishedOn(Long publishedOn) {
    this.publishedOn = publishedOn;
  }
}
