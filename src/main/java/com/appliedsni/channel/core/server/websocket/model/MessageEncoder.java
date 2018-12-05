package com.appliedsni.channel.core.server.websocket.model;
import javax.websocket.EncodeException;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<Message> {

	private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    public String encode(Message message) throws EncodeException {
    	String jsonInString=null;
    	try {
			 jsonInString = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
        return jsonInString;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}