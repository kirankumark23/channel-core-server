package com.appliedsni.channel.core.server.websocket.model;
import java.io.IOException;

import javax.websocket.DecodeException;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.services.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<Message> {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);
    @Override
    public Message decode(String s) throws DecodeException {
    	Message message=null;
    	try {
    		message =objectMapper.readValue(s,Message.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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