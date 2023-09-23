package FileTransfer;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileManage {
    // FileManager singleton instance for Multithread-safe implementation
    private static final FileManage instance = new FileManage();

    // file input stream for reading text files
    private FileInputStream inStream;

    // file output stream for writing text files
    private FileOutputStream outStream;

    /**
     * readFile method reads file from given path and returns string of file contents
     * @param fileName the fileName(file path) of the file
     * @return String that file content
     */
    public String readFile(String fileName) {
        String fileContent = "";
        try {
            inStream = new FileInputStream(fileName);
            // print read file
            int fileSize = inStream.available();
            for(int i = 0; i < fileSize; i++) {
                fileContent += (char) inStream.read();
            }
        } catch(Exception e) {
            System.err.println("File not found: " + fileName);
        } finally {
            try {
                if(inStream != null) {
                    inStream.close();
                }
            } catch(Exception ex) {
                System.err.println("Error while closing File I/O: " + ex);
            }
        }

        return fileContent;
    }

    /**
     * writeFile method creates a file with given name and fills it with given content.
     * @param fileName  output file name
     * @param fileContent   Content of the file
     */
    public void writeFile(String fileName, String fileContent) {
        try {
            outStream = new FileOutputStream(fileName);

            byte[] fileContentBytes = fileContent.getBytes();

            outStream.write(fileContentBytes);

        } catch(Exception e) {
            System.err.println("Error while writing into file " + fileName + ": " + e);
        } finally {
            try {
                if(outStream != null) {
                    outStream.close();
                }
            } catch(Exception ex) {
                System.err.println("Error while closing File I/O: " + ex);
            }
        }
    }

    /**
     * get file name with the provide file path
     * @param filePath  the file path
     * @return  the file name in String format
     */
    public String getFileName(String filePath) {

        String[] split =  filePath.split("\\/");

        return split[split.length - 1];
    }

    /**
     * get instance of File manage
     * @return instance of the class
     */
    public static FileManage getInstance() {
        return instance;
    }
}
