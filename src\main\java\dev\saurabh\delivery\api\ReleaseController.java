package dev.saurabh.delivery.api;
import dev.saurabh.delivery.service.ReleaseService;import jakarta.validation.Valid;import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;import java.util.*;import java.util.UUID;
@RestController @RequestMapping("/api/releases") public class ReleaseController{
 private final ReleaseService service;public ReleaseController(ReleaseService s){service=s;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public ReleaseView create(@Valid @RequestBody CreateReleaseRequest r){return service.create(r);}
 @GetMapping("/{id}") public ReleaseView get(@PathVariable UUID id){return service.get(id);}
 @PostMapping("/{id}/start") public ReleaseView start(@PathVariable UUID id){return service.start(id);}
 @PostMapping("/{id}/metrics") public EvaluationView evaluate(@PathVariable UUID id,@Valid @RequestBody EvaluateMetricsRequest r){return service.evaluate(id,r);}
 @PostMapping("/{id}/resume") public ReleaseView resume(@PathVariable UUID id){return service.resume(id);}
 @GetMapping("/{id}/evaluations") public List<EvaluationView> history(@PathVariable UUID id){return service.history(id);}
}

