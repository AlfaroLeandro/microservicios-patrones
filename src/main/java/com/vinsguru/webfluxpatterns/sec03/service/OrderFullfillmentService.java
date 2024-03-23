package com.vinsguru.webfluxpatterns.sec03.service;

import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFullfillmentService {

    @Autowired
    private List<Orchestrator> orchestrators; //escanea todas las implementaciones de Orchestrator y las trae en una lista

    /**
     * Es thread safe ya que cada orquestador modifica su request y su response, si chocaran en los atributos
     * que cambian podria haber Race condition
     */
    public Mono<OrchestationRequestContext> placeOrder(OrchestationRequestContext context) {
        var publishers = orchestrators.stream()
                        .map(o -> o.create(context)) //solo obtiene los publishers pero no suscribe (no ejecuta nada)
                        .collect(Collectors.toList());

        //lista de publishers, array de respuestas
        //ejemplo: publisher 1 -> devuelve string, p2 -> integer, p3->dto, etc.
        return Mono.zip(publishers, array -> array[0]) //uso la primera posicion ya que todos devuelven  OrchestationRequestContext
                    .cast(OrchestationRequestContext.class)
                    .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestationRequestContext context) {
        var allSuccess = this.orchestrators.stream().allMatch(o -> o.isSuccess().test(context));
        var status = allSuccess? Status.SUCCESS : Status.FAILED;
        context.setStatus(status);
    }
}
