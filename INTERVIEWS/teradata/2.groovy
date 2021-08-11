import java.io.IOException;
import java.sql.SQLException;
import groovy.sql.Sql;
import org.apache.commons.io.FilenameUtils;

void copyFile (File sourceFile , String targetPath)
throws IOException
{
	def command = [
		"CMD",
		"/C",
		"XCOPY",
		"/i",
		"/y",
		sourceFile.canonicalPath,
		FilenameUtils.separatorsToSystem(targetPath)
	];
	def proc = command.execute();
	proc.waitFor();
	if ( proc.exitValue() != 0 ) throw new IOException("File copy error for " + sourceFile.canonicalPath + " exit code: " + proc.exitValue());
}

int executeCmd(List command, long nM)
{
	List script = [];
	script += command;
	
	def proc = script.execute();
	def sout = new StringBuffer();
	def serr = new StringBuffer();
	proc.consumeProcessOutput(sout, serr);
	if (nM == 0 || nM == null) {
		proc.waitFor();
	} else {
		proc.waitForOrKill(nM);
	}

	if(sout.size()>0) println("OS StdOut:\n" + sout.toString()); sout.delete(0, sout.length());
	if(serr.size()>0) println("OS StdErr:\n" + serr.toString()); serr.delete(0, serr.length());
	
	return proc.exitValue();
}

File extractPGPFile(File inFile, String targetPath)
{
	def command ;
	String unPGPFileName = FilenameUtils.removeExtension(inFile.name)+ ".txt"
	command = [
		"gpg",
//...
		"--yes" ,
		"--batch",
		"--output" ,
		FilenameUtils.separatorsToSystem(targetPath + "/" + unPGPFileName),
		"--decrypt",
		inFile.canonicalPath
	];
	
	int exitValue = executeCmd(command, 50000);
	def delInd = inFile.delete();

	return new File(unPGPFileName);
}

String pathEMLStore = "S:\\...\\";

def command = [
	"CMD",
	"/C",
	"ZERAT",
	"S:\\...\\get_last_mail.ini"
];
def sout = new StringBuffer();
def serr = new StringBuffer();

int result = 0;
int i = 0;
while (result == 0)
{
	def proc = new ProcessBuilder(command).start();
	proc.consumeProcessOutput(sout, serr);
	proc.waitFor();
	if(sout.size()>0) println "---------- Command-line stdout: ----------\n" + sout.toString();
	if(serr.size()>0) println "---------- Command-line stderr: ----------\n" + serr.toString();
	result = proc.exitValue();
	if (result == 0) i++;
	sout.delete(0, sout.length());
	serr.delete(0, serr.length());
}

new File(pathEMLStore).listFiles().findAll{file-> FilenameUtils.wildcardMatch(file.name, "TDAT*.pgp")}.each{ f->
	def ftxt = extractPGPFile(f, pathEMLStore + "ATM")
}


	def config = new ConfigSlurper().parse(new File("S:\\...\\config.groovy").toURL())
	String TargetTdpId = config.QueueHandler.databaseConnection.TargetTdpId;
	String TargetUserName = config.QueueHandler.databaseConnection.TargetUserName;
	String TargetUserPassword = config.QueueHandler.databaseConnection.TargetUserPassword;
	String TargetWorkingDatabase = config.QueueHandler.databases.TargetWorkingDatabase;
	String InQueueTable = config.QueueHandler.tables.InQueueTable;
	String pathMailGrabberOutputFolder = config.QueueHandler.path.pathMailGrabberOutputFolder;

	def sql = Sql.newInstance("jdbc:teradata://${TargetTdpId}/TMODE=TERA,CHARSET=UTF8", "${TargetUserName}", "${TargetUserPassword}", "com.teradata.jdbc.TeraDriver");
	new File(pathEMLStore).listFiles().findAll{file-> (FilenameUtils.getExtension(file.name) == "eml")}.each{ f->
		try{
			copyFile(f, pathMailGrabberOutputFolder);
			sql.execute(
				"INSERT INTO ${TargetWorkingDatabase}.${InQueueTable} (INCOMING_CHANNEL_ID, CANONICAL_FILE_PATH ) VALUES(?,?)" ,
				["1", pathMailGrabberOutputFolder + "${f.name}"]
			)
		} 
		catch (SQLException e) { println "!!! ERROR: ${f.canonicalPath} !!! " + e.getMessage() }
		catch (IOException e) { println "!!! ERROR: ${f.canonicalPath} !!! " + e.getMessage() }
	}
	sql.close();


