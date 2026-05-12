package br.com.fecaf.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representação de um veículo para cadastro ou atualização")
public record VehicleDTO (

        @Schema(description = "Modelo do veículo", example = "Civic G10")
        String model,

        @Schema(description = "Ano de fabricação", example = "2022")
        Integer year,

        @Schema(description = "Marca/Fabricante", example = "Honda")
        String brand,

        @Schema(description = "Quilometragem atual", example = "15500.50")
        Double mileage,

        @Schema(description = "Tipo de combustível", example = "Flex", allowableValues = {"Flex", "Gasolina", "Diesel", "Elétrico"})
        String fuelType,

        @Schema(description = "Status de disponibilidade para venda/aluguel", example = "true")
        Boolean availability,

        @Schema(description = "Preço sugerido", example = "120000.00")
        Double price,

        @Schema(description = "URL da imagem do veículo", example = "https://link-da-imagem.com/civic.jpg")
        String imageUrl
){}
