import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Sort extends JPanel{
    int[] array;
    int arraySize = 60;
    private final int delay = 20;
    String CurrAlgo = "Bubble Sort";
    int currIndex = -1;
    
    private JSlider slider;
    private JLabel label;
    private JTextArea info;
    private String PseudoCode;

public void genArray(){
    array = new int[arraySize];
    Random r = new Random();
    for (int i = 0 ; i < arraySize ; i++) {
        array[i] = r.nextInt(500)+50;    
    }
    currIndex = -1 ;
}


public void PseudoCode(){
    switch (CurrAlgo) {
        case "Bubble Sort":
        PseudoCode ="Bubble Sort Pseudo Code:\n" +
        "1. Compare each pair of adjacent elements.\n"+
        "2. Swap if they are in the wrong order.\n"+
        "3. Repeat until no swaps are needed.\n";
        break;
        case "Insertion Sort":

        PseudoCode = "Insertion Sort Pseudo Code"+
        "1. Pick the next element.\n"+
                "2. Compare it with the previous elements.\n"+
                "3. Insert it into its correct position.\n";
        break;    
        case "Selection Sort":
        PseudoCode ="Selection Sort Pseudo Code:\n" +
                "1. Find the minimum element in the unsorted portion.\n"+
                "2. Swap it with the first unsorted element.\n"+
                "3. Move the boundary of sorted and unsorted portions";
        break;    
        case "Merge Sort":
        PseudoCode = "Merge Sort Pseudo Code\n"+
        "1. Divide the array into two halves.\n"+
        "2. Sort each half.\n"+
        "3. Merge the sorted halves.\n";
        break;    
        case "Quick Sort":
        PseudoCode ="Quick Sort Pseudo Code\n"+
        "1. Choose a pivot element.\n"+
                "2. Partition the array around the pivot.\n"+
                "3. Recursively apply Quick Sort to the sub-arrays\n.";
        break;   
        default:
        PseudoCode = "Select a Algorithm";
       
    }
}


protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int bar = getWidth() / arraySize; // Adjust bar width according to array size
    for (int i = 0; i < array.length; i++) {
        int h = array[i];
        if (i == currIndex) {
            g.setColor(Color.RED); // Highlight the current element being sorted
        } else {
            g.setColor(Color.CYAN); // Light color for other bars
        }
        g.fillRect(i * bar, getHeight() - h, bar , h);
    }
}

public void eroor(){
    try {
        Thread.sleep(delay);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
  
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

      // Bubble Sort
    private void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                currIndex = j; // Track the current index
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                }
                repaint();
                eroor();
            }
        }
        currIndex = -1; // Reset current index after sorting
    }

    // Selection Sort
    private void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                currIndex = j; // Track the current index
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(i, minIndex);
            }
            repaint();
            eroor();
        }
        currIndex = -1; // Reset current index after sorting
    }

    // Insertion Sort
    private void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                currIndex = j; // Track the current index
                array[j + 1] = array[j];
                j--;
                repaint();
                eroor();
            }
            array[j + 1] = key;
            repaint();
            eroor();
        }
        currIndex = -1; // Reset current index after sorting
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
            currIndex = k; // Track the current index
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            repaint();
            eroor();
            k++;
        }

        while (i < n1) {
            currIndex = k; // Track the current index
            array[k] = L[i];
            i++;
            k++;
            repaint();
            eroor();
        }

        while (j < n2) {
            currIndex = k; // Track the current index
            array[k] = R[j];
            j++;
            k++;
            repaint();
            eroor();
        }
        currIndex = -1; // Reset current index after sorting
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
            currIndex = j; // Track the current index
            if (array[j] < pivot) {
                i++;
                swap(i, j);
                repaint();
                eroor();
            }
        }
        swap(i + 1, high);
        repaint();
        eroor();
        return i + 1;
    }

public Sort(){

    genArray();
    JFrame frame = new JFrame("Sorting Visualizer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 600);
    this.setBackground(Color.BLACK);
    frame.add(this , BorderLayout.CENTER);
    JPanel btnPanel = new JPanel();
    btnPanel.setBackground(Color.DARK_GRAY);
    JButton start  = new JButton("Start");
    start.setBackground(Color.LIGHT_GRAY);
    JButton reset  = new JButton("Reset");
    reset.setBackground(Color.LIGHT_GRAY);
    JComboBox<String> algoSlec = new JComboBox<>(new String[]{
        "Bubble Sort","Insertion Sort" , "Selection Sort" , "Merge Sort" , "Quick Sort"
    }); 
    algoSlec.setBackground(Color.LIGHT_GRAY);

    btnPanel.add(start);
    btnPanel.add(reset);
    btnPanel.add(algoSlec);
    slider = new JSlider(10, 200, arraySize); // Min: 10, Max: 200, Default: 60
    label = new JLabel("Array Size: " + arraySize);

    // Set slider properties
    slider.setBackground(Color.DARK_GRAY);
    slider.setForeground(Color.WHITE);
    slider.setMajorTickSpacing(50);
    slider.setMinorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    btnPanel.add(slider);
    btnPanel.add(label);
    //Info
    info = new JTextArea(10,30);
    info.setBackground(Color.DARK_GRAY);
    info.setForeground(Color.WHITE);
    info.setFont(new Font("Monospace" , Font.PLAIN , 12));
    JScrollPane scroll = new JScrollPane(info);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    //for input

           start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    info.setText("Sorting using: " + CurrAlgo + "\n" + PseudoCode + "\n");
                    switch (CurrAlgo) {
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
                    info.append("Sorting completed!\n");
                }).start();
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                genArray();
                repaint();
                info.setText("Array reset. Choose a sorting algorithm and start.\n");
            }
        });

        algoSlec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrAlgo = (String) algoSlec.getSelectedItem();
                PseudoCode();
                info.setText("Current Algorithm: " + CurrAlgo + "\n" + PseudoCode + "\n");
            }
        });

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                arraySize = slider.getValue(); // Update array size
                label.setText("Array Size: " + arraySize);
                genArray(); // Generate new array with the updated size
                repaint();
                info.setText("Array size updated to: " + arraySize + "\n");
            }
        });

        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.add(scroll, BorderLayout.EAST); // Add scroll pane for info area
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sort::new );
    }
}
