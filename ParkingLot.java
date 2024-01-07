import java.io.File;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Car[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively completing the 
		// private calculateLotDimensions(...) that I have provided
		calculateLotDimensions(strFilename);

		// instantiate the lotDesign and occupancy variables!
		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Car[numRows][numSpotsPerRow];

		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively completing the 
		// private populateFromFile(...) that I have provided
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		if(!canParkAt(i, j, c)){ //check if car can be parked at i,j
			System.out.print("Car " + c + " cannot be parked at (" + i + "," + j + ")" +"\n");
		}else{
			occupancy[i][j] = c; //park car 
		}
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) { 
		if(i >= numRows || j >= numSpotsPerRow || occupancy[i][j] == null){ //check if out of bounds or occupied
			return null;
		}
		Car c = occupancy[i][j]; //remove car from occupancy
		occupancy[i][j] = null;

		return c;
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		if (i >= numRows || j >= numSpotsPerRow || occupancy[i][j] != null) { //check if out of bounds or occupied
			return false;
		}
		CarType carType = c.getType(); //get type of car c
		CarType spotType = lotDesign[i][j]; //get type of spot at i,j
		if (carType == CarType.ELECTRIC){ //check if spot is compatible with car c
			return (spotType == CarType.ELECTRIC || spotType == CarType.SMALL || spotType == CarType.REGULAR
					|| spotType == CarType.LARGE);
			}
		else if (carType == CarType.SMALL){
			return (spotType == CarType.SMALL || spotType == CarType.REGULAR || spotType == CarType.LARGE);
			}
		else if (carType == CarType.REGULAR){
			return (spotType == CarType.REGULAR || spotType == CarType.LARGE);
		}
		else if (carType == CarType.LARGE){
			return (spotType == CarType.LARGE);
		}

		return false;
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		int counter = 0;

		for(int i = 0; i < numRows; i++) //loop over rows
			for(int j = 0; j < numSpotsPerRow; j++){ //loop over spots
				if(lotDesign[i][j] != CarType.NA){ //check if spot type is NA and update counter
					counter++;
				}
			}
		return counter;
	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		int counter = 0;

		for(int i = 0; i < numRows; i++) //loop over rows 
			for(int j = 0; j < numSpotsPerRow; j++){ //loop over spots
				if(occupancy[i][j] != null){ //check if spot is occupied and update counter
					counter++;
				}
			}
		return counter;
	}

	private void calculateLotDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		while (scanner.hasNext()) {
			String str = scanner.nextLine(); //reads file line by line and create string of each line

			if(str.isEmpty()){
				//do nothing to empty line
			} else if (str.equals(SECTIONER)) {
				break; //exit while loop when line is '###'
			} else {
				String[] lst = str.split(SEPARATOR); //populate numSpots and numRows using array
				numSpotsPerRow = lst.length;
				numRows++;
			}
		}
		scanner.close();
	}

	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		// YOU MAY NEED TO DEFINE SOME LOCAL VARIABLES HERE!
		int rowNum = 0;

		// while loop for reading the lot design
		while (scanner.hasNext()) {
			String str = scanner.nextLine();

			if(str.isEmpty()){
				//do nothing to empty line
			} else if (str.equals(SECTIONER)) {
				break; //exit while loop
			}else{
				String[] lst = str.split(SEPARATOR); //add line elements to array
				for (int i = 0; i < lst.length; i++)
					lotDesign[rowNum][i] = Util.getCarTypeByLabel(lst[i].trim()); //assign cartype from file to lot

				rowNum++; //read row num
			}
		}

		// while loop for reading occupancy data
		while (scanner.hasNext()) {
			String str = scanner.nextLine();

			if(str.isEmpty()) {
				//do nothing to empty line
			}else{
				String[] lst = str.split(SEPARATOR); //add line elements to array
				int i = Integer.parseInt(lst[0].trim()); //read rownum and spotnum and assign to int variables
				int j = Integer.parseInt(lst[1].trim());

				CarType carType = Util.getCarTypeByLabel(lst[2].trim()); //read cartype of car
				String plateNum = lst[3].trim(); //read license plate
				Car c = new Car(carType, plateNum); //initalize new instance of car from line data

				park(i, j, c); //park car
			}
		}

		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 * 
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}
		}
		return buffer.toString();
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

	}
}