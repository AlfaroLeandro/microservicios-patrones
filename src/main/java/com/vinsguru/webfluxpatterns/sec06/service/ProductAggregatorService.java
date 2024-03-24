package com.vinsguru.webfluxpatterns.sec06.service;

import com.vinsguru.webfluxpatterns.sec06.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec06.client.PromotionClient;
import com.vinsguru.webfluxpatterns.sec06.client.ReviewClient;
import com.vinsguru.webfluxpatterns.sec06.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PromotionClient promotionClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Long id){
        return Mono.zip(
                this.productClient.getProduct(id),
                this.promotionClient.getPromotion(id),
                this.reviewClient.getReviews(id)
        ).map(t -> toDto(t.getT1(), t.getT2(), t.getT3()));
    }

    private ProductAggregate toDto(ProductResponse product, PromotionResponse promotion, List<Review> reviews) {
        var amountSaved = product.price() * promotion.discount() / 100;
        var discountedPrice = product.price() - amountSaved;
        var price = new Price(product.price(),
                              promotion.discount(),
                              discountedPrice,
                              amountSaved,
                              promotion.endDate());

        return new ProductAggregate(
                product.id(),
                product.category(),
                product.description(),
                price,
                reviews
        );
    }

}
