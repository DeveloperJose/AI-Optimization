public class SearchAlgorithm {
    // This is a very naive baseline scheduling strategy
    // It should be easily beaten by any reasonable strategy
    public Schedule naiveBaseline(SchedulingProblem problem, long deadline) {

        // get an empty solution to start from
        Schedule solution = problem.getEmptySchedule();

        for (int i = 0; i < problem.courses.size(); i++) {
            Course c = problem.courses.get(i);
            boolean scheduled = false;
            for (int j = 0; j < c.timeSlotValues.length; j++) {
                if (scheduled)
                    break;
                if (c.timeSlotValues[j] > 0) {
                    for (int k = 0; k < problem.rooms.size(); k++) {
                        if (solution.schedule[k][j] < 0) {
                            solution.schedule[k][j] = i;
                            scheduled = true;
                            break;
                        }
                    }
                }
            }
        }
        return solution;
    }

    private static double INITIAL_TEMP = 100;
    private static double COOLING_RATE = 0.0001;

    private static double coolingFunction(double temp, double timeleft, double difference) {
        return temp - (COOLING_RATE);
    }

    public Schedule simulatedAnnealing(SchedulingProblem problem, long deadline) {
        System.out.printf("Initial Temp: %s, Cooling Rate: %s\n", INITIAL_TEMP, COOLING_RATE);
        Schedule currentSolution = problem.getEmptySchedule();

        // Put all courses in the schedule naively to have a starting point
        for (int i = 0; i < problem.courses.size(); i++) {
            Course c = problem.courses.get(i);
            boolean scheduled = false;
            for (int j = 0; j < c.timeSlotValues.length; j++) {
                if (scheduled)
                    break;
                if (c.timeSlotValues[j] > 0) {
                    for (int k = 0; k < problem.rooms.size(); k++) {
                        if (currentSolution.schedule[k][j] < 0) {
                            currentSolution.schedule[k][j] = i;
                            scheduled = true;
                            break;
                        }
                    }
                }
            }
        }

        double temp = INITIAL_TEMP;
        double bestScore = problem.evaluateSchedule(currentSolution);
        Schedule bestSolution = currentSolution;

        // Main loop
        double timeleft = deadline - System.currentTimeMillis();
        while (timeleft > 0) {
            if (temp < 0)
                return bestSolution;

            // Create successor
            Schedule next = currentSolution.nextArrangement(problem);
            double nextScore = problem.evaluateSchedule(next);

            // Update temperature
            double difference = bestScore - nextScore;
            temp = coolingFunction(temp, timeleft, difference);

            // Successor is better than our best so far
            if (nextScore > bestScore) {
                bestScore = nextScore;
                bestSolution = next;
                currentSolution = next;
            } else {
                // Successor is worse. Use probability to decide if we want to
                // check it out
                double prob = Math.exp((nextScore - bestScore) / temp);
                // Use seed from main program to maintain consistency
                double rand = problem.random.nextDouble();

                if (prob > rand)
                    currentSolution = next;
            }

            // Update time
            timeleft = deadline - System.currentTimeMillis();
        }
        return bestSolution;
    }
}
