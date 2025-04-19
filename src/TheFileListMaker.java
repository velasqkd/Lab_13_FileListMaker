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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.util.Scanner;


public class TheFileListMaker {
    static ArrayList<String> list = new ArrayList<>();
    static Scanner in = new Scanner(System.in);
    static boolean fileNeedsSaved = false;
    static String currentFileName = "";

    public static void main(String[] args) {
        boolean running = true;
        String cmd;

        do {
            displayMenu();
            cmd = SafeInput.getRegExString(in, "Please enter a command", "[AaIiDdMmOoSsCcQqVv]");

            try {
                switch (cmd.toUpperCase()) {
                    case "O":
                        if (fileNeedsSaved) promptToSave();
                        openFile();
                        break;
                    case "A":
                        addItem();
                        break;
                    case "I":
                        insertItem();
                        break;
                    case "D":
                        deleteItem();
                        break;
                    case "M":
                        moveItem();
                        break;
                    case "S":
                        saveFile();
                        break;
                    case "C":
                        clearList();
                        break;
                    case "V":
                        viewList();
                        break;
                    case "Q":
                        if (fileNeedsSaved) promptToSave();
                        System.out.println("Exiting program...");
                        running = false;
                        break;
                }
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        } while (running);
    }

    public static void displayMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("A - Add an item to the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("M - Move an item within the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list to disk");
        System.out.println("C - Clear the entire list");
        System.out.println("V - View the current list");
        System.out.println("Q - Quit the program");
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to add");
        list.add(item);
        fileNeedsSaved = true;
    }

    private static void insertItem() {
        if (list.isEmpty()) {
            System.out.println("List is empty. Try adding items first.");
            return;
        }

        int index = SafeInput.getRangedInt(in, "Enter index (0 to " + list.size() + ")", 0, list.size());
        String item = SafeInput.getNonZeroLenString(in, "Enter item to insert");
        list.add(index, item);
        fileNeedsSaved = true;
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("Nothing to delete - list is empty.");
            return;
        }

        int index = SafeInput.getRangedInt(in, "Enter index of item to delete (0 to " + (list.size() - 1) + ")", 0, list.size() - 1);
        list.remove(index);
        fileNeedsSaved = true;
    }

    private static void moveItem() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }

        int fromIndex = SafeInput.getRangedInt(in, "Enter index of item to move (0 to " + (list.size() - 1) + ")", 0, list.size() - 1);
        int toIndex = SafeInput.getRangedInt(in, "Enter index to move item to (0 to " + list.size() + ")", 0, list.size());

        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        fileNeedsSaved = true;
    }

    private static void openFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            currentFileName = selectedFile.getAbsolutePath();
            Path file = selectedFile.toPath();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new BufferedInputStream(Files.newInputStream(file, CREATE))))) {
                list.clear();
                String rec;
                while ((rec = reader.readLine()) != null) {
                    list.add(rec);
                }
                fileNeedsSaved = false;
                System.out.println("File loaded: " + currentFileName);
            }
        } else {
            System.out.println("File open canceled.");
        }
    }

    private static void saveFile() throws IOException {
        if (currentFileName.isEmpty()) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save List As...");
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                currentFileName = file.getAbsolutePath();
                if (!currentFileName.endsWith(".txt")) {
                    currentFileName += ".txt";
                }
            } else {
                System.out.println("Save canceled.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new BufferedOutputStream(Files.newOutputStream(Paths.get(currentFileName), CREATE))))) {
            for (String rec : list) {
                writer.write(rec);
                writer.newLine();
            }
            fileNeedsSaved = false;
            System.out.println("List saved to: " + currentFileName);
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list?")) {
            list.clear();
            fileNeedsSaved = true;
        }
    }

    private static void promptToSave() throws IOException {
        if (SafeInput.getYNConfirm(in, "You have unsaved changes. Would you like to save?")) {
            saveFile();
        }
    }
}
