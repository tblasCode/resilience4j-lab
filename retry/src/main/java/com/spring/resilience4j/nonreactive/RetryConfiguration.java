package com.spring.resilience4j.nonreactive;

import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetryConfiguration {

  Logger LOG = LoggerFactory.getLogger(RetryConfiguration.class);

  @Bean
  public RegistryEventConsumer<Retry> myRetryRegistryEventConsumer() {

    return new RegistryEventConsumer<Retry>() {
      @Override
      public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
        LOG.info("init onEntryAddedEvent");
        entryAddedEvent.getAddedEntry().getEventPublisher()
            .onEvent(event -> LOG.info("onEntryAddedEvent" + event.toString()));
      }

      @Override
      public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {

      }

      @Override
      public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {

      }
    };
  }
}
