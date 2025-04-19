import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.nio.*;
import static java.nio.file.StandardOpenOption.*;


public class FileListMaker {
    static ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args) {
        try
        {
            OpenFile();
            for (String l : list) {
                System.out.println(l);
            }
            System.out.println("************************************");

            list.add("Banana");
            for (String l : list) {
                System.out.println(l);
            }
            SaveFile();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void OpenFile() throws IOException, FileNotFoundException
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            selectedFile = chooser.getSelectedFile();
            Path file = selectedFile.toPath();
            InputStream in =
                    new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in));

            int line = 0;
            while(reader.ready())
            {
                rec = reader.readLine();
                list.add(rec);
                line++;
                // echo to screen
                System.out.printf("\nLine %4d %-60s ", line, rec);
            }

            reader.close(); // must close the file to seal it and flush buffer
            System.out.println("\n\nData file read!");
        }
        else  // user closed the file dialog wihtout choosing
        {
            System.out.println("Failed to choose a file to process");
            System.out.println("Run the program again!");
            System.exit(0);
        }

    }

    private static void SaveFile() throws IOException
    {
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\data.txt");
        OutputStream out =
                new BufferedOutputStream(Files.newOutputStream(file, CREATE));
        BufferedWriter writer =
                new BufferedWriter(new OutputStreamWriter(out));

        for(String rec : list)
        {
            writer.write(rec, 0, rec.length());
            writer.newLine();  // adds the new line

        }
        writer.close(); // must close the file to seal it and flush buffer
        System.out.println("Data file written!");

    }
}