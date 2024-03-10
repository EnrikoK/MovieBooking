package cgi.demo.DTO;

import cgi.demo.entities.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseConfirmationDTO {

    private Screening screening;

    private List<SeatsProjectionIMPL> seats;
}
