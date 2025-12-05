package org.greta.eshop_api.domain.services;

import org.greta.eshop_api.domain.rules.ProductRules;
import org.greta.eshop_api.exceptions.ResourceNotFoundExceptions;
import org.greta.eshop_api.exposition.dtos.ProductRequestDTO;
import org.greta.eshop_api.exposition.dtos.ProductResponseDTO;
import org.greta.eshop_api.mappers.ProductMapper;
import org.greta.eshop_api.persistence.entities.ProductEntity;
import org.greta.eshop_api.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Cacheable("products")
    public List<ProductResponseDTO> findAll(){
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public List<ProductResponseDTO> searchProduct(String keyword){
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    public ProductResponseDTO findById(Long id){
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Produit avec l'ID : "+ id + " n'existe pas"));

        return ProductMapper.toDto(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDTO create (ProductRequestDTO dto){
        ProductEntity entity = ProductMapper.toEntity(dto);
        ProductRules.validateBeforeCreation(entity);
        ProductEntity saved = productRepository.save(entity);
        return ProductMapper.toDto(saved);
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDTO update (Long id, ProductRequestDTO dto){
        ProductEntity existing = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("Produit " + id + " introuvable."));
        existing.updateForm(dto);
        ProductRules.validateBeforeUpdate(existing);
        ProductEntity saved = productRepository.save(existing);
        return ProductMapper.toDto(saved);
    }

    public void delete(Long id){
        if (!productRepository.existsById(id)){
            throw new ResourceNotFoundExceptions("Impossible de supprimer : produit " + id + " introuvable.");
        }
        productRepository.deleteById(id);
    }

    public void deleteAll(){
        if(!(productRepository.count() >= 0)){
            throw new ResourceNotFoundExceptions("Impossible de tout supprimer");
        }
        productRepository.deleteAll();
    }
}
