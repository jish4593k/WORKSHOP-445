import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class PDFManager {

    public static void main(String[] args) {
     
        int chatId = 123;
        String pdfPath = createDirectoryPDF(chatId);
        System.out.println("Created PDF: " + pdfPath);

        int numPages = savePDF(pdfPath, "PDF_content".getBytes());
        System.out.println("Number of pages: " + numPages);

        String listedPDF = listPDF(chatId);
        System.out.println("Listed PDF: " + listedPDF);

        int numPDFs = countPDFs(chatId);
        System.out.println("Number of PDFs: " + numPDFs);

        deletePDF(pdfPath);
        deleteAllPDF(chatId);

        // Additional GUI part
        String selectedFile = openFileDialog();
        System.out.println("Selected PDF file: " + selectedFile);

        double[] torchTensor = generateTorchTensor();
        System.out.print("Torch Tensor: ");
        for (double value : torchTensor) {
            System.out.print(value + " ");
        }

        displaySeabornPlot();
    }

    private static String createDirectoryPDF(int chatId) {
        String randomToken = generateRandomToken(15);
        String pdfName = chatId + "_" + randomToken + ".pdf";
        String directoryPath = ".//" + chatId;

        File directory = new File(directoryPath);
        if (directory.exists()) {
            return new File(directory, pdfName).getPath();
        } else {
            directory.mkdir();
            return new File(directory, pdfName).getPath();
        }
    }

    private static int savePDF(String pdfPath, byte[] content) {
        try (FileOutputStream newPDF = new FileOutputStream(pdfPath)) {
            newPDF.write(content);
            int numPages = Objects.requireNonNull(PdfFileReader.getInstance(new File(pdfPath))).getNumPages();
            deletePDF(pdfPath);
            return numPages;
        } catch (Exception e) {
            System.out.println("Error with the PDF: " + e.getMessage());
            new File(pdfPath).delete();
            return 0;
        }
    }

    private static void deletePDF(String pdfPath) {
        new File(pdfPath).delete();
    }

    private static int countPDFs(int chatId) {
        String path = "./Fs/" + chatId;
        File directory = new File(path);
        File[] pdfFiles = directory.listFiles((dir, name) -> name.endsWith(".pdf"));
        return pdfFiles != null ? pdfFiles.length : 0;
    }

    private static void deleteAllPDF(int chatId) {
        String path = ".//" + chatId;
        File directory = new File(path);
        File[] pdfFiles = directory.listFiles((dir, name) -> name.endsWith(".pdf"));
        if (pdfFiles != null) {
            for (File file : pdfFiles) {
                file.delete();
            }
        }
        directory.delete();
    }

    private static String listPDF(int chatId) {
        String path = "./" + chatId + "";
        File directory = new File(path);
        File[] pdfFiles = directory.listFiles((dir, name) -> name.endsWith(".pdf"));
        if (pdfFiles != null && pdfFiles.length > 0) {
            return pdfFiles[0].getPath();
        }
        return "";
    }

    private static String generateRandomToken(int length) {
        byte[] randomBytes = new byte[length];
        new Random().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private static String openFileDialog() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the selected PDF file path: ");
        return scanner.nextLine();
    }

    private static double[] generateTorchTensor() {
        double[] torchTensor = new double[100];
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            torchTensor[i] = random.nextDouble();
        }
        return torchTensor;
    }

    
