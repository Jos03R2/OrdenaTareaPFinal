package miumg.edu.gt.ordenatareas.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import miumg.edu.gt.ordenatareas.config.RabbitConfig;

@Service
public class ColaProducer {

    private final RabbitTemplate rabbitTemplate;

    public ColaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(String mensaje) {
        rabbitTemplate.convertAndSend(RabbitConfig.NOMBRE_COLA, mensaje);
        System.out.println(" Enviado a la cola: " + mensaje);
    }
}


