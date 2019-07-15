package org.katas.refactoring;

import java.util.HashMap;
import java.util.Map;

public class OrderReceipt {

  private Order order;
  private final double TAX_RATE = 0.10;

  public OrderReceipt(Order order) {
    this.order = order;
  }

  public String printReceipt() {
    StringBuilder output = new StringBuilder();

    printHeaders(output);

    printNameAndAddressOfCustomer(output);

    Map<String,Double> map = printLineItemsAndCalculateSalesTaxAndTot(output);
    printsTheStateTax(output, map.get("totSalesTax"));

    printTotalAmount(output, map.get("tot"));
    return output.toString();
  }

  private Map<String, Double> printLineItemsAndCalculateSalesTaxAndTot(StringBuilder output) {
    double totSalesTax = 0d;
    double tot = 0d;
    for (LineItem lineItem : order.getLineItems()) {
      printLineItem(output, lineItem);

      double salesTax = getSalesTax(lineItem);
      totSalesTax += salesTax;

      tot = calculateTotalAmountOfLineItem(tot, lineItem, salesTax);
    }
    Map<String, Double> map = new HashMap<>();
    map.put("totSalesTax", totSalesTax);
    map.put("tot", tot);
    return map;
  }

  private void printTotalAmount(StringBuilder output, double tot) {
    output.append("Total Amount").append('\t').append(tot);
  }

  private void printsTheStateTax(StringBuilder output, double totSalesTx) {
    output.append("Sales Tax").append('\t').append(totSalesTx);
  }

  private double calculateTotalAmountOfLineItem(double tot, LineItem lineItem, double salesTax) {
    tot += lineItem.totalAmount() + salesTax;
    return tot;
  }

  private double getSalesTax(LineItem lineItem) {
    return lineItem.totalAmount() * TAX_RATE;
  }

  private void printLineItem(StringBuilder output, LineItem lineItem) {
    output.append(lineItem.getDescription());
    output.append('\t');
    output.append(lineItem.getPrice());
    output.append('\t');
    output.append(lineItem.getQuantity());
    output.append('\t');
    output.append(lineItem.totalAmount());
    output.append('\n');
  }

  private void printNameAndAddressOfCustomer(StringBuilder output) {
    output.append(order.getCustomerName());
    output.append(order.getCustomerAddress());
  }

  private void printHeaders(StringBuilder output) {
    output.append("======Printing Orders======\n");
  }
}