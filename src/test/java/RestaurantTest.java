import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class RestaurantTest {
    Restaurant restaurant;
    @Mock Restaurant mockedRestaurant;

    @BeforeEach
    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        MockitoAnnotations.initMocks(this);
        mockedRestaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(mockedRestaurant);
        LocalTime currentTime = LocalTime.parse("22:00:00");
        doReturn(currentTime).when(spyRestaurant).getCurrentTime();

        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(mockedRestaurant);
        LocalTime currentTime = LocalTime.parse("10:29:00");
        doReturn(currentTime).when(spyRestaurant).getCurrentTime();

        assertFalse(spyRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>> ORDER <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void calculate_order_Total_should_return_correct_price_when_one_item_is_added(){
        restaurant.addToMenu("Sizzling brownie",319);
        String item1 = "Sizzling brownie";
        assertEquals(319,restaurant.calculateOrderTotal("Sizzling brownie"));
    }

    @Test
    public void calculate_order_Total_should_return_correct_price_when_multiple_items_are_added() {
        restaurant.addToMenu("Sizzling brownie",319);
        String item1 = "Sizzling brownie";
        String item2 = "Sweet corn soup";
        assertEquals(319+119,restaurant.calculateOrderTotal("Sizzling brownie", "Sweet corn soup"));
    }

    @Test
    public void calculate_order_Total_should_return_correct_price_when_item_is_removed() {
        restaurant.addToMenu("Sizzling brownie",319);
        String item1 = "Sizzling brownie";
        String item2 = "Sweet corn soup";
        assertEquals(319+119,restaurant.calculateOrderTotal("Sizzling brownie", "Sweet corn soup"));
        //Assert after removing "Sizzling brownie" from order
        assertEquals(119,restaurant.calculateOrderTotal( "Sweet corn soup"));
    }

    @Test
    public void calculate_order_Total_should_return_zero_price_when_no_item_is_selected() {
        assertEquals(0,restaurant.calculateOrderTotal());
    }

    //<<<<<<<<<<<<<<<<<<<<<<< ORDER >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}