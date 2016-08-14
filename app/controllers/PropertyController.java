package controllers;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import models.BaseModel;
import models.Property;

/**
 * @author Kang Kang Wang
 */
public class PropertyController extends BaseController {
  public PropertyController() {
    this.injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(BaseModel.class).to(Property.class);
      }
    });
  }
}
