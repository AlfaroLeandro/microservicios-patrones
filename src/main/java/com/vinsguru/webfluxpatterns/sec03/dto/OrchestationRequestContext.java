package com.vinsguru.webfluxpatterns.sec03.dto;

import java.util.UUID;

public class OrchestationRequestContext {
    private UUID orderId = UUID.randomUUID();
    private OrderRequest orderRequest;
    private Integer productPrice;
    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;
    private InventoryRequest inventoryRequest;
    private InventoryResponse inventoryResponse;
    private ShippingRequest shippingRequest;
    private ShippingResponse shippingResponse;
    private Status status;

    public OrchestationRequestContext(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public OrchestationRequestContext() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public InventoryRequest getInventoryRequest() {
        return inventoryRequest;
    }

    public void setInventoryRequest(InventoryRequest inventoryRequest) {
        this.inventoryRequest = inventoryRequest;
    }

    public InventoryResponse getInventoryResponse() {
        return inventoryResponse;
    }

    public void setInventoryResponse(InventoryResponse inventoryResponse) {
        this.inventoryResponse = inventoryResponse;
    }

    public ShippingRequest getShippingRequest() {
        return shippingRequest;
    }

    public void setShippingRequest(ShippingRequest shippingRequest) {
        this.shippingRequest = shippingRequest;
    }

    public ShippingResponse getShippingResponse() {
        return shippingResponse;
    }

    public void setShippingResponse(ShippingResponse shippingResponse) {
        this.shippingResponse = shippingResponse;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
