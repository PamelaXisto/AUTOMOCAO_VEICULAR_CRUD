package br.com.fecaf.dto.request;

public record VehicleDTO (
    String model,
    Integer year,
    String brand,
    Double mileage,
    String fuelType,
    Boolean availability,
    Double price,
    String imageUrl
){}
