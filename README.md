# resilience4j-lab

== Introduction

Resilience4j is a lightweight fault tolerance library designed for functional programming.
Resilience4j provides higher-order functions (decorators) to enhance any functional interface,
lambda expression or method reference with a Circuit Breaker, Rate Limiter, Retry or Bulkhead.
You can stack more than one decorator on any functional interface, lambda expression or method reference.
The advantage is that you have the choice to select the decorators you need and nothing else.

Resilience4j 2 requires Java 17.

[source,java]
----
// Create a CircuitBreaker with default configuration
CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendService");

// Create a Retry with default configuration
// 3 retry attempts and a fixed time interval between retries of 500ms
Retry retry = Retry.ofDefaults("backendService");

// Create a Bulkhead with default configuration
Bulkhead bulkhead = Bulkhead.ofDefaults("backendService");

Supplier<String> supplier = () -> backendService
  .doSomething(param1, param2);

// Decorate your call to backendService.doSomething()
// with a Bulkhead, CircuitBreaker and Retry
// **note: you will need the resilience4j-all dependency for this
Supplier<String> decoratedSupplier = Decorators.ofSupplier(supplier)
  .withCircuitBreaker(circuitBreaker)
  .withBulkhead(bulkhead)
  .withRetry(retry)
  .decorate();

// Execute the decorated supplier and recover from any exception
String result = Try.ofSupplier(decoratedSupplier)
  .recover(throwable -> "Hello from Recovery").get();

// When you don't want to decorate your lambda expression,
// but just execute it and protect the call by a CircuitBreaker.
String result = circuitBreaker
  .executeSupplier(backendService::doSomething);

// You can also run the supplier asynchronously in a ThreadPoolBulkhead
 ThreadPoolBulkhead threadPoolBulkhead = ThreadPoolBulkhead
  .ofDefaults("backendService");

// The Scheduler is needed to schedule a timeout on a non-blocking CompletableFuture
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
TimeLimiter timeLimiter = TimeLimiter.of(Duration.ofSeconds(1));

CompletableFuture<String> future = Decorators.ofSupplier(supplier)
    .withThreadPoolBulkhead(threadPoolBulkhead)
    .withTimeLimiter(timeLimiter, scheduler)
    .withCircuitBreaker(circuitBreaker)
    .withFallback(asList(TimeoutException.class, CallNotPermittedException.class, BulkheadFullException.class),
      throwable -> "Hello from Recovery")
    .get().toCompletableFuture();
----

NOTE: With Resilience4j you don’t have to go all-in, you can
https://mvnrepository.com/artifact/io.github.resilience4j[*pick what you need*].

==  Documentation

Setup and usage is described in our *https://resilience4j.readme.io/docs[User Guide]*.

- https://github.com/resilience4j-docs-ja/resilience4j-docs-ja[有志による日本語訳(非公式) Japanese translation by volunteers(Unofficial)]

- https://github.com/lmhmhl/Resilience4j-Guides-Chinese[这是Resilience4j的非官方中文文档 Chinese translation by volunteers(Unofficial)]

== Overview

Resilience4j provides several core modules:

* resilience4j-circuitbreaker: Circuit breaking
* resilience4j-ratelimiter: Rate limiting
* resilience4j-bulkhead: Bulkheading
* resilience4j-retry: Automatic retrying (sync and async)
* resilience4j-timelimiter: Timeout handling
* resilience4j-cache: Result caching

There are also add-on modules for metrics, Retrofit, Feign, Kotlin, Spring, Ratpack, Vertx, RxJava2 and more.

NOTE: Find out full list of modules in our *https://resilience4j.readme.io/docs#section-modularization[User Guide]*.

TIP: For core modules package or `Decorators` builder see *https://mvnrepository.com/artifact/io.github.resilience4j/resilience4j-all[resilience4j-all]*.