import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.flextrade.jfixture.JFixture;
import com.malveillance.uberif.model.service.Service;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import com.malveillance.uberif.model.service;

public class ServiceTest {

    private static JFixture fixture = new JFixture();
    private static Map<String, Intersection> intersectionMap;
    private static double minX, xRange, maxY, yRange;

    @BeforeClass
    public static void setup() {
        // Initialize your static variables here
        minX = fixture.create(Double.class);
        xRange = fixture.create(Double.class);
        maxY = fixture.create(Double.class);
        yRange = fixture.create(Double.class);

        Service sut = new Service();
        intersectionMap = new HashMap<>();
    }

    @Test
    public void testLongitudeToX() {
        double longitude = fixture.create(Double.class);
        double paneWidth = fixture.create(Double.class);
        double expected = ((longitude - minX) / xRange) * paneWidth;
        assertEquals(expected, sut.longitudeToX(longitude, paneWidth), 0.001);
    }

    @Test
    public void testLatitudeToY() {
        double latitude = fixture.create(Double.class);
        double paneHeight = fixture.create(Double.class);
        double expected = ((maxY - latitude) / yRange) * paneHeight;
        assertEquals(expected, YourClassName.latitudeToY(latitude, paneHeight), 0.001);
    }

    @Test
    public void testGetIntersectionXWithValidId() {
        String interId = fixture.create(String.class);
        double paneWidth = fixture.create(Double.class);
        Intersection mockIntersection = mock(Intersection.class);
        mockIntersection.longitude = fixture.create(Double.class); // or use setter

        intersectionMap.put(interId, mockIntersection);

        double expected = YourClassName.longitudeToX(mockIntersection.longitude, paneWidth);
        assertEquals(expected, YourClassName.getIntersectionX(interId, paneWidth), 0.001);
    }

    @Test
    public void testGetIntersectionXWithInvalidId() {
        String interId = fixture.create(String.class);
        double paneWidth = fixture.create(Double.class);

        assertEquals(0.0, YourClassName.getIntersectionX(interId, paneWidth), 0.001);
    }

    // Similarly implement testGetIntersectionYWithValidId and testGetIntersectionYWithInvalidId
}
