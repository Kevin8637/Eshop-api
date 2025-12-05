package org.greta.eshop_api.domain.services;

import org.greta.eshop_api.exceptions.ResourceNotFoundExceptions;
import org.greta.eshop_api.exposition.dtos.CustomerRequestDTO;
import org.greta.eshop_api.exposition.dtos.CustomerResponseDTO;
import org.greta.eshop_api.mappers.CustomerMapper;
import org.greta.eshop_api.persistence.entities.CustomerEntity;
import org.greta.eshop_api.persistence.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Cacheable("customers")
    public List<CustomerResponseDTO> findAll(){
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public CustomerResponseDTO findById(Long id){
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Client avec l'ID : "+ id + " n'existe pas"));

        return CustomerMapper.toDto(customer);
    }

    @CacheEvict(value = "customers", allEntries = true)
    public CustomerResponseDTO create (CustomerRequestDTO dto){
        CustomerEntity entity = CustomerMapper.toEntity(dto);
        CustomerEntity saved = customerRepository.save(entity);
        return CustomerMapper.toDto(saved);
    }

    @CacheEvict(value = "customers", allEntries = true)
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto){
        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundExceptions("Client " + id + " introuvable."));
        existing.updateForm(dto);
        CustomerEntity saved = customerRepository.save(existing);
        return CustomerMapper.toDto(saved);
    }

    public void delete(Long id){
        if(!customerRepository.existsById(id)){
            throw new ResourceNotFoundExceptions("Impossible de supprimer : client " + id + " introuvable.");
        }
        customerRepository.deleteById(id);
    }
}
