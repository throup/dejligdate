package eu.throup.dejligdate;

import eu.throup.dejligdate.spike.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamplesTest {
    Date era = Date.ERA;
    Date epoch = new Date(1970, 1, 1);
    Date thirdMillenium = Date.parse("2001-01-01");
    Date fallOfTheWall = new Date(1989, 11, 9);
    Date firesAgain = Date.parse("2022-06-23");

    @Test
    void Era_isZeroDays_sinceTheEra() {
        assertEquals(0, era.daysSinceEra());
    }

    @Test
    void ThirdMillenium_is11323Days_sinceTheEraBegan() {
        // Think of all the fools celebrating one year earlier... :-D
        assertEquals(730485, thirdMillenium.daysSinceEra());
    }

    @Test
    void theWallFell_11323Days_beforeTheThirdMillenium() {
        assertEquals(4071, thirdMillenium.daysSince(fallOfTheWall));
    }

    @Test
    void thereAre_11323Days_betweenTheReturnOfSktHans_andTheEpoch() {
        assertEquals(19166, epoch.daysBetween(firesAgain));
    }
}
