package sample;

public enum itemsEnum {
    bA(new Item("Item A",12.23,false,false,1)),
    bB(new Item("Item B",14.5,false,false,1)),
    bC(new Item("Item C",20.25,false,false,1)),
    bD(new Item("Item D", 9.8,false,false,1)),
    bE(new Item("Item E", 10.12,false,false,1)),
    bF(new Item("Item F",16.18,false,false,1));

    Item item;

    itemsEnum(Item a) {
        this.item = a;
    }

    public Item getItem() {
        return item;
    }
}
