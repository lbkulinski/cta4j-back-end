package app.cta4j.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Represents the different transit lines in the system.")
public enum Line {
    @Schema(description = """
        The Red Line provides 24-hour train service between Howard on the North Side and 95th/Dan Ryan on the South \
        Side via subway through downtown Chicago.""")
    RED,

    @Schema(description = """
        The CTA Blue Line provides 24-hour rapid transit train service between Chicago-O'Hare International Airport \
        and the Forest Park terminal, via downtown Chicago.""")
    BLUE,

    @Schema(description = "The Brown Line operates rapid transit service, daily, from Kimball to downtown.")
    BROWN,

    @Schema(description = """
        The Green Line route provides rapid transit train service between Harlem in Forest Park, IL and Oak Park, IL \
        to 63rd Street on Chicago's South Side, through downtown via the Lake and Wabash sides of the Loop ‘L’.""")
    GREEN,

    @Schema(description = """
        The Orange Line provides rapid transit train service between Midway Airport and downtown (Loop), and provides \
        service to Chicago's Southwest Side.""")
    ORANGE,

    @Schema(description = """
        The Purple Line provides rapid transit train service between Linden (in Wilmette) and Howard (in Chicago) via \
        Evanston. Additionally, during weekday rush-periods, express service continues to downtown Loop.""")
    PURPLE,

    @Schema(description = """
        The Pink Line operates rapid transit service, daily, from 54th/Cermak (in Cicero, IL) to the downtown Chicago \
        Loop.""")
    PINK,

    @Schema(description = """
        The Yellow Line route provides rapid transit train service between Dempster-Skokie (in Skokie, IL) and Howard \
        (in Chicago), with connecting service to downtown Chicago via Purple Line Express or Red Line. This line is \
        also commonly known by its original service name: the "Skokie Swift.\"""")
    YELLOW,

    @Schema(description = """
        Represents a line that is not applicable. This is occasionally returned by the CTA API, but I am not sure \
        why at the moment.""")
    N_A;

    @JsonCreator
    public static Line parseString(String string) {
        Objects.requireNonNull(string);

        string = string.toUpperCase();

        return switch (string) {
            case "RED", "RED LINE" -> Line.RED;
            case "BLUE", "BLUE LINE" -> Line.BLUE;
            case "BRN", "BROWN LINE" -> Line.BROWN;
            case "G", "GREEN LINE" -> Line.GREEN;
            case "ORG", "ORANGE LINE" -> Line.ORANGE;
            case "P", "PURPLE LINE" -> Line.PURPLE;
            case "PINK", "PINK LINE" -> Line.PINK;
            case "Y", "YELLOW LINE" -> Line.YELLOW;
            case "N/A" -> Line.N_A;
            default -> {
                String message = "A line with the name \"%s\" does not exist".formatted(string);

                throw new IllegalArgumentException(message);
            }
        };
    }
}
