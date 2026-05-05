package br.com.fecaf.controller;

import br.com.fecaf.dto.request.RefreshTokenRequestDTO;
import br.com.fecaf.dto.response.RefreshTokenResponseDTO;
import br.com.fecaf.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService service;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(
            @RequestBody RefreshTokenRequestDTO request) {

        var response = service.refresh(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestBody RefreshTokenRequestDTO request) {

        service.revoke(request.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
