import dao.BaseDao;
import models.Property;
import models.PropertyMedia;
import org.junit.Test;
import org.mockito.Mockito;

import services.TestablePropertyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * @author Kang Kang Wang
 */
public class PropertyServiceTest {

  @Test
  public void testGetMedia() {
    Property mockProperty = new Property();
    mockProperty.media = new ArrayList<>();
    PropertyMedia mediaOne = new PropertyMedia();
    mediaOne.url = "some url";
    mockProperty.media.add(mediaOne);

    List<String> expected = new ArrayList<>();
    expected.add("some url");

    BaseDao<Property> dao = Mockito.mock(BaseDao.class);
    when(dao.findById(1)).thenReturn(mockProperty);

    TestablePropertyService service = new TestablePropertyService(dao);
    List<String> actual = service.getMediaUrls(1L);
    assertEquals(expected, actual);

    when(dao.findById(anyLong())).thenReturn(null);
    actual = service.getMediaUrls(1L);
    assertEquals(Collections.EMPTY_LIST, actual);
  }

  @Test
  public void testGetMediaCalls() {
    Property mockProperty = new Property();

    BaseDao<Property> dao = Mockito.mock(BaseDao.class);
    when(dao.findById(anyInt())).thenReturn(mockProperty);

    TestablePropertyService service = new TestablePropertyService(dao);
    service.getMediaUrls(1L);
    service.getMediaUrls(2L);

    verify(dao, times(1)).findById(1L);
    verify(dao, times(2)).findById(anyLong());
  }
}
