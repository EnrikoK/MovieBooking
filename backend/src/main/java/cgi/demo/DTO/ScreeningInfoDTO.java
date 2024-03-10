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
public class ScreeningInfoDTO {

    private Screening screening;
    private List<SeatsProjection> takenSeats;
}
