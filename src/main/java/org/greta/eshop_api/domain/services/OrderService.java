package org.greta.eshop_api.domain.services;

import org.greta.eshop_api.domain.rules.OrderRules;
import org.greta.eshop_api.exceptions.ResourceNotFoundExceptions;
import org.greta.eshop_api.exposition.dtos.*;
import org.greta.eshop_api.mappers.OrderMapper;
import org.greta.eshop_api.persistence.entities.OrderEntity;
import org.greta.eshop_api.persistence.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Cacheable("orders")
    public List<OrderResponseDTO> findAll(){
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public OrderResponseDTO findById(Long id){
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Commande avec l'ID : "+ id + " n'existe pas"));

        return OrderMapper.toDto(order);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public OrderResponseDTO create (OrderRequestDTO dto){
        OrderEntity entity = OrderMapper.toEntity(dto);
        OrderRules.validateOrderStatus(entity);
        OrderEntity saved = orderRepository.save(entity);
        return OrderMapper.toDto(saved);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public OrderResponseDTO update (Long id, OrderRequestDTO dto){
        OrderEntity existing = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("Commande " + id + " introuvable."));
        existing.updateForm(dto);
        OrderRules.validateOrderStatus(existing);
        OrderEntity saved = orderRepository.save(existing);
        return OrderMapper.toDto(saved);
    }

    public void delete(Long id){
        if (!orderRepository.existsById(id)){
            throw new ResourceNotFoundExceptions("Impossible de supprimer : commande " + id + " introuvable.");
        }
        orderRepository.deleteById(id);
    }
}
