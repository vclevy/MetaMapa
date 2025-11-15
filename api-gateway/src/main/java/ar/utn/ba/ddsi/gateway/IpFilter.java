package ar.utn.ba.ddsi.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class IpFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(IpFilter.class);

    // Me traigo IPs permitidas y denegadas
    @Value("${app.security.ip.denied:}")
    private String[] deniedIpArray;

    @Value("${app.security.ip.allowed:}")
    private String[] allowedIpArray;

    private Set<String> deniedIps = Collections.emptySet();
    private Set<String> allowedIps = Collections.emptySet();

    @PostConstruct
    private void initializeSets() {
        if (deniedIpArray != null) {
            deniedIps = Stream.of(deniedIpArray)
                    .filter(ip -> ip != null && !ip.trim().isEmpty())
                    .collect(Collectors.toSet());
        }
        if (allowedIpArray != null) {
            allowedIps = Stream.of(allowedIpArray)
                    .filter(ip -> ip != null && !ip.trim().isEmpty())
                    .collect(Collectors.toSet());
        }


        if (!deniedIps.isEmpty()) {
            logger.info("IP Deny List cargada: {}", deniedIps);
        }
        if (!allowedIps.isEmpty()) {
            logger.info("IP Allow List cargada: {}", allowedIps);
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String remoteIp = getClientIp(exchange);
        logger.info("IP del cliente detectada: {}", remoteIp);

        if (deniedIps.contains(remoteIp)) {
            logger.warn("Acceso denegado para IP (en Deny List): {}", remoteIp);
            return blockRequest(exchange);
        }

        if (!allowedIps.isEmpty() && !allowedIps.contains(remoteIp)) {
            logger.warn("Acceso denegado para IP (no en Allow List): {}", remoteIp);
            return blockRequest(exchange);
        }


        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        // Ejecutar este filtro con alta prioridad
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    private String getClientIp(ServerWebExchange exchange) {

        String ip = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");

        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.contains(",")) {
                ip = ip.split(",")[0].trim();
            }
            return ip;
        }

        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getAddress().getHostAddress();
        }

        return "unknown";
    }

    private Mono<Void> blockRequest(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}