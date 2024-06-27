package com.greentechpay.paymenthistoryservice.kafka;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentechpay.paymenthistoryservice.kafka.dto.PaymentSuccessEvent;
import com.greentechpay.paymenthistoryservice.kafka.dto.TResponse;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class PaymentSuccessEventDeserializer extends JsonDeserializer<PaymentSuccessEvent<?>> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public PaymentSuccessEvent<?> deserialize(String topic, Headers headers, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(PaymentSuccessEvent.class, TResponse.class);
            return OBJECT_MAPPER.readValue(data, javaType);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message", e);
        }
    }
}
