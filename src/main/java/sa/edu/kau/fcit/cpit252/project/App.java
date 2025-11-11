package sa.edu.kau.fcit.cpit252.project;

//It is a command-line program. so here we start with options and stuff
//Ask User what they want to view
        /*
        [0] Inventory database (there they can view, update and delete like they want)
        [1] Create Discount rule (here you make a rule with all its configs)
        [2] View already-made current discount rules and what items is it assigned to
        [3] Assign an item with a rule (maybe using an id for each rule and item like assign i7 to r3 or something)
        [4] Exit
        [5] print QR code of Item by its item_id
         */

//the plan is that each feature has its own class file, to keep this main class as clean as possible

public class App {
    public static void main(String[] args) {

        System.out.println("" +
                "*\n" +
                "*\n" +
                "*\n" +
                "-- Welcome to DynamicDiscountManager --\n" +
                "Manage your inventory stocks! \n" +
                "Create your Discount rules! \n" +
                "and print QR codes for live prices!");

        System.out.println("\n" +
                "        [0] Inventory database (there they can view, update and delete like they want)\n" +
                "        [1] Create Discount rule (here you make a rule with all its configs)\n" +
                "        [2] View already-made current discount rules and what items is it assigned to\n" +
                "        [3] Assign an item with a rule (maybe using an id for each rule and item like assign i7 to r3 or something)\n" +
                "        [4] print QR code of Item by its item_id\n" +
                "        [5] Exit"
        );

    }
}