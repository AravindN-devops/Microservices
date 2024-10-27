package com.eazybank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private final static Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)){
            logger.debug("eazyBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeaders));
        }
        else {
            String correlationId = generateCorrelationId();
             exchange = filterUtility.setCorrectionId(exchange, correlationId);
             logger.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}",correlationId);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders){
        if (filterUtility.getCorrelationId(requestHeaders) != null){
            return true;
        }
        return false;
    }

    private String generateCorrelationId(){
        return UUID.randomUUID().toString();
    }
}
