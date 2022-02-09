import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();
    private List<String> items = new ArrayList<String>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        //DELETE ABOVE STATEMENT AND WRITE CODE HERE
        LocalTime lt = getCurrentTime();
        if(lt.isBefore(closingTime) && lt.isAfter(openingTime)){
            return true;
        }
        return false;
    }

    public LocalTime getCurrentTime(){

        return  LocalTime.now();
    }

    public List<Item> getMenu() {
        //DELETE ABOVE RETURN STATEMENT AND WRITE CODE HERE
       return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {

        return name;
    }

    public List<String> itemSelected(String itemName){
        for(Item i : menu){
            if(i.getName().equals(itemName))
                items.add(i.getName());
        }
        return items;
    }

    protected int calculateTotalCost(List<String> items) {
        int totalCost =0;
        for(String s : items){
            Item i = findItemByName(s);
            totalCost = totalCost + i.getPrice();
        }
        return totalCost;
    }

}
