package test.java.co.uk.myhandicap.calculation;

import main.java.co.uk.myhandicap.calculation.HandicapCalculation;
import main.java.co.uk.myhandicap.exceptions.UserNotFoundException;
import main.java.co.uk.myhandicap.model.handicap.Handicap;
import main.java.co.uk.myhandicap.model.handicap.Hole;
import main.java.co.uk.myhandicap.model.handicap.Round;
import main.java.co.uk.myhandicap.model.handicap.ScoreCard;
import main.java.co.uk.myhandicap.model.user.User;
import main.java.co.uk.myhandicap.service.ScoreCardServiceImpl;
import main.java.co.uk.myhandicap.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Handicap Calculation Test
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 16/07/14
 * @project MyHandicapApp
 */
@RunWith(MockitoJUnitRunner.class)
public class HandicapCalculationTest {

    @InjectMocks
    private HandicapCalculation processor;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ScoreCardServiceImpl scoreCardService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void exceptionThrowForAnInvalidUserId() throws UserNotFoundException {

        when(userService.retrieveUserById(-1L)).thenReturn(null);

        expectedException.expect(UserNotFoundException.class);

        processor.calculateUserHandicapScore(-1L);

    }

    @Test
    public void defaultHandicapSetIfTheUserHasNotSubmittedAnyRoundsOfGolf() throws UserNotFoundException {

        User user = buildMockUser(1L);

        when(userService.retrieveUserById(user.getId())).thenReturn(user);

        List<ScoreCard> scoreCardList = new ArrayList<>();

        when(scoreCardService.retrieveUserScoredCardsById(user)).thenReturn(scoreCardList);

        Handicap playerHandicap = processor.calculateUserHandicapScore(1L);

        verify(scoreCardService, times(1)).retrieveUserScoredCardsById(Matchers.<User>any());
        assertEquals("17/07/2014", playerHandicap.getCalculatedOn());
        assertEquals("28", playerHandicap.getHandicapScore());

    }

    @Test
    public void userHandicapSuccessfullyReturnedWithAHandicapAndAllParameters() throws UserNotFoundException {

        User user = buildMockUser(1L);

        when(userService.retrieveUserById(user.getId())).thenReturn(user);

        List<List<String>> pars = new ArrayList<>();

        pars.add(new ArrayList<>(
                Arrays.asList("3", "3", "3", "3", "4", "3", "3", "3", "4",
                        "3", "3", "3", "3", "4", "3", "3", "3", "4"
                )));

        pars.add(new ArrayList<>(
                Arrays.asList("3", "3", "3", "3", "4", "3", "3", "3", "4",
                        "3", "3", "3", "3", "4", "3", "3", "3", "4"
                )));

        pars.add(new ArrayList<>(
                Arrays.asList("3", "3", "3", "3", "4", "3", "3", "3", "4",
                        "3", "3", "3", "3", "4", "3", "3", "3", "4"
                )));

        List<List<String>> scores = new ArrayList<>();

        scores.add(new ArrayList<>(
                Arrays.asList("4", "4", "5", "5", "5", "4", "8", "4", "5",
                        "4", "5", "4", "3", "7", "5", "5", "6", "5"
                )));

        scores.add(new ArrayList<>(
                Arrays.asList("4", "4", "4", "3", "6", "3", "3", "5", "5",
                        "3", "4", "5", "3", "5", "5", "4", "7", "4"
                )));

        scores.add(new ArrayList<>(
                Arrays.asList("5", "4", "4", "7", "6", "4", "3", "4", "5",
                        "4", "4", "5", "4", "5", "4", "4", "5", "7"
                )));

        List<ScoreCard> scoreCardList = buildMockScoreCard(3, pars, scores);

        when(scoreCardService.retrieveUserScoredCardsById(user)).thenReturn(scoreCardList);

        Handicap playerHandicap = processor.calculateUserHandicapScore(1L);
        assertEquals("17/07/2014", playerHandicap.getCalculatedOn());
        assertEquals("22", playerHandicap.getHandicapScore());
        assertEquals("3", playerHandicap.getNumberOfRounds());

    }

    private User buildMockUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    private List<ScoreCard> buildMockScoreCard(int numberOfScoreCards, List<List<String>> holeParList, List<List<String>> holeScoreList) {

        List<ScoreCard> scoreCardList = new ArrayList();

        for(int i = 1; i <= numberOfScoreCards; i++) {

            ScoreCard scoreCard = new ScoreCard();
            scoreCard.setPlayerId(1L);
            scoreCard.setSubmittedDate("15/07/2014");

            List<Round> golfRounds = new ArrayList<>();
            golfRounds.add(buildMockGolfRound(holeParList.get(i - 1), holeScoreList.get(i - 1)));

            scoreCard.setGolfRounds(golfRounds);

            scoreCardList.add(scoreCard);
        }

        return scoreCardList;
    }

    private Round buildMockGolfRound(List<String> holeParList, List<String> holeScoreList) {
        Round round = new Round();
        round.setCourseName("Course Name");
        round.setCoursePar("58");
        round.setPlayDate("16/07/2014");

        List<Hole> holes = new ArrayList<>();

        buildMockHoleAndAddToRound(holeParList, holeScoreList, holes);

        round.setHoles(holes);

        return round;
    }

    private void buildMockHoleAndAddToRound(List<String> holeParList, List<String> holeScoreList, List<Hole> holes) {
        for(int i = 0; i <= 17; i++) {

            Hole hole = new Hole();

            hole.setHolePar(holeParList.get(i));
            hole.setHoleScore(holeScoreList.get(i));

            holes.add(hole);
        }
    }

}
