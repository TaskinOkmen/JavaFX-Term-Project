/*	This Class return file paths as String based on level or high score file
 * 	
 * 	Taşkın ÖKMEN 	150122019
 * 	Harun SUBAŞI	150121060
 * 	Berke BAYRAM	150122009
 * */

package application;

import java.io.File;

public class FilePathHelper {

    // Get the file path for a specific level file based on the level number
    static String getLevelFile(Integer level) {
        return "src/main/java/levels/level" + level + ".txt";
    }

    // Get the file object for the high score file
    static File getHighScoreFile() {
        return new File("src/main/java/data/highScore.txt");
    }
}
