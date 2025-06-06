/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.ordenatareas.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ColaConsumer {

    @RabbitListener(queues = "${cola.tareas}")
    public void recibirMensaje(String mensaje) {
        System.out.println(" Mensaje recibido de RabbitMQ: " + mensaje);
    }
}
