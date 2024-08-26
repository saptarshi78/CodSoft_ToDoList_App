package com.example.to_do_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_do_list.ui.theme.To_do_listTheme

// Define the Task1 class here
data class Task1(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            To_do_listTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToDoListScreen()
                }
            }
        }
    }
}
@Composable
fun ToDoListScreen() {
    var tasks by remember { mutableStateOf(listOf<Task1>()) }
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var editingTaskIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Heading for the to-do list with underline
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "To Do List",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 2.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for adding or editing tasks
        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (taskTitle.isNotBlank()) {
                    if (editingTaskIndex != null) {
                        // Update existing task
                        tasks = tasks.toMutableList().apply {
                            this[editingTaskIndex!!] = Task1(title = taskTitle, description = taskDescription, isCompleted = this[editingTaskIndex!!].isCompleted)
                        }
                        editingTaskIndex = null
                    } else {
                        // Add new task
                        tasks = tasks + Task1(title = taskTitle, description = taskDescription)
                    }
                    taskTitle = ""
                    taskDescription = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (editingTaskIndex != null) "Save Changes" else "Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display tasks
        tasks.forEachIndexed { index, task ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Title: ${task.title}")
                    Text("Description: ${task.description}")
                }

                Row {
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = {
                            // Update task completion status
                            tasks = tasks.toMutableList().apply {
                                this[index] = task.copy(isCompleted = it)
                            }
                        }
                    )
                    Text(if (task.isCompleted) "Completed" else "Active")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = {
                        tasks = tasks.toMutableList().apply { removeAt(index) }
                    }
                ) {
                    Text("Delete")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        taskTitle = task.title
                        taskDescription = task.description
                        editingTaskIndex = index
                    }
                ) {
                    Text("Edit")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f)) // Push footer to the bottom

        // Footer with "Created by Saptarshi"
        Text(
            text = "Created by Saptarshi",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}
