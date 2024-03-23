package com.vinsguru.webfluxpatterns.sec03.service;

import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderCancellationService {

    private Sinks.Many<OrchestationRequestContext> sink; //factory de Flux o Mono
    private Flux<OrchestationRequestContext> flux;

    @Autowired
    private List<Orchestrator> orchestrators;

    @PostConstruct
    public void init() { //multicast -> lo que se publique se manda de forma independiente a todos los suscriptores
        this.sink = Sinks.many().multicast().onBackpressureBuffer(); //agrega un buffer por si se acumulan los datos
        this.flux = this.sink.asFlux().publishOn(Schedulers.boundedElastic()); // crea un Scheduler que asigna un hilo elástico (elastic thread) a cada suscripción, limitando el número de hilos activos.
        orchestrators.forEach(o -> this.flux.subscribe(o.cancel())); //manda a cancelar todos los orquestadores y los deja suscriptos al flux
    }

    public void cancelOrder(OrchestationRequestContext context) {
        this.sink.tryEmitNext(context);
    }

}
