package com.pvptowerdefense.server.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * The type Socket config.
 */
@Configuration
public class SocketConfig {
	/**
	 * Server endpoint exporter server endpoint exporter.
	 *
	 * @return the server endpoint exporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
