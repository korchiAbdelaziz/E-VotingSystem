package evotingsystem.resultservice.feignclients;

import evotingsystem.resultservice.feignclients.models.ElectorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "voter-service")
public interface ElectionClient {

    @GetMapping("/api/electors/{id}")
    ElectorDTO getElectorById(@PathVariable("id") Long id);
}