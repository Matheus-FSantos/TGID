package io.github.matheusfsantos.tgid.model.service.webhook;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="Webhook", url="https://webhook.site/cd585e9d-eedd-427f-8f6c-4ce2a0f1f103")
public interface WebHookSenderService {
	
	@PostMapping
	void sendMessage(@RequestBody String payload);
	
}
