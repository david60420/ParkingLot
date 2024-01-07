public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
	
		int n = 0;
		double average;

		while(average > THRESHOLD); {//System.out.println("\n==== Setting lot capacity to: " + (++n) + "====");
			int size = 0;
			for (int i = 0; i < NUM_RUNS; i++) {
				//long startTime = System.currentTimeMillis();
				Simulator simu = new Simulator(new ParkingLot(n), hourlyRate, 24 * 3600);
				simu.simulate();
				//long endTime = System.currentTimeMillis();
				//long runTime = endTime - startTime;
				//System.out.println("Simulation run " + i + " (" + runTime + "ms); Queue length at the end of " +
						"simulation run: " + simu.getIncomingQueueSize());
				size = size + sim.getIncomingQueueSize();
			}
			average = size / 10.0;
		}
		return n;
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}