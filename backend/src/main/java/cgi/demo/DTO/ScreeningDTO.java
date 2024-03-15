package cgi.demo.DTO;

import cgi.demo.entities.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreeningDTO {

    private Screening screening;

    private String score;

    private String posterUrl;
}
