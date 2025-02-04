package com.anemona.consumer1.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anemona.consumer1.model.AlertaMensaje;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Consumer1Service {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${aneback.url}")
    private String anebackUrl;

    @RabbitListener(queues = "alertas.queue")
    public void procesarAlerta(AlertaMensaje alerta) {
        try {
            log.info("Recibiendo alerta para procesar: {}", alerta);

            //crear alerta en aneback
            String url = anebackUrl + "/api/alertas/ingreso/" + alerta.getId_paciente();
            log.info("Intentando enviar alerta a URL: {}", url);

            //convertir AlertaMensaje al formato pa aneback
            Map<String, Object> alertaRequest = new HashMap<>();
            alertaRequest.put("descripcion_alerta", alerta.getDescripcion_alerta());
            alertaRequest.put("nivel_alerta", alerta.getNivel_alerta());
            alertaRequest.put("parametro_alterado", alerta.getParametro_alterado());
            alertaRequest.put("visto", false);
            alertaRequest.put("estadoVital", Map.of("id_estado", alerta.getId_estado_vital()));

            log.info("Payload de la peticion: {}", alertaRequest);

            ResponseEntity<?> response = restTemplate.postForEntity(
                url,
                alertaRequest,
                Object.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Alerta guardada exitosamente");
            } else {
                log.error("Error al guardar la alerta: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error procesando la alerta: {}", e.getMessage());
        }
    }
    
}
