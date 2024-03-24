package com.vinsguru.webfluxpatterns.sec07.service;

import com.vinsguru.webfluxpatterns.sec07.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec07.client.ReviewClient;
import com.vinsguru.webfluxpatterns.sec07.dto.ProductAggregate;
import com.vinsguru.webfluxpatterns.sec07.dto.ProductResponse;
import com.vinsguru.webfluxpatterns.sec07.dto.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;


    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Long id){
        return Mono.zip(
                this.productClient.getProduct(id),
                this.reviewClient.getReviews(id)
        ).map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toDto(ProductResponse product, List<Review> reviews) {
        return new ProductAggregate(
                product.id(),
                product.category(),
                product.description(),
                reviews
        );
    }

}
