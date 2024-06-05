package com.shop.demo.service.impl;

import com.shop.demo.config.Constants;
import com.shop.demo.domain.dto.request.CreateOrderRequest;
import com.shop.demo.domain.dto.response.PageResponse;
import com.shop.demo.domain.entities.Customer;
import com.shop.demo.domain.entities.Item;
import com.shop.demo.domain.entities.Order;
import com.shop.demo.enums.OrderStatus;
import com.shop.demo.enums.Status;
import com.shop.demo.exceptions.ValidationException;
import com.shop.demo.repository.CustomerRepository;
import com.shop.demo.repository.OrderItemRepository;
import com.shop.demo.service.OrderItemService;
import com.shop.demo.service.StorageService;
import com.shop.demo.utils.BigDecimalUtil;
import com.shop.demo.domain.dto.request.SearchOrderRequest;
import com.shop.demo.domain.dto.response.OrderDetail;
import com.shop.demo.repository.ItemRepository;
import com.shop.demo.repository.OrderRepository;
import com.shop.demo.service.OrderService;
import com.shop.demo.utils.CustomMessage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderItemService orderItemService;
    private final StorageService storageService;
    private final OrderRepository  orderRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final CustomMessage customMessage;

    public OrderServiceImpl (
        OrderItemService orderItemService,
        StorageService storageService,
        OrderRepository  orderRepository,
        ItemRepository itemRepository,
        OrderItemRepository orderItemRepository,
        CustomerRepository customerRepository,
        CustomMessage customMessage
    ) {
        this.orderItemService = orderItemService;
        this.storageService = storageService;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.customMessage = customMessage;
    }

    @Override
    @Transactional
    public void createOrder(@Valid @NotNull(message = "api.request.invalid") CreateOrderRequest request) {
        Customer customer;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ValidationException(Constants.Label.CUSTOMER_ID, customMessage.get(Constants.ErrorCode.E0001, Constants.Label.CUSTOMER)));
        } else if (request.getCustomerRequest() != null) {
            customer = new Customer(request.getCustomerRequest());
        } else {
            throw new ValidationException(Constants.Label.CUSTOMER, customMessage.get(Constants.ErrorCode.E0010, Constants.Label.CUSTOMER));
        }
        List<Long> requestItemIds = request.getItems()
                .stream()
                .map(CreateOrderRequest.Items::getId)
                .collect(Collectors.toList());
        List<Item> items = itemRepository.findAllById(requestItemIds);
        List<Long> itemIds = items.stream().map(Item::getId).collect(Collectors.toList());

        Set<Long> notFoundItemIds = requestItemIds.stream().filter(i -> !itemIds.contains(i)).collect(Collectors.toSet());
        Set<Long> inActivatedItemIds = new HashSet<>();
        Set<Long> outOfStockItemIds = new HashSet<>();
        Map<Long, Integer> requestNumberMap = request.getItems().stream().collect(Collectors.toMap(CreateOrderRequest.Items::getId, CreateOrderRequest.Items::getNumber));
        Map<Item, Integer> cartMap = new HashMap<>();

        items.forEach(item -> {
            if (item.getStatus() == Status.INACTIVATE) inActivatedItemIds.add(item.getId());
            Integer number = requestNumberMap.get(item.getId());
            if (number == null || item.getStorage() == null || number > item.getStorage().getInventory()) {
                outOfStockItemIds.add(item.getId());
            } else {
                cartMap.put(item, number);
            }
        });

        Map<String, List<String>> errors = new HashMap<>();
        if (!notFoundItemIds.isEmpty()) {
            customMessage.addMessage(errors, Constants.Label.ITEM_ID, customMessage.get(Constants.ErrorCode.E0001, Constants.Label.ITEM + "(" + notFoundItemIds + ")"));
        }
        if (!inActivatedItemIds.isEmpty()) {
            customMessage.addMessage(errors, Constants.Label.ITEM_ID, customMessage.get(Constants.ErrorCode.E0011, Constants.Label.ITEM + "(" + inActivatedItemIds + ")"));
        }
        if (!outOfStockItemIds.isEmpty()) {
            customMessage.addMessage(errors, Constants.Label.ITEM_ID, customMessage.get(Constants.ErrorCode.E0012, Constants.Label.ITEM + "(" + outOfStockItemIds + ")"));
        }
        if (!errors.isEmpty()) throw new ValidationException(errors);

        Order order = new Order();
        order.setCustomer(customer);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> e : cartMap.entrySet()) {
            totalPrice = totalPrice.add(e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())));
        }
        order.setTotalPrice(BigDecimalUtil.roundUp(totalPrice));
        orderRepository.save(order);

        orderItemService.create(order, cartMap);
        storageService.lockItemInStorage(cartMap);
    }

    @Override
    public OrderDetail getDetail(Long id) {
        if (id == null)
            throw new ValidationException(Constants.Label.ORDER_ID, customMessage.get(Constants.ErrorCode.E0010, Constants.Label.ORDER_ID));
        Order order = orderRepository.getDetailById(id)
                .orElseThrow(() -> new ValidationException(Constants.Label.ORDER_ID, customMessage.get(Constants.ErrorCode.E0001, Constants.Label.ORDER)));
        return new OrderDetail(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        if (id == null)
            throw new ValidationException(Constants.Label.ORDER_ID, customMessage.get(Constants.ErrorCode.E0010, Constants.Label.ORDER_ID));
        Order order = orderRepository.getDetailById(id)
                .orElseThrow(() -> new ValidationException(Constants.Label.ORDER_ID, customMessage.get(Constants.ErrorCode.E0001, Constants.Label.ORDER)));
        if (!order.isCancelable()) {
            throw new ValidationException(Constants.Label.ORDER_ID, customMessage.get(Constants.ErrorCode.E0013, Constants.Label.ORDER));
        }
        order.setStatus(OrderStatus.CANCELLED);

        Map<Item, Integer> cartMap = new HashMap<>();
        order.getOrderItems().forEach(oi -> {
            cartMap.put(oi.getItem(), oi.getItemNumber());
        });

        orderRepository.save(order);
        storageService.unLockItemInStorage(cartMap);
    }

    @Override
    public PageResponse<OrderDetail> search(SearchOrderRequest request) {
        PageRequest pageable = PageRequest.of(request.getRealCurrentPage(), request.getRowsPerPage());

        return PageResponse.of(
                orderRepository.search(
                        request.getId(),
                        request.getCustomerName(),
                        request.getStatus(),
                        pageable
                ).map(OrderDetail::new)
        );
    }
}
