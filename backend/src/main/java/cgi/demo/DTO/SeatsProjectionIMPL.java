package cgi.demo.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatsProjectionIMPL implements SeatsProjection{
    private int row;
    private int seat;

}
