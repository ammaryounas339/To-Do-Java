import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class ToDoAppGUI extends JFrame {
    private List<Task> taskList;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskJList;
    private JTextField taskInputField;
    private JComboBox<String> priorityComboBox;
    public ToDoAppGUI() {
        taskList = new ArrayList<>();
        taskListModel = new DefaultListModel<>();
        taskJList = new JList<>(taskListModel);
        // Create Swing components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(500, 400));
        taskJList.setFont(new Font("Sans-Serif", Font.BOLD, 16));
        taskInputField = new JTextField(20);
        priorityComboBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Done");
        JButton editButton = new JButton("Edit");

       // Load the images
        Image addImage = new ImageIcon("add.png").getImage();
        Image deleteImage = new ImageIcon("done.jpg").getImage();
        Image editImage = new ImageIcon("edit.png").getImage();

        // Define the desired dimensions for the resized images
        int buttonWidth = 32;
        int buttonHeight = 32;

        // Create scaled versions of the images
        Image scaledAddImage = addImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        Image scaledDeleteImage = deleteImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        Image scaledEditImage = editImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);

        // Create ImageIcon objects from the scaled images
        ImageIcon addIcon = new ImageIcon(scaledAddImage);
        ImageIcon deleteIcon = new ImageIcon(scaledDeleteImage);
        ImageIcon editIcon = new ImageIcon(scaledEditImage);

        addButton.setIcon(addIcon);
        deleteButton.setIcon(deleteIcon);
        editButton.setIcon(editIcon);
        // Set layout and add components to the main panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskInputField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskJList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up the main JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("To-Do App");
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTask();
            }
        });
    }

    private void addTask() {
        String taskDescription = taskInputField.getText().trim();
    try{
        if (!taskDescription.isEmpty()) {
            Priority priority = Priority.valueOf(priorityComboBox.getSelectedItem().toString().toUpperCase());
            if (priority != null) {
                Task newTask = new Task(taskDescription, priority);
                taskList.add(newTask);
                updateTaskList();
                taskInputField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a priority.", "Add Task",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task description.", "Add Task",
                    JOptionPane.WARNING_MESSAGE);
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    

    private void deleteTask() {
        int selectedIndex = taskJList.getSelectedIndex();
     try{
        if (selectedIndex >= 0) {
            Task selectedTask = taskList.get(selectedIndex);
            taskList.remove(selectedIndex);
            updateTaskList();
    
            String taskDescription = selectedTask.getDescription();
            JLabel messageLabel = new JLabel("<html><font color='red'>Congratulations</font> on completing the task:<br>" +
                "<font color='blue'>" + taskDescription + "</font></html>");
            messageLabel.setFont(new Font("Sans-Serif", Font.BOLD, 16));

            // Create a JOptionPane with the congratulatory message
            JOptionPane.showMessageDialog(this, messageLabel, "Task Completed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to be completed.", "Done",
                    JOptionPane.WARNING_MESSAGE);
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    private void editTask() {
        int selectedIndex = taskJList.getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                Task task = taskList.get(selectedIndex);
                String updatedDescription = JOptionPane.showInputDialog(this, "Edit Task", task.getDescription());
                if (updatedDescription != null && !updatedDescription.isEmpty()) {
                    task.setDescription(updatedDescription);
                    updateTaskList();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid task description.", "Edit Task", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to edit.", "Edit Task", JOptionPane.WARNING_MESSAGE);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateTaskList() {
        List<Task> highPriorityTasks = new ArrayList<>();
        List<Task> mediumPriorityTasks = new ArrayList<>();
        List<Task> lowPriorityTasks = new ArrayList<>();
        //Categorize tasks based on priority
        for (Task task : taskList) {
            Priority priority = task.getPriority();
            if (priority == Priority.HIGH) {
                    highPriorityTasks.add(task);
             } else if (priority == Priority.MEDIUM) {
                    mediumPriorityTasks.add(task);
             } else if (priority == Priority.LOW) {
                    lowPriorityTasks.add(task);
                }
            }
            taskListModel.clear();
    
        // Add tasks to the taskListModel in the desired order
        for (Task task : highPriorityTasks) {
            String elem =  with_color(task);
            taskListModel.addElement(elem);
            }
        for (Task task : mediumPriorityTasks) {
            String elem = with_color(task);
            taskListModel.addElement(elem);
            }
        for (Task task : lowPriorityTasks) {
            String elem = with_color(task);
            taskListModel.addElement(elem);
            }
        }
        
    public String with_color(Task task) {
        String taskDescription = task.getDescription();
        Priority priority = task.getPriority();
        String colorTag;
        // Set the color tag based on the priority
        if (priority == Priority.HIGH) {
            colorTag = "<font color='red'>";
        } else if (priority == Priority.MEDIUM) {
            colorTag = "<font color='blue'>";
        } else { // Low priority
            colorTag = "<font color='green'>";
        }
        // Create the HTML-formatted string with the color tag and task description
        String finalString = "<html>" + colorTag+"["+ priority.toString() + "] " + taskDescription + "</font></html>";

        return finalString;
      }

}



