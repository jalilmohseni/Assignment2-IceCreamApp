package org.example.icecreamorderapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing the Ice Cream Order System.
 * Handles user interactions, calculations, and updates the UI accordingly.
 * Author: Jalil
 * Date: February 2025
 */
public class IceCreamController {

    private static final double KIDS_PRICE = 3.50;
    private static final double SINGLE_PRICE = 5.50;
    private static final double DOUBLE_PRICE = 7.50;
    private static final double TRIPLE_PRICE = 9.50;
    private static final double WAFFLE_PRICE = 1.50;
    private static final double TOPPING_PRICE = 0.50;
    private static final double TAX_RATE = 0.05;
    // FXML elements
    @FXML private RadioButton RdioBtnDouble, RdioBtnKids, RdioBtnSingle, RdioBtnTripple;
    @FXML private ToggleGroup sizeSelectionToggleGroup;
    @FXML private Button btnAdd, btnCheckOut, btnNewOrder, btnExit;
    @FXML private ComboBox<String> cbofirstflavors, cbosecondflavors, cbothirdflavors;
    @FXML private CheckBox chkboxCandy, chkboxChocolateExtra, chkboxFruit, chkboxNuts, chkboxWaffle;
    @FXML private Label lblSubtotal, lblTax, lblTotal;
    @FXML private ListView<String> lvSummary;

    private double totalPrice = 0.0;
    private final List<String> availableFlavors = List.of("Vanilla", "Chocolate", "Strawberry", "Mint", "Coffee");

    /**
     * Initializes the UI elements and sets default values.
     */
    @FXML
    public void initialize() {
        cbofirstflavors.getItems().addAll(availableFlavors);
        cbosecondflavors.getItems().addAll(availableFlavors);
        cbothirdflavors.getItems().addAll(availableFlavors);

        sizeSelectionToggleGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> setDefaultFlavours());
        RdioBtnSingle.setSelected(true);
        setDefaultFlavours();
        updateSummary();
    }
    /**
     * Adds the selected ice cream order to the summary list.
     */
    @FXML
    public void addToOrder(ActionEvent event) {
        double sizePrice = getSizePrice();
        double extrasPrice = getExtrasPrice();
        double totalItemPrice = sizePrice + extrasPrice;

        List<String> selectedFlavors = getSelectedFlavors();
        List<String> selectedExtras = getSelectedExtras();

        String orderEntry = String.format("Size: %s - Flavours: %s - Extras: %s - $%.2f", getSizeText(),
                String.join(", ", selectedFlavors), String.join(", ", selectedExtras), totalItemPrice);

        lvSummary.getItems().add(orderEntry);
        totalPrice += totalItemPrice;
        updateSummary();
    }
    /**
     * Calculates the final price including tax and displays it in the summary.
     */
    @FXML
    public void checkOut(ActionEvent event) {
        if (totalPrice == 0.0) {
            lvSummary.getItems().add("⚠ No items in order. Please add to order first.");
            return;
        }

        double tax = totalPrice * TAX_RATE;
        double finalTotal = totalPrice + tax;

        lvSummary.getItems().add("---------------");
        lvSummary.getItems().add(String.format("Subtotal: $%.2f", totalPrice));
        lvSummary.getItems().add(String.format("Tax (5%%): $%.2f", tax));
        lvSummary.getItems().add(String.format("Total: $%.2f", finalTotal));
    }
    /**
     * Clears the current order and resets selections.
     */
    @FXML
    public void newOrder(ActionEvent event) {
        lvSummary.getItems().clear();
        totalPrice = 0.0;
        setDefaultFlavours();
        updateSummary();
    }
    /**
     * Closes the application.
     */
    @FXML
    public void exitApplication(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
    /**
     * Sets the default flavors based on the selected size.
     */
    private void setDefaultFlavours() {
        cbofirstflavors.setVisible(true);
        cbosecondflavors.setVisible(false);
        cbothirdflavors.setVisible(false);
        cbofirstflavors.setValue("Vanilla");

        if (RdioBtnDouble.isSelected()) {
            cbosecondflavors.setVisible(true);
            cbosecondflavors.setValue("Chocolate");
        } else if (RdioBtnTripple.isSelected()) {
            cbosecondflavors.setVisible(true);
            cbothirdflavors.setVisible(true);
            cbosecondflavors.setValue("Chocolate");
            cbothirdflavors.setValue("Strawberry");
        }
    }
    /**
     * Gets the selected flavors based on the visible combo boxes.
     */
    private List<String> getSelectedFlavors() {
        List<String> flavors = new ArrayList<>();
        if (cbofirstflavors.getValue() != null) flavors.add(cbofirstflavors.getValue());
        if (cbosecondflavors.isVisible() && cbosecondflavors.getValue() != null) flavors.add(cbosecondflavors.getValue());
        if (cbothirdflavors.isVisible() && cbothirdflavors.getValue() != null) flavors.add(cbothirdflavors.getValue());
        return flavors;
    }
    /**
     * Gets the price based on the selected size.
     */
    private double getSizePrice() {
        if (RdioBtnKids.isSelected()) return KIDS_PRICE;
        if (RdioBtnSingle.isSelected()) return SINGLE_PRICE;
        if (RdioBtnDouble.isSelected()) return DOUBLE_PRICE;
        if (RdioBtnTripple.isSelected()) return TRIPLE_PRICE;
        return 0.0;
    }
    /**
     * Gets the selected size as text.
     */
    private String getSizeText() {
        if (RdioBtnKids.isSelected()) return "Kids";
        if (RdioBtnSingle.isSelected()) return "Single";
        if (RdioBtnDouble.isSelected()) return "Double";
        if (RdioBtnTripple.isSelected()) return "Triple";
        return "";
    }
    /**
     * Gets the selected extras.
     */
    private List<String> getSelectedExtras() {
        List<String> extras = new ArrayList<>();
        if (chkboxWaffle.isSelected()) extras.add("Waffle Bowl");
        if (chkboxCandy.isSelected()) extras.add("Candy");
        if (chkboxChocolateExtra.isSelected()) extras.add("Chocolate");
        if (chkboxFruit.isSelected()) extras.add("Fruit");
        if (chkboxNuts.isSelected()) extras.add("Nuts");
        return extras;
    }
    /**
     * Calculates the extra toppings price.
     */
    private double getExtrasPrice() {
        double extrasCost = 0.0;
        if (chkboxWaffle.isSelected()) extrasCost += WAFFLE_PRICE;
        if (chkboxCandy.isSelected()) extrasCost += TOPPING_PRICE;
        if (chkboxChocolateExtra.isSelected()) extrasCost += TOPPING_PRICE;
        if (chkboxFruit.isSelected()) extrasCost += TOPPING_PRICE;
        if (chkboxNuts.isSelected()) extrasCost += TOPPING_PRICE;
        return extrasCost;
    }
    /**
     * Updates the summary labels with the current order details.
     */
    private void updateSummary() {
        lblSubtotal.setText(String.format("Subtotal: $%.2f", totalPrice));
        lblTax.setText(String.format("Tax (5%%): $%.2f", totalPrice * TAX_RATE));
        lblTotal.setText(String.format("Total: $%.2f", totalPrice + (totalPrice * TAX_RATE)));
    }
}
