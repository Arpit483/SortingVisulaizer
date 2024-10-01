import tkinter as tk
from tkinter import ttk
import random

class SortingVisualizer:
    def __init__(self, root):
        self.root = root
        self.root.title("Sorting Algorithm Visualizer")
        self.array = []
        self.canvas = tk.Canvas(self.root, width=600, height=400, bg="black")
        self.canvas.pack()

        control_frame = tk.Frame(self.root)
        control_frame.pack()

        self.size_slider = tk.Scale(control_frame, from_=10, to=100, label="Array Size", orient="horizontal")
        self.size_slider.pack(side="left")

        self.speed_slider = tk.Scale(control_frame, from_=1, to=10, label="Speed", orient="horizontal")
        self.speed_slider.pack(side="left")

        self.generate_button = tk.Button(control_frame, text="Generate Array", command=self.generate_array)
        self.generate_button.pack(side="left")

        self.sort_button = tk.Button(control_frame, text="Start Sorting", command=self.start_sorting)
        self.sort_button.pack(side="left")

        self.algo_combobox = ttk.Combobox(control_frame, values=["Bubble Sort", "Quick Sort", "Merge Sort", "Selection Sort", "Insertion Sort"])
        self.algo_combobox.set("Bubble Sort")
        self.algo_combobox.pack(side="left")

        self.info_box = tk.Text(self.root, height=10, width=50)
        self.info_box.pack(pady=10)

    def generate_array(self):
        self.array = [random.randint(1, 100) for _ in range(self.size_slider.get())]
        self.draw_array(self.array, ['red' for _ in range(len(self.array))])

    def draw_array(self, array, color_array):
        self.canvas.delete("all")
        c_height = 400
        c_width = 600
        bar_width = c_width / (len(array) + 1)
        offset = 30
        spacing = 10

        for i, value in enumerate(array):
            x0 = i * bar_width + offset + spacing
            y0 = c_height - value * 3
            x1 = (i + 1) * bar_width + offset
            y1 = c_height
            self.canvas.create_rectangle(x0, y0, x1, y1, fill=color_array[i])

        self.root.update_idletasks()

    def start_sorting(self):
        selected_algo = self.algo_combobox.get()
        self.display_algo_info(selected_algo)

        if selected_algo == "Bubble Sort":
            self.animate(self.bubble_sort())
        elif selected_algo == "Quick Sort":
            self.animate(self.quick_sort(0, len(self.array) - 1))
        elif selected_algo == "Merge Sort":
            self.animate(self.merge_sort(0, len(self.array) - 1))
        elif selected_algo == "Selection Sort":
            self.animate(self.selection_sort())
        elif selected_algo == "Insertion Sort":
            self.animate(self.insertion_sort())

    def animate(self, generator):
        try:
            next(generator)
            self.root.after(int(1000 / self.speed_slider.get()), lambda: self.animate(generator))
        except StopIteration:
            pass

    def bubble_sort(self):
        array = self.array
        for i in range(len(array) - 1):
            for j in range(len(array) - i - 1):
                if array[j] > array[j + 1]:
                    array[j], array[j + 1] = array[j + 1], array[j]
                    self.draw_array(array, ['green' if x == j or x == j + 1 else 'red' for x in range(len(array))])
                    yield

    def quick_sort(self, low, high):
        array = self.array
        if low < high:
            # Partition the array and get the pivot index
            pi = yield from self.partition(low, high)
            
            # Recursively apply quick sort to the left of the pivot
            yield from self.quick_sort(low, pi - 1)
            
            # Recursively apply quick sort to the right of the pivot
            yield from self.quick_sort(pi + 1, high)

    def partition(self, low, high):
        array = self.array
        pivot = array[high]  # Pivot is the last element
        i = low - 1  # Index of the smaller element
        
        for j in range(low, high):
            if array[j] < pivot:
                i += 1
                # Swap elements
                array[i], array[j] = array[j], array[i]
                self.draw_array(array, ['yellow' if x == i or x == j else 'red' for x in range(len(array))])
                yield
        
        # Swap the pivot element with the element at i + 1
        array[i + 1], array[high] = array[high], array[i + 1]
        self.draw_array(array, ['yellow' if x == i + 1 else 'red' for x in range(len(array))])
        yield
        return i + 1  # Return the partition index

    def merge_sort(self, left, right):
        if left < right:
            middle = (left + right) // 2
            yield from self.merge_sort(left, middle)
            yield from self.merge_sort(middle + 1, right)
            yield from self.merge(left, middle, right)

    def merge(self, left, middle, right):
        array = self.array
        left_copy = array[left:middle + 1]
        right_copy = array[middle + 1:right + 1]
        left_idx, right_idx = 0, 0

        for array_idx in range(left, right + 1):
            if left_idx < len(left_copy) and (right_idx >= len(right_copy) or left_copy[left_idx] <= right_copy[right_idx]):
                array[array_idx] = left_copy[left_idx]
                left_idx += 1
            else:
                array[array_idx] = right_copy[right_idx]
                right_idx += 1
            self.draw_array(array, ['purple' if x == array_idx else 'red' for x in range(len(array))])
            yield

    def selection_sort(self):
        array = self.array
        for i in range(len(array)):
            min_idx = i
            for j in range(i + 1, len(array)):
                if array[j] < array[min_idx]:
                    min_idx = j
                self.draw_array(array, ['green' if x == min_idx or x == j else 'red' for x in range(len(array))])
                yield
            array[i], array[min_idx] = array[min_idx], array[i]
            self.draw_array(array, ['green' if x <= i else 'red' for x in range(len(array))])
            yield

    def insertion_sort(self):
        array = self.array
        for i in range(1, len(array)):
            key = array[i]
            j = i - 1
            while j >= 0 and key < array[j]:
                array[j + 1] = array[j]
                j -= 1
                self.draw_array(array, ['green' if x == j or x == j + 1 else 'red' for x in range(len(array))])
                yield
            array[j + 1] = key
            self.draw_array(array, ['green' if x <= i else 'red' for x in range(len(array))])
            yield

    def display_algo_info(self, algo_name):
        self.info_box.delete(1.0, tk.END)
        complexities = {
            "Bubble Sort": "O(n^2)",
            "Quick Sort": "O(n log n) on average, O(n^2) worst-case",
            "Merge Sort": "O(n log n)",
            "Selection Sort": "O(n^2)",
            "Insertion Sort": "O(n^2) average, O(n) best case"
        }
        if algo_name == "Bubble Sort":
            pseudo_code = """Bubble Sort:
1. Compare each pair of adjacent elements.
2. Swap if they are in the wrong order.
3. Repeat until no swaps are needed."""
        elif algo_name == "Quick Sort":
            pseudo_code = """Quick Sort:
1. Choose a pivot element.
2. Partition the array around the pivot.
3. Recursively apply Quick Sort to the sub-arrays."""
        elif algo_name == "Merge Sort":
            pseudo_code = """Merge Sort:
1. Divide the array into two halves.
2. Sort each half.
3. Merge the sorted halves."""
        elif algo_name == "Selection Sort":
            pseudo_code = """Selection Sort:
1. Find the minimum element in the unsorted portion.
2. Swap it with the first unsorted element.
3. Move the boundary of sorted and unsorted portions."""
        elif algo_name == "Insertion Sort":
            pseudo_code = """Insertion Sort:
1. Pick the next element.
2. Compare it with the previous elements.
3. Insert it into its correct position."""

        self.info_box.insert(tk.END, f"Algorithm: {algo_name}\nComplexity: {complexities[algo_name]}\n\n{pseudo_code}")

root = tk.Tk()
visualizer = SortingVisualizer(root)
root.mainloop()
