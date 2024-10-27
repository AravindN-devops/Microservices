package com.eazybank.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;


@Component
public class FilterUtility {

    final static public String CORRELATION_ID = "eazybank-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders){
        if(requestHeaders.get(CORRELATION_ID) != null) {
          List<String> requestHeadersList = requestHeaders.get(CORRELATION_ID);
            return requestHeadersList.stream().findFirst().get();
        }
        else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value){
        return  exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }

    public ServerWebExchange setCorrectionId(ServerWebExchange exchange,String correlationId){
        return this.setRequestHeader(exchange,CORRELATION_ID,correlationId);
    }
}
