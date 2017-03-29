******************************************
******************************************
        AI Scheduling Program
******************************************
******************************************

Author: Jose Perez <josegperez@mail.com>

Usage: 
    Use the commandline to run the application jar file as follows
    <> denotes parameters which are required

    java -jar schedule.jar <nBuild> <nRooms> <nCourses> <timeLimit> <algorithm> <seed>
        <nBuild> - Number of buildings
        <nRooms> - Number of rooms
        <nCourses> - Number of courses
        <timeLimit> - Time limit for algorithm (in seconds)
        <algorithm> - Algorithm to use for solving
            0 = Naive
            1 = Simulated Annealing
        <seed> - Seed for random number generation. 
                 Use the same seed in every experiment for consistent results.