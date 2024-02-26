import com.fmap.batch.reader.RestaurantItemReader;
import com.fmap.dto.RestaurantRes;
import com.fmap.utils.LocalDataApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class RestaurantItemReaderTest {

    private LocalDataApiUtils localDataApiUtils;
    private RestaurantItemReader restaurantItemReader;
    private List<RestaurantRes> mockData;

    @BeforeEach
    void setUp() {
        localDataApiUtils = Mockito.mock(LocalDataApiUtils.class);

        RestaurantRes restaurant1 = new RestaurantRes("rstNm1");
        RestaurantRes restaurant2 = new RestaurantRes("rstNm2");
        mockData = Arrays.asList(restaurant1, restaurant2);

        when(localDataApiUtils.fetchAndUpdateData()).thenReturn(mockData);

        restaurantItemReader = new RestaurantItemReader(localDataApiUtils);
    }

    @Test
    void read_ShouldReturnRestaurants() throws Exception {
        RestaurantRes firstRead = restaurantItemReader.read();
        assertThat(firstRead).isEqualToComparingFieldByField(mockData.get(0));

        RestaurantRes secondRead = restaurantItemReader.read();
        assertThat(secondRead).isEqualToComparingFieldByField(mockData.get(1));

        RestaurantRes thirdRead = restaurantItemReader.read();
        assertThat(thirdRead).isNull();
    }
}
