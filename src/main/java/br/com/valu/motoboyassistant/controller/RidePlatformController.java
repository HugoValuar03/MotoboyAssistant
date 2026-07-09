package br.com.valu.motoboyassistant.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.valu.motoboyassistant.domain.RidePlatform;
import br.com.valu.motoboyassistant.dto.RidePlatformResponse;

@RestController
@RequestMapping("/platforms")
public class RidePlatformController {

    @GetMapping
    public List<RidePlatformResponse> findAll() {
        return Arrays.stream(RidePlatform.values())
                .map(platform -> new RidePlatformResponse(
                        platform.name(),
                        platform.getLabel()))
                .toList();
    }

}
