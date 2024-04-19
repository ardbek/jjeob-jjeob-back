import com.fmap.restaurant.controller.RestaurantController;
import com.fmap.restaurant.entity.Restaurant;
import com.fmap.restaurant.service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RestaurantController.class)
@ContextConfiguration(classes = RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void 모든_음식점_조회_테스트() throws Exception {
        Restaurant restaurant1 = Mockito.mock(Restaurant.class);
        Mockito.when(restaurant1.getRestaurantNo()).thenReturn(1L);
        Mockito.when(restaurant1.getRstntNm()).thenReturn("또복이 식당");

        Restaurant restaurant2 = Mockito.mock(Restaurant.class);
        Mockito.when(restaurant2.getRestaurantNo()).thenReturn(2L);
        Mockito.when(restaurant2.getRstntNm()).thenReturn("용한 식당");

        List<Restaurant> allRestaurants = Arrays.asList(restaurant1, restaurant2);

        Mockito.when(restaurantService.getAllRestaurant()).thenReturn(allRestaurants);

        mvc.perform(get("/api/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurantNo").value(1L))
                .andExpect(jsonPath("$[0].rstntNm").value("또복이 식당"))
                .andExpect(jsonPath("$[1].restaurantNo").value(2L))
                .andExpect(jsonPath("$[1].rstntNm").value("용한 식당"));
    }

    @Test
    public void 음식점_id_조회_테스트() throws Exception {
        Restaurant restaurant = Mockito.mock(Restaurant.class);
        Mockito.when(restaurant.getRestaurantNo()).thenReturn(1L);
        Mockito.when(restaurant.getRstntNm()).thenReturn("또복이 식당");

        Mockito.when(restaurantService.getRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        mvc.perform(get("/api/restaurant/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.restaurantNo").value(1L))
                .andExpect(jsonPath("$.data.rstntNm").value("또복이 식당"));

    }

}