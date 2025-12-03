package evotingsystem.voteservice.feignclients;

import evotingsystem.voteservice.feignclients.Elector;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "voter-service")
public interface ElectorClient {

    @GetMapping("/api/electors/{id}")
    Elector getElectorById(@PathVariable("id") Long id);
}