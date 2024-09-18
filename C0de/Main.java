import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Random;

public class Main extends JPanel {

    private int[] array;
    private int arraySize = 60; // Default array size
    private final int DELAY = 20;
    private String currentAlgorithm = "Bubble Sort";
    private int currentIndex = -1; // To highlight the current element being sorted

    private JSlider arraySizeSlider; // Slider for adjusting array size
    private JLabel arraySizeLabel;   // Label to display current array size

    public Main() {
        generateArray();

        JFrame frame = new JFrame("Sorting Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Set dark background
        this.setBackground(Color.BLACK);

        frame.add(this, BorderLayout.CENTER);

        // Control panel for buttons and slider
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.DARK_GRAY);

        JButton startButton = new JButton("Start");
        JButton resetButton = new JButton("Reset");
        JComboBox<String> algorithmSelector = new JComboBox<>(new String[]{
                "Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"
        });

        startButton.setBackground(Color.LIGHT_GRAY);
        resetButton.setBackground(Color.LIGHT_GRAY);
        algorithmSelector.setBackground(Color.LIGHT_GRAY);

        buttonsPanel.add(startButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(algorithmSelector);

        // Array size slider and label
        arraySizeSlider = new JSlider(10, 200, arraySize); // Min: 10, Max: 200, Default: 60
        arraySizeLabel = new JLabel("Array Size: " + arraySize);

        // Set slider properties
        arraySizeSlider.setBackground(Color.DARK_GRAY);
        arraySizeSlider.setForeground(Color.WHITE);
        arraySizeSlider.setMajorTickSpacing(50);
        arraySizeSlider.setMinorTickSpacing(10);
        arraySizeSlider.setPaintTicks(true);
        arraySizeSlider.setPaintLabels(true);

        buttonsPanel.add(arraySizeLabel);
        buttonsPanel.add(arraySizeSlider);

        // Add action listeners for buttons and slider
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    switch (currentAlgorithm) {
                        case "Bubble Sort":
                            bubbleSort();
                            break;
                        case "Selection Sort":
                            selectionSort();
                            break;
                        case "Insertion Sort":
                            insertionSort();
                            break;
                        case "Merge Sort":
                            mergeSort(0, array.length - 1);
                            break;
                        case "Quick Sort":
                            quickSort(0, array.length - 1);
                            break;
                    }
                }).start();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateArray();
                repaint();
            }
        });

        algorithmSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentAlgorithm = (String) algorithmSelector.getSelectedItem();
            }
        });

        arraySizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                arraySize = arraySizeSlider.getValue(); // Update array size
                arraySizeLabel.setText("Array Size: " + arraySize);
                generateArray(); // Generate new array with the updated size
                repaint();
            }
        });

        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void generateArray() {
        array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(450) + 50;
        }
        currentIndex = -1; // Reset current index
    }

    // Drawing bars on the panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int barWidth = getWidth() / arraySize; // Adjust bar width according to array size
        for (int i = 0; i < array.length; i++) {
            int height = array[i];
            if (i == currentIndex) {
                g.setColor(Color.RED); // Highlight the current element being sorted
            } else {
                g.setColor(Color.CYAN); // Light color for other bars
            }
            g.fillRect(i * barWidth, getHeight() - height, barWidth, height);
        }
    }

    // Bubble Sort
    private void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                currentIndex = j; // Track the current index
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                }
                repaint();
                sleep();
            }
        }
        currentIndex = -1; // Reset current index after sorting
    }

    // Selection Sort
    private void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                currentIndex = j; // Track the current index
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(i, minIndex);
            }
            repaint();
            sleep();
        }
        currentIndex = -1; // Reset current index after sorting
    }

    // Insertion Sort
    private void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                currentIndex = j; // Track the current index
                array[j + 1] = array[j];
                j--;
                repaint();
                sleep();
            }
            array[j + 1] = key;
            repaint();
            sleep();
        }
        currentIndex = -1; // Reset current index after sorting
    }

    // Merge Sort
    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Sort first and second halves
            mergeSort(left, mid);
            mergeSort(mid + 1, right);

            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            currentIndex = k; // Track the current index
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            repaint();
            sleep();
            k++;
        }

        while (i < n1) {
            currentIndex = k; // Track the current index
            array[k] = L[i];
            i++;
            k++;
            repaint();
            sleep();
        }

        while (j < n2) {
            currentIndex = k; // Track the current index
            array[k] = R[j];
            j++;
            k++;
            repaint();
            sleep();
        }
        currentIndex = -1; // Reset current index after sorting
    }

    // Quick Sort
    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            currentIndex = j; // Track the current index
            if (array[j] < pivot) {
                i++;
                swap(i, j);
                repaint();
                sleep();
            }
        }
        swap(i + 1, high);
        repaint();
        sleep();
        return i + 1;
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}