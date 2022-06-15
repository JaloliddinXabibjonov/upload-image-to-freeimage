package UploadPhoto.upload.config;


import java.nio.charset.StandardCharsets;
import org.springframework.util.Base64Utils;
//import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import javax.net.ssl.SSLException;
import reactor.netty.http.client.HttpClient;
//import reactor.netty.transport.logging.AdvancedByteBufFormat;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(UploadProperties.class)
public class UploadConfiguration {

//    @Bean("uploadRestTemplate")
//    public RestTemplate uploadRestTemplate(RestTemplateBuilder restTemplateBuilder, UploadProperties uploadProperties) {
//        return restTemplateBuilder
//                .setReadTimeout(uploadProperties.getReadTimeout())
//                .setConnectTimeout(uploadProperties.getConnectTimeout())
//                .basicAuthorization(uploadProperties.getLogin(), uploadProperties.getPassword())
//                .build();
//    }


    @Bean("upload-web-client")
    public WebClient webClient(UploadProperties uploadProperties) throws SSLException {

        SslContext sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build();

        HttpClient httpClient = HttpClient
            .create().secure(t -> t.sslContext(sslContext))
//            .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
            ;

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl(uploadProperties.getUrl())
    //        .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((uploadProperties.getUsername() + ":" + uploadProperties.getPassword()).getBytes(StandardCharsets.UTF_8)))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    //                .filters(exchangeFilterFunctions -> {
    //                    exchangeFilterFunctions.add(logRequest());
    //                    exchangeFilterFunctions.add(logResponse());
    //                })
            .build();
    }


}
