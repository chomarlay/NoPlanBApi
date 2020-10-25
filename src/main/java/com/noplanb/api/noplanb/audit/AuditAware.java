package com.noplanb.api.noplanb.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditAware implements AuditorAware<String>{
	
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("NoPlanB");
	}

}
