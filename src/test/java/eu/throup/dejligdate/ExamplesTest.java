package eu.throup.dejligdate;

import eu.throup.dejligdate.spike.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamplesTest {
    Date epoch = Date.EPOCH;
    Date thirdMillenium = Date.parse("2001-01-01");
    Date fallOfTheWall = new Date(1989, 11, 9);
    Date firesAgain = Date.parse("2022-06-23");

    @Test
    void Epoch_isZeroDays_sinceTheEpoch() {
        assertEquals(0, epoch.daysSinceEpoch());
    }

    @Test
    void ThirdMillenium_is11323Days_sinceTheEpoch() {
        // Think of all the fools celebrating one year earlier... :-D
        assertEquals(11323, thirdMillenium.daysSinceEpoch());
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
