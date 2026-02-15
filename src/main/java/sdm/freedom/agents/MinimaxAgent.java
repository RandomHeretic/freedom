package sdm.freedom.agents;

import sdm.freedom.Move;
import sdm.freedom.State;

import java.util.Arrays;
import java.util.Comparator;

public class MinimaxAgent extends AbstractAgent {

    private final int MAX_DEPTH_PLIES = 8;

    protected MinimaxAgent(int playerNumber) {
        super("AI Player", playerNumber);
    }

    @Override
    public Move selectNextMove(State s) {

        return Arrays.stream(s.getLegalSuccessors())
                .max(Comparator.comparingDouble(
                        m -> {
                            State newState = s.clone();
                            newState.applyMove(m, super.PLAYER_NUMBER);
                            return alphaBetaMinimaxStep(newState, MAX_DEPTH_PLIES, Integer.MIN_VALUE, Integer.MAX_VALUE, super.PLAYER_NUMBER);
                        }
                )).orElseThrow();

    }

    private int alphaBetaMinimaxStep(State s, int depth, int alpha, int beta, int currentPlayer) {
        int[] scores = s.giveBoard().evaluateBoard();
        int maximizerPlayerIdx = super.PLAYER_NUMBER-1;
        int minimizerPlayerIdx = 1-maximizerPlayerIdx;
        boolean maximise = currentPlayer == super.PLAYER_NUMBER;

        if(s.isTerminal())
            if(scores[maximizerPlayerIdx] > scores[minimizerPlayerIdx])
                return Integer.MAX_VALUE; //win
            else if(scores[maximizerPlayerIdx] < scores[minimizerPlayerIdx])
                return Integer.MIN_VALUE; //loss
            else
                return 0; //draw

        if(depth == 0)
            return scores[maximizerPlayerIdx] - scores[minimizerPlayerIdx];

        int bestScore = maximise ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for(Move next : s.getLegalSuccessors()) {
            State newState = s.clone();
            newState.applyMove(next, currentPlayer);
            int value = alphaBetaMinimaxStep(newState, depth-1, alpha, beta, 3-currentPlayer);
            bestScore = maximise ? Math.max(bestScore, value) :  Math.min(bestScore, value);
            alpha = maximise ? Math.max(alpha, bestScore) : alpha;
            beta = maximise ? beta : Math.min(beta, bestScore);
            if(alpha >= beta) break;
        }
        return bestScore;
    }
}
