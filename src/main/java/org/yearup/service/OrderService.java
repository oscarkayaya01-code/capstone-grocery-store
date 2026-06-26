package org.yearup.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ProfileRepository;
import org.yearup.repository.ShoppingCartRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private  OrderLineItemRepository checkoutRepository;
    private ShoppingCartService shoppingCartService;
    private  OrderLineItemRepository orderLineItemRepository;
    private ProfileRepository profileRepository;
    private  ProductService productService;


    @Autowired
    public OrderService(OrderRepository orderRepository, OrderLineItemRepository orderLineItemRepository,
                        ProfileRepository profileRepository, ProductService productService,
                        ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.profileRepository = profileRepository;
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Transactional
    public Order create(int userId) {
        var profile = profileRepository.findById(userId).orElse(null);
        if (profile == null) return null;

        var shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null || shoppingCart.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");

        var order = new Order();
        order.setUserId(userId);
        order.setDate(LocalDate.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        order.setShippingAmount(String.valueOf(0));

        Order savedOrder = orderRepository.save(order);


        for (var shops : shoppingCart) {
            Product product = productService.getById(shops.getProductId());
            OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setOrderId(savedOrder.getOrderId());
            orderLineItem.setProductId(product.getProductId());
            orderLineItem.setSalesPrice(product.getPrice());
            orderLineItem.setQuantity(shops.getQuantity());
            orderLineItem.setDiscount(0);

            orderLineItemRepository.save(orderLineItem);
        }

        shoppingCartRepository.deleteByUserId(userId);

        return savedOrder;

    }

}