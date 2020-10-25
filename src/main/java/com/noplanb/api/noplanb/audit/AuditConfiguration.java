package com.noplanb.api.noplanb.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditAwareRef")
public class AuditConfiguration {
	
	@Bean
	public AuditAware auditAwareRef() {
		return new AuditAware();
	}
}
