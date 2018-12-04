package com.appliedsni.channel.core.server.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext)
    {
        System.setProperty("secret", "youtube");
        System.setProperty("interval", "60000");
        System.setProperty("threadCount", "20");
        System.setProperty("clockSkew", "10");
        System.setProperty("audience", "appliedsni");
        System.setProperty("issuer", "appliedsni");
        System.setProperty("authoritiesClaimName", "appliedsni");
        System.setProperty("refreshCountClaimName", "appliedsni");
        
        System.setProperty("refreshLimitClaimName", "appliedsni");
        System.setProperty("validFor", "500");
        System.setProperty("refreshLimit", "1000");
        System.setProperty("mongoPort", "27017");
        System.setProperty("mongoIp", "localhost");
        System.setProperty("mongoDBName", "channel");
        System.setProperty("companyLibPath", "channel");
        Properties props = new Properties();
        props.setProperty("asda","sfsef");
        props.setProperty("fsef","fasf");
        try {
			InitialContext ctx = new InitialContext(props);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
}