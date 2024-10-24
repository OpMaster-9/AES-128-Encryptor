import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private static String file;
    private static String directory;
    public static void GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Encrypter");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLayout(null);
        frame.setSize(800, 550);
        frame.getContentPane().setBackground(new Color(50, 50, 50));

        Font font = new Font("Arial", Font.PLAIN, 40);

        JTextField fileName = new JTextField();
        fileName.setVisible(true);
        fileName.setBounds(25, 25, 725, 50);
        fileName.setBackground(new Color(100, 100, 100));
        fileName.setForeground(new Color(200, 200, 200));
        fileName.setBorder(null);
        fileName.setFont(font);
        fileName.setEditable(false);
        fileName.setFocusable(false);
        fileName.setText("No File selected");
        frame.add(fileName);

        JButton selectFile = new JButton();
        selectFile.setBounds(25, 100,725,50);
        selectFile.setBackground(new Color(100, 100, 100));
        selectFile.setForeground(new Color(200, 200, 200));
        selectFile.setVisible(true);
        selectFile.setBorder(null);
        selectFile.setFont(font);
        selectFile.setFocusable(false);
        selectFile.setText("Select File");
        selectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fileDialog = new FileDialog(frame, "Select a File", FileDialog.LOAD);
                fileDialog.setVisible(true);
                directory = fileDialog.getDirectory();
                file = fileDialog.getFile();
                if (file != null) {
                    fileName.setText(file);
                } else {
                    fileName.setText("No File Selected");
                }
            }
        });
        frame.add(selectFile);

        JTextField key = new JTextField();
        key.setVisible(true);
        key.setBounds(25, 175, 725, 225);
        key.setBackground(new Color(100, 100, 100));
        key.setForeground(new Color(200, 200, 200));
        key.setBorder(null);
        key.setFont(font);
        key.setText("Key here");
        frame.add(key);

        JButton encrypt = new JButton();
        encrypt.setBounds(25, 425,350,75);
        encrypt.setBackground(new Color(100, 100, 100));
        encrypt.setForeground(new Color(200, 200, 200));
        encrypt.setVisible(true);
        encrypt.setBorder(null);
        encrypt.setFont(font);
        encrypt.setFocusable(false);
        encrypt.setText("Encrypt");
        encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String originalText = FileTool.readFileToString(directory + file);
                String encryptedText;
                try {
                    encryptedText = Encryption.encrypt(originalText,key.getText());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                FileTool.writeStringToFile(directory+file,encryptedText);
            }
        });
        frame.add(encrypt);

        JButton decrypt = new JButton();
        decrypt.setBounds(400, 425,350,75);
        decrypt.setBackground(new Color(100, 100, 100));
        decrypt.setForeground(new Color(200, 200, 200));
        decrypt.setVisible(true);
        decrypt.setBorder(null);
        decrypt.setFont(font);
        decrypt.setFocusable(false);
        decrypt.setText("Decrypt");
        decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedText = FileTool.readFileToString(directory+file);
                String decryptedText;
                try {
                    decryptedText = Encryption.decrypt(encryptedText,key.getText());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                FileTool.writeStringToFile(directory+file,decryptedText);
            }
        });
        frame.add(decrypt);

        frame.setVisible(true);
    }
}
