package models;

import com.google.gson.annotations.Until;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseModel implements Serializable {

  @Until(1.0)
  @Version
  @Column(name = "VERSION")
  private Integer version = Integer.valueOf(0);

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
