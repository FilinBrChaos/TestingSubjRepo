import org.example.Statistic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {
    private Statistic statistic;

    @BeforeEach
    void beforeEach() {
        statistic = new Statistic();
    }

    @Test
    public void getAverageSuccess() {
        List<Double> scores;
        scores = new ArrayList<>();
        scores.add(30d);
        scores.add(50d);
        scores.add(70d);

        assertEquals(50, statistic.getAverageScore(scores));
    }

    @Test
    void getAverageWEmptyInputSuccess() {
        assertEquals(0, statistic.getAverageScore(Collections.emptyList()));
    }

    @Test
    void getAverageWOneMarkSuccess() {
        List<Double> oneMark = new ArrayList();
        oneMark.add(50d);
        assertEquals(50, statistic.getAverageScore(oneMark));
    }

    @Test
    void getAverageWNegativeDataFail() {
        List<Double> badArr = new ArrayList();
        badArr.add(10d);
        badArr.add(-10d);

        String message = assertThrows(
                IllegalArgumentException.class,
                () -> statistic.getAverageScore(badArr)
        ).getMessage();
        assertEquals(
                "Invalid mark (Below zero).",
                message
        );
    }

    @Test
    void getAverageWOverflowDataFail() {
        List<Double> badArr = new ArrayList();
        badArr.add(30d);
        badArr.add(101d);

        String message = assertThrows(
                IllegalArgumentException.class,
                () -> statistic.getAverageScore(badArr)
        ).getMessage();
        assertEquals(
                "Invalid mark (Above hundred).",
                message
        );
    }
}
