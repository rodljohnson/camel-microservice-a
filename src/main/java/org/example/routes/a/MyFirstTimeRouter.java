package org.example.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimeRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent loggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("My constant message")
                .log("${body}")
//                .transform().constant("Time now is " + LocalDateTime.now())
//                .bean("getCurrentTimeBean")GetCurr.log("${body}")entTimeBean
                .bean(getCurrentTimeBean)
                .log("${body}")
                .bean(loggingProcessingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessing())
                .to("log:first-timer");
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}

//@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) {
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}

class SimpleLoggingProcessing implements Processor {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(Exchange exchange) {
        logger.info("SimpleLoggingProcessing {}", exchange.getMessage().getBody());
    }
}
