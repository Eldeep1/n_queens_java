/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package NQueensss;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author MeoW
 */
public class NQueensss extends JFrame {

    public List<JPanel> chessboardPanels;

    public static int n;                 // Number of chessboards
    int cellSize = 50;          // Size of each cell in the chessboard
    int separatorSize = 10;     // Size of the separator between chessboards
    int chessboardSize = n * cellSize;
    private JPanel topPanel;  // Declare topPanel as an instance variable

    public NQueensss() {
        this.n = 4; // Assign the value of n

        // Calculate chessboardSize after n is assigned
        this.chessboardSize = n * cellSize;

        setTitle("Chessboards");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("Enter the board size: ");
        JTextField boardSizeField = new JTextField(4); // 5 columns for the text field
        boardSizeField.setText(Integer.toString(n)); // Set the default board size
        JButton startButton = new JButton("Start");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the value of n from the text field
                try {
                    n = Integer.parseInt(boardSizeField.getText());
                    System.out.println(n);

                } catch (NumberFormatException ex) {
                    System.out.println(boardSizeField.getText());
                    System.out.println("Invalid input. Please enter a valid integer.");
                    return;
                }

                // Check if n is a positive integer
                if (n <= 0) {
                    System.out.println(boardSizeField.getText());
                    System.out.println(n);
                    System.out.println("Invalid board size. Please use a positive integer.");
                    return;
                }

                updateChessboardInBackground();
            }
        });
        
        topPanel.add(label);
        topPanel.add(boardSizeField);
        topPanel.add(startButton);

        chessboardSize = n * cellSize;

        chessboardPanels = createChessboardPanels();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < chessboardPanels.size(); i++) {
            mainPanel.add(createSeparator());

            JPanel chessboardContainer = new JPanel();  // Create a container panel
            chessboardContainer.setPreferredSize(new Dimension(chessboardSize, chessboardSize));
            chessboardContainer.add(chessboardPanels.get(i));  // Add chessboard panel to container
            mainPanel.add(chessboardContainer);

//        if (i < chessboardPanels.size() - 1) {
            mainPanel.add(createSeparator());
//        }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getViewport().setPreferredSize(new Dimension(800, 800));
        setContentPane(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<JPanel> createChessboardPanels() {
        List<JPanel> panels = new ArrayList<>(n * n);
        Color lightSquareColor = new Color(255, 223, 186);
        Color darkSquareColor = new Color(160, 82, 45);

        for (int i = 0; i < n; i++) {
            JPanel chessboardPanel = new JPanel(new GridLayout(n, n));

            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    JPanel square = new JPanel();
                    square.setBackground((row + col) % 2 == 0 ? lightSquareColor : darkSquareColor);
                    square.setPreferredSize(new Dimension(cellSize, cellSize));
                    chessboardPanel.add(square);
                }
            }

            panels.add(chessboardPanel);

        }
        return panels;
    }

    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(separatorSize, separatorSize));
        return separator;
    }

    private void removeElementFromChessboard(int i, int x, int y) {
        if (i >= 0 && i < chessboardPanels.size()) {
            JPanel chessboardPanel = chessboardPanels.get(i);
            Component component = chessboardPanel.getComponent(y * n + x);
//try {
//    // Sleep for 1 second
//    TimeUnit.SECONDS.sleep(5);
//} catch (InterruptedException e) {
//    e.printStackTrace();
//}
            if (component instanceof JPanel) {
                JPanel square = (JPanel) component;
                square.removeAll(); // Clear existing components
                square.revalidate(); // Refresh the panel
                square.repaint(); // Repaint the panel
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void placeXOnChessboard(int i, int x, int y) {
        if (i >= 0 && i < chessboardPanels.size()) {
            JPanel chessboardPanel = chessboardPanels.get(i);
            Component component = chessboardPanel.getComponent(y * n + x);

            if (component instanceof JPanel) {
                JPanel square = (JPanel) component;
                square.removeAll(); // Clear existing components
                square.add(createYourElement()); // Add 'X' or your custom element

                square.revalidate(); // Refresh the panel
                square.repaint(); // Repaint the panel
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JPanel createYourElement() {
        JPanel elementPanel = new JPanel();

        URL url = NQueensss.class.getResource("queen.png");
        // Load the image
        ImageIcon originalIcon = new ImageIcon(url); // Replace with the actual path to your image

        // Check if the image is loaded successfully
        if (originalIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // Scale the image to the desired size
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

            // Create a new ImageIcon with the scaled image
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Create a JLabel with the scaled ImageIcon
            JLabel label = new JLabel(scaledIcon);

            // Set layout to FlowLayout for more control
            elementPanel.setLayout(new FlowLayout());

            // Add the JLabel with the scaled image to the panel
            elementPanel.add(label);

            // Set preferred size to match the image size
            elementPanel.setPreferredSize(new Dimension(100, 100));

            // Make the panel non-opaque to allow the background to show through
            elementPanel.setOpaque(false);
        } else {
            // Handle the case when the image cannot be loaded
            System.out.println("Failed to load the image");
        }

        return elementPanel;
    }
    
    private void updateChessboard() {
        chessboardSize = n * cellSize;

        chessboardPanels = createChessboardPanels();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < chessboardPanels.size(); i++) {
            mainPanel.add(createSeparator());

            JPanel chessboardContainer = new JPanel();  // Create a container panel
            chessboardContainer.setPreferredSize(new Dimension(chessboardSize, chessboardSize));
            chessboardContainer.add(chessboardPanels.get(i));  // Add chessboard panel to container
            mainPanel.add(chessboardContainer);

            mainPanel.add(createSeparator());
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getViewport().setPreferredSize(new Dimension(800, 800));
        setContentPane(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    
    private static boolean isSafe(int[][] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }

        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        for (int i = row, j = col; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

 
    private  boolean solver(int[][] board, int row, int col, int whichOne) {

        if (row >= n) {
            return true;
        }

        if (row == 0) {
            if (board[row][col] == 0) {
                board[row][col] = 1;
//                        System.out.println("Queen placed at row " + row + ", column " + col);
                       placeXOnChessboard(whichOne, col, row);

                if (solver(board, row + 1, col, whichOne)) {
                    return true;
                }
                board[row][col] = 0;
//                        System.out.println("Queen removed from row " + row + ", column " + col);
               removeElementFromChessboard(whichOne, col, row);

            }
        }

        if (n > row && row > 0) {
            for (int i = 0; i < n; i++) {
                if (isSafe(board, row, i)) {
                    board[row][i] = 1;
//                    System.out.println("Queen placed at row " + row + ", column " + i);
if(i<n){
                    try {
                            placeXOnChessboard(whichOne, i, row);

    } catch (Exception e) {
                     
                        
    }
    
}

                    if (solver(board, row + 1, i, whichOne)) {
                        return true;
                    }
                    board[row][i] = 0;
//                    System.out.println("Queen removed from row " + row + ", column " + i);
                    removeElementFromChessboard(whichOne, i, row);

                }
            }
        }

        return false;
    }

    
     private void updateChessboardInBackground() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                updateChessboard();
                
                
    Thread[] threads = new Thread[n];

    for (int i = 0; i < n; i++) {
        final int boardSize = n;
        final int columnIndex = i;

        threads[i] = new Thread(() -> solveNQ(boardSize, columnIndex));
        threads[i].start();
    }

    try {
        for (Thread thread : threads) {
            thread.join(); // Wait for all threads to finish

        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }


                return null;
            }

            @Override
            protected void done() {
                // You can add any additional post-processing here if needed
            }
        };

        worker.execute();
    }
     
    public  void solveNQ(int n, int whichOne) {
        
        int[][] board = new int[n][n];

    
            for (int i = 0; i < n; i++) {
                int[] row = new int[n];
                board[i] = row;
            }
            solver(board, 0, whichOne, whichOne);
//                System.out.println("Solution found:");
                // Assuming number_of_trials_back_track is a variable defined elsewhere
                // number_of_trials_back_track = number_of_trials[0];
                return;
    }
    
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NQueensss().setVisible(true);
            }
        });
    }

}
