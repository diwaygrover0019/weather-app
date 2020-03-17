package com.deloitte.diway.weatherapp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.support.MarshallingUtils;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import java.util.concurrent.CompletableFuture;

public class GlobalWeatherClient extends WebServiceGatewaySupport {

    private final Logger logger = LoggerFactory.getLogger(GlobalWeatherClient.class);

    @Async("taskExecutorGlobalWeatherClient")
    public CompletableFuture<String> callWebService(final Object request) {

        final String content = getWebServiceTemplate().sendAndReceive(
                message -> MarshallingUtils.marshal(getWebServiceTemplate().getMarshaller(), request, message),
                message -> {
                    DOMSource source = (DOMSource) message.getPayloadSource();
                    Node node = source.getNode().getFirstChild();
                    return node.getTextContent();
                });

        logger.debug("GlobalWeatherClient content: {}", content);
        logger.info("Downstream response received successfully.");
        return CompletableFuture.completedFuture(content);
    }

}
