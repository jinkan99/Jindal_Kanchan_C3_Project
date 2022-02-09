import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    List<String> itemsSelectedByUser = new ArrayList<String>();
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void setup(){

        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant rest = Mockito.spy(restaurant);

        Mockito.when(rest.getCurrentTime()).thenReturn(LocalTime.parse("21:45:00"));

        assertTrue(rest.isRestaurantOpen());


    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant rest = Mockito.spy(restaurant);
        LocalTime localTime1 = LocalTime.parse("10:00:00");
        LocalTime localTime = LocalTime.parse("23:00:00");

        Mockito.when(rest.getCurrentTime()).thenReturn(localTime, localTime1);


        assertFalse(rest.isRestaurantOpen());
        assertFalse(rest.isRestaurantOpen());


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

    //<<<<<<<<<<<<<<<<<<<adding new feature of calculating total cost for all the items added>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void the_cost_of_items_selected_should_be_added_to_return_the_total_cost_of_the_order(){

        itemsSelectedByUser = restaurant.itemSelected("Sweet corn soup");
        itemsSelectedByUser = restaurant.itemSelected("Vegetable lasagne");

        int ActualCost = restaurant.calculateTotalCost(itemsSelectedByUser);
        int expectedCost = 119 + 269; // adding the cost of items selected by the user

        assertEquals(2, itemsSelectedByUser.size());
        assertThat(itemsSelectedByUser, hasItems("Sweet corn soup","Vegetable lasagne"));
        assertEquals(expectedCost, ActualCost);
    }

    @Test
    public void if_none_of_items_are_selected_then_it_should_return_the_total_cost_of_the_order_as_zero(){

        int ActualCost = restaurant.calculateTotalCost(itemsSelectedByUser);

        assertEquals(0, ActualCost);
    }
}