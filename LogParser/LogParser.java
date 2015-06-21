import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogParser {

	//Database Object
	private static LogDatabase logDB = new LogDatabase();
	
	//main method drives execution
	//parses all files located in specified directory
	//and populates tables in DB with parsed data
	public static void main(String[] args) {
		
		String rootDirectory = "C:\\Users\\Adrian Garcia\\Downloads\\Captures";
		parseAllFilesInDirectory(rootDirectory);

	}
	
	//this method takes a root directory path and finds/parses all files
	//in that directory and subdirectories
	private static void parseAllFilesInDirectory(String rootDirectory) {
	    File directory = new File(rootDirectory);

	    File[] fList = directory.listFiles();

	    for (File file : fList) {
	        if (file.isFile()) {
	        	parseFileIntoDB(file.getAbsolutePath(),file.getName());
	        } else if (file.isDirectory()) {
	            parseAllFilesInDirectory(file.getAbsolutePath());
	        }
	    }
		
	}
	
	//this method will take a file, determine its extension and call the right parse method
	private static void parseFileIntoDB(String path, String filename){
	 	String[] splitFileName = filename.split("\\.");
	   	if(splitFileName[1].equals("capture")){
	   		parseCaptureIntoDB(path, filename);
	   	}
	   	else if(splitFileName[1].equals("mgencapture")){
	   		parseMgencaptureIntoDB(path, filename);
	   	}
	   	else if(splitFileName[1].equals("arff")){
	   		parseArffIntoDB(path, filename);
	   	}
	   	else{
	   		//invalid file format, do not try to parse file
	   	}
		
	}

	//this method will parse an .arff file and commit it to the DB
	private static void parseArffIntoDB(String path, String filename) {
		File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                //if line is not blank or line is not part of header, parse
                if(!line.isEmpty() && !line.contains("@")){
                
	                String[] attribute = line.split(",");
	
	                String rowPath = attribute[0];
	                int attackNodeNum = Integer.parseInt(attribute[1]);
	                String description = attribute[2];
	                int fromHop = Integer.parseInt(attribute[3]);
	                int toHop = Integer.parseInt(attribute[4]);
	                String type = attribute[5];
	                int distance = Integer.parseInt(attribute[6]);
	                String passthrough = attribute[7];
	                double beforeDelay = Double.parseDouble(attribute[8]);
	                double beforeMissed = Double.parseDouble(attribute[9]);
	                double beforeOOO = Double.parseDouble(attribute[10]);
	                double beforeNumPackets = Double.parseDouble(attribute[11]);
	                double duringDelay = Double.parseDouble(attribute[12]);
	                double duringMissed = Double.parseDouble(attribute[13]);
	                double duringOOO = Double.parseDouble(attribute[14]);
	                double duringNumPackets = Double.parseDouble(attribute[15]);
	                double afterDelay = Double.parseDouble(attribute[16]);
	                double afterMissed = Double.parseDouble(attribute[17]);
	                double afterOOO = Double.parseDouble(attribute[18]);
	                double afterNumPackets = Double.parseDouble(attribute[19]);
	                String srcSpoofed = attribute[20];
	                String destSpoofed = attribute[21];
	                int hopsToSpoofed = Integer.parseInt(attribute[22]);
	                String duringLinkLost = attribute[23];
	                String afterLinkLost = attribute[24];
	                String attackName = attribute[25];
	                int hopsFromSpoofedToDest = Integer.parseInt(attribute[26]);
	                String attackerCloserToDestThanSpoofed = attribute[27];
	                String spoofedBetweenAttacker = attribute[28];
	                String isDstBetweenSpoofedAndAttacker = attribute[29];
	                String spoofedBetweenAttackergw = attribute[30];
	                String isDstBetweenSpoofedAndAttackergw = attribute[31];
	                String isAttackerBetweenSpoofedAndDst = attribute[32];
	                String isAttackerBetweenSpoofedAndDstgw = attribute[33];
	                String isSrcBetweenSpoofedAndDst = attribute[34];
	                String isSrcBetweenSpoofedAndDstgw = attribute[35];
	                String altPathWithoutAttacker = attribute[36];
	                
	                String SQLQuery = "INSERT INTO `summerpracticum`.`arff` (`path`, "
	                		+ "`attackNodeNum`, "
	                		+ "`description`, "
	                		+ "`fromHop`, "
	                		+ "`toHop`, "
	                		+ "`type`, "
	                		+ "`distance`, "
	                		+ "`passthrough`, "
	                		+ "`beforeDelay`, "
	                		+ "`beforeMissed`, "
	                		+ "`beforeOOO`, "
	                		+ "`beforeNumPackets`, "
	                		+ "`duringDelay`, "
	                		+ "`duringMissed`, "
	                		+ "`duringOOO`, "
	                		+ "`duringNumPackets`, "
	                		+ "`afterDelay`, "
	                		+ "`afterMissed`, "
	                		+ "`afterOOO`, "
	                		+ "`afterNumPackets`, "
	                		+ "`srcSpoofed`, "
	                		+ "`destSpoofed`, "
	                		+ "`hopsToSpoofed`, "
	                		+ "`duringLinkLost`, "
	                		+ "`afterLinkLost`, "
	                		+ "`attackName`, "
	                		+ "`hopsFromSpoofedToDest`, "
	                		+ "`attackerCloserToDestThanSpoofed`, "
	                		+ "`spoofedBetweenAttacker`, "
	                		+ "`isDstBetweenSpoofedAndAttacker`, "
	                		+ "`spoofedBetweenAttackergw`, "
	                		+ "`isDstBetweenSpoofedAndAttackergw`, "
	                		+ "`isAttackerBetweenSpoofedAndDst`, "
	                		+ "`isAttackerBetweenSpoofedAndDstgw`, "
	                		+ "`isSrcBetweenSpoofedAndDst`, "
	                		+ "`isSrcBetweenSpoofedAndDstgw`, "
	                		+ "`altPathWithoutAttacker` "
	                		+ ") "
	                		+ "VALUES ('"+rowPath+"','"
	                		+attackNodeNum+"','"
	                		+description+"','"
	                		+fromHop+"','"
	                		+toHop+"','"
	                		+type+"','"
	                		+distance+"','"
	                		+passthrough+"','"
	                		+beforeDelay+"','"
	                		+beforeMissed+"','"
	                		+beforeOOO+"','"
	                		+beforeNumPackets+"','"
	                		+duringDelay+"','"
	                		+duringMissed+"','"
	                		+duringOOO+"','"
	                		+duringNumPackets+"','"
	                		+afterDelay+"','"
	                		+afterMissed+"','"
	                		+afterOOO+"','"
	                		+afterNumPackets+"','"
	                		+srcSpoofed+"','"
	                		+destSpoofed+"','"
	                		+hopsToSpoofed+"','"
	                		+duringLinkLost+"','"
	                		+afterLinkLost+"','"
	                		+attackName+"','"
	                		+hopsFromSpoofedToDest+"','"
	                		+attackerCloserToDestThanSpoofed+"','"
	                		+spoofedBetweenAttacker+"','"
	                		+isDstBetweenSpoofedAndAttacker+"','"
	                		+spoofedBetweenAttackergw+"','"
	                		+isDstBetweenSpoofedAndAttackergw+"','"
	                		+isAttackerBetweenSpoofedAndDst+"','"
	                		+isAttackerBetweenSpoofedAndDstgw+"','"
	                		+isSrcBetweenSpoofedAndDst+"','"
	                		+isSrcBetweenSpoofedAndDstgw+"','"
	                		+altPathWithoutAttacker+"')";
	                System.out.println(SQLQuery);
	
	                logDB.executeQuery(SQLQuery);
                }//end if
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	
	}

	//this method parses an .mgencapture file and commits it to the DB
	private static void parseMgencaptureIntoDB(String path, String filename) {
		File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                //if line is not blank, parse
                if(!line.isEmpty()){
                
	                String[] attribute = line.split(";");
	                //insert into DB
	                int time = Integer.parseInt(attribute[0]);
	                String[] splitNodeName = filename.split("\\.");
	                String node = splitNodeName[0].substring(1);
	                String flow = attribute[1];
	                double totalDelay = Double.parseDouble(attribute[2]);
	                double totalMissedPackets = Double.parseDouble(attribute[3]);
	                double totalOOO = Double.parseDouble(attribute[4]);
	                double totalNumPackets = Double.parseDouble(attribute[5]);
	                String attackRunning = attribute[6];
	                String route = attribute[7];
	                
	                parseMgenCaptureFlowIntoDB(path,time,node,flow);
	                parseRoutesIntoDB(path,time,node,route);
	                
	                String SQLQuery = "INSERT INTO `summerpracticum`.`mgencapture` (`path`,"
	                		+ " `time`,"
	                		+ " `node`,"
	                		+ " `totalDelay`,"
	                		+ " `totalMissedPackets`,"
	                		+ " `totalOOO`,"
	                		+ " `totalNumPackets`, "
	                		+ "`attackRunning`) " + "VALUES ('"+path+"','"
	                		+time+"','"
	                		+node+"','"
	                		+totalDelay+"','"
	                		+totalMissedPackets+"','"
	                		+totalOOO+"','"
	                		+totalNumPackets+"','"
	                		+attackRunning+"')";
	                
	                System.out.println(SQLQuery);
	                
	                logDB.executeQuery(SQLQuery);
                }//end if
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
	}

	//this method parses an mgen capture flow and commits it to the DB
	private static void parseMgenCaptureFlowIntoDB(String path, int time,
			String node, String flow) {
		String[] splitFlow = flow.split(":");
		
		//manipulate first half of flow splitFlow[0]
		String innerFlow = null;
		String proto = null;
		
		List<String> allMatches = new ArrayList<String>();
		
		Pattern p = Pattern.compile("\\'(.*?)\\'");
		Matcher m = p.matcher(flow);
		
		while(m.find())
		{
			allMatches.add(m.group(1));
		}
		
		if (!allMatches.isEmpty()){
		innerFlow = allMatches.get(0);
		proto = allMatches.get(1);
		}
		
		//manipulate second half of flow splitFlow[1]
		String[] parsedSecondHalf = splitFlow[1].split(",");
		
		double delay = Double.parseDouble(parsedSecondHalf[0].substring(2));
		double missedPackets = Double.parseDouble(parsedSecondHalf[1]); 
		double OOO = Double.parseDouble(parsedSecondHalf[2]);
		double numPackets = Double.parseDouble(parsedSecondHalf[3]);
		String dist = parsedSecondHalf[4];
		dist = dist.replace("'", "");
		
		String SQLQuery = "INSERT INTO `summerpracticum`.`mgencaptureflows` (`path`,"
				+ " `time`,"
				+ " `node`,"
				+ " `flow`,"
				+ " `proto`,"
				+ " `delay`,"
				+ " `missedPackets`,"
				+ " `ooo`,"
				+ " `numPackets`,"
				+ " `dist`) " + "VALUES ('"+path+"','"
				+time+"','"
				+node+"','"
				+innerFlow+"','"
				+proto+"','"
				+delay+"','"
				+missedPackets+"','"
				+OOO+"','"
				+numPackets+"','"
				+dist+"')";

		System.out.println(SQLQuery);
		
		logDB.executeQuery(SQLQuery);
	}

	//this file parses a .capture file and commits it to the DB
	private static void parseCaptureIntoDB(String path, String filename) {
		File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                //if line is not blank, parse
                if(!line.isEmpty()){
                	
	                String[] attribute = line.split(";");
	                //insert into DB
	                int time = Integer.parseInt(attribute[0]);
	                String[] splitNodeName = filename.split("\\.");
	                String node = splitNodeName[0].substring(1);
	                String flow = attribute[1];
	                String route = attribute[2];
	                String attackRunning = attribute[3];
	                
	                parseCaptureFlowIntoDB(path,time,node,flow);
	                parseRoutesIntoDB(path,time,node,route);
	                
	                String SQLQuery = "INSERT INTO `summerpracticum`.`capture` (`path`,"
	                		+ " `time`,"
	                		+ " `node`,"
	                		+ " `attackRunning`) VALUES ('"+path+"','"
	                		+time+"','"
	                		+node+"','"
	                		+attackRunning+"')";
	                
	                System.out.println(SQLQuery);
	
	                logDB.executeQuery(SQLQuery);
                }//end if
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
	}

	//this file populates the routes table on the DB
	private static void parseRoutesIntoDB(String path, int time, String node,
			String route) {
		route = route.replace("'", "''");
		String SQLQuery = "INSERT INTO `summerpracticum`.`routes` (`path`, "
				+ "`time`, "
				+ "`node`, "
				+ "`route`) " + "VALUES ('"+path+"','"
				+time+"','"
				+node+"','"
				+route+"')";
		
		System.out.println(SQLQuery);
		
		logDB.executeQuery(SQLQuery);
	}

	//this file parses a capture flow and commits it to the DB
	private static void parseCaptureFlowIntoDB(String path, int time,
			String node, String flow) {
		String innerFlow = null;
		String proto = null;
		String hopsToSrc = null;
		String hopsToDst = null;
		List<String> allMatches = new ArrayList<String>();
		
		Pattern p = Pattern.compile("\\'(.*?)\\'");
		Matcher m = p.matcher(flow);
		
		while(m.find())
		{
			allMatches.add(m.group(1));
		}
		
		if (!allMatches.isEmpty()){
		innerFlow = allMatches.get(0);
		proto = allMatches.get(1);
		hopsToSrc = allMatches.get(2);
		hopsToDst = allMatches.get(3);
		}
		String SQLQuery = "INSERT INTO `summerpracticum`.`captureflows` (`path`, "
				+ "`time`, "
				+ "`node`, "
				+ "`flow`, "
				+ "`proto`, "
				+ "`hopsToSrc`, "
				+ "`hopsToDst`) " + "VALUES ('"+path+"','"
				+time+"','"
				+node+"','"
				+innerFlow+"','"
				+proto+"','"
				+hopsToSrc+"','"
				+hopsToDst+"')";
		
		System.out.println(SQLQuery);
        
		logDB.executeQuery(SQLQuery);
	}
	
	
}
