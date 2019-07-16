package org.katas.refactoring;

import java.util.List;

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

    printsTheStateTax(output,calculateTotalSalesTax(order.getLineItems()));

    printTotalAmount(output,calculateTotalAmount(order.getLineItems()));

    return output.toString();
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

  private void printLineItem(StringBuilder output) {
    order.getLineItems().stream().forEach(e->output
        .append(e.getDescription())
        .append("\t")
        .append(e.getPrice())
        .append("\t")
        .append(e.getQuantity())
        .append("\t")
        .append(e.totalAmount())
        .append('\n')
    );
    System.out.println(output);
  }

  private void printNameAndAddressOfCustomer(StringBuilder output) {
    output.append(order.getCustomerName());
    output.append(order.getCustomerAddress());
  }

  private void printHeaders(StringBuilder output) {
    output.append("======Printing Orders======\n");
  }
}