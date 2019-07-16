package org.katas.refactoring;

import java.util.HashMap;
import java.util.List;
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
    printLineItem(output);

    Map<String, Double> map = printLineItemsAndCalculateSalesTaxAndTot(output);
    printsTheStateTax(output, map.get("totalSalesTax"));

    printTotalAmount(output, map.get("total"));
    return output.toString();
  }

  private Map<String, Double> printLineItemsAndCalculateSalesTaxAndTot(StringBuilder output) {
    double totalSalesTax = 0d;
    double totalPrice = 0d;
    for (LineItem lineItem : order.getLineItems()) {
      double salesTax = getSalesTax(lineItem);
      totalSalesTax += salesTax;
      totalPrice = calculateTotalAmountOfLineItem(totalPrice, lineItem, salesTax);
    }
    Map<String, Double> map = new HashMap<>();
    map.put("totalSalesTax", totalSalesTax);
    map.put("totalPrice", totalPrice);
    return map;
  }

  private double calculateTotalSalesTax(List<LineItem> lineItems) {
    return lineItems.stream()
        .mapToDouble(item -> item.totalAmount() * TAX_RATE)
        .sum();
  }

  private double calculateTotalAmount(List<LineItem> lineItems) {
    return lineItems.stream()
        .mapToDouble(item -> item.totalAmount() * TAX_RATE + item.totalAmount())
        .sum();
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

  private void printLineItem(StringBuilder output) {
    order.getLineItems().stream().map(e->output
        .append(e.getDescription())
        .append("\t")
        .append(e.getQuantity())
        .append("\t")
        .append(e.getQuantity())
        .append("\t")
        .append(e.totalAmount())
        .append('\n')
    );
  }

  private void printNameAndAddressOfCustomer(StringBuilder output) {
    output.append(order.getCustomerName());
    output.append(order.getCustomerAddress());
  }

  private void printHeaders(StringBuilder output) {
    output.append("======Printing Orders======\n");
  }
}